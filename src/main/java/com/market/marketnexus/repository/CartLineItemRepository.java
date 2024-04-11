package com.market.marketnexus.repository;

import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CartLineItemRepository extends CrudRepository<CartLineItem, Long> {
   // CartLineItem findByUserSaleInsertedat(User user, Sale sale, LocalDateTime insertedAt);

   Set<CartLineItem> findAllByUser(User user);
}
