package com.market.marketnexus.controller.validator;

import com.market.marketnexus.model.User;
import com.market.marketnexus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

   @Autowired
   private UserService userService;

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      User user = (User) object;
      if (this.userService.existsByEmail(user.getEmail())) {
         //String[] errorArgs = {""};
         System.out.println("Esiste");
         errors.reject("emailUniqueError", "Email " + user.getEmail() + " already used.");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return User.class.equals(aClass);
   }
}
