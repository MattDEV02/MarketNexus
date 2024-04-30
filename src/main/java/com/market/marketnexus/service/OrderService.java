package com.market.marketnexus.service;

import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
   @Autowired
   private OrderRepository orderRepository;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private UserService userService;
   @Autowired
   private CartService cartService;
   @Autowired
   private CartRepository cartRepository;

   public Order getOrder(Long orderId) {
      return this.orderRepository.findById(orderId).orElse(null);
   }

   public List<Object[]> getAllOrdersByUserId(@NotNull Long userId) {
      return this.orderRepository.findAllByUserId(userId);
   }

   @Transactional
   public Order makeOrder(Long userId, Cart cart) {
      User user = this.userRepository.findById(userId).orElse(null);
      if (user != null) {
         Order order = new Order(user, cart);
         Order savedOrder = this.orderRepository.save(order);
         Float newUserOrderBalance = user.getBalance() - order.getCart().getCartPrice();
         this.userService.updateUserBalance(user, newUserOrderBalance);
         Hibernate.initialize(user.getOrders());
         user.getOrders().add(savedOrder);
         cart.setUser(null);
         this.cartRepository.save(cart);
         Cart newCart = new Cart(user);
         user.setCart(newCart);
         this.userRepository.save(user);
         this.cartRepository.save(newCart);
         return savedOrder;
      } else {
         return null;
      }
   }

}
