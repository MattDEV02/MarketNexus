package com.market.marketnexus.service;

import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
   @Autowired
   protected UserRepository userRepository;
   @Autowired
   protected CartRepository cartRepository;
   @Autowired
   protected OrderRepository orderRepository;

   public Boolean existsByEmail(String email) {
      return this.userRepository.existsByEmail(email);
   }

   public User getUser(Credentials credentials) {
      Optional<User> result = this.userRepository.findByCredentials(credentials);
      return result.orElse(null);
   }

   @Transactional
   public User saveUser(User user) {
      User savedUser = this.userRepository.save(user);
      Cart cart = new Cart();
      this.cartRepository.save(cart);
      return savedUser;
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
         this.updateUserBalance(user, updatedUser.getBalance());
         user.setNation(updatedUser.getNation());
         return this.userRepository.save(user);
      }
      return null;
   }

   @Transactional
   public Boolean deleteUser(User user) {
      this.cartRepository.deleteByUser(user);
      this.userRepository.delete(user);
      return !this.userRepository.existsById(user.getId());
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

   public List<Object[]> getAllOrdersForUser(@NotNull Long userId) {
      return this.orderRepository.findAllByUserId(userId);
   }

   public List<Object[]> usersPublishedSalesStats() {
      return this.userRepository.userSalesStats();
   }

   @Transactional
   public void updateUserBalance(@NotNull User user, Float newBalance) {
      user.setBalance(Utils.roundNumberTo2Decimals(newBalance));
   }
}
