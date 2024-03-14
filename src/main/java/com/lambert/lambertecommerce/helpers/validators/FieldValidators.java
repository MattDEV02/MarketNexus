package com.lambert.lambertecommerce.helpers.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidators {


   private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

   private static final Pattern EMAIL_PATTERN = Pattern.compile(FieldValidators.EMAIL_REGEX);


   private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8}$";

   private static final Pattern PASSWORD_PATTERN = Pattern.compile(FieldValidators.PASSWORD_REGEX);

   public static boolean emailValidator(String email) {
      Matcher email_matcher = FieldValidators.EMAIL_PATTERN.matcher(email);
      return email_matcher.matches();
   }

   public static boolean passwordValidator(String password) {
      Matcher password_matcher = FieldValidators.PASSWORD_PATTERN.matcher(password);
      return password_matcher.matches();
   }
}
