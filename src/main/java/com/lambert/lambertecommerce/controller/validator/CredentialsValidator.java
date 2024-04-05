package com.lambert.lambertecommerce.controller.validator;

import com.lambert.lambertecommerce.helpers.validators.FieldValidators;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.repository.CredentialsRepository;
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

   public String getConfirmPassword() {
      return this.confirmPassword;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Credentials credentials = (Credentials) object;
      if (this.credentialsRepository.existsByUsername(credentials.getUsername())) {
         //String[] errorArgs = {""};
         errors.reject("usernameUniqueError", "Username " + credentials.getUsername() + " already used.");
      }
      if (!FieldValidators.passwordValidator(credentials.getPassword())) {
         errors.reject("passwordFormatError", "La password deve essere lunga 8 caratteri e..." + credentials.getPassword());
      }
      if (this.getConfirmPassword() != null && !this.getConfirmPassword().isEmpty() && !credentials.getPassword().equals(this.getConfirmPassword())) {
         errors.reject("passwordDifferentFromConfirmPasswordError", "La password deve essere uguale alla confirm password.");
      }

   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Credentials.class.equals(aClass);
   }
}
