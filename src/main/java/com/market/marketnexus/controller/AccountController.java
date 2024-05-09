package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.OrderService;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + APIPrefixes.ACCOUNT)
public class AccountController {

   public final static String UPDATE_SUCCESSFUL = "redirect:/" + APIPrefixes.ACCOUNT + "?accountUpdatedSuccessful=true#update-account-form";
   public final static String UPDATE_ERROR = "/" + APIPrefixes.ACCOUNT + ".html";
   public final static String ACCOUNT_DELETED_SUCCESSFULLY = "redirect:/logout";
   public final static String ACCOUNT_DELETED = APIPrefixes.ACCOUNT + ".html";
   private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private SaleService saleService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;
   @Autowired
   private StatsController statsController;
   @Autowired
   private UserValidator userValidator;
   @Autowired
   private CredentialsValidator credentialsValidator;

   @GetMapping(value = {"", "/"})
   public ModelAndView showUserAccount(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.ACCOUNT + GlobalValues.TEMPLATES_EXTENSION);
      if (loggedUser != null) {
         Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
         Set<Sale> soldSaleProducts = this.saleService.getAllUserSoldSales(loggedUser);
         Set<Sale> orderedProducts = this.orderService.getUserOrderedSales(loggedUser);
         modelAndView.addObject("user", loggedUser);
         modelAndView.addObject("credentials", loggedUser.getCredentials());
         modelAndView.addObject("saleProducts", saleProducts);
         modelAndView.addObject("soldSaleProducts", soldSaleProducts);
         modelAndView.addObject("orderedProducts", orderedProducts);
         modelAndView.addObject("tableData", this.statsController.getTableData());
      }
      return modelAndView;
   }

   @GetMapping(value = {"/{username}", "/{username}/"})
   public ModelAndView showUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NonNull User loggedUser, @PathVariable("username") String username) {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.ACCOUNT + GlobalValues.TEMPLATES_EXTENSION);
      try {
         Credentials credentials = this.credentialsService.getCredentials(username);
         User user = this.userService.getUser(credentials);
         modelAndView.addObject("user", user);
         Set<Sale> saleProducts = this.saleService.getAllSalesByUser(user);
         Set<Sale> soldSaleProducts = this.saleService.getAllUserSoldSales(user);
         Set<Sale> orderedProducts = this.orderService.getUserOrderedSales(user);
         modelAndView.addObject("saleProducts", saleProducts);
         modelAndView.addObject("soldSaleProducts", soldSaleProducts);
         modelAndView.addObject("orderedProducts", orderedProducts);
         modelAndView.addObject("credentials", user.getCredentials());
         if (user.equals(loggedUser)) {
            modelAndView.addObject("tableData", this.statsController.getTableData());
         }
      } catch (UserCredentialsUsernameNotExistsException userCredentialsUsernameNotExistsException) {
         LOGGER.error(userCredentialsUsernameNotExistsException.getMessage());
      } finally {
         modelAndView.addObject("searchedUsername", username);
      }
      return modelAndView;
   }

   @PostMapping(value = {"/updateAccount", "/updateAccount/"})
   public ModelAndView updateUserAccount(
           @ModelAttribute("loggedUser") User loggedUser,
           @Valid @NonNull @ModelAttribute("user") User user,
           @NonNull BindingResult userBindingResult,
           @Valid @NonNull @ModelAttribute("credentials") Credentials credentials,
           @NonNull BindingResult credentialsBindingResult,
           @RequestParam("confirm-password") String confirmPassword
   ) {
      ModelAndView modelAndView = new ModelAndView(AccountController.UPDATE_ERROR);
      this.userValidator.setAccountUpdate(true);
      this.credentialsValidator.setAccountUpdate(true);
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         modelAndView.setViewName(AccountController.UPDATE_SUCCESSFUL);
         if (TypeValidators.validateString(confirmPassword)) {
            Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);
         }
         user.setCredentials(credentials);
         User updatedUser = this.userService.updateUser(loggedUser.getId(), user);
         Utils.updateUserCredentialsAuthentication(updatedUser.getCredentials());
         LOGGER.info("Updated account with User ID: {}", updatedUser.getId());
      } else {
         Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
         Set<Sale> soldSaleProducts = this.saleService.getAllUserSoldSales(loggedUser);
         Set<Sale> orderedProducts = this.orderService.getUserOrderedSales(loggedUser);
         modelAndView.addObject("user", user);
         credentials.setInsertedAt(loggedUser.getCredentials().getInsertedAt());
         credentials.setUpdatedAt(loggedUser.getCredentials().getUpdatedAt());
         modelAndView.addObject("credentials", credentials);
         modelAndView.addObject("saleProducts", saleProducts);
         modelAndView.addObject("soldSaleProducts", soldSaleProducts);
         modelAndView.addObject("orderedProducts", orderedProducts);
         modelAndView.addObject("tableData", this.statsController.getTableData());
         List<ObjectError> userGlobalErrors = userBindingResult.getGlobalErrors();
         List<ObjectError> credentialsGlobalErrors = credentialsBindingResult.getGlobalErrors();
         for (ObjectError userGlobalError : userGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(userGlobalError.getCode()), userGlobalError.getDefaultMessage());
         }
         for (ObjectError credentialGlobalErrors : credentialsGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(credentialGlobalErrors.getCode()), credentialGlobalErrors.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/delete", "/delete/"})
   public ModelAndView deleteUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NonNull User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(AccountController.ACCOUNT_DELETED);
      if (this.userService.deleteUser(loggedUser)) {
         modelAndView.setViewName(AccountController.ACCOUNT_DELETED_SUCCESSFULLY);
         LOGGER.info("Deleted account with User ID: {}", loggedUser.getId());
      } else {
         modelAndView.addObject("accountNotDeletedError", true);
      }
      return modelAndView;
   }
}
