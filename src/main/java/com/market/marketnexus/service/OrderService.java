package com.market.marketnexus.service;

import com.market.marketnexus.model.*;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
   @Autowired
   private OrderRepository orderRepository;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private UserService userService;
   @Autowired
   private CartRepository cartRepository;
   @Autowired
   private CartService cartService;

   public Set<Order> getAllOrdersByUser(@NotNull User user) {
      return this.orderRepository.findAllByUser(user);
   }

   @Transactional
   public Order makeOrder(Long userId) {
      Order savedOrder = null;
      User user = this.userRepository.findById(userId).orElse(null);
      if (user != null) {
         Cart cart = this.userService.getUserCurrentCart(user.getId());
         Float newUserOrderBalance = user.getBalance() - cart.getCartPrice();
         this.userService.updateUserBalance(user, newUserOrderBalance);
         this.cartService.updateCartLineItemsSalesIsSold(cart.getId());
         Order order = new Order(user, cart);
         savedOrder = this.orderRepository.save(order);
         Cart newCart = new Cart(user);
         Cart savedNewCart = this.cartRepository.save(newCart);
         //  Hibernate.initialize(user.getCarts());
         user.getCarts().add(savedNewCart);
         this.userRepository.save(user);
      }
      return savedOrder;
   }

   @Transactional
   public Set<Sale> getUserOrderedSales(@NotNull User user) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Order> orders = this.getAllOrdersByUser(user);
      Cart cart = null;
      List<CartLineItem> cartLineItems = null;
      Sale sale = null;
      for (Order order : orders) {
         cart = order.getCart();
         //   Hibernate.initialize(cart.getCartLineItems());
         cartLineItems = cart.getCartLineItems();
         for (CartLineItem cartLineItem : cartLineItems) {
            sale = cartLineItem.getSale();
            result.add(sale);
         }
      }
      return result;
   }

   public List<Object[]> getAllOrdersForUser(@NotNull Long userId) {
      return this.orderRepository.findAllByUserId(userId);
   }

}
