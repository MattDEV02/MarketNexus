package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Order;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderService {
   @Autowired
   protected OrderRepository orderRepository;

   public Set<Order> findAllOrdersByUser(User user) {
      return this.orderRepository.findAllByUser(user);
   }
}
