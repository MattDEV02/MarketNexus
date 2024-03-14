package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.controller.validator.CredentialsValidator;
import com.lambert.lambertecommerce.controller.validator.UserValidator;
import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CredentialsService;
import com.lambert.lambertecommerce.service.NationService;
import com.lambert.lambertecommerce.service.UserService;
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

import java.lang.reflect.Field;

import static com.lambert.lambertecommerce.helpers.constants.FieldSizes.*;

@Controller

public class AuthenticationController {

   private static final Field[] fieldSizesFields = FieldSizes.class.getFields();
   Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;
   @Autowired
   private NationService nationService;
   @Autowired
   private UserValidator userValidator;
   @Autowired
   private CredentialsValidator credentialsValidator;

   // @Autowired
//   private Set<Nation> nations = this.nationService.getAllNations();

   @GetMapping(value = "/")
   public ModelAndView showIndex() {
      return new ModelAndView("index.html");
   }

   @GetMapping(value = "/registration")
   public ModelAndView showRegisterForm() throws IllegalAccessException {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      modelAndView.addObject("nations", this.nationService.getAllNations());
      for (Field field : AuthenticationController.fieldSizesFields) {
         modelAndView.addObject(field.getName(), field.get(null));
      }
      return modelAndView;
   }

   @PostMapping(value = {"/registerNewUser"})
   public ModelAndView registerUser(@Valid @ModelAttribute("user") User user,
                                    BindingResult userBindingResult,
                                    @Valid @ModelAttribute("credentials") Credentials credentials,
                                    BindingResult credentialsBindingResult,
                                    @RequestParam("confirm-password") String confirmPassword) throws IllegalAccessException {
      final String registrationSuccessful = "registrationSuccessful.html";
      final String registrationError = "registration.html";
      ModelAndView modelAndView = new ModelAndView();
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         this.userService.saveUser(user);
         credentials.setUser(user);
         this.credentialsService.saveCredentials(credentials);
         modelAndView.setViewName(registrationSuccessful);
      } else {
         for (ObjectError error : userBindingResult.getGlobalErrors()) {
            modelAndView.addObject(error.getCode(), error.getDefaultMessage());
         }
         for (ObjectError error : credentialsBindingResult.getGlobalErrors()) {
            modelAndView.addObject(error.getCode(), error.getDefaultMessage());
         }
         modelAndView.addObject("nations", this.nationService.getAllNations());
         for (Field field : AuthenticationController.fieldSizesFields) {
            modelAndView.addObject(field.getName(), field.get(null));
         }
         modelAndView.setViewName(registrationError);
      }
      return modelAndView;
   }

/*
   @GetMapping(value = "/registrationSuccessful")
   public ModelAndView defaultAfterLogin() {
      ModelAndView modelAndView = new ModelAndView("registrationSuccessful");
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
      modelAndView.addObject("user", userDetails);
      modelAndView.addObject("credentials", credentials);
      return modelAndView;
   }
*/

   @GetMapping(value = "/login")
   public ModelAndView showLoginForm() {
      ModelAndView modelAndView = new ModelAndView("login");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      modelAndView.addObject("USERNAME_MIN_LENGTH", USERNAME_MIN_LENGTH);
      modelAndView.addObject("USERNAME_MAX_LENGTH", USERNAME_MAX_LENGTH);
      modelAndView.addObject("PASSWORD_MIN_LENGTH", PASSWORD_MIN_LENGTH);
      modelAndView.addObject("PASSWORD_MAX_LENGTH", PASSWORD_MAX_LENGTH);
      return modelAndView;
   }
}
