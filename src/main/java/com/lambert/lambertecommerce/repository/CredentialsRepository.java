package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
   
   public boolean existsByUsername(String username);

   public Optional<Credentials> findByUsername(String username);
}
