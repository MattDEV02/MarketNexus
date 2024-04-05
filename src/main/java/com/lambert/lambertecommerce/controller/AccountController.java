package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.controller.validator.CredentialsValidator;
import com.lambert.lambertecommerce.controller.validator.UserValidator;
import com.lambert.lambertecommerce.helpers.constants.PathSuffixes;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.Order;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CredentialsService;
import com.lambert.lambertecommerce.service.OrderService;
import com.lambert.lambertecommerce.service.SaleService;
import com.lambert.lambertecommerce.service.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + PathSuffixes.ACCOUNT)
public class AccountController {

   @Autowired
   private SaleService saleService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;
   @Autowired
   private UserValidator userValidator;
   @Autowired
   private CredentialsValidator credentialsValidator;

   @GetMapping(value = "")
   public ModelAndView showUserAccount(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/account.html");
      Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
      Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(loggedUser);
      modelAndView.addObject("user", loggedUser);
      modelAndView.addObject("saleProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      return modelAndView;
   }

   @GetMapping(value = "/{username}")
   public ModelAndView showUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NotNull User loggedUser, @PathVariable("username") String username) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/account.html");
      Credentials credentials = this.credentialsService.getCredentials(username);
      User user = this.userService.getUser(credentials);
      Set<Sale> saleProducts = this.saleService.getAllSalesByUser(user);
      System.out.println(saleProducts);
      Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(user);
      System.out.println(orderedProducts);
      modelAndView.addObject("saleProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      modelAndView.addObject("user", user);
      return modelAndView;
   }

   @PostMapping(value = "/updateAccount")
   public ModelAndView updateUserAccount(
           @Valid @ModelAttribute("loggedUser") @NotNull User loggedUser,
           @Valid @ModelAttribute("user") User user,
           BindingResult userBindingResult,
           @Valid @ModelAttribute("credentials") Credentials credentials,
           BindingResult credentialsBindingResult) {
      final String updateSuccessful = "redirect:/account";
      final String updateError = "registration.html";
      ModelAndView modelAndView = new ModelAndView(updateError);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         modelAndView.setViewName(updateSuccessful);
         modelAndView.addObject("updateSuccessful", true);
         this.credentialsService.updateCredentials(loggedUser.getCredentials().getId(), credentials);
         this.userService.updateUser(loggedUser.getId(), user);
      } else {
         for (ObjectError error : userBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(error.getCode()), error.getDefaultMessage());
         }
         for (ObjectError error : credentialsBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(error.getCode()), error.getDefaultMessage());
         }
      }
      return modelAndView;
   }
}
