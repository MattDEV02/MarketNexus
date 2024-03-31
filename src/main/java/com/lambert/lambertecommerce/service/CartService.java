package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Cart;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CartService {
   @Autowired
   protected CartRepository cartRepository;

   public Set<Cart> findAllCartsByUser(User user) {
      return this.cartRepository.findAllByUser(user);
   }

   @Transactional
   public Cart saveCart(Cart cart) {
      return this.cartRepository.save(cart);
   }
}
