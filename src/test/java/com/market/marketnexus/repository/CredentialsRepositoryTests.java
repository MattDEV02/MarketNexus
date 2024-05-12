package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CredentialsRepositoryTests {
   private String existingUsername;
   private String notExistingUsername;

   @Autowired
   private CredentialsRepository credentialsRepository;

   @BeforeEach
   public void setUp() {
      this.existingUsername = "Lamb";
      this.notExistingUsername = "Lamb02";
   }

   @After
   public void tearDown() {

   }

   @Test
   public void testFindByUsername() {
      Credentials credentials = new Credentials();
      credentials.setUsername(this.existingUsername);
      assertNotNull(this.existingUsername);
      assertNotNull(credentials);
      assertNotNull(this.credentialsRepository.findByUsername(this.existingUsername).orElse(null));
      assertEquals(credentials, this.credentialsRepository.findByUsername(this.existingUsername).orElse(null));
   }

   @Test
   public void testFindByUsernameCredentialsNotFound() {
      Credentials credentials = new Credentials();
      credentials.setUsername(this.notExistingUsername);
      assertNotNull(this.notExistingUsername);
      assertNotNull(credentials);
      assertNull(this.credentialsRepository.findByUsername(this.notExistingUsername).orElse(null));
      assertNotEquals(credentials, this.credentialsRepository.findByUsername(this.notExistingUsername).orElse(null));
   }

   @Test
   public void testUserExistsByUsername() {
      assertFalse(this.credentialsRepository.existsByUsername(this.notExistingUsername));
      assertTrue(this.credentialsRepository.existsByUsername(this.existingUsername));
   }
}
