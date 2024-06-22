package com.market.marketnexus.repository;

import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

   @Query(value = """
            SELECT
                DISTINCT
                    cli.sale.id AS saleId, cli.sale.product.name AS productName, FUNCTION('TO_CHAR', order.insertedAt, 'yyyy-MM-dd') AS inserted_at
            FROM
                Order order
                    JOIN order.cart.cartLineItems cli
            WHERE
                order.user.id = :userId
           """
   )
   public List<Object[]> findAllByUserId(Long userId);

   public Iterable<Order> findAllByUserOrderByInsertedAtDesc(User user);
}
