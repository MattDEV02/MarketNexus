package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CredentialsService;
import com.lambert.lambertecommerce.service.NationService;
import com.lambert.lambertecommerce.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

   Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;
   @Autowired
   private NationService nationService;

   @GetMapping(value = "/")
   public ModelAndView index() {
      return new ModelAndView("index.html");
   }

   @GetMapping(value = "/registration")
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      modelAndView.addObject("nations", this.nationService.getAllNations());
      return modelAndView;
   }

   @GetMapping(value = "/login")
   public ModelAndView showLoginForm() {
      ModelAndView modelAndView = new ModelAndView("login");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @PostMapping(value = "/afterLogin")
   public ModelAndView defaultAfterLogin() {
      ModelAndView modelAndView = new ModelAndView();
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      System.out.println(authentication);
      if (authentication instanceof AnonymousAuthenticationToken) {
         modelAndView.setViewName("index.html");
      } else {
         UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
         if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            modelAndView.setViewName("admin/index.html");
         }
      }
      return modelAndView;
   }

   @PostMapping(value = {"/registerNewUser"})
   public ModelAndView registerUser(@Valid @ModelAttribute("user") User user,
                                    BindingResult userBindingResult,
                                    @Valid @ModelAttribute("credentials") Credentials credentials,
                                    BindingResult credentialsBindingResult) {
      final String registrationSuccessful = "login.html";
      final String registrationError = "registration.html";
      ModelAndView modelAndView = new ModelAndView();
      // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
      if (!userBindingResult.hasErrors()) {
         userService.saveUser(user);
         credentials.setUser(user);
         credentialsService.saveCredentials(credentials);
         modelAndView.setViewName(registrationSuccessful);
      } else {
         modelAndView.setViewName(registrationError);
      }
      return modelAndView;
   }
}
