package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import com.lambert.lambertecommerce.helpers.credentials.Utils;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.Nation;
import com.lambert.lambertecommerce.model.ProductCategory;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CredentialsService;
import com.lambert.lambertecommerce.service.NationService;
import com.lambert.lambertecommerce.service.ProductCategoryService;
import com.lambert.lambertecommerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@ControllerAdvice
public class GlobalController {

   private static final Logger LOGGER = LoggerFactory.getLogger(GlobalController.class);
   private static final Map<String, Object> fieldSizesMap = new HashMap<String, Object>();

   static {
      // Inizializza la mappa dei valori dei campi alla creazione dell'istanza della classe
      Field[] fields = FieldSizes.class.getDeclaredFields();
      for (Field field : fields) {
         try {
            // Ottieni il nome del campo
            String name = field.getName();

            // Ottieni il valore del campo
            Object value = field.get(null);
            // Aggiungi il nome del campo e il suo valore alla mappa
            GlobalController.fieldSizesMap.put(name, value);
         } catch (IllegalAccessException e) {
            // Gestione dell'eccezione
            LOGGER.warn("Impossible to access at this field: " + field.getName(), e);
         }
      }
   }

   @Autowired
   private NationService nationService;
   @Autowired
   private UserService userService;
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private ProductCategoryService productCategoryService;

   @ModelAttribute("fieldSizes")
   public Map<String, Object> getFieldSizesFields() {
      return GlobalController.fieldSizesMap;
   }

   @ModelAttribute("nations")
   public Set<Nation> getNations(@NotNull HttpServletRequest request) {
      Set<Nation> nations = null;
      final String URI = request.getRequestURI();
      if (URI.equals("/dashboard/account") || URI.equals("/registration") || URI.equals("/registerNewUser")) {
         nations = this.nationService.getAllNations();
      }
      return nations;
   }

   @ModelAttribute("loggedUser")
   public User getUser(Model model) {
      UserDetails userDetails = null;
      Credentials credentials = null;
      User loggedUser = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         credentials = this.credentialsService.getCredentials(userDetails.getUsername());
         loggedUser = this.userService.getUser(credentials.getUser().getId());
         model.addAttribute("loggedUser", loggedUser);
      }
      return loggedUser;
   }

   @ModelAttribute("loggedCredentials")
   public Credentials getCredentials(Model model) {
      UserDetails userDetails = null;
      Credentials loggedCredentials = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         loggedCredentials = this.credentialsService.getCredentials(userDetails.getUsername());
         model.addAttribute("loggedCredentials", loggedCredentials);
      }
      return loggedCredentials;
   }

   @ModelAttribute("productCategories")
   public Set<ProductCategory> getProductCategories() {
      Set<ProductCategory> productCategories = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         productCategories = this.productCategoryService.getAllProductCategories();
      }
      return productCategories;
   }
}
