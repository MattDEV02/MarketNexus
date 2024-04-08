package com.market.marketnexus.controller;

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

   @GetMapping(value = {"", "/"})
   public ModelAndView showUserAccount(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.ACCOUNT + ".html");
      Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
      Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(loggedUser);
      modelAndView.addObject("user", loggedUser);
      modelAndView.addObject("credentials", loggedUser.getCredentials());
      modelAndView.addObject("saleProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      return modelAndView;
   }

   @GetMapping(value = {"/{username}", "/{username}/"})
   public ModelAndView showUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NotNull User loggedUser, @PathVariable("username") String username) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.ACCOUNT + ".html");
      Credentials credentials = this.credentialsService.getCredentials(username);
      User user = this.userService.getUser(credentials);
      Set<Sale> saleProducts = this.saleService.getAllSalesByUser(user);
      Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(user);
      modelAndView.addObject("saleProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      modelAndView.addObject("user", user);
      modelAndView.addObject("credentials", user.getCredentials());
      return modelAndView;
   }

   @PostMapping(value = {"/updateAccount", "/updateAccount/"})
   public ModelAndView updateUserAccount(
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @Valid @ModelAttribute("user") User user,
           BindingResult userBindingResult) {
      final String updateSuccessful = "redirect:/" + PathSuffixes.ACCOUNT + "?accountUpdatedSuccessful=true";
      final String updateError = "/" + PathSuffixes.ACCOUNT + ".html";
      ModelAndView modelAndView = new ModelAndView(updateError);
      this.userValidator.validate(user, userBindingResult);
      if (!userBindingResult.hasFieldErrors()) {
         modelAndView.setViewName(updateSuccessful);
         User updatedUser = this.userService.updateUser(loggedUser.getId(), user);
         loggedUser = updatedUser; // TODO: ?
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
}
