package com.market.marketnexus.service;

import com.market.marketnexus.helpers.validators.DateValidators;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
   @Autowired
   protected CartRepository cartRepository;

   public Cart getCart(Long cartId) {
      return this.cartRepository.findById(cartId).orElse(null);
   }

   public Set<Cart> getAllCartsByUser(User user) {
      return this.cartRepository.findAllByUser(user);
   }

   @Transactional
   public Cart saveCart(Cart cart) {
      return this.cartRepository.save(cart);
   }

   @Transactional
   public Boolean deleteCart(Cart cart) {
      this.cartRepository.delete(cart);
      return DateValidators.validateTimestamp(cart.getInsertedAt());
   }

   @Transactional
   public Cart deleteCartById(Long cartId) {
      Optional<Cart> cartToDelete = this.cartRepository.findById(cartId);
      this.cartRepository.deleteById(cartId);
      return cartToDelete.orElse(null);
   }
}
