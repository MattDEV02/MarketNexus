package com.market.marketnexus.repository;

import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OrderRepository extends CrudRepository<Order, Long> {
   //Order findByUserSaleInsertedat(User user, Sale selling, LocalDateTime insertedAt);

   public Set<Order> findAllByUser(User user);
}
