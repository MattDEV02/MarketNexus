package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface SaleRepository extends CrudRepository<Sale, Long> {
   Optional<Sale> findByUserAndProductAndInsertedAt(User user, Product product, LocalDateTime insertedAt);


   Set<Sale> findAllByUser(User user);

   Set<Sale> findAllByUserAndProduct(User user, Product product);
}
