package com.market.marketnexus.service;

import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.repository.CartLineItemRepository;
import com.market.marketnexus.repository.CartRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
   @Autowired
   private CartRepository cartRepository;
   @Autowired
   private CartLineItemRepository cartLineItemRepository;

   public Boolean existsCartBtId(Long cartId) {
      return this.cartRepository.existsById(cartId);
   }

   public Cart getCart(Long cartId) {
      return this.cartRepository.findById(cartId).orElse(null);
   }

   @Transactional
   public void updateCartLineItemsSalesIsSold(Long cartId) {
      Cart cart = this.getCart(cartId);
      //Hibernate.initialize(cart.getCartLineItems());
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      Sale sale = null;
      for (CartLineItem cartLineItem : cartLineItems) {
         sale = cartLineItem.getSale();
         sale.setIsSold(true);
      }
   }

   @Transactional
   public List<CartLineItem> getAllCartLineItems(Long cartId) {
      List<CartLineItem> result = new ArrayList<CartLineItem>();
      Cart cart = this.getCart(cartId);
      if (cart != null) {
         result = new ArrayList<CartLineItem>();
         //Hibernate.initialize(cart.getCartLineItems());
         List<CartLineItem> cartLineItems = cart.getCartLineItems();
         cart.sortCartLineItemsByInsertedAt();
         Sale sale = null;
         for (CartLineItem cartLineItem : cartLineItems) {
            sale = cartLineItem.getSale();
            if (!sale.getIsSold()) {
               result.add(cartLineItem);
            }
         }
      }
      return result;
   }

   @Transactional
   public List<CartLineItem> getAllSoldCartLineItems(Long cartId) {
      Cart cart = this.getCart(cartId);
      List<CartLineItem> result = new ArrayList<CartLineItem>();
      if (cart != null) {
         //Hibernate.initialize(cart.getCartLineItems());
         List<CartLineItem> cartLineItems = cart.getCartLineItems();
         Sale sale = null;
         for (CartLineItem cartLineItem : cartLineItems) {
            sale = cartLineItem.getSale();
            if (sale.getIsSold()) {
               result.add(cartLineItem);
            }
         }
      }
      return result;
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

   public Float calculateCartPrice(@NotNull Cart cart) {
      Float result = 0F;
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      for (CartLineItem cartLineItem : cartLineItems) {
         result += cartLineItem.getSale().getSalePrice();
      }
      return Utils.roundNumberTo2Decimals(result);
   }
}
