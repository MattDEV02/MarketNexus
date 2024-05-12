package com.market.marketnexus.service;

import com.market.marketnexus.exception.UserEmailNotExistsException;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.User;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTests {

   private String existingEmail;
   private String notExistingEmail;


   @Autowired
   private UserService userService;

   @BeforeEach
   public void setUp() {
      this.existingEmail = "matteolambertucci3@gmail.com";
      this.notExistingEmail = "matteolambertucci@gmail.com";
   }

   @After
   public void tearDown() {

   }

   @Test
   public void testGetUserByEmail() throws UserEmailNotExistsException {
      User user = new User();
      user.setEmail(this.existingEmail);
      assertNotNull(this.existingEmail);
      assertNotNull(user);
      assertDoesNotThrow(() -> this.userService.getUser(this.existingEmail));
      assertEquals(user, this.userService.getUser(this.existingEmail));
   }

   @Test
   public void testGetUserByEmailUserNotFound() throws UserEmailNotExistsException {
      assertNotNull(this.notExistingEmail);
      assertThrows(UserEmailNotExistsException.class, () -> this.userService.getUser(this.notExistingEmail));
   }

   @Test
   public void testGetUserCurrentCart() {
      User user = new User();
      Cart cart = new Cart(user);
      Cart cart2 = new Cart(user);
      user.getCarts().add(cart);
      user.getCarts().add(cart2);
      assertNotNull(user);
      assertNotNull(cart);
      assertNotNull(cart2);
      assertEquals(user, cart.getUser());
      assertEquals(user, cart2.getUser());
      assertNotNull(user.getCarts());
      assertEquals(2, user.getCarts().size());
      assertIterableEquals(Arrays.asList(cart, cart2), user.getCarts());
      assertNotNull(this.userService.getUserCurrentCart(1L));
   }

   @Test
   public void testUserExistsByEmail() {
      assertTrue(this.userService.userExistsByEmail(this.existingEmail));
      assertFalse(this.userService.userExistsByEmail(this.notExistingEmail));
   }
}
