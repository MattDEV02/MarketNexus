package com.market.marketnexus.service;

import com.market.marketnexus.helpers.product.ProductImageFileUtils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.repository.ProductCategoryRepository;
import com.market.marketnexus.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.market.marketnexus.helpers.sale.Utils.roundNumberTo2Decimals;

@Service
public class ProductService {
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;
   @Autowired
   protected CartLineItemService cartLineItemService;

   @Transactional
   public Product saveProduct(@NotNull Product product, Integer productImagesNumber) {
      Float roundedProductPrice = roundNumberTo2Decimals(product.getPrice());
      product.setPrice(roundedProductPrice);
      Product savedProduct = this.productRepository.save(product);
      for (Integer i = 0; i < productImagesNumber; i++) {
         savedProduct.getImageRelativePaths().add(ProductImageFileUtils.getProductImagePath(product, i));
      }
      return savedProduct;
   }

   @Transactional
   public Product updateProduct(@NotNull Product productToUpdate, @NotNull Product product, Integer productImagesNumber) {
      productToUpdate.setName(product.getName());
      productToUpdate.setDescription(product.getDescription());
      productToUpdate.setPrice(roundNumberTo2Decimals(product.getPrice()));
      productToUpdate.setCategory(product.getCategory());
      if (productImagesNumber > 0) {
         List<String> productImageRelativePath = productToUpdate.getImageRelativePaths();
         productImageRelativePath.clear();
         for (Integer i = 0; i < productImagesNumber; i++) {
            productImageRelativePath.add(ProductImageFileUtils.getProductImagePath(productToUpdate, i));
         }
      }
      Product updatedProduct = this.productRepository.save(productToUpdate);
      return updatedProduct;
   }

   public Product getProduct(Long productId) {
      return this.productRepository.findById(productId).orElse(null);
   }
}
