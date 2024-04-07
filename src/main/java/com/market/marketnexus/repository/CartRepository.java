package com.market.marketnexus.repository;

import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CartRepository extends CrudRepository<Cart, Long> {
   // Cart findByUserSaleInsertedat(User user, Sale sale, LocalDateTime insertedAt);

   Set<Cart> findAllByUser(User user);
}
