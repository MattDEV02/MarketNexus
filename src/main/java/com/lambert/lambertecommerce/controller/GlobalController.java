package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.Nation;
import com.lambert.lambertecommerce.model.ProductCategory;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CredentialsService;
import com.lambert.lambertecommerce.service.NationService;
import com.lambert.lambertecommerce.service.ProductCategoryService;
import com.lambert.lambertecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

@ControllerAdvice
public class GlobalController {

   @Autowired
   private NationService nationService;
   @Autowired
   private UserService userService;
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private ProductCategoryService productCategoryService;

   @ModelAttribute("nations")
   public Set<Nation> getNations() {
      Set<Nation> nations = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication instanceof AnonymousAuthenticationToken) {
         nations = this.nationService.getAllNations();
      }
      return nations;
   }

   @ModelAttribute("loggedUser")
   public User getUser() {
      UserDetails userDetails = null;
      Credentials credentials = null;
      User loggedUser = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
         userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         credentials = this.credentialsService.getCredentials(userDetails.getUsername());
         loggedUser = this.userService.getUser(credentials.getUser().getId());
      }
      return loggedUser;
   }

   @ModelAttribute("loggedCredentials")
   public Credentials getCredentials() {
      UserDetails userDetails = null;
      Credentials loggedCredentials = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
         userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         loggedCredentials = this.credentialsService.getCredentials(userDetails.getUsername());
      }
      return loggedCredentials;
   }

   @ModelAttribute("productCategories")
   public Set<ProductCategory> getProductCategories() {
      Set<ProductCategory> productCategories = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
         productCategories = this.productCategoryService.getAllProductCategories();
      }
      return productCategories;
   }
}
