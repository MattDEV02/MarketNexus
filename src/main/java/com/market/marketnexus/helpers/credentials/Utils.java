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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Utils {

   public static boolean userIsLoggedIn(Authentication authentication) {
      return !(authentication instanceof AnonymousAuthenticationToken);
   }

   public static boolean userIsLoggedInWithUsernameAndPassword(Authentication authentication) {
      return authentication instanceof UsernamePasswordAuthenticationToken;
   }

   public static boolean userIsLoggedInWithOAuth2(Authentication authentication) {
      return authentication instanceof OAuth2AuthenticationToken;
   }

   public static void updateUserCredentialsAuthentication(@NotNull Credentials credentials) {
      Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedInWithOAuth2(currentAuthentication)) {
         Utils.updateUserCredentialsOAuth2Authentication(currentAuthentication, credentials);
      } else if (Utils.userIsLoggedInWithUsernameAndPassword(currentAuthentication)) {
         Utils.updateUserCredentialsUsernameAndPasswordAuthentication(currentAuthentication, credentials);
      }
   }

   public static void updateUserCredentialsUsernameAndPasswordAuthentication(@NotNull Authentication currentAuthentication, @NotNull Credentials credentials) {
      Collection<GrantedAuthority> authorities = new ArrayList<>(currentAuthentication.getAuthorities());
      UserDetails currentUserDetails = (UserDetails) (currentAuthentication.getPrincipal());
      authorities.clear();
      GrantedAuthority newGrantedAuthority = new SimpleGrantedAuthority(credentials.getRole());
      authorities.add(newGrantedAuthority);
      UserDetails newPrincipal = new User(credentials.getUsername(), credentials.getPassword(), currentUserDetails.getAuthorities());
      Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
              newPrincipal,
              null, // credentials
              authorities
      );
      SecurityContextHolder.getContext().setAuthentication(newAuthentication);
   }

   public static void updateUserCredentialsOAuth2Authentication(@NotNull Authentication currentAuthentication, @NotNull Credentials credentials) {
      Collection<GrantedAuthority> authorities = new ArrayList<>(currentAuthentication.getAuthorities());
      OAuth2User currentOAuth2User = (OAuth2User) (currentAuthentication.getPrincipal());
      GrantedAuthority newGrantedAuthority = new SimpleGrantedAuthority(credentials.getRole());
      authorities.removeIf(grantedAuthority -> grantedAuthority.toString().contains(Roles.BUYER.toString()) || grantedAuthority.toString().contains(Roles.SELLER.toString()));
      authorities.add(newGrantedAuthority);
      OAuth2User newPrincipal = new DefaultOAuth2User(authorities, currentOAuth2User.getAttributes(), "sub");
      Authentication newAuthentication = new OAuth2AuthenticationToken(
              newPrincipal,
              authorities,
              ((OAuth2AuthenticationToken) currentAuthentication).getAuthorizedClientRegistrationId()
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
