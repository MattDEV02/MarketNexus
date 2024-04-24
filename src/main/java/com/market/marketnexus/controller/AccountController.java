package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.OrderService;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + APIPrefixes.ACCOUNT)
public class AccountController {

   public final static String UPDATE_SUCCESSFUL = "redirect:/" + APIPrefixes.ACCOUNT + "?accountUpdatedSuccessful=true#update-account-form";
   public final static String UPDATE_ERROR = "/" + APIPrefixes.ACCOUNT + ".html";
   public final static String ACCOUNT_DELETED_SUCCESSFULLY = "redirect:/logout";
   public final static String ACCOUNT_DELETED = APIPrefixes.ACCOUNT + ".html";

   @Autowired
   private SaleService saleService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showUserAccount(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      System.out.println(this.userService.getAllSalesForUser(loggedUser.getId()));
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.ACCOUNT + ".html");
      Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
      Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(loggedUser);
      modelAndView.addObject("user", loggedUser);
      modelAndView.addObject("credentials", loggedUser.getCredentials());
      modelAndView.addObject("saleProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      modelAndView.addObject("userDataRows", new ArrayList<String>(Arrays.asList("1", "2")));
      return modelAndView;
   }

   @GetMapping(value = {"/{username}", "/{username}/"})
   public ModelAndView showUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NonNull User loggedUser, @PathVariable("username") String username) {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.ACCOUNT + ".html");
      Credentials credentials = this.credentialsService.getCredentials(username);
      User user = this.userService.getUser(credentials);
      modelAndView.addObject("user", user);
      modelAndView.addObject("searchedUsername", username);
      if (user != null) {
         Set<Sale> saleProducts = this.saleService.getAllSalesByUser(user);
         Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(user);
         modelAndView.addObject("saleProducts", saleProducts);
         modelAndView.addObject("orderedProducts", orderedProducts);
         modelAndView.addObject("credentials", user.getCredentials());
      }
      return modelAndView;
   }

   @PostMapping(value = {"/updateAccount", "/updateAccount/"})
   public ModelAndView updateUserAccount(
           @Valid @NonNull @ModelAttribute("loggedUser") User loggedUser,
           @Valid @NonNull @ModelAttribute("user") User user,
           @NonNull BindingResult userBindingResult,
           @Valid @NonNull @ModelAttribute("credentials") Credentials credentials,
           @NonNull BindingResult credentialsBindingResult
   ) {
      ModelAndView modelAndView = new ModelAndView(AccountController.UPDATE_ERROR);
      if (!userBindingResult.hasFieldErrors() && !credentialsBindingResult.hasFieldErrors()) {
         modelAndView.setViewName(AccountController.UPDATE_SUCCESSFUL);
         user.setCredentials(credentials);
         User updatedUser = this.userService.updateUser(loggedUser.getId(), user);
         System.out.println("quu");
         Utils.updateUserCredentialsAuthentication(updatedUser.getCredentials());
      } else {
         Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
         Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(loggedUser);
         modelAndView.addObject("saleProducts", saleProducts);
         modelAndView.addObject("orderedProducts", orderedProducts);
         modelAndView.addObject("credentials", loggedUser.getCredentials());
         for (ObjectError error : userBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(error.getCode()), error.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/delete", "/delete/"})
   public ModelAndView deleteUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NonNull User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(AccountController.ACCOUNT_DELETED);
      if (this.userService.deleteUser(loggedUser)) {
         modelAndView.setViewName(AccountController.ACCOUNT_DELETED_SUCCESSFULLY);
      } else {
         modelAndView.addObject("accountNotDeletedError", true);
      }
      return modelAndView;
   }
}
