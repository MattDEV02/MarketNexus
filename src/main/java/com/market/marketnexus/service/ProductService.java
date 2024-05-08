package com.market.marketnexus.service;

import com.market.marketnexus.helpers.constants.Paths;
import com.market.marketnexus.helpers.product.Utils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.repository.ProductCategoryRepository;
import com.market.marketnexus.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;
   @Autowired
   protected CartLineItemService cartLineItemService;

   public Product getProduct(Long id) {
      Optional<Product> result = this.productRepository.findById(id);
      return result.orElse(null);
   }

   @Transactional
   public Product saveProduct(@NotNull Product product) {
      Product savedProduct = this.productRepository.save(product);
      savedProduct.setImageRelativePath(Paths.IMAGES + Utils.getProductRelativeImageDirectory(savedProduct) + "/" + Utils.getProductRelativeImageFile(savedProduct));
      return savedProduct;
   }

}
