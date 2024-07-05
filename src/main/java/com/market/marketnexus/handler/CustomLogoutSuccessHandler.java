package com.market.marketnexus.handler;


import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.service.CredentialsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

   @Autowired
   private CredentialsService credentialsService;

   @Override
   public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
      try {
         if (Utils.userIsLoggedIn(authentication)) {
            Credentials credentials = this.credentialsService.getCredentials(authentication.getName());
            this.credentialsService.updateIsOnline(credentials, false);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/login?logoutSuccessful=true"); // Reindirizza dopo il logout
            // Puoi aggiungere qui log, aggiornamenti di stato, ecc.
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}