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
            SELECT s.id,
                  p.name AS productName,
                  TO_CHAR(o.insertedAt, 'yyyy-MM-dd') AS inserted_at
           FROM Orders o
               JOIN o.user u
               JOIN o.cart c
               JOIN c.cartLineItems cli
               JOIN cli.sale s
               JOIN s.product p
           WHERE u.id = :userId
           """
   )
   public List<Object[]> findAllByUserId(Long userId);

   public Iterable<Order> findAllByUser(User user);
}
