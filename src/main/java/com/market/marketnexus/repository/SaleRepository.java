package com.market.marketnexus.repository;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface SaleRepository extends CrudRepository<Sale, Long> {
   Optional<Sale> findByUserAndProductAndInsertedAt(User user, Product product, LocalDateTime insertedAt);

   Set<Sale> findAllByOrderByUpdatedAt();

   Set<Sale> findAllByUser(User user);

   Set<Sale> findAllByUserAndProduct(User user, Product product);
}
