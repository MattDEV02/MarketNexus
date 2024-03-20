package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Cart;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CartRepository extends CrudRepository<Cart, Long> {
   // Cart findByUserSellingInsertedat(User user, Sale selling, LocalDateTime insertedAt);

   Set<Cart> findAllByUser(User user);
}
