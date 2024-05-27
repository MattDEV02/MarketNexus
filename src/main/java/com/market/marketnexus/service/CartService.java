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

   @Transactional
   public void updateCartLineItemsSalesIsSold(@NotNull Cart cart) {
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      Sale sale = null;
      for (CartLineItem cartLineItem : cartLineItems) {
         sale = cartLineItem.getSale();
         sale.setIsSold(true);
      }
   }

   public List<CartLineItem> getAllSoldCartLineItems(@NotNull Cart cart) {
      List<CartLineItem> soldSalesCartLineItems = new ArrayList<CartLineItem>();
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      Sale sale = null;
      for (CartLineItem cartLineItem : cartLineItems) {
         sale = cartLineItem.getSale();
         if (sale != null && sale.getIsSold()) {
            soldSalesCartLineItems.add(cartLineItem);
         }
      }
      return soldSalesCartLineItems;
   }

   public Boolean existsCartLineItemSale(@NotNull Cart cart, @NotNull Sale sale) {
      return this.cartLineItemRepository.existsByCartAndSale(cart, sale);
   }

   @Transactional
   public CartLineItem makeCartLineItem(@NotNull Cart cart, @NotNull Sale sale) {
      CartLineItem cartLineItem = new CartLineItem(cart, sale);
      CartLineItem cartLineItemSaved = this.cartLineItemRepository.save(cartLineItem);
      cart.getCartLineItems().add(cartLineItemSaved);
      Float newCartPrice = this.calculateCartPrice(cart);
      cart.setCartPrice(newCartPrice);
      this.cartRepository.save(cart);
      return cartLineItemSaved;
   }

   @Transactional
   public Boolean deleteCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId) {
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
      Float cartPrice = 0.0F;
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      Sale sale = null;
      for (CartLineItem cartLineItem : cartLineItems) {
         sale = cartLineItem.getSale();
         cartPrice += sale.getSalePrice();
      }
      return Utils.roundNumberTo2Decimals(cartPrice);
   }
}
