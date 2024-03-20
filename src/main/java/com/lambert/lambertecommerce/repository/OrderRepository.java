package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Order;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OrderRepository extends CrudRepository<Order, Long> {
   //Order findByUserSellingInsertedat(User user, Sale selling, LocalDateTime insertedAt);

   public Set<Order> findAllByUser(User user);
}
