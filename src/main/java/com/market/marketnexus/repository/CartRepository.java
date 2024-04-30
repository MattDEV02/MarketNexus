package com.market.marketnexus.repository;

import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
   public Optional<Cart> findByUser(User user);

   public void deleteByUser(User user);
}
