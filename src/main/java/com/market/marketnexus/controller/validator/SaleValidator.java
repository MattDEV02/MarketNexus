package com.market.marketnexus.controller.validator;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.repository.ProductRepository;
import com.market.marketnexus.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SaleValidator implements Validator {

   @Autowired
   private SaleRepository saleRepository;
   @Autowired
   private ProductRepository productRepository;

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Sale sale = (Sale) object;
      Product product = sale.getProduct();
      if (this.saleRepository.existsByUser(sale.getUser()) && this.productRepository.existsByNameAndDescriptionAndPriceAndCategory(product.getName(), product.getDescription(), product.getPrice(), product.getCategory())) {
         errors.reject("saleAlreadyPublishedError", "You have already published this sale.");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Sale.class.equals(aClass);
   }
}
