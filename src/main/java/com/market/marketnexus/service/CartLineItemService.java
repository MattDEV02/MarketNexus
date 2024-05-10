package com.market.marketnexus.service;

import com.market.marketnexus.repository.CartLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartLineItemService {
   @Autowired
   protected CartLineItemRepository cartLineItemRepository;
}
