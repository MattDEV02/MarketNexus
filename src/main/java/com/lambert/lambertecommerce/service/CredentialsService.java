package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.repository.CredentialsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class CredentialsService {

   protected PasswordEncoder passwordEncoder;
   protected CredentialsRepository credentialsRepository;

   @Transactional
   public Credentials getCredentials(Long id) {
      Optional<Credentials> result = this.credentialsRepository.findById(id);
      return result.orElse(null);
   }

   @Transactional
   public Credentials getCredentials(String username) {
      Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
      return result.orElse(null);
   }

   @Transactional
   public Credentials saveCredentials(Credentials credentials) {
      credentials.setRole(Credentials.DEFAULT_ROLE);
      credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
      return this.credentialsRepository.save(credentials);
   }
}
