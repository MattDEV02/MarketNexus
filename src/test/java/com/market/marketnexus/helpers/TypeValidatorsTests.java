package com.market.marketnexus.helpers;

import com.market.marketnexus.helpers.validators.TypeValidators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TypeValidatorsTests {
   
   @Test
   public void testValidateTimestamp() {
      assertFalse(TypeValidators.validateTimestamp(LocalDateTime.now()));
      assertTrue(TypeValidators.validateTimestamp(LocalDateTime.MIN));
      assertFalse(TypeValidators.validateTimestamp(LocalDateTime.MAX));
      assertFalse(TypeValidators.validateTimestamp(null));
      assertThrows(DateTimeParseException.class, () -> TypeValidators.validateTimestamp(LocalDateTime.parse("")));
      assertThrows(DateTimeParseException.class, () -> TypeValidators.validateTimestamp(LocalDateTime.parse("Matteo")));
   }

   @Test
   public void testValidateString() {
      assertFalse(TypeValidators.validateString(""));
      assertFalse(TypeValidators.validateString(null));
      assertFalse(TypeValidators.validateString(new String("")));
      assertTrue(TypeValidators.validateString("Smartphone"));
      assertTrue(TypeValidators.validateString("Smart Phone"));
   }
}
