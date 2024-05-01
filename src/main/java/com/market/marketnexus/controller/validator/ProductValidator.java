package com.market.marketnexus.controller.validator;

import com.market.marketnexus.helpers.constants.FieldSizes;
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
         errors.rejectValue("name", "product.name.invalidFormat");
      }
      if (product.getCategory() == null) {
         errors.rejectValue("category", "product.category.notExists");
      }
      if (this.getProductImage() == null || this.getProductImage().isEmpty()) {
         errors.reject("productImageEmptyError", "Invalid or empty product image.");
      } else {
         if (this.getProductImage().getSize() > FieldSizes.PRODUCT_IMAGE_MAX_BYTE_SIZE) {
            errors.reject("productImageSizeError", "File size exceeds the allowed limit of " + (FieldSizes.PRODUCT_IMAGE_MAX_BYTE_SIZE / 1000) + " KB.");
         }
         String originalFilename = this.getProductImage().getOriginalFilename();
         if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpeg"))) {
            errors.reject("productImageExtensionError", "Unsupported file type. Please upload a .jpg, .jpeg or .png file.");
         }
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Product.class.equals(aClass);
   }
}
