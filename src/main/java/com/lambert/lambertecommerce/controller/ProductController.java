package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.controller.validator.ProductValidator;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.lambert.lambertecommerce.helpers.constants.FieldSizes.PRODUCT_NAME_MAX_LENGTH;
import static com.lambert.lambertecommerce.helpers.constants.FieldSizes.PRODUCT_NAME_MIN_LENGTH;

@Controller
public class ProductController {

   Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;
   @Autowired
   private NationService nationService;
   @Autowired
   private ProductValidator productValidator;
   @Autowired
   private ProductService productService;
   @Autowired
   private ProductCategoryService productCategoryService;

  
   @GetMapping(value = "/dashboard")
   public ModelAndView showAdminIndex() {
      ModelAndView modelAndView = new ModelAndView("dashboard/index.html");
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      System.out.println(authentication.toString());
      if (authentication.isAuthenticated()) {
         System.out.println("Va bene");
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
         User user = this.userService.getUser(credentials.getUser().getId());
         modelAndView.addObject("user", user);
         modelAndView.addObject("credentials", credentials);
         modelAndView.addObject("products", this.productService.getAllProducts());
         modelAndView.addObject("PRODUCT_NAME_MIN_LENGTH", PRODUCT_NAME_MIN_LENGTH);
         modelAndView.addObject("PRODUCT_NAME_MAX_LENGTH", PRODUCT_NAME_MAX_LENGTH);
         modelAndView.addObject("categories", this.productCategoryService.getAllProductCategories());
      } else {
         System.out.println("Non va bene");
      }
      return modelAndView;
   }

   @GetMapping(value = "/dashboard/searchProduct")
   public ModelAndView searchProduct(@Valid @ModelAttribute("product") Product product,
                                     BindingResult productBindingResult) {
      ModelAndView modelAndView = new ModelAndView("dashboard/product.html");

      return modelAndView;
   }

   public ModelAndView showAccount() {
      ModelAndView modelAndView = new ModelAndView("dashboard/account.html");
      return modelAndView;
   }

   public ModelAndView showCart() {
      ModelAndView modelAndView = new ModelAndView("dashboard/cart.html");
      return modelAndView;
   }

   @GetMapping(value = "/dashboard/newProduct")
   public ModelAndView formNewProduct() {
      ModelAndView modelAndView = new ModelAndView("dashboard/newProduct.html");
      modelAndView.addObject("product", new Product());
      return modelAndView;
   }

   @PostMapping("/dashboard/publishNewProduct")
   public ModelAndView newProduct(@ModelAttribute("artist") Product product) {
      final String registrationSuccessful = "dashboard/product/index.html";
      final String registrationError = "dashboard/newProduct.html";
      ModelAndView modelAndView = new ModelAndView();
      if (!productService.existsById(product.getId())) {
         this.productService.saveProduct(product);
         // TODO: successful message
         modelAndView.addObject("product", product);
         modelAndView.setViewName(registrationSuccessful);
      } else {
         modelAndView.addObject("errorMessage", "Product already exists.");
         modelAndView.setViewName(registrationError);
      }
      return modelAndView;
   }

   @GetMapping("/dashboard/product/{id}")
   public ModelAndView getProductById(@PathVariable("id") Long id) {
      ModelAndView modelAndView = new ModelAndView("dashboard/product/index.html");
      modelAndView.addObject("product", this.productService.findById(id));
      return modelAndView;
   }
/*
   @GetMapping("/artist")
   public ModelAndView getArtists() {
      ModelAndView modelAndView = new ModelAndView("dashboard/index.html");
      modelAndView.addObject("products", this.productService.findAll());
      return modelAndView;
   }
   */

}
