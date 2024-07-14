package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.ProductValidator;
import com.market.marketnexus.controller.validator.SaleValidator;
import com.market.marketnexus.exception.SaleNotFoundException;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.product.ProductImageFileUtils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.ProductCategoryService;
import com.market.marketnexus.service.ProductService;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.notification.PublishedSaleNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(value = "/" + APIPaths.SALES)
public class SaleController {

   public final static String PUBLISH_SUCCESSFUL_VIEW = "redirect:/" + APIPaths.SALES + "/sale/";
   public final static String PUBLISH_ERROR_VIEW = APIPaths.MARKETPLACE + "/newSale" + GlobalValues.TEMPLATES_EXTENSION;
   private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
   @Autowired
   private ProductCategoryService productCategoryService;
   @Autowired
   private ProductService productService;
   @Autowired
   private SaleService saleService;
   @Autowired
   private PublishedSaleNotificationService publishedSaleNotificationService;
   @Autowired
   private SaleValidator saleValidator;
   @Autowired
   private ProductValidator productValidator;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllSales() {
      ModelAndView modelAndView = new ModelAndView(APIPaths.MARKETPLACE + "/sales.html");
      Iterable<Sale> sales = this.saleService.getAllSales();
      modelAndView.addObject("sales", sales);
      modelAndView.addObject("hasSearchedSales", false);
      return modelAndView;
   }

   @GetMapping(value = {"/searchSales", "/searchSales/"})
   public ModelAndView searchSales(@NonNull HttpServletRequest request, @Valid @ModelAttribute("loggedUser") User loggedUser) {
      String productName = request.getParameter("product-name");
      String productCategoryId = request.getParameter("category");
      Boolean isAsyncSearch = Boolean.valueOf(request.getParameter("isAsyncSearch"));
      SaleController.LOGGER.info("User username: {} has searched sale with product name: {} and product category ID: {}", loggedUser != null ? loggedUser.getCredentials().getUsername() : "Google User", productName, productCategoryId);
      ModelAndView modelAndView = new ModelAndView(APIPaths.SALES + GlobalValues.TEMPLATES_EXTENSION + (isAsyncSearch ? " :: dynamicSalesSection" : ""));
      Iterable<Sale> searchedSales = null;
      if (productName.isEmpty() && productCategoryId.isEmpty()) {
         searchedSales = this.saleService.getAllSales();
      } else if (productCategoryId.isEmpty()) {
         searchedSales = this.saleService.getAllSalesByProductName(productName);
      } else {
         try {
            Long longProductCategoryId = Long.parseLong(productCategoryId);
            modelAndView.addObject("searchedProductCategoryName", this.productCategoryService.getProductCategory(longProductCategoryId).getName());
            searchedSales = productName.isEmpty() ? this.saleService.getAllSalesByProductCategoryId(longProductCategoryId) : this.saleService.getAllSalesByProductNameAndProductCategoryId(productName, longProductCategoryId);
         } catch (NumberFormatException numberFormatException) {
            SaleController.LOGGER.error(numberFormatException.getMessage());
            searchedSales = this.saleService.getAllSales();
         }
      }
      modelAndView.addObject("sales", searchedSales);
      modelAndView.addObject("searchedProductName", productName);
      modelAndView.addObject("hasSearchedSales", true);
      return modelAndView;
   }

   @GetMapping(value = {"/newSale", "/newSale/"})
   public ModelAndView showNewSaleForm() {
      ModelAndView modelAndView = new ModelAndView(APIPaths.MARKETPLACE + "/newSale" + GlobalValues.TEMPLATES_EXTENSION);
      modelAndView.addObject("sale", new Sale());
      modelAndView.addObject("product", new Product());
      modelAndView.addObject("isUpdate", false);
      return modelAndView;
   }

