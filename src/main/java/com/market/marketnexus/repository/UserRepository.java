package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   public Boolean existsByEmail(String email);

   public Optional<User> findByEmail(String email);

   public Optional<User> findByCredentials(Credentials credentials);

   @Query(value = """
           SELECT DISTINCT user.nation.name AS nationName,
                                                      COUNT(DISTINCT user.id) AS numbersOfUsers
                                      FROM User user
                                      WHERE user.credentials.id IN (:credentialsIds)
                                      GROUP BY user.nation.name
           """
   )
   public List<Object[]> countUsersByNation(@Param("credentialsIds") List<Long> credentialsIds);

   @Query(value = """
           SELECT *
           FROM GET_USERS_SALES_STATS();
           """,
           nativeQuery = true)
   public List<Object[]> userSalesStats();

}
