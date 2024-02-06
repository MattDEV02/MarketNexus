package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
   // Cart findByUserSellingInsertedat(User user, Selling selling, LocalDateTime insertedAt);
}
