package com.market.marketnexus.helpers.credentials;

import com.market.marketnexus.model.Credentials;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Utils {

   public static boolean userIsLoggedIn(Authentication authentication) {
      return !(authentication instanceof AnonymousAuthenticationToken);
   }

   public static void updateUserCredentialsAuthentication(@NotNull Credentials credentials) {
      Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetails currentUserDetails = (UserDetails) currentAuthentication.getPrincipal();
      Collection<GrantedAuthority> updatedAuthorities = new ArrayList<>(currentAuthentication.getAuthorities());
      updatedAuthorities.clear();
      updatedAuthorities.add(new SimpleGrantedAuthority(credentials.getRole()));
      UserDetails newPrincipal = new User(credentials.getUsername(), credentials.getPassword(), currentUserDetails.getAuthorities());
      Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
              newPrincipal,
              null, // credentials
              updatedAuthorities
      );
      SecurityContextHolder.getContext().setAuthentication(newAuthentication);
   }

   public static void cryptAndSaveUserCredentialsPassword(@NotNull Credentials credentials, @NotNull PasswordEncoder passwordEncoder) {
      String encodedPassword = passwordEncoder.encode(credentials.getPassword());
      credentials.setPassword(encodedPassword);
   }

   public static @NotNull Map<String, Roles> getAllRoles() {
      Map<String, Roles> result = new HashMap<String, Roles>();
      Roles[] allRoles = Roles.values();
      for (Roles role : allRoles) {
         result.put(role.name().replace("_ROLE", ""), role);
      }
      return result;
   }

   public static @NotNull Boolean existsRole(String stringRole) {
      Roles[] roles = Roles.values();
      for (Roles role : roles) {
         if (role.toString().equals(stringRole)) {
            return true;
         }
      }
      return false;
   }
}
