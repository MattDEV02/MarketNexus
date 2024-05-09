package com.market.marketnexus.service;

import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.helpers.credentials.Roles;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.repository.CredentialsRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

   @Autowired
   protected CredentialsRepository credentialsRepository;

   public Credentials getCredentials(String username) {
      return this.credentialsRepository.findByUsername(username).orElseThrow(() -> new UserCredentialsUsernameNotExistsException("User Credentials with username '" + username + "' does not exist."));
   }

   public Boolean areBuyerCredentials(@NotNull Credentials credentials) {
      return credentials.getRole().contains(Roles.BUYER_ROLE.toString());
   }

   public Boolean areSellerCredentials(@NotNull Credentials credentials) {
      return credentials.getRole().contains(Roles.SELLER_ROLE.toString().replace("_ROLE", ""));
   }

}
