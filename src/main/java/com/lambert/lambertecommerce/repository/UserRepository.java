package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
   public boolean existsByEmail(String email);
}
