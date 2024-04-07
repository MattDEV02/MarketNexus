package com.market.marketnexus.helpers.credentials;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

   public Set<Roles> getAllRoles() {
      Set<Roles> allRoles = new HashSet<Roles>(Arrays.asList(Roles.values()));
      return allRoles;
   }
}
