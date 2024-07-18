package com.market.marketnexus.service;

import com.market.marketnexus.helpers.constants.FieldSizes;
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

   @Transactional
   public void updateCartLineItemsSalesQuantityAndIsSold(@NotNull Cart cart) {
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      for (CartLineItem cartLineItem : cartLineItems) {
         Sale sale = cartLineItem.getSale();
         Integer newSaleQuantity = sale.getQuantity() - cartLineItem.getQuantity();
         sale.setQuantity(newSaleQuantity);
         Float newSalePrice = Utils.calculateSalePrice(sale, Sale.SALE_DEFAULT_QUANTITY);
         sale.setSalePrice(newSalePrice);
         sale.setIsSold(true);
      }
   }

   public List<CartLineItem> getAllNotSoldCartLineItems(@NotNull Cart cart) {
      List<CartLineItem> notSoldSalesCartLineItems = new ArrayList<CartLineItem>();
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      Sale sale = null;
      for (CartLineItem cartLineItem : cartLineItems) {
         sale = cartLineItem.getSale();
         if (sale != null && sale.getQuantity() > 0) {
            notSoldSalesCartLineItems.add(cartLineItem);
         }
      }
      return notSoldSalesCartLineItems;
   }

   public Boolean existsCartLineItemSale(@NotNull Cart cart, @NotNull Sale sale) {
      return this.cartLineItemRepository.existsByCartAndSale(cart, sale);
   }

   @Transactional
   public CartLineItem makeCartLineItem(@NotNull Cart cart, @NotNull Sale sale) {
      CartLineItem cartLineItem = new CartLineItem(cart, sale);
      CartLineItem cartLineItemSaved = this.cartLineItemRepository.save(cartLineItem);
      cart.addCartLineItem(cartLineItem);
      Float newCartPrice = this.calculateCartPrice(cart);
      cart.setCartPrice(newCartPrice);
      this.cartRepository.save(cart);
      return cartLineItemSaved;
   }

   @Transactional
   public Boolean deleteCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId) {
      CartLineItem cartLineItemToRemove = cart.getCartLineItem(cartLineItemId);
      if (cartLineItemToRemove != null) {
         cart.removeCartLineItem(cartLineItemToRemove);
         Float newCartPrice = this.calculateCartPrice(cart);
         cart.setCartPrice(newCartPrice);
         this.cartRepository.save(cart);
      } else {
         return false;
      }
      return !this.cartLineItemRepository.existsById(cartLineItemId);
   }

   public void updateCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId, Integer quantity) {
      CartLineItem cartLineItemToUpdate = cart.getCartLineItem(cartLineItemId);
      if (cartLineItemToUpdate != null && quantity >= FieldSizes.CARTLINEITEM_QUANTITY_MIN_VALUE && quantity <= FieldSizes.CARTLINEITEM_QUANTITY_MAX_VALUE && quantity <= cartLineItemToUpdate.getSale().getQuantity()) {
         cartLineItemToUpdate.setQuantity(quantity);
         Float newCartLineItemPrice = Utils.calculateSalePrice(cartLineItemToUpdate.getSale(), cartLineItemToUpdate.getQuantity());
         cartLineItemToUpdate.setCartLineItemPrice(newCartLineItemPrice);
         this.cartLineItemRepository.save(cartLineItemToUpdate);
         Float newCartPrice = this.calculateCartPrice(cart);
         cart.setCartPrice(newCartPrice);
         this.cartRepository.save(cart);
      }
   }

   public Float calculateCartPrice(@NotNull Cart cart) {
      Float cartPrice = 0.0F;
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      for (CartLineItem cartLineItem : cartLineItems) {
         cartPrice += cartLineItem.getCartLineItemPrice();
      }
      return Utils.roundNumberTo2Decimals(cartPrice);
   }
}
