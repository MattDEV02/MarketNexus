package com.market.marketnexus.controller.validator;

import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.helpers.validators.FieldValidators;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {
   @Autowired
   private CredentialsRepository credentialsRepository;
   private String confirmPassword;
   private Boolean isAccountUpdate = false;

   public Boolean getIsAccountUpdate() {
      return this.isAccountUpdate;
   }

   public void setIsAccountUpdate(Boolean isAccountUpdate) {
      this.isAccountUpdate = isAccountUpdate;
   }

   public String getConfirmPassword() {
      return this.confirmPassword;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Credentials credentials = (Credentials) object;
      if (!this.getIsAccountUpdate() && this.credentialsRepository.existsByUsername(credentials.getUsername())) {
         errors.rejectValue("username", "credentials.username.unique");
      }
      if (!this.getIsAccountUpdate() && !FieldValidators.passwordValidator(credentials.getPassword())) {
         errors.rejectValue("password", "credentials.password.invalidFormat");
      } else if (this.isAccountUpdate && TypeValidators.validateString(credentials.getPassword()) && !FieldValidators.passwordValidator(credentials.getPassword())) {
         errors.rejectValue("password", "credentials.password.invalidFormat");
      }
      if (this.getConfirmPassword() == null || !credentials.getPassword().equals(this.getConfirmPassword())) {
         errors.reject("passwordDifferentFromConfirmPasswordError", "The password must be the same as the confirm password.");
      }
      if (!Utils.existsRole(credentials.getRole())) {
         errors.rejectValue("role", "credentials.role.notExists");
      }

   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Credentials.class.equals(aClass);
   }
}
