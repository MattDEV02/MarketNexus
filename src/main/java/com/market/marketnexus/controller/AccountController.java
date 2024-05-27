package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
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

@Controller
@RequestMapping(value = "/" + APIPrefixes.ACCOUNT)
public class AccountController {

   public final static String UPDATE_SUCCESSFUL_VIEW = "redirect:/" + APIPrefixes.ACCOUNT + "?accountUpdatedSuccessful=true#update-account-form";
   public final static String UPDATE_ERROR_VIEW = "/" + APIPrefixes.ACCOUNT + ".html";
   public final static String DELETE_SUCCESSFUL_VIEW = "redirect:/logout";
   public final static String DELETE_ERROR_VIEWD = APIPrefixes.ACCOUNT + ".html";
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
         Iterable<Sale> sales = this.saleService.getAllSalesByUser(loggedUser);
         Iterable<Sale> soldSales = this.saleService.getAllUserSoldSales(loggedUser);
         Iterable<Sale> orderedSales = this.orderService.getUserOrderedSales(loggedUser);
         modelAndView.addObject("user", loggedUser);
         modelAndView.addObject("credentials", loggedUser.getCredentials());
         modelAndView.addObject("sales", sales);
         modelAndView.addObject("soldSale", soldSales);
         modelAndView.addObject("orderedSales", orderedSales);
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
         Iterable<Sale> sales = this.saleService.getAllSalesByUser(user);
         Iterable<Sale> soldSales = this.saleService.getAllUserSoldSales(user);
         Iterable<Sale> orderedSales = this.orderService.getUserOrderedSales(user);
         modelAndView.addObject("user", user);
         modelAndView.addObject("credentials", user.getCredentials());
         modelAndView.addObject("sales", sales);
         modelAndView.addObject("soldSale", soldSales);
         modelAndView.addObject("orderedSales", orderedSales);
         if (user.equals(loggedUser)) {
            modelAndView.addObject("tableData", this.statsController.getTableData());
         }
      } catch (UserCredentialsUsernameNotExistsException userCredentialsUsernameNotExistsException) {
         AccountController.LOGGER.error(userCredentialsUsernameNotExistsException.getMessage());
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
      ModelAndView modelAndView = new ModelAndView(AccountController.UPDATE_ERROR_VIEW);
      this.userValidator.setAccountUpdate(true);
      this.credentialsValidator.setAccountUpdate(true);
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         modelAndView.setViewName(AccountController.UPDATE_SUCCESSFUL_VIEW);
         if (TypeValidators.validateString(confirmPassword)) {
            Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);
         }
         user.setCredentials(credentials);
         User updatedUser = this.userService.updateUser(loggedUser.getId(), user);
         Utils.updateUserCredentialsAuthentication(updatedUser.getCredentials());
         AccountController.LOGGER.info("Updated account with User ID: {}", updatedUser.getId());
      } else {
         Iterable<Sale> sales = this.saleService.getAllSalesByUser(loggedUser);
         Iterable<Sale> soldSales = this.saleService.getAllUserSoldSales(loggedUser);
         Iterable<Sale> orderedSales = this.orderService.getUserOrderedSales(loggedUser);
         credentials.setInsertedAt(loggedUser.getCredentials().getInsertedAt());
         credentials.setUpdatedAt(loggedUser.getCredentials().getUpdatedAt());
         modelAndView.addObject("user", user);
         modelAndView.addObject("credentials", credentials);
         modelAndView.addObject("sales", sales);
         modelAndView.addObject("soldSale", soldSales);
         modelAndView.addObject("orderedSales", orderedSales);
         modelAndView.addObject("tableData", this.statsController.getTableData());
         List<ObjectError> userErrors = userBindingResult.getAllErrors();
         for (ObjectError userGlobalError : userErrors) {
            modelAndView.addObject(Objects.requireNonNull(userGlobalError.getCode()), userGlobalError.getDefaultMessage());
         }
         List<ObjectError> credentialsErrors = credentialsBindingResult.getAllErrors();
         for (ObjectError credentialGlobalErrors : credentialsErrors) {
            modelAndView.addObject(Objects.requireNonNull(credentialGlobalErrors.getCode()), credentialGlobalErrors.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/delete", "/delete/"})
   public ModelAndView deleteUserAccountByUsername(@Valid @NonNull @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(AccountController.DELETE_ERROR_VIEWD);
      if (this.userService.deleteUser(loggedUser)) {
         modelAndView.setViewName(AccountController.DELETE_SUCCESSFUL_VIEW);
         AccountController.LOGGER.info("Deleted account with User ID: {}", loggedUser.getId());
      } else {
         modelAndView.addObject("accountNotDeletedError", true);
         AccountController.LOGGER.error(GlobalErrorsMessages.ACCOUNT_NOT_DELETED_ERROR);
      }
      return modelAndView;
   }
}
