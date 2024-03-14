package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.ProductCategory;
import com.lambert.lambertecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductCategoryService {
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;

   @Transactional
   public ProductCategory getProductCategory(Long id) {
      Optional<ProductCategory> result = this.productCategoryRepository.findById(id);
      return result.orElse(null);
   }

   @Transactional
   public ProductCategory saveProductCategory(ProductCategory productCategory) {
      return this.productCategoryRepository.save(productCategory);
   }

   /**
    * This method retrieves all Users from the DB.
    *
    * @return a Set with all the retrieved Users
    */
   @Transactional
   public Set<ProductCategory> getAllProductCategories() {
      Set<ProductCategory> result = new HashSet<ProductCategory>();
      Iterable<ProductCategory> iterable = this.productCategoryRepository.findAll();
      for (ProductCategory productCategory : iterable) {
         result.add(productCategory);
      }
      return result;
   }
}
