package com.market.marketnexus.repository;

import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Sale;
import org.springframework.data.repository.CrudRepository;

public interface CartLineItemRepository extends CrudRepository<CartLineItem, Long> {
   public Boolean existsByCartAndSale(Cart cart, Sale sale);


}
