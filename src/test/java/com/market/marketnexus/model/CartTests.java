package com.market.marketnexus.model;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartTests {
   @BeforeEach
   public void setUp() {

   }

   @After
   public void tearDown() {

   }

   @Test
   public void testCartStartPrice() {
      Cart cart = new Cart();
      assertNotNull(cart);
      assertNotNull(Cart.CART_START_PRICE);
      assertEquals(cart.getClass(), Cart.class);
      assertEquals(cart.getCartPrice().getClass(), Float.class);
      assertEquals(cart.getCartPrice(), 0.0F);
      assertTrue(cart.getCartLineItems().isEmpty());
   }

   @Test
   public void testCartCartLineItemsOrdering() {
      Cart cart = new Cart();
      Sale sale1 = new Sale();
      assertNotNull(cart);
      assertNotNull(sale1);
      assertFalse(sale1.getIsSold());
      assertEquals(sale1.getQuantity(), Sale.SALE_DEFAULT_QUANTITY);
      assertEquals(sale1.getSalePrice(), 0.0F);
      CartLineItem cartLineItem1 = new CartLineItem(cart, sale1);
      assertNotNull(cartLineItem1);
      assertEquals(cartLineItem1.getQuantity(), CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY);
      cartLineItem1.setInsertedAt(LocalDateTime.now());
      assertEquals(cartLineItem1.getInsertedAt().getClass(), LocalDateTime.class);
      Sale sale2 = new Sale();
      assertNotNull(sale2);
      CartLineItem cartLineItem2 = new CartLineItem(cart, sale2);
      assertNotNull(cartLineItem2);
      assertEquals(cartLineItem1.getQuantity(), CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY);
      cartLineItem2.setInsertedAt(LocalDateTime.MAX);
      assertEquals(cartLineItem2.getInsertedAt().getClass(), LocalDateTime.class);
      assertFalse(sale2.getIsSold());
      assertEquals(sale2.getQuantity(), Sale.SALE_DEFAULT_QUANTITY);
      assertEquals(sale2.getSalePrice(), 0.0F);
      Sale sale3 = new Sale();
      assertNotNull(sale3);
      assertFalse(sale3.getIsSold());
      assertEquals(sale3.getQuantity(), Sale.SALE_DEFAULT_QUANTITY);
      assertEquals(sale3.getSalePrice(), 0.0F);
      CartLineItem cartLineItem3 = new CartLineItem(cart, sale3);
      assertEquals(cartLineItem1.getQuantity(), CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY);
      assertNotNull(cartLineItem3);
      cartLineItem3.setInsertedAt(LocalDateTime.MIN);
      assertEquals(cartLineItem3.getInsertedAt().getClass(), LocalDateTime.class);
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      assertNotNull(cartLineItems);
      assertEquals(cartLineItems, Lists.newArrayList());
      assertEquals(cartLineItems.size(), 0);
      cartLineItems.add(cartLineItem1);
      assertEquals(cartLineItems.size(), 1);
      cartLineItems.add(cartLineItem2);
      assertEquals(cartLineItems.size(), 2);
      cartLineItems.add(cartLineItem3);
      assertEquals(cartLineItems.size(), 3);
      assertTrue(LocalDateTime.MAX.isAfter(LocalDateTime.now()));
      assertTrue(LocalDateTime.MAX.compareTo(LocalDateTime.now()) > 0);
      assertEquals(cartLineItems, Lists.newArrayList(cartLineItem2, cartLineItem1, cartLineItem3));
      assertThrows(IndexOutOfBoundsException.class, () -> cartLineItems.get(4));
      assertEquals(cart.getCartPrice(), Cart.CART_START_PRICE);
   }


}
