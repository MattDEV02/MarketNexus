package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Cart;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CartRepository extends CrudRepository<Cart, Long> {
   // Cart findByUserSaleInsertedat(User user, Sale sale, LocalDateTime insertedAt);

   Set<Cart> findAllByUser(User user);
}
