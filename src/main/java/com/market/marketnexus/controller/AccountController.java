package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.helpers.constants.PathSuffixes;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.OrderService;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
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
      modelAndView.addObject("credentials", loggedUser.getCredentials());
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
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @Valid @ModelAttribute("user") User user,
           BindingResult userBindingResult) {
      System.out.println(userBindingResult);
      ModelAndView modelAndView = new ModelAndView("redirect:/" + PathSuffixes.DASHBOARD + "/account");
      this.userValidator.validate(user, userBindingResult);
      if (!userBindingResult.hasFieldErrors()) {
         modelAndView.addObject("accountUpdatedSuccessful", true);
         User updatedUser = this.userService.updateUser(loggedUser.getId(), user);
         loggedUser = updatedUser;
         System.out.println(loggedUser);
      } else {
         for (ObjectError error : userBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(error.getCode()), error.getDefaultMessage());
         }

      }
      return modelAndView;
   }
}
