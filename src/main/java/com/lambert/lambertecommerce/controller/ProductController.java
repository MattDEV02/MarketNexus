package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

   private ProductRepository productRepository;

   @GetMapping(value = "/admin/newProductForm")
   public ModelAndView formNewProduct() {
      ModelAndView modelAndView = new ModelAndView("admin/newProductForm.html");
      modelAndView.addObject("product", new Product());
      return modelAndView;
   }

   @PostMapping("/admin/product")
   public ModelAndView newProduct(@ModelAttribute("artist") Product product) {
      final String registrationSuccessful = "product.html";
      final String registrationError = "admin/newProductForm.html";
      ModelAndView modelAndView = new ModelAndView();
      if (!productRepository.existsById(product.getId())) {
         this.productRepository.save(product);
         modelAndView.addObject("product", product);
         modelAndView.setViewName(registrationSuccessful);
      } else {
         modelAndView.addObject("errorMessage", "Product already exists.");
         modelAndView.setViewName(registrationSuccessful);
      }
      return modelAndView;
   }

   @GetMapping("/product/{id}")
   public ModelAndView getProductById(@PathVariable("id") Long id) {
      ModelAndView modelAndView = new ModelAndView("product.html");
      modelAndView.addObject("product", this.productRepository.findById(id));
      return modelAndView;
   }

   @GetMapping("/artist")
   public ModelAndView getArtists() {
      ModelAndView modelAndView = new ModelAndView("products.html");
      modelAndView.addObject("products", this.productRepository.findAll());
      return modelAndView;
   }
}
