package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
   @Autowired
   protected UserRepository userRepository;

   /**
    * This method retrieves a User from the DB based on its ID.
    *
    * @param id the id of the User to retrieve from the DB
    * @return the retrieved User, or null if no User with the passed ID could be found in the DB
    */
   @Transactional
   public User getUser(Long id) {
      Optional<User> result = this.userRepository.findById(id);
      return result.orElse(null);
   }

   /**
    * This method saves a User in the DB.
    *
    * @param user the User to save into the DB
    * @return the saved User
    * @throws DataIntegrityViolationException if a User with the same username
    *                                         as the passed User already exists in the DB
    */
   @Transactional
   public User saveUser(User user) {
      return this.userRepository.save(user);
   }

   /**
    * This method retrieves all Users from the DB.
    *
    * @return a Set with all the retrieved Users
    */
   @Transactional
   public Set<User> getAllUsers() {
      Set<User> result = new HashSet<User>();
      Iterable<User> iterable = this.userRepository.findAll();
      for (User user : iterable) {
         result.add(user);
      }
      return result;
   }

}
