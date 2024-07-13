package com.market.marketnexus.handler;


import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;

   @Override
   public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
      Credentials credentials = null;
      try {
         if (Utils.userIsLoggedIn(authentication)) {
            credentials = this.credentialsService.getCredentials(authentication.getName());
         }
      } catch (UserCredentialsUsernameNotExistsException userCredentialsUsernameNotExistsException) {
         if (Utils.userIsLoggedInWithOAuth2(authentication)) {
            Object principal = authentication.getPrincipal();
            OAuth2User oAuth2User = (OAuth2User) (principal);
            String email = oAuth2User.getAttribute("email");
            User loggedUser = this.userService.getUser(email);
            credentials = loggedUser.getCredentials();
         }
      } finally {
         try {
            this.credentialsService.updateIsOnline(credentials, false);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.sendRedirect("/login?logoutSuccessful=true"); // Reindirizza dopo il logout
         } catch (IOException iOException) {
            iOException.printStackTrace();
         }
      }

   }
}