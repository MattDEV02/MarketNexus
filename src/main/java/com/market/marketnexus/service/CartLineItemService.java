package com.market.marketnexus.service;

import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.repository.CartLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartLineItemService {
   @Autowired
   protected CartLineItemRepository cartLineItemRepository;

   public CartLineItem getCartLineItems(Long cartLineItemId) {
      return this.cartLineItemRepository.findById(cartLineItemId).orElse(null);
   }

   @Transactional
   public CartLineItem saveCartLineItem(CartLineItem cartLineItem) {
      return this.cartLineItemRepository.save(cartLineItem);
   }

   @Transactional
   public Boolean deleteCartLineItem(CartLineItem cartLineItemToDelete) {
      this.cartLineItemRepository.delete(cartLineItemToDelete);
      return this.cartLineItemRepository.existsById(cartLineItemToDelete.getId());
   }

   @Transactional
   public CartLineItem deleteCartLineItemById(Long cartLineItemId) {
      Optional<CartLineItem> cartToDelete = this.cartLineItemRepository.findById(cartLineItemId);
      this.cartLineItemRepository.deleteById(cartLineItemId);
      return cartToDelete.orElse(null);
   }

}
