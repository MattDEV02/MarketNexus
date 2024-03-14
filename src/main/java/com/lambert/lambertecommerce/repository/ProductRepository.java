package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Long> {
   public Set<Product> findAllByName(String name);

   //public Optional<Product> findById(Long id);

   public boolean existsByName(String name);
}
