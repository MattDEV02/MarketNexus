package com.market.marketnexus.model;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CredentialsTests {
   private final String username = "Lamb";
   private final String password = "password";
   private final String password2 = "password2";

   @BeforeEach
   public void setUp() {
     
   }

   @After
   public void tearDown() {

   }

   @Test
   public void testCredentialsDefaultRole() {
      Credentials credentials = new Credentials(this.username, this.password);
      assertNotNull(credentials);
      assertNotNull(Credentials.DEFAULT_ROLE);
      assertEquals(credentials.getClass(), Credentials.class);
      assertEquals(credentials.getRole(), Credentials.DEFAULT_ROLE.toString());
   }

   @Test
   public void testCredentialsEquals() {
      Credentials credentials = new Credentials(this.username, this.password);
      Credentials equalCredentials = new Credentials(this.username, this.password2);
      assertNotNull(this.username);
      assertNotNull(this.password);
      assertNotNull(this.password2);
      assertNotNull(credentials);
      assertNotNull(equalCredentials);
      assertEquals(credentials, equalCredentials);
      assertNotSame(credentials, equalCredentials);
      assertNotEquals(credentials.getPassword(), equalCredentials.getPassword());
      equalCredentials.setPassword(credentials.getPassword());
      assertEquals(credentials.getPassword(), equalCredentials.getPassword());
   }

   @Test
   public void testCredentialsSame() {
      Credentials credentials = new Credentials(this.username, this.password);
      Credentials sameCredentials = credentials;
      assertNotNull(this.username);
      assertNotNull(this.password);
      assertNotNull(credentials);
      assertNotNull(sameCredentials);
      assertSame(credentials, sameCredentials);
      assertEquals(credentials, sameCredentials);
      assertEquals(credentials.getPassword(), sameCredentials.getPassword());
      sameCredentials.setPassword(this.password2);
      assertEquals(credentials.getPassword(), sameCredentials.getPassword());
   }
}
