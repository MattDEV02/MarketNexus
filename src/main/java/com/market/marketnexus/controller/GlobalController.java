package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.*;
import com.market.marketnexus.helpers.credentials.Roles;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.Nation;
import com.market.marketnexus.model.ProductCategory;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.NationService;
import com.market.marketnexus.service.ProductCategoryService;
import com.market.marketnexus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalController {

   private static final Map<String, Object> GLOBAL_CONSTANTS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> GLOBAL_ERRORS_MESSAGES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> GLOBAL_SUCCESS_MESSAGES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> FIELD_SIZES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> TEMPORALS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> API_PREFIXES_MAP = new HashMap<String, Object>();
   private static final Map<String, Roles> ALL_ROLES_MAP = Utils.getAllRoles();

   static {
      GlobalValues.fillGlobalMap(GlobalValues.class, GlobalController.GLOBAL_CONSTANTS_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(GlobalErrorsMessages.class, GlobalController.GLOBAL_ERRORS_MESSAGES_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(GlobalSuccessMessages.class, GlobalController.GLOBAL_SUCCESS_MESSAGES_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(FieldSizes.class, GlobalController.FIELD_SIZES_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(Temporals.class, GlobalController.TEMPORALS_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(APIPrefixes.class, GlobalController.API_PREFIXES_MAP);
   }

   @Autowired
   private NationService nationService;
   @Autowired
   private UserService userService;
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private ProductCategoryService productCategoryService;

   @ModelAttribute("GLOBAL_CONSTANTS_MAP")
   public Map<String, Object> getGlobalConstantsMap() {
      return GlobalController.GLOBAL_CONSTANTS_MAP;
   }

   @ModelAttribute("FIELD_SIZES_MAP")
   public Map<String, Object> getFieldSizesMap() {
      return GlobalController.FIELD_SIZES_MAP;
   }

   @ModelAttribute("TEMPORALS_MAP")
   public Map<String, Object> getTemporalsMap() {
      return GlobalController.TEMPORALS_MAP;
   }

   @ModelAttribute("API_PREFIXES_MAP")
   public Map<String, Object> getApiPrefixesMap() {
      return GlobalController.API_PREFIXES_MAP;
   }

   @ModelAttribute("ALL_ROLES_MAP")
   public Map<String, Roles> getRoles() {
      return GlobalController.ALL_ROLES_MAP;
   }

   @ModelAttribute("loggedUser")
   public User getUser(Model model) {
      UserDetails userDetails = null;
      Credentials credentials = null;
      User loggedUser = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         userDetails = (UserDetails) authentication.getPrincipal();
         credentials = this.credentialsService.getCredentials(userDetails.getUsername());
         loggedUser = this.userService.getUser(credentials);
         model.addAttribute("loggedUser", loggedUser);
      }
      return loggedUser;
   }

   @ModelAttribute("nationsMap")
   public Map<Long, Nation> getNations(@NonNull HttpServletRequest request) {
      Map<Long, Nation> nations = null;
      final String URI = request.getRequestURI();
      if (URI.contains("/regist") || URI.contains("/" + APIPrefixes.ACCOUNT)) {
         nations = this.nationService.getAllNationsMap();
      }
      return nations;
   }

   @ModelAttribute("productCategoriesMap")
   public Map<Long, ProductCategory> getProductCategories() {
      Map<Long, ProductCategory> productCategories = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         productCategories = this.productCategoryService.getAllProductCategoriesMap();
      }
      return productCategories;
   }
}
