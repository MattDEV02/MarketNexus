package com.market.marketnexus.repository;

import com.market.marketnexus.model.Nation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NationRepository extends CrudRepository<Nation, Long> {
   public Set<Nation> findAllByOrderByName();

}
