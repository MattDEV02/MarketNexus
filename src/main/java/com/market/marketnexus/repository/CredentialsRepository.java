package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

   public boolean existsByUsername(String username);

   public Optional<Credentials> findByUsername(String username);
}
