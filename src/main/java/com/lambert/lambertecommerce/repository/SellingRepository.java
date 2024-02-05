package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.Selling;
import com.lambert.lambertecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface SellingRepository extends CrudRepository<Selling, Long> {
   Selling findByUserProductInsertedat(User user, Product product, LocalDateTime insertedAt);
}
