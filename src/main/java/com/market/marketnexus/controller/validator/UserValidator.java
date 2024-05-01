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
   private Boolean isAccountUpdate = false;

   public Boolean getIsAccountUpdate() {
      return this.isAccountUpdate;
   }

   public void setAccountUpdate(Boolean isAccountUpdate) {
      this.isAccountUpdate = isAccountUpdate;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      User user = (User) object;
      if (!this.isAccountUpdate && this.userService.existsByEmail(user.getEmail())) {
         //String[] errorArgs = {""};
         errors.rejectValue("email", "user.email.unique");
      }
      if (user.getNation() == null || !this.nationService.existsById(user.getNation().getId())) {
         errors.rejectValue("nation", "user.nation.notExists");
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return User.class.equals(aClass);
   }
}
