package com.market.marketnexus.repository;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SaleRepository extends CrudRepository<Sale, Long> {
   public Optional<Sale> findByUserAndProductAndInsertedAt(User user, Product product, LocalDateTime insertedAt);

   public Set<Sale> findAllByOrderByUpdatedAt();

   public Set<Sale> findAllByUser(User user);

   public Set<Sale> findAllByUserAndProduct(User user, Product product);

   @Query("SELECT pc.name AS productCategory, COUNT(DISTINCT s.id) AS numberOfSales " +
           "FROM Sale s " +
           "JOIN s.product p " +
           "JOIN p.category pc " +
           "GROUP BY pc.name " +
           "ORDER BY pc.name ASC")
   public List<Object[]> countSalesByProductCategory();

}
