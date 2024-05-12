package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

   public boolean existsByUsername(String username);

   public Optional<Credentials> findByUsername(String username);
}
