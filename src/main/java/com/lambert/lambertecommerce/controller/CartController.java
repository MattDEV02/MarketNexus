package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.helpers.constants.PathSuffixes;
import com.lambert.lambertecommerce.helpers.validators.DateValidators;
import com.lambert.lambertecommerce.model.Cart;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.CartService;
import com.lambert.lambertecommerce.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping(value = "/" + PathSuffixes.CART)
public class CartController {

   @Autowired
   private SaleService saleService;
   @Autowired
   private CartService cartService;

   @GetMapping(value = "")
   public ModelAndView showCart(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/cart.html");
      Set<Cart> cartProducts = this.cartService.getAllCartsByUser(loggedUser);
      modelAndView.addObject("cartProducts", cartProducts);
      return modelAndView;
   }

   @GetMapping(value = "/{saleId}")
   public ModelAndView addSaleProductToCartById(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("saleId") Long saleId, @RequestParam("quantity") Integer quantity) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/sale.html");
      Sale sale = this.saleService.getSale(saleId);
      Cart cart = new Cart(sale, loggedUser, quantity);
      Cart savedCart = this.cartService.saveCart(cart);
      modelAndView.addObject("sale", savedCart.getSale());
      modelAndView.addObject("isAddedToCart", DateValidators.validateTimestamp(savedCart.getInsertedAt()));
      return modelAndView;
   }

   @GetMapping(value = "/delete/{cartId}")
   public ModelAndView deleteCartById(@PathVariable("cartId") Long cartId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + PathSuffixes.DASHBOARD + "/cart");
      Cart cartToDelete = this.cartService.getCart(cartId);
      if (!this.cartService.deleteCart(cartToDelete)) {
         modelAndView.addObject("errorCartNotDeleted", true);
      }
      return modelAndView;
   }
}
