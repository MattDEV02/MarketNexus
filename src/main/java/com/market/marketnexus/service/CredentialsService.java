package com.market.marketnexus.service;

import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.repository.CredentialsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialsService {

   @Autowired
   protected CredentialsRepository credentialsRepository;
   @PersistenceContext
   private EntityManager entityManager;

   public Credentials getCredentials(String username) {
      return this.credentialsRepository.findByUsername(username).orElseThrow(() -> new UserCredentialsUsernameNotExistsException("User Credentials with username '" + username + "' does not exist."));
   }

   public void updateIsOnline(Credentials credentials, Boolean isOnline) {
      if (credentials != null) {
         credentials.setIsOnline(isOnline);
         this.credentialsRepository.save(credentials);
      }
   }

   public List<Credentials> getCredentialsByCriteria(Boolean isOnline, String role, LocalDateTime registeredFrom, LocalDateTime registeredTo) {
      CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
      CriteriaQuery<Credentials> criteriaQuery = criteriaBuilder.createQuery(Credentials.class);
      Root<Credentials> rootCredentials = criteriaQuery.from(Credentials.class);

      List<Predicate> predicates = new ArrayList<>();

      if (isOnline != null) {
         predicates.add(criteriaBuilder.equal(rootCredentials.get("isOnline"), isOnline));
      }
      if (Utils.existsRole(role)) {
         predicates.add(criteriaBuilder.equal(rootCredentials.get("role"), role));
      }
      if (TypeValidators.validateTimestamp(registeredFrom)) {
         predicates.add(criteriaBuilder.greaterThan(rootCredentials.get("insertedAt"), registeredFrom));
      }
      if (TypeValidators.validateTimestamp(registeredFrom)) {
         predicates.add(criteriaBuilder.lessThan(rootCredentials.get("insertedAt"), registeredTo));
      }

      criteriaQuery.select(rootCredentials).where(predicates.toArray(new Predicate[0]));

      return this.entityManager.createQuery(criteriaQuery).getResultList();
   }

}
