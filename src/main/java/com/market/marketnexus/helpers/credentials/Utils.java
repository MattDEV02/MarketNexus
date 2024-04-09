package com.market.marketnexus.helpers.credentials;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

public class Utils {

   public static boolean userIsLoggedIn(User user) {
      return false;
   }

   public static boolean userIsLoggedIn(Credentials credentials) {
      return false;
   }

   public static boolean userIsLoggedIn(Authentication authentication) {
      return !(authentication instanceof AnonymousAuthenticationToken);
   }

   public static Map<String, Roles> getAllRoles() {
      Map<String, Roles> result = new HashMap<String, Roles>();
      Roles[] allRoles = Roles.values();
      for (Roles role : allRoles) {
         result.put(role.name(), role);
      }
      return result;
   }
}
