package com.market.marketnexus.service;

import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

   @Autowired
   protected CredentialsRepository credentialsRepository;

   public Credentials getCredentials(String username) {
      return this.credentialsRepository.findByUsername(username).orElseThrow(() -> new UserCredentialsUsernameNotExistsException("User Credentials with username '" + username + "' does not exist."));
   }

}
