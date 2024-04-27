package com.market.marketnexus.service;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.UserRepository;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
   @Autowired
   protected UserRepository userRepository;

   public Boolean existsByEmail(String email) {
      return this.userRepository.existsByEmail(email);
   }

   public User getUser(Long id) {
      Optional<User> result = this.userRepository.findById(id);
      return result.orElse(null);
   }

   public User getUser(Credentials credentials) {
      Optional<User> result = this.userRepository.findByCredentials(credentials);
      return result.orElse(null);
   }

   @Transactional
   public User saveUser(User user) {
      return this.userRepository.save(user);
   }

   @Transactional
   public User updateUser(Long id, @NonNull User updatedUser) {
      Credentials updatedCredentials = updatedUser.getCredentials();
      User user = this.userRepository.findById(id).orElse(null);
      if (user != null) {
         Credentials credentials = user.getCredentials();
         updatedCredentials.setInsertedAt(credentials.getInsertedAt());
         user.getCredentials().setUsername(updatedCredentials.getUsername());
         user.getCredentials().setRole(updatedCredentials.getRole());
         user.getCredentials().preUpdate();
         user.setName(updatedUser.getName());
         user.setSurname(updatedUser.getSurname());
         user.setBirthDate(updatedUser.getBirthDate());
         user.setBalance(updatedUser.getBalance());
         user.setNation(updatedUser.getNation());
         return this.userRepository.save(user);
      }
      return null;
   }

   @Transactional
   public Boolean deleteUser(User user) {
      this.userRepository.delete(user);
      return this.userRepository.existsById(user.getId());
   }

   public List<Object[]> countUsersByNation() {
      return this.userRepository.countUsersByNation();
   }

   @Transactional
   public Set<Sale> getAllSalesForUser(@NotNull Long userId) {
      // Ottieni l'utente dal database
      User user = this.userRepository.findById(userId).orElse(null);

      // Verifica se l'utente è stato trovato
      if (user != null) {
         // Forza il caricamento delle vendite associato all'utente
         Hibernate.initialize(user.getSales());
         // Ottieni la lista delle vendite associate all'utente
         Set<Sale> sales = user.getSales();
         return sales;
      } else {
         // Se l'utente non è stato trovato, restituisci null o gestisci il caso di errore
         return null;
      }
   }

   public Set<Order> getAllOrdersForUser(@NotNull Long userId) {
      User user = this.userRepository.findById(userId).orElse(null);
      if (user != null) {
         /*
          Hibernate.initialize(user.getOrders());
          Set<Order> orders = user.getOrders();
          return orders;
         */
         return new HashSet<Order>();
      } else {
         return null;
      }
   }

   public List<Object[]> usersPublishedSalesStats() {
      return this.userRepository.usersPublishedSalesStats();
   }
}
