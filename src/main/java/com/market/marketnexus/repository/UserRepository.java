package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
   public boolean existsByEmail(String email);

   public Optional<User> findByEmail(String email);

   public Optional<User> findByCredentials(Credentials credentials);

   @Query(value = "SELECT n.name AS nationName, COUNT(DISTINCT u.id) AS numbersOfUsers " +
           "FROM User u JOIN u.nation n " +
           "GROUP BY n.name")
   public List<Object[]> countUsersByNation();

   @Query(value = """
                SELECT "User",
                       MIN(salesPublishedPerDay)               AS MIN,
                       MAX(salesPublishedPerDay)               AS MAX,
                       SUM(salesPublishedPerDay)               AS total,
                       ROUND(AVG(salesPublishedPerDay), 2) AS AVG,
                       CAST((ROUND(
                                CAST(
                                        (
                                            CAST(SUM(salesPublishedPerDay) AS FLOAT) / (SELECT COUNT(DISTINCT id) FROM Sales)
                                            ) AS NUMERIC
                                ),
                                2
                        ) * 100) AS TEXT) || ' %'               AS "%"
                FROM (SELECT c.username           AS "User",
                             s.inserted_at,
                             COUNT(DISTINCT s.id) AS salesPublishedPerDay
                      FROM Users u
                               JOIN Credentials c ON u.credentials = c.id
                               LEFT JOIN Sales s ON s._user = u.id
                      GROUP BY c.username, s.inserted_at
                      ORDER BY salesPublishedPerDay DESC) AS userAndDayToSalesPublished
                GROUP BY "User";
           """,
           nativeQuery = true)
   public List<Object[]> usersPublishedSalesStats();

}
