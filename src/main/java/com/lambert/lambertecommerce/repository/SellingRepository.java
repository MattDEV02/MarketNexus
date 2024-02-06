package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Selling;
import org.springframework.data.repository.CrudRepository;

public interface SellingRepository extends CrudRepository<Selling, Long> {
   //Selling findByUserProductInsertedat(User user, Product product, LocalDateTime insertedAt);
}
