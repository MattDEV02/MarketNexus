package com.market.marketnexus.service;

import com.market.marketnexus.helpers.constants.ProjectPaths;
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
      savedProduct.setImageRelativePath(ProjectPaths.IMAGES + Utils.getProductImageDirectoryName(savedProduct) + "/" + Utils.getProductImageFileName(savedProduct));
      return savedProduct;
   }

}
