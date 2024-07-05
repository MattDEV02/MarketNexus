package com.market.marketnexus.controller;

import com.market.marketnexus.exception.UserEmailNotExistsException;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalController {

   private static final Map<String, Object> GLOBAL_CONSTANTS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> FIELD_SIZES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> TEMPORALS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> API_PATHS_MAP = new HashMap<String, Object>();
   private static final Map<String, Roles> ALL_ROLES_MAP = Utils.getAllRoles();

   static {
      GlobalValues.fillGlobalMap(GlobalValues.class, GlobalController.GLOBAL_CONSTANTS_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(FieldSizes.class, GlobalController.FIELD_SIZES_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(Temporals.class, GlobalController.TEMPORALS_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(APIPaths.class, GlobalController.API_PATHS_MAP);
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

   @ModelAttribute("API_PATHS_MAP")
   public Map<String, Object> getApiPrefixesMap() {
      return GlobalController.API_PATHS_MAP;
   }

   @ModelAttribute("ALL_ROLES_MAP")
   public Map<String, Roles> getRoles() {
      return GlobalController.ALL_ROLES_MAP;
   }

   @ModelAttribute("loggedUser")
   public User getLoggedUser() {
      Credentials credentials = null;
      User loggedUser = null;
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Authentication authentication = securityContext.getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         Object principal = authentication.getPrincipal();
         if (Utils.userIsLoggedInWithOAuth2(authentication)) {
            String email = null;
            try {
               OAuth2User oAuth2User = (OAuth2User) (principal);
               email = oAuth2User.getAttribute("email");
               loggedUser = this.userService.getUser(email);
               credentials = loggedUser.getCredentials();
               Utils.updateUserCredentialsOAuth2Authentication(authentication, credentials);
            } catch (UserEmailNotExistsException userEmailNotExistsException) {
               // oppure si può fare un redirect al login con messaggio di credenziali invalide.
               System.out.println("User with email: " + email + " has logged in with Google and without " + GlobalValues.APP_NAME + " account.");
            }
         } else if (Utils.userIsLoggedInWithUsernameAndPassword(authentication)) {
            UserDetails userDetails = (UserDetails) (principal);
            credentials = this.credentialsService.getCredentials(userDetails.getUsername());
            loggedUser = this.userService.getUser(credentials);
         }
         this.credentialsService.updateIsOnline(credentials, true); // credentials not null at this point
      }
      return loggedUser;
   }

   @ModelAttribute("nationsMap")
   public Map<Long, Nation> getNations(@NonNull HttpServletRequest request) {
      Map<Long, Nation> nations = null;
      final String URI = request.getRequestURI();
      if (URI.contains("/regist") || URI.contains("/" + APIPaths.ACCOUNT)) {
         nations = this.nationService.getAllNationsMap();
      }
      return nations;
   }

   @ModelAttribute("productCategoriesMap")
   public Map<Long, ProductCategory> getProductCategories() {
      Map<Long, ProductCategory> productCategories = null;
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Authentication authentication = securityContext.getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         productCategories = this.productCategoryService.getAllProductCategoriesMap();
      }
      return productCategories;
   }
}
