package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.UserService;
import com.market.marketnexus.service.email.ForgotUsernameEmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class AuthenticationController {

   public final static String REGISTRATION_SUCCESSFUL = "redirect:/login?registrationSuccessful=true";
   public final static String REGISTRATION_ERROR = "registration.html";
   private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private UserService userService;
   @Autowired
   private UserValidator userValidator;
   @Autowired
   private CredentialsValidator credentialsValidator;
   @Autowired
   private ForgotUsernameEmailService forgotUsernameEmailService;

   @GetMapping(value = {"/registration", "/registration/"})
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @PostMapping(value = {"/registerNewUser", "/registerNewUser/"})
   public ModelAndView registerUser(@Valid @NonNull @ModelAttribute("user") User user,
                                    @NonNull BindingResult userBindingResult,
                                    @Valid @NonNull @ModelAttribute("credentials") Credentials credentials,
                                    @NonNull BindingResult credentialsBindingResult,
                                    @NonNull @RequestParam("confirm-password") String confirmPassword) {
      ModelAndView modelAndView = new ModelAndView(AuthenticationController.REGISTRATION_ERROR);
      this.userValidator.setAccountUpdate(false);
      this.credentialsValidator.setAccountUpdate(false);
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);
         user.setCredentials(credentials);
         User savedUser = this.userService.saveUser(user);
         if (savedUser != null) {
            LOGGER.info("Registered account with User ID: {}", savedUser.getId());
            modelAndView.setViewName(AuthenticationController.REGISTRATION_SUCCESSFUL);
         } else {
            modelAndView.addObject("userNotRegisteredError", "Server ERROR, User not registered.");
         }
      } else {
         List<ObjectError> userGlobalErrors = userBindingResult.getGlobalErrors();
         List<ObjectError> credentialsGlobalErrors = credentialsBindingResult.getGlobalErrors();
         for (ObjectError userGlobalError : userGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(userGlobalError.getCode()), userGlobalError.getDefaultMessage());
         }
         for (ObjectError credentialGlobalErrors : credentialsGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(credentialGlobalErrors.getCode()), credentialGlobalErrors.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/login", "/login/"})
   public ModelAndView showLoginForm() {
      ModelAndView modelAndView = new ModelAndView("login.html");
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @GetMapping(value = {"/forgotUsername", "forgotUsername/"})
   public ModelAndView showForgotUsernameForm() {
      ModelAndView modelAndView = new ModelAndView("forgotUsername.html");
      modelAndView.addObject("user", new User());
      return modelAndView;
   }

   @PostMapping(value = {"/sendForgotUsernameEmail", "/sendForgotUsernameEmail/"})
   public ModelAndView sendForgotUsernameEmail(
           @Valid @NonNull @ModelAttribute("user") User user,
           @NonNull BindingResult userBindingResult, @RequestParam("email") String email) {
      ModelAndView modelAndView = new ModelAndView("forgotUsername.html");
      if (!userBindingResult.hasFieldErrors("email")) {
         if (this.userService.existsByEmail(email)) {
            User userByEmail = this.userService.getUser(email);
            try {
               this.forgotUsernameEmailService.sendEmail(userByEmail.getEmail(), userByEmail.getCredentials().getUsername());
            } catch (IOException exception) {
               LOGGER.error(exception.getMessage());
            }
         } else {
            modelAndView.addObject("emailNotExistsError", true);
         }
         modelAndView.addObject("emailSent", true);
      }
      return modelAndView;
   }
}
