package com.market.marketnexus.helpers;

import com.market.marketnexus.helpers.validators.FieldValidators;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FieldValidatorsTests {
   @Before
   public void setUp() {

   }

   @After
   public void tearDown() {

   }

   @Test
   public void testPasswordValidator() {
      assertFalse(FieldValidators.passwordValidator(""));
      assertFalse(FieldValidators.passwordValidator("m"));
      assertFalse(FieldValidators.passwordValidator("Matteo"));
      assertFalse(FieldValidators.passwordValidator("Matteooo"));
      assertFalse(FieldValidators.passwordValidator("Matteo022"));
      assertFalse(FieldValidators.passwordValidator("matteo1"));
      assertTrue(FieldValidators.passwordValidator("Matteo02"));
      assertTrue(FieldValidators.passwordValidator("Gabriel1"));
   }

   @Test
   public void testProductNameValidator() {
      assertFalse(FieldValidators.productNameValidator("!*ceef"));
      assertFalse(FieldValidators.productNameValidator("aa"));
      assertFalse(FieldValidators.productNameValidator(""));
      assertTrue(FieldValidators.productNameValidator("Smartphone"));
      assertTrue(FieldValidators.productNameValidator("SmartPhone"));
   }
}
