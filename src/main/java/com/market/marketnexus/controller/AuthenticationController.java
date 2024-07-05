package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.exception.UserEmailNotExistsException;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.UserService;
import com.market.marketnexus.service.email.ForgotUsernameEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Controller
public class AuthenticationController {

   public final static String REGISTRATION_SUCCESSFUL_VIEW = "redirect:/login?registrationSuccessful=true";
   public final static String REGISTRATION_ERROR_VIEW = "registration.html";
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
      ModelAndView modelAndView = new ModelAndView(AuthenticationController.REGISTRATION_ERROR_VIEW);
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);
         user.setCredentials(credentials);
         User savedUser = this.userService.saveUser(user);
         if (savedUser != null) {
            AuthenticationController.LOGGER.info("Registered account with User ID: {}", savedUser.getId());
            modelAndView.setViewName(AuthenticationController.REGISTRATION_SUCCESSFUL_VIEW);
         } else {
            AuthenticationController.LOGGER.error(GlobalErrorsMessages.USER_NOT_REGISTERED_ERROR);
            modelAndView.addObject("userNotRegisteredError", "Server ERROR, User not registered.");
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
           @NonNull BindingResult userBindingResult) {
      ModelAndView modelAndView = new ModelAndView("forgotUsername.html");
      if (!userBindingResult.hasFieldErrors("email")) {
         try {
            User userByEmail = this.userService.getUser(user.getEmail());
            this.forgotUsernameEmailService.sendEmail(userByEmail.getEmail(), userByEmail.getCredentials().getUsername());
            modelAndView.addObject("emailSentSuccess", true);
         } catch (IOException | MessagingException exception) {
            AuthenticationController.LOGGER.error(exception.getMessage());
            modelAndView.addObject("emailNotSentError", true);
            AuthenticationController.LOGGER.error(GlobalErrorsMessages.EMAIL_NOT_SENT_ERROR);
         } catch (UserEmailNotExistsException userEmailNotExistsException) {
            AuthenticationController.LOGGER.error(userEmailNotExistsException.getMessage());
            modelAndView.addObject("emailNotExistsError", true);
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/" + APIPaths.MARKETPLACE, "/" + APIPaths.MARKETPLACE + "/"})
   public ModelAndView redirectToMarketPlaceSales() {
      return new ModelAndView("redirect:/" + APIPaths.SALES);
   }

   @PostMapping("/saveToken")
   public ResponseEntity<?> saveToken(@RequestBody String token) {
      try {
         Path path = Paths.get("src/main/resources/static/txt/tokens.txt");
         List<String> existingTokens = Files.readAllLines(path);

         if (!existingTokens.contains(token)) {
            Files.write(path, (token + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return new ResponseEntity<>(HttpStatus.OK);
         } else {
            // Token già esistente, gestisci come preferisci
            return new ResponseEntity<>("Token already exists", HttpStatus.CONFLICT);
         }
      } catch (IOException e) {
         AuthenticationController.LOGGER.error(e.getMessage());
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}
