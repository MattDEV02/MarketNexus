package com.market.marketnexus.helpers.credentials;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

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

   public static void updateUserCredentialsAuthentication(@NotNull Credentials credentials) {
      Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetails currentUserDetails = (UserDetails) currentAuthentication.getPrincipal();
      UserDetails newPrincipal = new org.springframework.security.core.userdetails.User(credentials.getUsername(), credentials.getPassword(), currentUserDetails.getAuthorities());
      Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
              newPrincipal,
              null, // authentication.credentials
              currentAuthentication.getAuthorities()
      );
      SecurityContextHolder.getContext().setAuthentication(newAuthentication);
   }

   public static void cryptAndSaveUserCredentialsPassword(@NotNull Credentials credentials, @NotNull PasswordEncoder passwordEncoder) {
      credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
   }

   public static void saveUserCredentialsRole(@NotNull Credentials credentials, Roles role) {

   }

   public static @NotNull Map<String, Roles> getAllRoles() {
      Map<String, Roles> result = new HashMap<String, Roles>();
      Roles[] allRoles = Roles.values();
      for (Roles role : allRoles) {
         result.put(role.name().replace("_ROLE", ""), role);
      }
      return result;
   }

   public static Boolean existsRole(String stringRole) {
      Boolean exists = false;
      Roles[] roles = Roles.values();
      for (Roles role : roles) {
         if (role.toString().equals(stringRole)) {
            exists = true;
            break;
         }
      }
      return exists;
   }
}
