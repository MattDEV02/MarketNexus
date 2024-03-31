package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
   public boolean existsByEmail(String email);

   public Optional<User> findByEmail(String email);

   public Optional<User> findByCredentials(Credentials credentials);

}
