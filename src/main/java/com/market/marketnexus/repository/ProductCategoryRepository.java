package com.market.marketnexus.repository;

import com.market.marketnexus.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
   public Iterable<ProductCategory> findAllByOrderByName();
}
