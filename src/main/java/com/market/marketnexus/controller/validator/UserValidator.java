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
   private Boolean isAccountUpdate = false;

   public Boolean getIsAccountUpdate() {
      return this.isAccountUpdate;
   }

   public void setIsAccountUpdate(Boolean isAccountUpdate) {
      this.isAccountUpdate = isAccountUpdate;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      User user = (User) object;
      if (!this.getIsAccountUpdate() && this.userService.userExistsByEmail(user.getEmail())) {
         errors.reject("user.email.unique", "Email already used.");
      }
      if (user.getNation() == null) {
         errors.reject("user.nation.notExists", "Selected Nation does not exists.");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return User.class.equals(aClass);
   }
}
