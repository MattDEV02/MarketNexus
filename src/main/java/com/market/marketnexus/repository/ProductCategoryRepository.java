package com.market.marketnexus.repository;

import com.market.marketnexus.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
   public Set<ProductCategory> findAllByOrderByName();
}
