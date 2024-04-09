package com.market.marketnexus.controller.validator;

import com.market.marketnexus.helpers.validators.FieldValidators;
import com.market.marketnexus.model.Product;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductValidator implements Validator {


   private MultipartFile productImage;

   public MultipartFile getProductImage() {
      return this.productImage;
   }

   public void setProductImage(MultipartFile productImage) {
      this.productImage = productImage;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Product product = (Product) object;
      if (!FieldValidators.productNameValidator(product.getName())) {
         errors.reject("productNameFormatError", "Invalid product name format." + product.getName());
      }
      if (this.getProductImage() == null || this.getProductImage().isEmpty()) {
         errors.reject("productImageEmptyError", "Invalid or empty product image.");
      }
      if (product.getCategory() == null) {
         errors.reject("productCategoryNotExistsError", "Selected Product Category not exists.");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Product.class.equals(aClass);
   }
}
