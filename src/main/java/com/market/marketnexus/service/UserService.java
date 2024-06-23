package com.market.marketnexus.service;

import com.market.marketnexus.exception.UserEmailNotExistsException;
import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   @Autowired
   protected UserRepository userRepository;
   @Autowired
   protected CartRepository cartRepository;
   @Autowired
   protected OrderRepository orderRepository;

   public Boolean userExistsByEmail(String email) {
      return this.userRepository.existsByEmail(email);
   }

   public User getUser(Long userId) {
      Optional<User> result = this.userRepository.findById(userId);
      return result.orElse(null);
   }

   public User getUser(Credentials credentials) {
      Optional<User> result = this.userRepository.findByCredentials(credentials);
      return result.orElse(null);
   }

   public User getUser(String email) {
      return this.userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotExistsException("User with email '" + email + "' does not exist."));
   }

   @Transactional
   public Cart getUserCurrentCart(Long userId) {
      Cart currentCart = null;
      User user = this.getUser(userId);
      if (user != null) {
         List<Cart> carts = user.getCarts();
         currentCart = carts.get(carts.size() - 1);
      }
      return currentCart;
   }

   @Transactional
   public User saveUser(@NotNull User user) {
      User savedUser = this.userRepository.save(user);
      Cart cart = new Cart(user);
      Cart savedCart = this.cartRepository.save(cart);
      savedUser.getCarts().add(savedCart);
      return savedUser;
   }

   @Transactional
   public User updateUser(Long userId, @NonNull User updatedUser) {
      Credentials updatedCredentials = updatedUser.getCredentials();
      User user = this.getUser(userId);
      if (user != null) {
         Credentials credentials = user.getCredentials();
         updatedCredentials.setInsertedAt(credentials.getInsertedAt());
         user.getCredentials().setUsername(updatedCredentials.getUsername());
         if (TypeValidators.validateString(updatedCredentials.getPassword())) {
            user.getCredentials().setPassword(updatedCredentials.getPassword());
         }
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
      this.userRepository.delete(user);
      return !this.userRepository.existsById(user.getId());
   }

   public List<Object[]> countUsersByNation() {
      return this.userRepository.countUsersByNation();
   }

   public List<Object[]> usersPublishedSalesStats() {
      return this.userRepository.userSalesStats();
   }

   @Transactional
   public void updateUserBalance(@NotNull User user, Float newBalance) {
      Float roundedNewBalance = Utils.roundNumberTo2Decimals(newBalance);
      user.setBalance(roundedNewBalance);
   }
}
