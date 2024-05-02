package com.market.marketnexus.service;

import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartLineItemRepository;
import com.market.marketnexus.repository.CartRepository;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartService {
   @Autowired
   private CartRepository cartRepository;
   @Autowired
   private CartLineItemRepository cartLineItemRepository;

   public Boolean existsCartBtId(Long cartId) {
      return this.cartRepository.existsById(cartId);
   }

   @Transactional
   public Cart saveCart(Cart cart) {
      Float cartPrice = this.calculateCartPrice(cart);
      cart.setCartPrice(cartPrice);
      return this.cartRepository.save(cart);
   }

   public Cart getCart(Long cartId) {
      return this.cartRepository.findById(cartId).orElse(null);
   }


   public Cart getCart(User user) {
      return this.cartRepository.findByUser(user).orElse(null);
   }

   @Transactional
   public void updateCartLineItemsSalesIsSold(Long cartId) {
      Cart cart = this.getCart(cartId);
      Hibernate.initialize(cart.getCartLineItems());
      Set<CartLineItem> cartLineItems = cart.getCartLineItems();
      Sale sale = null;
      for (CartLineItem cartLineItem : cartLineItems) {
         sale = cartLineItem.getSale();
         sale.setIsSold(true);
      }
   }

   @Transactional
   public Set<CartLineItem> getAllCartLineItems(Long cartId) {
      Cart cart = this.getCart(cartId);
      if (cart != null) {
         Set<CartLineItem> result = new HashSet<CartLineItem>();
         Hibernate.initialize(cart.getCartLineItems());
         Set<CartLineItem> cartLineItems = cart.getCartLineItems();
         Sale sale = null;
         for (CartLineItem cartLineItem : cartLineItems) {
            sale = cartLineItem.getSale();
            if (!sale.getIsSold()) {
               result.add(cartLineItem);
            }
         }
         return result;
      } else {
         return null;
      }
   }

   @Transactional
   public Set<CartLineItem> getAllSoldCartLineItems(Long cartId) {
      Cart cart = this.getCart(cartId);
      if (cart != null) {
         Set<CartLineItem> result = new HashSet<CartLineItem>();
         Hibernate.initialize(cart.getCartLineItems());
         Set<CartLineItem> cartLineItems = cart.getCartLineItems();
         Sale sale = null;
         for (CartLineItem cartLineItem : cartLineItems) {
            sale = cartLineItem.getSale();
            if (sale.getIsSold()) {
               result.add(cartLineItem);
            }
         }
         return result;
      } else {
         return null;
      }
   }

   public Boolean existsCartLineItemSale(Long cartId, Sale sale) {
      Cart cart = this.getCart(cartId);
      return this.cartLineItemRepository.existsByCartAndSale(cart, sale);
   }

   @Transactional
   public CartLineItem makeCartLineItem(Long cartId, Sale sale) {
      Cart cart = this.getCart(cartId);
      CartLineItem cartLineItem = new CartLineItem(cart, sale);
      CartLineItem cartLineItemSaved = this.cartLineItemRepository.save(cartLineItem); //
      //Hibernate.initialize(cart.getCartLineItems());
      cart.getCartLineItems().add(cartLineItemSaved);
      Float newCartPrice = this.calculateCartPrice(cart);
      cart.setCartPrice(newCartPrice);
      this.cartRepository.save(cart);
      //this.cartLineItemRepository.save(cartLineItem);
      return cartLineItemSaved;
   }

   @Transactional
   public Boolean deleteCartLineItem(@NotNull Long cartId, Long cartLineItemId) {
      Cart cart = this.getCart(cartId);
      // Hibernate.initialize(cart.getCartLineItems());
      CartLineItem cartLineItemToRemove = cart.getCartLineItems().stream()
              .filter(cartLineItem -> cartLineItem.getId().equals(cartLineItemId))
              .findFirst().orElse(null);
      cart.getCartLineItems().remove(cartLineItemToRemove);
      Float newCartPrice = this.calculateCartPrice(cart);
      cart.setCartPrice(newCartPrice);
      this.cartRepository.save(cart);
      return !this.cartLineItemRepository.existsById(cartLineItemId);
   }

   @Transactional
   public Boolean deleteAllCartLineItems(@NotNull Long cartId) {
      Cart cart = this.getCart(cartId);
      // Hibernate.initialize(cart.getCartLineItems());
      cart.getCartLineItems().clear();
      Float newCartPrice = 0.0F;
      cart.setCartPrice(newCartPrice);
      this.cartRepository.save(cart);
      return !this.cartLineItemRepository.findAll().iterator().hasNext();
   }

   public Float calculateCartPrice(@NotNull Cart cart) {
      Float result = 0F;
      Set<CartLineItem> cartLineItems = cart.getCartLineItems();
      for (CartLineItem cartLineItem : cartLineItems) {
         result += cartLineItem.getSale().getSalePrice();
      }
      return Utils.roundNumberTo2Decimals(result);
   }
}
