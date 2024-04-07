package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class AuthenticationController {

   private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;
   @Autowired
   private UserValidator userValidator;
   @Autowired
   private CredentialsValidator credentialsValidator;


   @GetMapping(value = "/registration")
   public ModelAndView showRegisterForm() throws IllegalAccessException {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @PostMapping(value = "/registerNewUser")
   public ModelAndView registerUser(@Valid @ModelAttribute("user") User user,
                                    BindingResult userBindingResult,
                                    @Valid @ModelAttribute("credentials") Credentials credentials,
                                    BindingResult credentialsBindingResult,
                                    @RequestParam("confirm-password") String confirmPassword) {
      final String registrationSuccessful = "redirect:/login";
      final String registrationError = "registration.html";
      ModelAndView modelAndView = new ModelAndView(registrationError);
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         Credentials savedCredentials = this.credentialsService.saveCredentials(credentials);
         user.setCredentials(savedCredentials);
         this.userService.saveUser(user);
         modelAndView.setViewName(registrationSuccessful);
         modelAndView.addObject("registrationSuccessful", true);
      } else {
         for (ObjectError userGlobalError : userBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(userGlobalError.getCode()), userGlobalError.getDefaultMessage());
         }
         for (ObjectError credentialsGlobalError : credentialsBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(credentialsGlobalError.getCode()), credentialsGlobalError.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = "/login")
   public ModelAndView showLoginForm() {
      ModelAndView modelAndView = new ModelAndView("login.html");
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }
}
