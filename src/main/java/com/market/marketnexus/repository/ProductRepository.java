package com.market.marketnexus.repository;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
   public Set<Product> findAllByNameContainingIgnoreCaseOrderById(String name);

   public Set<Product> findAllByCategoryOrderById(ProductCategory category);

   public Set<Product> findAllByNameContainingIgnoreCaseAndCategoryOrderById(String name, ProductCategory category);

   public Boolean existsByNameAndDescriptionAndPriceAndCategoryOrderById(String name, String description, Float price, ProductCategory category);
}
