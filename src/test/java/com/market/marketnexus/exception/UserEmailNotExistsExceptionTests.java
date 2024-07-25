package com.market.marketnexus.exception;

import com.market.marketnexus.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserEmailNotExistsExceptionTests {

   @Autowired
   private UserService userService;

   @Before
   public void setUp() {

   }

   @After
   public void tearDown() {

   }

   @Test
   public void userEmailNotExistsThrows() {
      assertThrows(UserEmailNotExistsException.class, () -> {
         throw new UserEmailNotExistsException();
      });
      final String invalidAndNotExistsEmail = "m@i";
      assertNotNull(invalidAndNotExistsEmail);
      assertThrows(UserEmailNotExistsException.class, () -> {
         this.userService.getUser(invalidAndNotExistsEmail);
      });
   }

   @Test
   public void userEmailNotExistsNotThrows() {
      final String validAndExistsEmail = "matteolambertucci3@gmail.com";
      assertNotNull(validAndExistsEmail);
      assertDoesNotThrow(() -> this.userService.getUser(validAndExistsEmail), validAndExistsEmail + " exists.");
   }
}
