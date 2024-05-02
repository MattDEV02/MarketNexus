package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.credentials.Roles;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartService;
import com.market.marketnexus.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping(value = "/" + APIPrefixes.CART)
public class CartController {

   @Autowired
   private SaleService saleService;
   @Autowired
   private CartService cartService;


   @GetMapping(value = {"", "/"})
   public ModelAndView showCartLineItem(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.CART + GlobalValues.TEMPLATES_EXTENSION);
      Cart cart = loggedUser.getCart();
      Set<CartLineItem> cartLineItems = this.cartService.getAllCartLineItems(cart.getId());
      modelAndView.addObject("cart", cart);
      modelAndView.addObject("cartLineItems", cartLineItems);
      return modelAndView;
   }

   @GetMapping(value = {"/{saleId}", "/{saleId}/"})
   public ModelAndView addSaleProductToCartById(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("saleId") Long saleId, @RequestParam("quantity") Integer quantity) {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.SALE + GlobalValues.TEMPLATES_EXTENSION);
      Sale sale = this.saleService.getSale(saleId);
      modelAndView.addObject("sale", sale);
      modelAndView.addObject("isAddedToCart", false);
      if (!loggedUser.getCredentials().getRole().contains(Roles.BUYER_ROLE.toString())) {
         modelAndView.addObject("userNotBuyerAddOwnSaleToCartError", true);
         return modelAndView;
      }
      Cart cart = loggedUser.getCart();
      if (loggedUser.equals(sale.getUser())) {
         modelAndView.addObject("userAddOwnSaleToCartError", true);
      } else if (this.cartService.existsCartLineItemSale(cart.getId(), sale)) {
         modelAndView.addObject("userAddExistingSaleToCartError", true);
      } else {
         CartLineItem savedCartLineItem = this.cartService.makeCartLineItem(cart.getId(), sale);
         modelAndView.addObject("sale", savedCartLineItem.getSale());
         modelAndView.addObject("isAddedToCart", TypeValidators.validateTimestamp(savedCartLineItem.getInsertedAt())); //
      }
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{cartLineItemId}", "/delete/{cartLineItemId}/"})
   public ModelAndView deleteCartLineItemById(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("cartLineItemId") Long cartLineItemId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPrefixes.CART);
      Cart cart = loggedUser.getCart();
      if (this.cartService.deleteCartLineItem(cart.getId(), cartLineItemId)) {
         modelAndView.addObject("cartDeletedSuccess", true);
      } else {
         modelAndView.addObject("cartNotDeletedError", true);
      }
      return modelAndView;
   }
}
