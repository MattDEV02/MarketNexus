package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APISuffixes;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + APISuffixes.ACCOUNT)
public class AccountController {

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
      ModelAndView modelAndView = new ModelAndView(APISuffixes.ACCOUNT + ".html");
      Set<Sale> saleProducts = this.saleService.getAllSalesByUser(loggedUser);
      Set<Order> orderedProducts = this.orderService.getAllOrdersByUser(loggedUser);
      modelAndView.addObject("user", loggedUser);
      modelAndView.addObject("credentials", loggedUser.getCredentials());
      modelAndView.addObject("saleProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      return modelAndView;
   }

   @GetMapping(value = {"/{username}", "/{username}/"})
   public ModelAndView showUserAccountByUsername(@Valid @ModelAttribute("loggedUser") @NonNull User loggedUser, @PathVariable("username") String username) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.ACCOUNT + ".html");
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
           @Valid @NonNull @ModelAttribute("loggedUser") User loggedUser,
           @Valid @NonNull @ModelAttribute("user") User user,
           @NonNull BindingResult userBindingResult,
           @Valid @NonNull @ModelAttribute("credentials") Credentials credentials,
           @NonNull BindingResult credentialsBindingResult
   ) {
      final String updateSuccessful = "redirect:/" + APISuffixes.ACCOUNT + "?accountUpdatedSuccessful=true";
      final String updateError = "/" + APISuffixes.ACCOUNT + ".html";
      ModelAndView modelAndView = new ModelAndView(updateError);
      if (!userBindingResult.hasFieldErrors() && !credentialsBindingResult.hasFieldErrors()) {
         modelAndView.setViewName(updateSuccessful);
         Credentials updatedCredentials = this.credentialsService.updateCredentials(loggedUser.getCredentials().getId(), credentials);
         user.setCredentials(updatedCredentials);
         this.userService.updateUser(loggedUser.getId(), user);
         Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
         UserDetails currentUserDetails = (UserDetails) currentAuthentication.getPrincipal();
         UserDetails newPrincipal = new org.springframework.security.core.userdetails.User(updatedCredentials.getUsername(), updatedCredentials.getPassword(), currentUserDetails.getAuthorities());
         Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                 newPrincipal,
                 null, // authentication.credentials
                 currentAuthentication.getAuthorities()
         );
         // Imposta l'oggetto Authentication aggiornato nel contesto di sicurezza
         SecurityContextHolder.getContext().setAuthentication(newAuthentication);
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
