package com.lambert.lambertecommerce.controller.validator;

import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

   @Autowired
   private UserRepository userRepepository;

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      User user = (User) object;
      if (this.userRepepository.existsByEmail(user.getEmail())) {
         //String[] errorArgs = {""};
         errors.reject("emailUniqueError", "Email " + user.getEmail() + " already used.");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return User.class.equals(aClass);
   }
}
