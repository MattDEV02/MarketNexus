package com.market.marketnexus.service;

import com.market.marketnexus.helpers.validators.DateValidators;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class OrderService {
   @Autowired
   protected OrderRepository orderRepository;

   public Order getOrder(Long orderId) {
      return this.orderRepository.findById(orderId).orElse(null);
   }

   public Set<Order> getAllOrdersByUser(User user) {
      return this.orderRepository.findAllByUser(user);
   }

   @Transactional
   public Order saveOrder(Order order) {
      return this.orderRepository.save(order);
   }

   @Transactional
   public Boolean deleteOrder(Order order) {
      this.orderRepository.delete(order);
      return DateValidators.validateTimestamp(order.getInsertedAt());
   }
}
