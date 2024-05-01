package com.market.marketnexus.service;

import com.market.marketnexus.model.*;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import org.hibernate.Hibernate;
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

   public Order getOrder(Long orderId) {
      return this.orderRepository.findById(orderId).orElse(null);
   }

   public List<Object[]> getAllOrdersByUserId(@NotNull Long userId) {
      return this.orderRepository.findAllByUserId(userId);
   }

   public Set<Order> getAllOrdersByUser(@NotNull User user) {
      return this.orderRepository.findAllByUser(user);
   }

   @Transactional
   public Order makeOrder(User user, Cart cart) {
      if (user != null) {
         Order order = new Order(user, cart);
         Order savedOrder = this.orderRepository.save(order);
         Float newUserOrderBalance = user.getBalance() - order.getCart().getCartPrice();
         this.userService.updateUserBalance(user, newUserOrderBalance);
         this.cartService.updateCartLineItemsSalesIsSold(cart.getId());
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

   @Transactional
   public Set<Sale> getUserOrderedSales(@NotNull User user) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Order> orders = this.getAllOrdersByUser(user);
      Cart cart = null;
      Set<CartLineItem> cartLineItems = null;
      Sale sale = null;
      for (Order order : orders) {
         cart = order.getCart();
         Hibernate.initialize(cart.getCartLineItems());
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
