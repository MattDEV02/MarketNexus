package com.market.marketnexus.service;

import com.market.marketnexus.model.*;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import com.market.marketnexus.service.email.OrderedUserSaleEmailService;
import jakarta.mail.MessagingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
   @Autowired
   private OrderedUserSaleEmailService orderedUserSaleEmailService;

   public Iterable<Order> getAllOrdersByUser(@NotNull User user) {
      return this.orderRepository.findAllByUserOrderByInsertedAtDesc(user);
   }

   @Transactional
   public Order makeOrder(Long userId) {
      Order savedOrder = null;
      User user = this.userRepository.findById(userId).orElse(null);
      if (user != null) {
         Cart cart = this.userService.getUserCurrentCart(user.getId());
         Float newUserOrderBalance = user.getBalance() - cart.getCartPrice();
         this.userService.updateUserBalance(user, newUserOrderBalance);
         this.cartService.updateCartLineItemsSalesQuantityAndIsSold(cart);
         Order order = new Order(user, cart);
         savedOrder = this.orderRepository.save(order);
         Cart newCart = new Cart(user);
         Cart savedNewCart = this.cartRepository.save(newCart);
         user.getCarts().add(savedNewCart);
         this.userRepository.save(user);
      }
      return savedOrder;
   }

   public Set<Sale> getUserOrderedSales(@NotNull User user) {
      Set<Sale> orderedSales = new HashSet<Sale>();
      Iterable<Order> orders = this.getAllOrdersByUser(user);
      Cart cart = null;
      List<CartLineItem> cartLineItems = null;
      Sale sale = null;
      for (Order order : orders) {
         cart = order.getCart();
         cartLineItems = cart.getCartLineItems();
         for (CartLineItem cartLineItem : cartLineItems) {
            sale = cartLineItem.getSale();
            orderedSales.add(sale);
         }
      }
      return orderedSales;
   }

   public List<Object[]> getAllOrdersForUser(@NotNull Long userId) {
      return this.orderRepository.findAllByUserId(userId);
   }

   public void sendSalesOrderEmail(@NotNull User userBuyer, @NotNull Order order) {
      try {
         Set<Sale> sales = order.getCart().getCartLineItems().stream()
                 .map(CartLineItem::getSale)
                 .collect(Collectors.toSet());
         for (Sale sale : sales) {
            this.orderedUserSaleEmailService.sendEmail(userBuyer, sale);
         }
      } catch (IOException | MessagingException exception) {
         exception.printStackTrace();
      }
   }

}
