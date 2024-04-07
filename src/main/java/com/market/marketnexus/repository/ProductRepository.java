package com.market.marketnexus.repository;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Long> {
   public Set<Product> findAllByName(String name);

   public Set<Product> findAllByCategory(ProductCategory category);

   public Set<Product> findAllByNameAndCategory(String name, ProductCategory category);

}
