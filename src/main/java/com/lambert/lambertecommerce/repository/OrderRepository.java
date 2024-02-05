package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Order;
import com.lambert.lambertecommerce.model.Selling;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface OrderRepository extends CrudRepository<Order, Long> {
   Order findByUserSellingInsertedat(User user, Selling selling, LocalDateTime insertedAt);
}
