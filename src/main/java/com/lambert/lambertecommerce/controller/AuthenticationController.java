package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CredentialsService;
import com.lambert.lambertecommerce.service.UserService;
import jakarta.validation.Valid;
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

   private CredentialsService credentialsService;
   private UserService userService;

   @GetMapping(value = "/")
   public ModelAndView index() {
      final String index = "index.html";
      final String adminIndex = "admin/index.html";
      ModelAndView modelAndView = new ModelAndView();
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication instanceof AnonymousAuthenticationToken) {
         modelAndView.setViewName(index);
      } else {
         UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
         if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            modelAndView.setViewName(adminIndex);
         }
      }
      return modelAndView;
   }

   @GetMapping(value = "/registration")
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @GetMapping(value = "/login")
   public ModelAndView showLoginForm() {
      return new ModelAndView("login");
   }

   @GetMapping(value = "/success")
   public ModelAndView defaultAfterLogin() {
      final String index = "index.html";
      final String adminIndex = "admin/index.html";
      ModelAndView modelAndView = new ModelAndView();
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
      if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
         modelAndView.setViewName(adminIndex);
      } else {
         modelAndView.setViewName(index);
      }
      return modelAndView;
   }

   @PostMapping(value = {"/registrerNewUser"})
   public ModelAndView registerUser(@Valid @ModelAttribute("user") User user,
                                    BindingResult userBindingResult,
                                    @Valid @ModelAttribute("credentials") Credentials credentials,
                                    BindingResult credentialsBindingResult) {
      final String registrationSuccessful = "admin/index.html";
      final String registrationError = "registration.html";
      ModelAndView modelAndView = new ModelAndView();
      // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         userService.saveUser(user);
         credentials.setUser(user);
         credentialsService.saveCredentials(credentials);
         modelAndView.addObject("user", user);
         modelAndView.setViewName(registrationSuccessful);
      } else {
         modelAndView.setViewName(registrationError);
      }
      return modelAndView;
   }
}
