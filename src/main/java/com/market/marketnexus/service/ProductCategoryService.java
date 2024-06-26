package com.market.marketnexus.service;

import com.market.marketnexus.model.ProductCategory;
import com.market.marketnexus.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductCategoryService {
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;

   public ProductCategory getProductCategory(Long productCategoryId) {
      return this.productCategoryRepository.findById(productCategoryId).orElse(null);
   }

   public Iterable<ProductCategory> getAllProductCategories() {
      return this.productCategoryRepository.findAllByOrderByName();
   }

   public Map<Long, ProductCategory> getAllProductCategoriesMap() {
      Map<Long, ProductCategory> allProductCategoriesMap = new HashMap<Long, ProductCategory>();
      Iterable<ProductCategory> allProductCategoriesOrderByName = this.getAllProductCategories();
      for (ProductCategory productCategory : allProductCategoriesOrderByName) {
         allProductCategoriesMap.put(productCategory.getId(), productCategory);
      }
      return allProductCategoriesMap;
   }
}
