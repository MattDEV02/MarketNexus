package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
   //Order findByUserSellingInsertedat(User user, Selling selling, LocalDateTime insertedAt);
}
