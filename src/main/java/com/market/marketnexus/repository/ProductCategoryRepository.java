package com.market.marketnexus.repository;

import com.market.marketnexus.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
   public Set<ProductCategory> findAllByOrderByName();
}
