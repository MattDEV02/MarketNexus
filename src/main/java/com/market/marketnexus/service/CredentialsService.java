package com.market.marketnexus.service;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialsService {

   @Autowired
   protected CredentialsRepository credentialsRepository;

   public Credentials getCredentials(Long id) {
      Optional<Credentials> result = this.credentialsRepository.findById(id);
      return result.orElse(null);
   }

   public Credentials getCredentials(String username) {
      Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
      return result.orElse(null);
   }

}
