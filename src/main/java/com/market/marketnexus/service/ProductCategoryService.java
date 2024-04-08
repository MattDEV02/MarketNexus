package com.market.marketnexus.service;

import com.market.marketnexus.model.ProductCategory;
import com.market.marketnexus.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductCategoryService {
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;

   public ProductCategory getProductCategory(Long id) {
      Optional<ProductCategory> result = this.productCategoryRepository.findById(id);
      return result.orElse(null);
   }

   public Set<ProductCategory> getAllProductCategories() {
      Set<ProductCategory> allProductCategoriesOrderByName = this.productCategoryRepository.findAllByOrderByName();
      return allProductCategoriesOrderByName;
   }
}
