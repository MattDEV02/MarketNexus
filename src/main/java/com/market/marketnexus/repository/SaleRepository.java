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
           WITH RECURSIVE date_series AS (SELECT NOW() AS date
                                          UNION ALL
                                          SELECT (date - INTERVAL '1 day')
                                          FROM date_series
                                          WHERE date_series.date > (NOW() - INTERVAL '6 days'))
           SELECT TO_CHAR(date_series.date, 'yyyy-MM-dd') AS day,
                  COALESCE(COUNT(DISTINCT s.id), 0)             AS numberOfSales
           FROM date_series
                    LEFT JOIN Sales s ON CAST(date_series.date AS DATE) = CAST(s.inserted_at AS DATE) AND s._user = :userId
           GROUP BY date_series.date
           ORDER BY date_series.date;
           """,
           nativeQuery = true)
   public List<Object[]> countCurrentWeekUserSales(@Param("userId") Long userId);

}
