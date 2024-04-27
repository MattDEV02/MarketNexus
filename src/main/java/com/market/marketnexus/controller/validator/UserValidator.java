package com.market.marketnexus.controller.validator;

import com.market.marketnexus.model.User;
import com.market.marketnexus.service.NationService;
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
   @Autowired
   private NationService nationService;

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      User user = (User) object;
      if (this.userService.existsByEmail(user.getEmail())) {
         //String[] errorArgs = {""};
         errors.reject("emailUniqueError", "Email " + user.getEmail() + " already used.");
      }
      if (user.getNation() == null || !this.nationService.existsById(user.getNation().getId())) {
         errors.reject("nationNotExistsError", "Selected Nation not exists.");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return User.class.equals(aClass);
   }
}
