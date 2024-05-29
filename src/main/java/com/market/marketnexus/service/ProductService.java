package com.market.marketnexus.service;

import com.market.marketnexus.helpers.product.Utils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.repository.ProductCategoryRepository;
import com.market.marketnexus.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;
   @Autowired
   protected CartLineItemService cartLineItemService;

   @Transactional
   public Product saveProduct(@NotNull Product product) {
      Product savedProduct = this.productRepository.save(product);
      savedProduct.setImageRelativePath(Utils.getProductImagePath(product));
      return savedProduct;
   }

   @Transactional
   public Product updateProduct(@NotNull Product productToUpdate, @NotNull Product product, @NotNull Boolean isProductImageUpdated) {
      productToUpdate.setName(product.getName());
      productToUpdate.setDescription(product.getDescription());
      productToUpdate.setPrice(product.getPrice());
      productToUpdate.setCategory(product.getCategory());
      Product updatedProduct = this.productRepository.save(productToUpdate);
      if (isProductImageUpdated) {
         updatedProduct.setImageRelativePath(Utils.getProductImagePath(updatedProduct));
      }
      return updatedProduct;
   }

   public Product getProduct(Long productId) {
      return this.productRepository.findById(productId).orElse(null);
   }
}
