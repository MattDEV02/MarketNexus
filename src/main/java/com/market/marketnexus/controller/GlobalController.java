package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APISuffixes;
import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.Global;
import com.market.marketnexus.helpers.constants.Temporals;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
   private static final Map<String, Object> GLOBAL_CONSTANTS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> FIELD_SIZES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> TEMPORALS_MAP = new HashMap<String, Object>();
   private static final Map<String, Roles> ALL_ROLES_MAP = Utils.getAllRoles();
   //TODO: Global constants.

   static {
      GlobalController.GLOBAL_CONSTANTS_MAP.put("APP_NAME", Global.APP_NAME);
      GlobalController.GLOBAL_CONSTANTS_MAP.put("CHARSET", Global.CHARSET);
      GlobalController.GLOBAL_CONSTANTS_MAP.put("LANG", Global.LANG);
   }

   static {
      Field[] fields = FieldSizes.class.getDeclaredFields();
      for (Field field : fields) {
         try {
            String name = field.getName();
            Object value = field.get(null);
            GlobalController.FIELD_SIZES_MAP.put(name, value);
         } catch (IllegalAccessException | IllegalArgumentException illegalException) {
            LOGGER.warn("Impossible to access at this field: {}", field.getName(), illegalException);
         }
      }
   }

   static {
      Field[] fields = Temporals.class.getDeclaredFields();
      for (Field field : fields) {
         try {
            String name = field.getName();
            Object value = field.get(null);
            GlobalController.TEMPORALS_MAP.put(name, value);
         } catch (IllegalAccessException | IllegalArgumentException illegalException) {
            LOGGER.warn("Impossible to access at this field: {}", field.getName(), illegalException);
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

   @ModelAttribute("ALL_ROLES_MAP")
   public Map<String, Roles> getRoles() {
      return GlobalController.ALL_ROLES_MAP;
   }

   @ModelAttribute("nations")
   public Set<Nation> getNations(@NonNull HttpServletRequest request) {
      Set<Nation> nations = null;
      final String URI = request.getRequestURI();
      if (URI.contains("/regist") || URI.contains("/" + APISuffixes.ACCOUNT)) {
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
         userDetails = (UserDetails) authentication.getPrincipal();
         credentials = this.credentialsService.getCredentials(userDetails.getUsername());
         loggedUser = this.userService.getUser(credentials);
         model.addAttribute("loggedUser", loggedUser);
      }
      return loggedUser;
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
