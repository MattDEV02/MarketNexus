package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
   @Autowired
   protected UserRepository userRepository;

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

}
