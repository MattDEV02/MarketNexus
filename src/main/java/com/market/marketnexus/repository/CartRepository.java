package com.market.marketnexus.repository;

import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
   public void deleteByUser(User user);
}
