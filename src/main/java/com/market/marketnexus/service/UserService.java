package com.market.marketnexus.service;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

   public Set<User> getAllUsers() {
      Set<User> result = new HashSet<User>();
      Iterable<User> iterable = this.userRepository.findAll();
      for (User user : iterable) {
         result.add(user);
      }
      return result;
   }

   @Transactional
   public User updateUser(Long id, @NonNull User updatedUser) {
      User user = this.userRepository.findById(id).orElse(null);
      if (user != null) {
         user.setName(updatedUser.getName());
         user.setSurname(updatedUser.getSurname());
         user.setBirthDate(updatedUser.getBirthDate());
         user.setBalance(updatedUser.getBalance());
         user.setNation(updatedUser.getNation());
         return this.userRepository.save(user);
      }
      return null;
   }

}
