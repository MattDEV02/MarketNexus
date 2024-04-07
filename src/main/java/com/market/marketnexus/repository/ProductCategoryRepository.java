package com.market.marketnexus.repository;

import com.market.marketnexus.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
   public ProductCategory findByName(String name);
}
