package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.controller.validator.ProductValidator;
import com.lambert.lambertecommerce.helpers.constants.PathSuffixes;
import com.lambert.lambertecommerce.helpers.products.Utils;
import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.ProductService;
import com.lambert.lambertecommerce.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + PathSuffixes.DASHBOARD)
public class SaleController {

   private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
   @Autowired
   private ProductValidator productValidator;
   @Autowired
   private ProductService productService;
   @Autowired
   private SaleService saleService;

   @GetMapping(value = "")
   public ModelAndView showAllSales() {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/index.html");
      modelAndView.addObject("sales", this.saleService.getAllSales());
      return modelAndView;
   }

   @GetMapping(value = "/searchProducts")
   public ModelAndView searchSales(@NotNull HttpServletRequest request) {
      String productName = request.getParameter("product-name");
      String productCategoryId = request.getParameter("category");
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/index.html");
      Set<Sale> sales = null;
      if (productName.isEmpty() && productCategoryId.isEmpty()) {
         sales = this.saleService.getAllSales();
      } else if (productCategoryId.isEmpty()) {
         sales = this.saleService.getAllSalesByProductName(productName);
      } else {
         Long longProductCategoryId = Long.parseLong(productCategoryId);
         if (productName.isEmpty()) {
            sales = this.saleService.getAllSalesByProductCategoryId(longProductCategoryId);
         } else {
            sales = this.saleService.getAllSalesByProductNameAndProductCategoryId(productName, longProductCategoryId);
         }
      }
      modelAndView.addObject("sales", sales);
      return modelAndView;
   }

   @GetMapping(value = "/newSale")
   public ModelAndView showNewSaleForm() {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/newSale.html");
      modelAndView.addObject("sale", new Sale());
      modelAndView.addObject("product", new Product());
      return modelAndView;
   }

   @PostMapping(value = "/publishNewSale")
   public ModelAndView publishNewSale(
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @Valid @ModelAttribute("product") Product product,
           BindingResult productBindingResult,
           @Valid @ModelAttribute("sale") Sale sale,
           BindingResult saleBindingResult,
           @RequestParam("product-image") MultipartFile productImage) {
      System.out.println(productBindingResult);
      System.out.println(saleBindingResult);
      final String publishSuccessful = "redirect:/" + PathSuffixes.DASHBOARD + "/product/";
      final String publishError = PathSuffixes.DASHBOARD + "/newSale.html";
      ModelAndView modelAndView = new ModelAndView(publishError);
      this.productValidator.setProductImage(productImage);
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors() && !saleBindingResult.hasErrors()) {
         Product savedProduct = this.productService.saveProduct(product);
         Sale savedSale = this.saleService.saveSale(sale, loggedUser, savedProduct);
         if (Utils.storeProductImage(savedProduct, productImage)) {
            modelAndView.setViewName(publishSuccessful + savedProduct.getId().toString());
            modelAndView.addObject("product", savedProduct);
            modelAndView.addObject("sale", savedSale);
         }
      } else {
         for (ObjectError error : productBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(error.getCode()), error.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = "/sale/{saleId}")
   public ModelAndView showSaleById(@PathVariable("saleId") Long saleId) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/sale.html");
      Sale sale = this.saleService.getSale(saleId);
      modelAndView.addObject("sale", sale);
      modelAndView.addObject("isAddedToCart", false);
      return modelAndView;
   }


}