   @PostMapping(value = {"/publishNewSale", "/publishNewSale/"})
   public ModelAndView publishNewSale(
           @Valid @ModelAttribute("product") Product product,
           @NonNull BindingResult productBindingResult,
           @Valid @ModelAttribute("sale") Sale sale,
           @NonNull BindingResult saleBindingResult,
           @RequestParam("product-images") MultipartFile[] productImages,
           @Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(SaleController.PUBLISH_ERROR_VIEW);
      sale.setUser(loggedUser);
      sale.setProduct(product);
      this.saleValidator.validate(sale, saleBindingResult);
      this.productValidator.setProductImages(productImages);
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors() && !saleBindingResult.hasErrors()) {
         final Integer notEmptyProductImagesNumber = productImages.length;
         Product savedProduct = this.productService.saveProduct(product, notEmptyProductImagesNumber);
         for (Integer i = 0; i < notEmptyProductImagesNumber; i++) {
            ProductImageFileUtils.storeProductImage(savedProduct, productImages[i], i);
         }
         Sale savedSale = this.saleService.saveSale(sale);
         SaleController.LOGGER.info("Published new Sale with ID: {}", savedSale.getId());
         this.publishedSaleNotificationService.sendNotificationToAllUsers(savedSale);
         modelAndView.setViewName(SaleController.PUBLISH_SUCCESSFUL_VIEW + savedProduct.getId().toString());
         modelAndView.addObject("sale", savedSale);
         modelAndView.addObject("salePublishedSuccess", true);
      } else {
         SaleController.LOGGER.error(GlobalErrorsMessages.SALE_NOT_PUBLISHED_ERROR);
         List<ObjectError> productErrors = productBindingResult.getAllErrors();
         for (ObjectError productGlobalError : productErrors) {
            modelAndView.addObject(Objects.requireNonNull(productGlobalError.getCode()), productGlobalError.getDefaultMessage());
         }
         List<ObjectError> saleErrors = saleBindingResult.getAllErrors();
         for (ObjectError saleGlobalError : saleErrors) {
            modelAndView.addObject(Objects.requireNonNull(saleGlobalError.getCode()), saleGlobalError.getDefaultMessage());
         }
         modelAndView.addObject("isUpdate", false);
      }
      return modelAndView;
   }

   @GetMapping(value = {"/updateSale/{saleId}", "/updateSale/{saleId}"})
   public ModelAndView showUpdateSaleForm(@PathVariable("saleId") Long saleId, @Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APIPaths.MARKETPLACE + "/newSale" + GlobalValues.TEMPLATES_EXTENSION);
      try {
         Sale sale = this.saleService.getSale(saleId);
         if (!sale.getUser().equals(loggedUser)) {
            modelAndView.setViewName("redirect:/" + APIPaths.ACCOUNT);
            modelAndView.addObject("userCantManageNotHisSaleError", true);
            return modelAndView;
         }
         modelAndView.addObject("sale", sale);
         modelAndView.addObject("product", sale.getProduct());
         modelAndView.addObject("isUpdate", true);
      } catch (SaleNotFoundException saleNotFoundException) {
         SaleController.LOGGER.error(saleNotFoundException.getMessage());
         modelAndView.setViewName("redirect:/" + APIPaths.MARKETPLACE + "/sale/" + saleId);
      }
      return modelAndView;
   }

   @PostMapping(value = {"/publishUpdatedSale/{saleId}", "/publishUpdatedSale/{saleId}/"})
   public ModelAndView updateSale(
           @PathVariable("saleId") Long saleId,
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @Valid @ModelAttribute("product") Product product,
           @NonNull BindingResult productBindingResult,
           @Valid @ModelAttribute("sale") Sale sale,
           @NonNull BindingResult saleBindingResult,
           @RequestParam(name = "product-images", required = false) MultipartFile[] productImages) {
      ModelAndView modelAndView = new ModelAndView(SaleController.PUBLISH_ERROR_VIEW);
      sale.setUser(loggedUser);
      sale.setProduct(product);
      this.productValidator.setIsUpdate(true);
      this.saleValidator.validate(sale, saleBindingResult);
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors() && !saleBindingResult.hasErrors()) {
         MultipartFile[] notEmptyProductImages = Arrays.stream(productImages).filter(productImage -> !productImage.isEmpty()).toArray(MultipartFile[]::new);
         final Integer notEmptyProductImagesNumber = notEmptyProductImages.length;
         Sale saleToUpdate = this.saleService.getSale(saleId);
         Product productToUpdate = this.productService.getProduct(saleToUpdate.getProduct().getId());
         Product updatedProduct = this.productService.updateProduct(productToUpdate, product, notEmptyProductImagesNumber);
         if (updatedProduct != null) {
            if (!productImages[0].isEmpty()) {
               ProductImageFileUtils.deleteProductImages(productToUpdate);
            }
            for (Integer i = 0; i < notEmptyProductImagesNumber; i++) {
               ProductImageFileUtils.storeProductImage(updatedProduct, notEmptyProductImages[i], i);
            }
            sale.setProduct(updatedProduct);
            Sale updatededSale = this.saleService.updateSale(saleToUpdate, sale);
            SaleController.LOGGER.info("Updated Sale with ID: {}", updatededSale.getId());
            modelAndView.setViewName(SaleController.PUBLISH_SUCCESSFUL_VIEW + updatededSale.getId().toString());
            modelAndView.addObject("sale", updatededSale);
            modelAndView.addObject("saleUpdatedSuccess", true);
         }
      } else {
         SaleController.LOGGER.error(GlobalErrorsMessages.SALE_NOT_PUBLISHED_ERROR);
         List<ObjectError> productErrors = productBindingResult.getAllErrors();
         for (ObjectError productGlobalError : productErrors) {
            modelAndView.addObject(Objects.requireNonNull(productGlobalError.getCode()), productGlobalError.getDefaultMessage());
         }
         List<ObjectError> saleErrors = saleBindingResult.getAllErrors();
         for (ObjectError saleGlobalError : saleErrors) {
            modelAndView.addObject(Objects.requireNonNull(saleGlobalError.getCode()), saleGlobalError.getDefaultMessage());
         }
         sale.setId(saleId);
         modelAndView.addObject("sale", sale);
         modelAndView.addObject("product", sale.getProduct());
         modelAndView.addObject("isUpdate", true);
      }
      return modelAndView;
   }

   @DeleteMapping(value = {"/deleteSale/{saleId}", "/deleteSale/{saleId}"})
   public ResponseEntity<?> deleteSale(@PathVariable("saleId") Long saleId, @Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPaths.ACCOUNT + "#sales");
      String redirect = "/" + APIPaths.ACCOUNT + "?";
      ResponseEntity<?> responseEntity = null;
      try {
         Sale sale = this.saleService.getSale(saleId);
         if (!sale.getUser().equals(loggedUser)) {
            modelAndView.addObject("userCantManageNotHisSaleError", true);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("redirect", "/" + APIPaths.ACCOUNT));
            return responseEntity;
         }
         if (this.saleService.deleteSale(sale)) {
            modelAndView.addObject("saleDeletedSuccess", true);
            SaleController.LOGGER.info("Deleted Sale with Sale ID: {}", saleId);
            redirect += "saleDeletedSuccess=true#sales";
            responseEntity = ResponseEntity.ok().body(Map.of("redirect", redirect));
         } else {
            SaleController.LOGGER.error(GlobalErrorsMessages.SALE_NOT_DELETED_ERROR);
            redirect += "saleNotDeletedError=true#sales";
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("redirect", redirect));
         }
      } catch (SaleNotFoundException saleNotFoundException) {
         SaleController.LOGGER.error(saleNotFoundException.getMessage());
         responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("redirect", "/" + APIPaths.SALES + "/sale/" + saleId));
      }
      return responseEntity;
   }

   @GetMapping(value = {"/sale/{saleId}", "/sale/{saleId}/"})
   public ModelAndView showSaleById(@PathVariable("saleId") Long saleId) {
      ModelAndView modelAndView = new ModelAndView(APIPaths.MARKETPLACE + "/sale.html");
      try {
         Sale sale = this.saleService.getSale(saleId);
         //this.publishedSaleNotificationService.sendNotificationToAllUsers(sale);
         modelAndView.addObject("sale", sale);
         modelAndView.addObject("saleId", saleId);
         modelAndView.addObject("isAddedToCart", false);
      } catch (SaleNotFoundException saleNotFoundException) {
         SaleController.LOGGER.error(saleNotFoundException.getMessage());
      }
      return modelAndView;
   }

}