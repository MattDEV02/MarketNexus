package com.market.marketnexus.service;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

   @Transactional
   public Credentials saveCredentials(@NonNull Credentials credentials) {
      return this.credentialsRepository.save(credentials);
   }

   @Transactional
   public Credentials updateCredentials(Long id, @NonNull Credentials updatedCredentials) {
      Credentials credentials = this.credentialsRepository.findById(id).orElse(null);
      if (credentials != null) {
         credentials.setUsername(updatedCredentials.getUsername());
         return this.credentialsRepository.save(credentials);
      }
      return null;
   }
}
