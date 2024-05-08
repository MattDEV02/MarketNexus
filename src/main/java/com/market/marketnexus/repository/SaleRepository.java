package com.market.marketnexus.repository;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SaleRepository extends CrudRepository<Sale, Long> {
   public Optional<Sale> findByUserAndProductAndInsertedAt(User user, Product product, LocalDateTime insertedAt);

   public Set<Sale> findAllByOrderByUpdatedAt();

   public Set<Sale> findAllByUser(User user);

   public Set<Sale> findAllByUserAndProduct(User user, Product product);

   @Query(value = """
           SELECT *
           FROM GET_USER_SOLD_SALES_STATS(:userId);
           """,
           nativeQuery = true)
   public List<Object[]> countCurrentWeekUserSales(@Param("userId") Long userId);

}
