package com.market.marketnexus.repository;

import com.market.marketnexus.model.Nation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationRepository extends CrudRepository<Nation, Long> {
   public Iterable<Nation> findAllByOrderByName();

   List<Nation> findByNameContainingIgnoreCase(String query);
}
