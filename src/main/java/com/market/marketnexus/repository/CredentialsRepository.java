package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

   public Boolean existsByUsername(String username);

   public Optional<Credentials> findByUsername(String username);

   @Query(value = """
           SELECT 
               DISTINCT 
                  c.id
           FROM 
               Credentials c
           WHERE 
               (:isOnline IS NULL OR c.is_online = :isOnline) AND
               (:role IS NULL OR c.role = :role) AND
               (:registeredFrom IS NULL OR c.inserted_at >= CAST(:registeredFrom AS DATE)) AND
               (:registeredTo IS NULL OR c.inserted_at <= CAST(:registeredTo AS DATE)) 
             
           """, nativeQuery = true)
   public List<Long> findCredentialsIdsByFilters(
           @Param("isOnline") Boolean isOnline,
           @Param("role") String role,
           @Param("registeredFrom") String registeredFrom,
           @Param("registeredTo") String registeredTo);
}
