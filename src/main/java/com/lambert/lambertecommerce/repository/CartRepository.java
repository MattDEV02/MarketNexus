package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Cart;
import com.lambert.lambertecommerce.model.Selling;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface CartRepository extends CrudRepository<Cart, Long> {
   Cart findByUserSellingInsertedat(User user, Selling selling, LocalDateTime insertedAt);
}
