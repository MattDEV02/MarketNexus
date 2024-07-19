# MarketNexus

<p align="center">
<img  title="MarketNexus Logo"  alt="MarketNexus Logo"  width="19.5%"  src="/src/main/resources/static/images/logo/logo.png"/>
</p>

MarketNexus is a marketplace created with Spring boot where Users can sell, manage, view and purchase Products (by
placing them in their carts) of different categories, view their statistics dynamically, other Users' data and much
more.
There are 3 type of Users (User roles):

1. Seller Users: They can publish and sell their Products. They can't add other Users Products in their Cart and buy
   them.
2. Buyer Users: They can add other Users Products in their Cart and buy them. They can't publish and sell their
   Products.
3. Seller and Buyer Users: They can do all things written in 1. and 2.

## Use Cases 👔

### Use Case UC1:

### Use Case UC2:

### Use Case UC3:

#### Make an Order - Primary Actor: A Buyer User.

`Primary Success Scenario:`

1. The Buyer User wants to place an Order.
2. The Buyer User enters his username and password. The System verifies that the data entered is correct, and
   authenticates the Buyer User. The System displays the Buyer User's data (such username).
3. The Buyer User chooses the “Enter Sales in the Shopping Cart” activity. The System displays the list of all the Sales
   on the system for each Sale the relative Product data and the sold quantity.
4. The Student enters (selecting it from the list of Sales) the identification code of a Sale in which they are
   interested in purchasing. The System displays information about the selected Sale (data related Product, quantity and
   price of the Sale).
5. The Buyer User enters the quantity of that Product he/she intends to purchase. The System records the Buyer User's
   choice of quantity.
   The Buyer User repeats steps 4-5 until it indicates that it is finished.
6. The System displays a summary of the Shopping Cart entered by the Buyer User (listing the Cart rows with Sales
   chosen, each with the
   details of the relevant Product, quantity and price of the Sale, and the total cost of the Shopping Cart).
7. The Buyer User confirms the purchase of the Shopping Cart entered. The System records the purchase by the Buyer User,
   also recording the date and time of purchase with an Order summary for the buyer User.

`Extensions:`

- 6a. The Buyer User, who does not want to add the selected Sale to his or her current Shopping Cart, cancels the
  selection of the Sale. The System does not record the addition of the Sale in the Buyer User's Shopping Cart.
- 8a. The Buyer User cancels the Purchase transaction of the current Shopping Cart. The System does not record the Buyer
  User's purchase of the Shopping Cart.

### Use Case UC4:

### Use Case UC5:

### Use Case UC6:

### Use Case UC7:

### Use Case UC8:

### Use Case UC9:

## UML Domain Model ‍🎓

<img title="MarketNexus UML Domain Model" alt="MarketNexus UML Domain model" src="/src/main/resources/static/images/README/schemas/OO/OOA/Domain_Model.png" width="100%" />

## UML System Sequence Diagram ‍🎓

<img title="MarketNexus UML SSD" alt="MarketNexus UML SSD" src="/src/main/resources/static/images/README/schemas/OO/OOA/SSD.png" width="100%" />

<!--

## UML Interaction Diagram ‍🎓

<img title="MarketNexus UML Interaction Diagram" alt="MarketNexus UML Interaction Diagram" src="/src/main/resources/static/images/README/schemas/OO/OOD/Interaction_Diagram.png" width="100%" />

-->

## UML Domain Class Diagram 🤓

<img title="MarketNexus UML DCD" alt="MarketNexus UML DCD" src="/src/main/resources/static/images/README/schemas/OO/OOD/DCD.png" width="100%" />

## ER Model 🔢

<img title="MarketNexus ER model" alt="MarketNexus ER model" src="/src/main/resources/static/images/README/schemas/DB/ER_Model.png" width="100%" />

## Relational Model 👨‍🎓

<img title="MarketNexus Relational model" alt="MarketNexus Relational model" src="/src/main/resources/static/images/README/schemas/DB/Relational_Model.png" width="100%" />

## Network 🛜

<img title="MarketNexus Network" alt="MarketNexus Network" src="/src/main/resources/static/images/README/schemas/network/marketnexus_network.png" width="100%" />

## Key Features and characteristics✨

- **Usage:** Users can publish their products, put product in their cart (set the quantity), buy other products,
  search products of a
  wide range of categories and more.

- **Responsive:** The site is responsive and user-friendly.

- **Dynamic**: The site is content-dynamic and interactive. There are also special effects.

- **Security and user errors control:** The user's sensitive data, such as their password, are encrypted and stored in a
  very robust database. There are also errors control in in client-side and server-side (also CHECKS and TRIGGERS in the
  Database) and it is possible to sign in
  with your google account. Furthermore, sensitive data present in the configuration files has been hidden via
  environment variables.

- **Tested:** The project is tested with Junit tests.

- **Modularity:** The project is divided in many logic modules, packages, fragments, directories and global variables...

- **Tooltips guide Display:** There are many tooltips and popups that guide the User in the site. There is also a FAQs
  page.

- **Language:** There is the English language at the moment and a bit of internationalization.

## Screenshots 📸

## `Home page`

<p align="center">
	<img  title="MarketNexus HomeScreen screenshoot 1"  alt="MarketNexus HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%" />
</p>

## `Registration page`

<p align="center">
<img  title="MarketNexus RegScreen screenshoot 1"  alt="MarketNexus RegScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/registration/1.png"  width="100%" />
</p>

## `Login page`

<p align="center">
	<img  title="MarketNexus LoginScreen screenshoot 1"  alt="MarketNexus LoginScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/login/1.png"  width="100%" />
</p>

## `FAQs page`

<p align="center">
	<img  title="MarketNexus FAQsScreen screenshoot 1"  alt="MarketNexus HelpScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/FAQs/1.png"  width="100%" />
</p>

## `Forgot username page`

<p align="center">
	<img  title="MarketNexus ForgotusernameScreen screenshoot 1"  alt="MarketNexus ForgotusernameScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/forgotUsername/1.png"  width="100%" />
</p>

## `Sales page`

<p align="center">
	<img  title="MarketNexus Sales screenshoot 1"  alt="MarketNexus Sales screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/marketplace/1.png"  width="100%" />
    <img  title="MarketNexus Sales screenshoot 2"  alt="MarketNexus Sales screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/marketplace/2.png"  width="100%" />
</p>

## `Account page`

<p align="center">
	<img  title="MarketNexus AccountScreen screenshoot 1"  alt="MarketNexus MarketplaceScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/account/1.png"  width="100%" />
    <img  title="MarketNexus AccountScreen screenshoot 2"  alt="MarketNexus MarketplaceScreen screenshoot 2"  src="/src/main/resources/static/images/README/screenshots/account/2.png"  width="100%" />
    <img  title="MarketNexus AccountScreen screenshoot 3"  alt="MarketNexus MarketplaceScreen screenshoot 3"  src="/src/main/resources/static/images/README/screenshots/account/3.png"  width="100%" />

</p>

## `New Sale page`

<p align="center">
	<img  title="MarketNexus NewSale screenshoot 1"  alt="MarketNexus NewSale screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/newSale/1.png"  width="100%" />
</p>

## `Cart page`

<p align="center"> 
	<img  title="MarketNexus CartScreen screenshoot 1"  alt="MarketNexus StatsScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/cart/1.png"  width="100%" />
	<img  title="MarketNexus CartScreen screenshoot 2"  alt="MarketNexus StatsScreen screenshoot 2"  src="/src/main/resources/static/images/README/screenshots/cart/2.png"  width="100%" />
	<img  title="MarketNexus CartScreen screenshoot 3"  alt="MarketNexus StatsScreen screenshoot 3"  src="/src/main/resources/static/images/README/screenshots/cart/3.png"  width="100%" />
	<img  title="MarketNexus CartScreen screenshoot 4"  alt="MarketNexus StatsScreen screenshoot 4"  src="/src/main/resources/static/images/README/screenshots/cart/4.png"  width="100%" />
</p>

## Installation 🚀 and usage⚡

### Requirements

- Java 17 +
- Maven 3.9 +
- PostgreSQL 16.0 +

### Installation Instructions

1. Clone the repository:

```bash
git clone https://github.com/MattDEV02/MarketNexus.git
```

2. Navigate to the project directory:

```bash
cd MarketNexus
```

3. Install dependencies:

```bash
mvnw install

# or using gradle

# gradle install
```

4. Build Java code:

```bash
mvnw compile

# or using gradle

# gradle compileJava
```

5. Packaging the code up in a JAR file:

```bash
mvnw package

# or using gradle

# gradle assemble
```

6. Execute the JAR file:

```bash
java -jar target/MarketNexus-0.0.1-SNAPSHOT.jar
```

P.S. = Remember to create and populate the Postgres Database by running the script contained in the root folder of the
project (MarketNexus.sql).

## Some code examples 🤖

### `MarketNexusApplication.java` -> `com.market.marketnexus.MarketNexusApplication`

```java
package com.market.marketnexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * MarketNexusApplication, the main entry point of the Spring Boot application.
 *
 * <p>This class is annotated with {@code @SpringBootApplication} which is a
 * convenience annotation that adds all of the following:
 * <ul>
 *   <li>{@code @Configuration}</li>
 *   <li>{@code @EnableAutoConfiguration}</li>
 *   <li>{@code @ComponentScan}</li>
 * </ul>
 *
 * <p>The {@code main} method uses {@link SpringApplication#run} to launch the application.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     SpringApplication.run(Application.class, args);
 * }
 * }
 * </pre>
 *
 * <p>This is typically the main class in a Spring Boot application, used to bootstrap the application.
 *
 * @see SpringApplication
 * @see Configuration
 * @see EnableWebMvc
 * @see SpringBootApplication
 */
@Configuration
@EnableWebMvc
@SpringBootApplication
public class MarketNexusApplication {
   // CTRL + FN + F9
   public static void main(String[] args) {
      SpringApplication.run(MarketNexusApplication.class, args);
   }

}
```

### `AuthConfiguration.java` -> `com.market.marketnexus.authentication.AuthConfiguration`

```java
package com.market.marketnexus.authentication;

import com.market.marketnexus.handler.CustomLogoutSuccessHandler;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.helpers.credentials.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AuthConfiguration implements WebMvcConfigurer {

   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:" + ProjectPaths.STATIC + "/"};
   @Autowired
   private DataSource dataSource;
   @Autowired
   private CustomLogoutSuccessHandler customLogoutSuccessHandler;

   @Override
   public void addResourceHandlers(@NonNull ResourceHandlerRegistry resourceHandlerRegistry) {
      resourceHandlerRegistry.addResourceHandler("/**")
              .addResourceLocations(AuthConfiguration.CLASSPATH_RESOURCE_LOCATIONS)
      //  .setCachePeriod(0)
      ;
   }

   @Autowired
   public void configureGlobal(@NonNull AuthenticationManagerBuilder authenticationManagerBuilder)
           throws Exception {
      authenticationManagerBuilder.jdbcAuthentication()
              //use the autowired datasource to access the saved credentials
              .dataSource(this.dataSource)
              //retrieve username and role
              .authoritiesByUsernameQuery("SELECT username, role FROM Credentials WHERE username = ?")
              //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
              .usersByUsernameQuery("SELECT username, password, TRUE AS enabled FROM Credentials WHERE username = ?");
   }

   @Bean
   public PasswordEncoder passwordEncoder() { // Bcrypt algorithm
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(@NonNull AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   protected SecurityFilterChain configure(final @NonNull HttpSecurity httpSecurity) throws Exception {
      httpSecurity
              .cors(AbstractHttpConfigurer::disable)
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(
                      authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                              .requestMatchers(HttpMethod.GET, "/", "/registration", "/login", "/forgotUsername", "/logout", "/FAQs", "/css/**", "/js/**", "/images/**").permitAll()
                              .requestMatchers(HttpMethod.POST, "/registerNewUser", "/sendForgotUsernameEmail", "/storeFirebaseToken").permitAll()
                              .requestMatchers("/json/**", "/txt/**").denyAll()
                              .requestMatchers("/firebase-cloud-messaging-push-scope", "/firebase-messaging-sw.js").permitAll()
                              .requestMatchers(new RegexRequestMatcher(".*newSale.*", null)).hasAnyAuthority(Roles.SELLER_AND_BUYER.toString(), Roles.SELLER.toString())
                              .requestMatchers(HttpMethod.GET, "/" + APIPaths.MARKETPLACE + "/sales/updatedSale").hasAnyAuthority(Roles.SELLER_AND_BUYER.toString(), Roles.SELLER.toString())
                              .requestMatchers(HttpMethod.POST, "/" + APIPaths.MARKETPLACE + "/sales/publishUpdatedSale").hasAnyAuthority(Roles.SELLER_AND_BUYER.toString(), Roles.SELLER.toString())
                              .requestMatchers(new RegexRequestMatcher(".*cart.*", null)).hasAnyAuthority(Roles.SELLER_AND_BUYER.toString(), Roles.BUYER.toString())
                              .requestMatchers(new RegexRequestMatcher(".*order.*", null)).hasAnyAuthority(Roles.SELLER_AND_BUYER.toString(), Roles.BUYER.toString())
                              .requestMatchers(HttpMethod.GET, "/" + APIPaths.MARKETPLACE + "/**").authenticated()
                              .requestMatchers(HttpMethod.POST, "/" + APIPaths.MARKETPLACE + "/**").authenticated()
                              .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")
                      .defaultSuccessUrl("/" + APIPaths.SALES, true)
                      .failureUrl("/login?invalidCredentials=true")
                      .usernameParameter("username")
                      .passwordParameter("password")
                      .permitAll()
              )
              .oauth2Login(oauth2Login ->
                      oauth2Login
                              .loginPage("/oauth2/authorization/google")
                              .defaultSuccessUrl("/" + APIPaths.SALES, true)
                              .failureUrl("/login?invalidCredentials=true")
                              .permitAll()
              )
              .logout(logout -> logout
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/login?logoutSuccessful=true")
                      .invalidateHttpSession(true)
                      .clearAuthentication(true)
                      .deleteCookies("JSESSIONID")
                      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .clearAuthentication(true)
                      .logoutSuccessHandler(this.customLogoutSuccessHandler)
                      .permitAll());
      return httpSecurity.build();
   }

}
```

### `FirebaseConfig.java` -> `com.market.marketnexus.config.FirebaseConfig`

```java
package com.market.marketnexus.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

   @Bean
   public FirebaseApp firebaseApp() throws IOException {
      FileInputStream serviceAccount =
              new FileInputStream("src/main/resources/static/json/marketnexus-firebase-adminsdk-14lel-745b5f32e4.json");

      FirebaseOptions options = FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .build();

      return FirebaseApp.initializeApp(options);
   }

   @Bean
   public FirebaseMessaging firebaseMessaging() throws IOException {
      return FirebaseMessaging.getInstance(this.firebaseApp());
   }
}
```

### `CustomLogoutSuccessHandler.java` -> `com.market.marketnexus.handler.CustomLogoutSuccessHandler`

```java
package com.market.marketnexus.handler;


import com.market.marketnexus.exception.UserCredentialsUsernameNotExistsException;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

   @Autowired
   private CredentialsService credentialsService;
   @Autowired
   private UserService userService;

   @Override
   public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
      Credentials credentials = null;
      try {
         if (Utils.userIsLoggedIn(authentication)) {
            credentials = this.credentialsService.getCredentials(authentication.getName());
         }
      } catch (UserCredentialsUsernameNotExistsException userCredentialsUsernameNotExistsException) {
         if (Utils.userIsLoggedInWithOAuth2(authentication)) {
            Object principal = authentication.getPrincipal();
            OAuth2User oAuth2User = (OAuth2User) (principal);
            String email = oAuth2User.getAttribute("email");
            User loggedUser = this.userService.getUser(email);
            credentials = loggedUser.getCredentials();
         }
      } finally {
         try {
            this.credentialsService.updateIsOnline(credentials, false);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.sendRedirect("/login?logoutSuccessful=true"); // Reindirizza dopo il logout
         } catch (IOException iOException) {
            iOException.printStackTrace();
         }
      }

   }
}
```

### `AuthenticationController.java` -> `com.market.marketnexus.controller.AuthenticationController`

```java
package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.CredentialsValidator;
import com.market.marketnexus.controller.validator.UserValidator;
import com.market.marketnexus.exception.UserEmailNotExistsException;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.UserService;
import com.market.marketnexus.service.email.ForgotUsernameEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

@Controller
public class AuthenticationController {

   public final static String REGISTRATION_SUCCESSFUL_VIEW = "redirect:/login?registrationSuccessful=true";
   public final static String REGISTRATION_ERROR_VIEW = "registration.html";
   private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private UserService userService;
   @Autowired
   private UserValidator userValidator;
   @Autowired
   private CredentialsValidator credentialsValidator;
   @Autowired
   private ForgotUsernameEmailService forgotUsernameEmailService;

   @GetMapping(value = {"/registration", "/registration/"})
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("user", new User());
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @PostMapping(value = {"/registerNewUser", "/registerNewUser/"})
   public ModelAndView registerUser(@Valid @NonNull @ModelAttribute("user") User user,
                                    @NonNull BindingResult userBindingResult,
                                    @Valid @NonNull @ModelAttribute("credentials") Credentials credentials,
                                    @NonNull BindingResult credentialsBindingResult,
                                    @NonNull @RequestParam("confirm-password") String confirmPassword) {
      ModelAndView modelAndView = new ModelAndView(AuthenticationController.REGISTRATION_ERROR_VIEW);
      this.credentialsValidator.setConfirmPassword(confirmPassword);
      this.userValidator.validate(user, userBindingResult);
      this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
         Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);
         user.setCredentials(credentials);
         User savedUser = this.userService.saveUser(user);
         if (savedUser != null) {
            AuthenticationController.LOGGER.info("Registered account with User ID: {}", savedUser.getId());
            modelAndView.setViewName(AuthenticationController.REGISTRATION_SUCCESSFUL_VIEW);
         } else {
            AuthenticationController.LOGGER.error(GlobalErrorsMessages.USER_NOT_REGISTERED_ERROR);
            modelAndView.addObject("userNotRegisteredError", "Server ERROR, User not registered.");
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/login", "/login/"})
   public ModelAndView showLoginForm() {
      ModelAndView modelAndView = new ModelAndView("login.html");
      modelAndView.addObject("credentials", new Credentials());
      return modelAndView;
   }

   @GetMapping(value = {"/forgotUsername", "forgotUsername/"})
   public ModelAndView showForgotUsernameForm() {
      ModelAndView modelAndView = new ModelAndView("forgotUsername.html");
      modelAndView.addObject("user", new User());
      return modelAndView;
   }

   @PostMapping(value = {"/sendForgotUsernameEmail", "/sendForgotUsernameEmail/"})
   public ModelAndView sendForgotUsernameEmail(
           @Valid @NonNull @ModelAttribute("user") User user,
           @NonNull BindingResult userBindingResult) {
      ModelAndView modelAndView = new ModelAndView("forgotUsername.html");
      if (!userBindingResult.hasFieldErrors("email")) {
         try {
            User userByEmail = this.userService.getUser(user.getEmail());
            this.forgotUsernameEmailService.sendEmail(userByEmail.getEmail(), userByEmail.getCredentials().getUsername());
            modelAndView.addObject("emailSentSuccess", true);
         } catch (IOException | MessagingException exception) {
            AuthenticationController.LOGGER.error(exception.getMessage());
            modelAndView.addObject("emailNotSentError", true);
            AuthenticationController.LOGGER.error(GlobalErrorsMessages.EMAIL_NOT_SENT_ERROR);
         } catch (UserEmailNotExistsException userEmailNotExistsException) {
            AuthenticationController.LOGGER.error(userEmailNotExistsException.getMessage());
            modelAndView.addObject("emailNotExistsError", true);
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/" + APIPaths.MARKETPLACE, "/" + APIPaths.MARKETPLACE + "/"})
   public ModelAndView redirectToMarketPlaceSales() {
      return new ModelAndView("redirect:/" + APIPaths.SALES);
   }

   @PostMapping("/storeFirebaseToken")
   public ResponseEntity<?> storeFirebaseToken(@RequestBody Map<String, String> data) {
      try {
         Path path = Paths.get("src/main/resources/static/txt/tokens.txt");
         List<String> existingTokens = Files.readAllLines(path);
         String token = data.get("token");
         if (token != null && !existingTokens.contains(token)) {
            Files.write(path, (token + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            AuthenticationController.LOGGER.info("Firebase token stored successfully: {}", token);
            return new ResponseEntity<>(HttpStatus.OK);
         } else {
            // Token già esistente
            String responseMessage = "Firebase token already exists";
            AuthenticationController.LOGGER.warn(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
         }
      } catch (IOException e) {
         AuthenticationController.LOGGER.error(e.getMessage());
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}
```

### `UserService.java` -> `com.market.marketnexus.service.UserService`

```java
package com.market.marketnexus.service;

import com.market.marketnexus.exception.UserEmailNotExistsException;
import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartRepository;
import com.market.marketnexus.repository.OrderRepository;
import com.market.marketnexus.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   @Autowired
   protected UserRepository userRepository;
   @Autowired
   protected CartRepository cartRepository;
   @Autowired
   protected OrderRepository orderRepository;

   public Boolean userExistsByEmail(String email) {
      return this.userRepository.existsByEmail(email);
   }

   public User getUser(Long userId) {
      Optional<User> result = this.userRepository.findById(userId);
      return result.orElse(null);
   }

   public User getUser(Credentials credentials) {
      Optional<User> result = this.userRepository.findByCredentials(credentials);
      return result.orElse(null);
   }

   public User getUser(String email) {
      return this.userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotExistsException("User with email '" + email + "' does not exist."));
   }

   @Transactional
   public Cart getUserCurrentCart(Long userId) {
      Cart currentCart = null;
      User user = this.getUser(userId);
      if (user != null) {
         List<Cart> carts = user.getCarts();
         currentCart = carts.get(carts.size() - 1);
      }
      return currentCart;
   }

   @Transactional
   public User saveUser(@NotNull User user) {
      User savedUser = this.userRepository.save(user);
      Cart cart = new Cart(user);
      Cart savedCart = this.cartRepository.save(cart);
      savedUser.getCarts().add(savedCart);
      return savedUser;
   }

   @Transactional
   public User updateUser(Long userId, @NonNull User updatedUser) {
      Credentials updatedCredentials = updatedUser.getCredentials();
      User user = this.getUser(userId);
      if (user != null) {
         Credentials credentials = user.getCredentials();
         updatedCredentials.setInsertedAt(credentials.getInsertedAt());
         user.getCredentials().setUsername(updatedCredentials.getUsername());
         if (TypeValidators.validateString(updatedCredentials.getPassword())) {
            user.getCredentials().setPassword(updatedCredentials.getPassword());
         }
         user.getCredentials().setRole(updatedCredentials.getRole());
         user.getCredentials().preUpdate();
         user.setName(updatedUser.getName());
         user.setSurname(updatedUser.getSurname());
         user.setBirthDate(updatedUser.getBirthDate());
         this.updateUserBalance(user, updatedUser.getBalance());
         user.setNation(updatedUser.getNation());
         return this.userRepository.save(user);
      }
      return null;
   }

   @Transactional
   public Boolean deleteUser(User user) {
      this.userRepository.delete(user);
      return !this.userRepository.existsById(user.getId());
   }

   public List<Object[]> countUsersByNation() {
      return this.userRepository.countUsersByNation();
   }

   public List<Object[]> usersPublishedSalesStats() {
      return this.userRepository.userSalesStats();
   }

   @Transactional
   public void updateUserBalance(@NotNull User user, Float newBalance) {
      Float roundedNewBalance = Utils.roundNumberTo2Decimals(newBalance);
      user.setBalance(roundedNewBalance);
   }
}
```

### `SaleRepository.java` -> `com.market.marketnexus.repository.SaleRepository`

```java
package com.market.marketnexus.repository;

import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

   public Iterable<Sale> findAllByOrderByUpdatedAt();

   public Iterable<Sale> findAllByUser(User user);

   @Query(value = """
           SELECT *
           FROM GET_USER_SOLD_SALES_STATS(:userId);
           """,
           nativeQuery = true)
   public List<Object[]> countCurrentWeekUserSales(@Param("userId") Long userId);
}

```

### `Cart.java` -> `com.market.marketnexus.model.Cart`

```java
package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Cart")
@Table(name = "Carts", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = {@UniqueConstraint(name = "carts_user_insertedat_unique", columnNames = {"_user", "inserted_at"})})
public class Cart {
   public final static Float CART_START_PRICE = 0.0F;
   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(value = FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @Min((long) FieldSizes.CART_CARTPRICE_MIN_VALUE)
   @Column(name = "cart_price", nullable = false)
   private Float cartPrice;

   @ManyToOne(targetEntity = User.class, optional = true)
   @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "carts_users_fk"))
   private User user;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime insertedAt;

   @OneToMany(targetEntity = CartLineItem.class, mappedBy = "cart", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
   @OrderBy(value = "insertedAt DESC")
   private List<CartLineItem> cartLineItems;

   public Cart() {
      this.cartPrice = Cart.CART_START_PRICE;
      this.user = null;
      this.cartLineItems = new ArrayList<CartLineItem>();
   }

   public Cart(User user) {
      this.user = user;
      this.cartPrice = Cart.CART_START_PRICE;
      this.cartLineItems = new ArrayList<CartLineItem>();
   }

   public Float getCartPrice() {
      return this.cartPrice;
   }

   public void setCartPrice(Float cartPrice) {
      this.cartPrice = cartPrice;
   }

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public LocalDateTime getInsertedAt() {
      return this.insertedAt;
   }

   public void setInsertedAt(LocalDateTime insertedAt) {
      this.insertedAt = insertedAt;
   }

   public List<CartLineItem> getCartLineItems() {
      return this.cartLineItems;
   }

   public void setCartLineItems(List<CartLineItem> cartLineItems) {
      this.cartLineItems = cartLineItems;
   }

   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      Cart cart = (Cart) object;
      return Objects.equals(this.getId(), cart.getId()) || (Objects.equals(this.getUser(), cart.getUser()) && Objects.equals(this.getInsertedAt(), cart.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "Cart: {" +
              // "id = " + this.getId() != null ? this.getId().toString() : "null" +
              //", user = " + this.getUser().toString() +
              ", cartPrice = " + this.getCartPrice().toString() +
              //", cartLineItems = " + this.getCartLineItems().toString() +
              //", insertedAt = " + this.getInsertedAt() != null ? this.getInsertedAt().toString() : "null" +
              " }";
   }

   public CartLineItem getCartLineItem(Long cartLineItemId) {
      return this.getCartLineItems().stream()
              .filter(cartLineItem -> cartLineItem.getId().equals(cartLineItemId))
              .findFirst().orElse(null);
   }

   public void addCartLineItem(CartLineItem cartLineItem) {
      this.getCartLineItems().add(cartLineItem);
   }

   public void removeCartLineItem(CartLineItem cartLineItem) {
      this.getCartLineItems().remove(cartLineItem);
   }
}
```

### `SaleNotFoundException.java` -> `com.market.marketnexus.exception.SaleNotFoundException`

```java
package com.market.marketnexus.exception;

public class SaleNotFoundException extends RuntimeException {
   public SaleNotFoundException() {
      super();
   }

   public SaleNotFoundException(String message) {
      super(message);
   }
}
```

### `pom.xml`

```XML
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.market</groupId>
    <artifactId>MarketNexus</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>MarketNexus</name>
    <description>MarketNexus is a marketplace made in Spring boot 🤖</description>
    <properties>
        <java.version>17</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>24.1.0</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
            <version>3.1.5</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-client</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <contributors>
        <contributor>
            <name>Matteo Lambertucci</name>
            <email>mat.lambertucci@stud.uniroma3.it</email>
            <url>https://github.com/MattDEV02</url>
            <organization>Università degli Studi di Roma Tre</organization>
            <organizationUrl>https://www.uniroma3.it/</organizationUrl>
            <roles>
                <role>ALL</role>
            </roles>
            <timezone>+2</timezone>
        </contributor>
    </contributors>

</project>
```

### `/src/main/resources/application.properties`

```properties
#==================================
# = Datasource configurations
#==================================
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=debug
logging.level.org.hibernate.type.descriptor.sql=trace
spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.default_schema=marketnexus
#spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
#==================================
# = Web server configurations
#==================================
spring.application.name=MarketNexus
server.port=80
server.servlet.context-path=/
logging.level.root=INFO
spring.servlet.multipart.max-file-size=5000KB
spring.servlet.multipart.max-request-size=5000KB
server.error.whitelabel.enabled=false
server.error.include-stacktrace=always
#server.servlet.session.timeout=40m
spring.web.resources.static-locations=classpath:/static/
spring.servlet.multipart.enabled=true
spring.web.locale=en_US
#spring.web.resources.cache.period=0
#spring.web.resources.chain.cache=false
spring.jackson.time-zone=Europe/Rome
#==================================
# = Messages configurations
#==================================
spring.messages.basename=messages/messages
spring.messages.encoding=ISO-8859-1
#==================================
# = Thymeleaf configurations
#==================================
spring.thymeleaf.check-template-location=true
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.servlet.content-type=text/html
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true
spring.thymeleaf.encoding=UTF-8
#==================================
# = SMTP configurations
#==================================
spring.mail.host=smtp.office365.com
spring.mail.port=587
spring.mail.protocol=smtp
spring.mail.username=mat.lambertucci@stud.uniroma3.it
spring.mail.password=${SMTP_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
#==================================
# = Google Oauth2 configurations
#==================================
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_OAUTH2_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_OAUTH2_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.scope=profile, email
spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:80/login/oauth2/code/google
spring.security.oauth2.client.registration.google.authorization-grant-type=authorization_code
spring.security.oauth2.client.provider.google.authorization-uri=https://accounts.google.com/o/oauth2/auth
spring.security.oauth2.client.provider.google.token-uri=https://oauth2.googleapis.com/token
spring.security.oauth2.client.provider.google.user-info-uri=https://www.googleapis.com/oauth2/v3/userinfo
```

### `/templates/marketplace/cart.html`

```thymeleaftemplatesfragmentexpressions
<!DOCTYPE html>
<html th:lang="${GLOBAL_CONSTANTS_MAP.get('LANG')}" th:xmlns:th="${GLOBAL_CONSTANTS_MAP.get('TEMPLATES_XMLNS')}">

<head th:replace="~{fragments/shared/head.html :: head(title = 'Cart')}">

</head>
<link rel="stylesheet" th:href="@{'/css/' + ${API_PATHS_MAP.get('MARKETPLACE')} + '/shared/style.css'}"/>
<link rel="stylesheet" th:href="@{'/css/' + ${API_PATHS_MAP.get('CART')} + '/style.css'}"/>
<body>
<div th:replace="~{fragments/shared/pagination/header/marketplaceHeader.html :: marketplaceHeader()}">
</div>
<main id="cart-container">
   <div class="container" th:fragment="dynamicCartSection">
      <div class="row justify-content-center">
         <noscript th:replace="~{fragments/shared/noScript.html :: noScript()}"></noscript>
         <div class="col-12 mt-5">
            <div class="row text-center">
               <h1 th:text="${cartLineItems != null && !#lists.isEmpty(cartLineItems) ? 'Your' : 'No'} + ' Cart Products 👀'">
                  Cart
               </h1>
            </div>
         </div>
         <div class="col-12 my-5"
              th:with="cartLineItemNotDeletedError = ${param.cartLineItemNotDeletedError != null}, cartLineItemDeletedSuccess = ${param.cartLineItemDeletedSuccess != null}, userBalanceLowerThanCartPriceError = ${param.userBalanceLowerThanCartPriceError != null}, userNotBuyerAddSaleToCartError = ${param.userNotBuyerAddSaleToCartError != null}, userAddOwnSaleToCartError = ${param.userAddOwnSaleToCartError != null}, emptyCartError = ${param.emptyCartError != null}, userCartNotExistsError = ${param.userCartNotExistsError != null}">
            <div
                  th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Cart line not deleted.', condition = ${cartLineItemNotDeletedError})}"></div>
            <div
                  th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Users cannot add them Sale to them Cart.', condition = ${userAddOwnSaleToCartError})}"></div>
            <div
                  th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Order not possible, your Cart is empty.', condition = ${emptyCartError})}"></div>
            <div
                  th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Your balance is not sufficient to complete the order.', condition = ${userBalanceLowerThanCartPriceError})}"></div>
            <div
                  th:replace="~{fragments/shared/message/success/successMessage.html :: successMessage(text = 'Cart line deleted.', condition = ${cartLineItemDeletedSuccess})}"></div>
            <div th:replace="~{fragments/marketplace/cart/modal/confirmOrderModal.html :: confirmOrderModal(cart = ${cart})}"></div>
            <div class="row justify-content-center" th:each="cartLineItem : ${cartLineItems}">
               <div th:replace="~{fragments/marketplace/cart/cartLineInformation.html :: cartLineInformation(cartLineItem = ${cartLineItem})}"></div>
            </div>
            <div th:replace="~{fragments/marketplace/cart/cartTotalLineInformation.html :: cartTotalLineInformation(cart = ${cart})}"></div>
         </div>
      </div>
      <script th:charset="${GLOBAL_CONSTANTS_MAP.get('CHARSET')}"
              th:src="@{/js/libraries/axios/axios.min.js}" type="text/javascript"></script>
      <script th:charset="${GLOBAL_CONSTANTS_MAP.get('CHARSET')}" th:src="@{/js/shared/validators.js}"
              type="text/javascript"></script>
      <script th:charset="${GLOBAL_CONSTANTS_MAP.get('CHARSET')}"
              th:src="@{'/js/' + ${API_PATHS_MAP.get('CART')} + '/index.js'}"
              type="text/javascript"></script>
   </div>
</main>
<div th:replace="~{fragments/shared/pagination/footer/footer.html :: footer()}">
</div>
</body>

</html>
```

### `/css/shared/style.css`

```CSS
[type="submit"] {
   border: none;
   cursor: pointer;
}

.btn:disabled {
   opacity: 1;
!important;
}

@media (max-width: 767px) {
   img.card-img-top {
      height: 27.5rem;
   }
}

@media (min-width: 768px) and (max-width: 991px) {
   img.card-img-top {
      height: 19.5rem;
   }
}

@media (min-width: 992px) and (max-width: 1199px) {
   img.card-img-top {
      height: 26rem;
   }
}

@media (min-width: 1200px) {
   img.card-img-top {
      height: 18rem;
   }
}

.tooltip {
   color: #FFFFFF;
!important /* Cambia il colore del testo */
border-radius: 10 px;
!important /* Arrotonda i bordi */
box-shadow: 0 0 10 px rgba(0, 0, 0, 0.5);
!important /* Aggiungi ombreggiatura */
font-size: 16 px;
!important /* Cambia la dimensione del testo */
}

.tooltip .tooltip-arrow {

}

.tooltip-inner {
   padding: 10px 13px;
!important /* Aggiungi spaziatura interna */
}

.btn-google {
   display: flex;
   align-items: center;
   justify-content: center;
   background-color: #FFFFFF;
   color: #757575;
   border: 1px solid #DCDCDC;
   border-radius: 0.375rem;
   padding: 0.5rem 0.75rem;
   font-size: 1rem;
   font-weight: 500;
   text-decoration: none;
   box-shadow: 0 2px 4px 0 #00000019;
}

.btn-google:hover {
   background-color: #F5F5F5;
   color: #757575;
   text-decoration: none;
}

.btn-google .google-icon-wrapper {
   background-color: #FFFFFF;
   display: inline-flex;
   align-items: center;
   justify-content: center;
   margin-right: 0.3rem;
   padding-right: 0.3rem;
}

.btn-google .google-icon-wrapper img {
   width: 18px;
   height: 18px;
}
```

### `/js/marketplace/account/stats/chart/index.js`

```javascript
document.addEventListener("DOMContentLoaded", () => {

   //Chart.defaults.elements.bar.borderWidth = 2;

   const CHART_TYPES = {
      bar: "bar",
      line: "line",
      horizontalBar: "horizontalBar",
      pie: "pie",
      //radar: "radar",
      //polarArea: "polarArea",
      bubble: "bubble",
      doughnut: "doughnut",
   };

   const isMultiColorChartType = chartType => chartType === CHART_TYPES.pie || chartType === CHART_TYPES.doughnut;

   const getChartColor = chartType =>
      isMultiColorChartType(chartType) ? [
         "#0D6EFD", // PRIMARY
         "#6C757D", // SECONDARY
         "#198754", // SUCCESS
         "#DC3545", // DANGER
         "#FFC107", // WARNING
         "#0DCAF0", // INFO
         "#212529", // DARK
      ] : "#1D86BA";


   const chartTypeSelect = document.getElementById("chart-type-select");

   const weekDaysXToNumberOfSalesY = [];

   let weekDaysX = null, numberOfSoldSalesY = null;

   const canvas = document.getElementById("chart");

   const ctx = canvas.getContext("2d");

   let type = null, data = null, options = null, config = null;

   axios.get(`${baseAPIURI}chartData`)
      .then(response => {
         console.log(response);
         if (validateObject(response) && validateObject(response.data) && response.status === 200) {
            const chartData = response.data;
            chartData.forEach(chartDataRow => {
               weekDaysXToNumberOfSalesY.push({
                  weekDay: chartDataRow[0],
                  numberOfSoldSales: chartDataRow[1],
               });
            });
            weekDaysX = weekDaysXToNumberOfSalesY.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.weekDay);
            numberOfSoldSalesY = weekDaysXToNumberOfSalesY.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.numberOfSoldSales);
            type = CHART_TYPES.bar;
            data = {
               labels: weekDaysX,
               datasets: [{
                  label: " Number sold of Sales in this day",
                  data: numberOfSoldSalesY,
                  borderWidth: 2,
                  backgroundColor: "#1D86BA",
                  borderColor: "#000000",
                  pointRadius: 5,
               }]
            };
            options = {
               indexAxis: "x",
               responsive: true,
               maintainAspectRatio: false,
               plugins: {
                  title: {
                     display: true,
                     text: "Number of sold Sales in this week",
                     fullSize: true,
                     font: {
                        weight: "bold",
                        size: 15.5
                     }
                  },
                  legend: {
                     labels: {
                        font: {
                           size: 15
                        }
                     }
                  },
               },
               scales: {
                  x: {
                     beginAtZero: true,
                     ticks: {
                        font: {
                           size: 14
                        },
                     }
                  },
                  y: {
                     beginAtZero: true,
                     ticks: {
                        font: {
                           size: 13
                        },
                        callback: (value) => parseInt(value) === value ? value : null
                     }
                  }
               }
            };
            config = {
               type,
               data,
               options,
            }
            chart = new Chart(ctx, config);
            chartTypeSelect.addEventListener("change", () => {
               const selectedChartType = chartTypeSelect.value;
               chart.config.type = selectedChartType;
               chart.config.data.datasets[0].backgroundColor = getChartColor(selectedChartType);
               chart.update();
            });
         }
      })
      .catch(error => console.error("Error:", error));
});

const downloadPDFChartButton = document.getElementById("pdf-download-chart-button"),
   downloadPNGChartButton = document.getElementById("png-download-chart-button")
printChartButton = document.getElementById("print-chart-button");

downloadPDFChartButton.addEventListener("click", event => {
   const {jsPDF} = window.jspdf;
   const pdf = new jsPDF();
   const imgData = canvas.toDataURL("image/png");

   // Dimensioni della pagina del PDF
   const pageWidth = pdf.internal.pageSize.getWidth();
   const pageHeight = pdf.internal.pageSize.getHeight();

   const canvasWidth = canvas.width;
   const canvasHeight = canvas.height;
   const ratio = Math.min(pageWidth / canvasWidth, pageHeight / canvasHeight);

   // Calcola le nuove dimensioni per mantenere le proporzioni
   const imgWidth = canvasWidth * ratio;
   const imgHeight = canvasHeight * ratio;

   // Centra l'immagine nella pagina PDF
   const x = (pageWidth - imgWidth) / 2;
   const y = (pageHeight - imgHeight) / 2;

   // Aggiungi l'immagine del canvas al PDF
   pdf.addImage(imgData, "PNG", x, y, imgWidth, imgHeight);
   pdf.save("your_sold_sales_chart.pdf");
});

downloadPNGChartButton.addEventListener("click", () => {
   const link = document.createElement("a");
   link.download = "your_sold_sales_chart.png";
   link.href = canvas.toDataURL("image/png");
   link.click();
});

printChartButton.addEventListener("click", event => {
   const imgData = canvas.toDataURL("image/png");
   const printWindow = window.open("", "_blank");
   printWindow.document.write("<html><head><title>Your sold sales Chart</title><style>body{margin:0;display:flex;align-items:center;justify-content:center;} img{max-width:100%;max-height:100%;}</style></head><body>");
   printWindow.document.write(`<img src="${imgData}" alt="Number of sold Sales in this week" />`);
   printWindow.document.write("</body></html>");
   printWindow.document.close();
   printWindow.focus();
   // Usa un timeout per garantire che la pagina sia caricata prima della stampa
   setTimeout(() => {
      printWindow.print();
      printWindow.close();
   }, 750);
});
```

### `/src/test/java/com/market/martketnexus/model/CartTests.java` -> `com.market.marketnexus.model.CartTests`

```java
package com.market.marketnexus.model;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartsTest {
   @BeforeEach
   public void setUp() {

   }

   @After
   public void tearDown() {

   }

   @Test
   public void testCartStartPrice() {
      Cart cart = new Cart();
      assertNotNull(cart);
      assertNotNull(Cart.CART_START_PRICE);
      assertEquals(cart.getClass(), Cart.class);
      assertEquals(cart.getCartPrice().getClass(), Float.class);
      assertEquals(cart.getCartPrice(), 0.0F);
      assertTrue(cart.getCartLineItems().isEmpty());
   }

   @Test
   public void testCartCartLineItemsOrdering() {
      Cart cart = new Cart();
      Sale sale1 = new Sale();
      assertNotNull(cart);
      assertNotNull(sale1);
      assertFalse(sale1.getIsSold());
      assertEquals(sale1.getQuantity(), Sale.SALE_DEFAULT_QUANTITY);
      assertEquals(sale1.getSalePrice(), 0.0F);
      CartLineItem cartLineItem1 = new CartLineItem(cart, sale1);
      assertNotNull(cartLineItem1);
      assertEquals(cartLineItem1.getQuantity(), CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY);
      cartLineItem1.setInsertedAt(LocalDateTime.now());
      assertEquals(cartLineItem1.getInsertedAt().getClass(), LocalDateTime.class);
      Sale sale2 = new Sale();
      assertNotNull(sale2);
      CartLineItem cartLineItem2 = new CartLineItem(cart, sale2);
      assertNotNull(cartLineItem2);
      assertEquals(cartLineItem1.getQuantity(), CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY);
      cartLineItem2.setInsertedAt(LocalDateTime.MAX);
      assertEquals(cartLineItem2.getInsertedAt().getClass(), LocalDateTime.class);
      assertFalse(sale2.getIsSold());
      assertEquals(sale2.getQuantity(), Sale.SALE_DEFAULT_QUANTITY);
      assertEquals(sale2.getSalePrice(), 0.0F);
      Sale sale3 = new Sale();
      assertNotNull(sale3);
      assertFalse(sale3.getIsSold());
      assertEquals(sale3.getQuantity(), Sale.SALE_DEFAULT_QUANTITY);
      assertEquals(sale3.getSalePrice(), 0.0F);
      CartLineItem cartLineItem3 = new CartLineItem(cart, sale3);
      assertEquals(cartLineItem1.getQuantity(), CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY);
      assertNotNull(cartLineItem3);
      cartLineItem3.setInsertedAt(LocalDateTime.MIN);
      assertEquals(cartLineItem3.getInsertedAt().getClass(), LocalDateTime.class);
      List<CartLineItem> cartLineItems = cart.getCartLineItems();
      assertNotNull(cartLineItems);
      assertEquals(cartLineItems, Lists.newArrayList());
      assertEquals(cartLineItems.size(), 0);
      cartLineItems.add(cartLineItem1);
      assertEquals(cartLineItems.size(), 1);
      cartLineItems.add(cartLineItem2);
      assertEquals(cartLineItems.size(), 2);
      cartLineItems.add(cartLineItem3);
      assertEquals(cartLineItems.size(), 3);
      assertTrue(LocalDateTime.MAX.isAfter(LocalDateTime.now()));
      assertTrue(LocalDateTime.MAX.compareTo(LocalDateTime.now()) > 0);
      assertEquals(cartLineItems, Lists.newArrayList(cartLineItem2, cartLineItem1, cartLineItem3));
      assertThrows(IndexOutOfBoundsException.class, () -> cartLineItems.get(4));
      assertEquals(cart.getCartPrice(), Cart.CART_START_PRICE);
   }
}
```

# API Documentation 👨‍💻

## `BaseController`

The `BaseController` serves the homepage and a FAQs page, providing basic navigation and information about the
application.

## Endpoints

### GET /

- **Description**: Displays the homepage of the application.
- **Response**: The homepage view (`index.html`).

### GET /FAQs

- **Description**: Shows a list of frequently asked questions and their answers.
- **Response**: A view (`FAQs.html`) displaying FAQs.
    - The FAQs are dynamically populated from a static map, covering topics like registration, account management,
      offline navigation, device compatibility, product categories, password requirements, account information,
      repository location, and site authorship.

## Notes

- The FAQs are stored in a static `Map<String, String>` within the `BaseController`, making it easy to update or add new
  questions and answers.
- This controller uses `ModelAndView` to return views, ensuring that data can be passed to the templates for rendering.

## `AuthenticationController`

The `AuthenticationController` manages user authentication processes, including registration, login, and username
recovery. It also handles the storage of Firebase tokens for push notifications.

## Endpoints

### GET /registration

- **Description**: Displays the user registration form.
- **Response**: A view with the registration form.

### POST /registerNewUser

- **Description**: Registers a new user with the provided credentials.
- **Parameters**:
    - `user`: User details.
    - `credentials`: Login credentials.
    - `confirm-password`: Confirmation of the user's password.
- **Response**: Redirects to the login page with a success message if registration is successful; otherwise, displays
  registration errors.

### GET /login

- **Description**: Displays the login form.
- **Response**: A view with the login form.

### GET /forgotUsername

- **Description**: Displays the form for recovering a forgotten username.
- **Response**: A view with the forgot username form.

### POST /sendForgotUsernameEmail

- **Description**: Sends an email to the user with their username if the provided email is associated with an account.
- **Parameters**:
    - `user`: User details, specifically the email address.
- **Response**: A view indicating whether the email was sent successfully or not.

### GET /marketplace

- **Description**: Redirects to the marketplace sales page.
- **Response**: A redirection to the sales page.

### POST /storeFirebaseToken

- **Description**: Stores a Firebase token for push notifications.
- **Parameters**:
    - `data`: A map containing the `token` key with the Firebase token as its value.
- **Response**: HTTP status indicating success or failure of token storage.

## Notes

- The controller uses `ModelAndView` to return views, ensuring that data can be passed to the templates for rendering.
- Error handling is implemented for operations that require specific conditions (e.g., valid data, unique email).
- The controller interacts with `UserService`, `PasswordEncoder`, and `ForgotUsernameEmailService` to manage user
  authentication and notification operations.

## `SaleController`

The `SaleController` manages operations related to sales, including displaying all sales, searching for sales,
publishing new sales, updating existing sales, and deleting sales.

## Endpoints

### GET /marketplace/sales

- **Description**: Displays all sales.
- **Response**: A view with all sales listed.

### GET /marketplace/sales/searchSales

- **Description**: Searches for sales based on product name and/or product category.
- **Parameters**:
    - `product-name`: Name of the product to search for.
    - `category`: ID of the product category to search for.
    - `isAsyncSearch`: Boolean indicating if the search is asynchronous.
- **Response**: A view with the search results.

### GET /marketplace/sales/newSale

- **Description**: Shows the form to publish a new sale.
- **Response**: A form view for publishing a new sale.

### POST /marketplace/sales/publishNewSale

- **Description**: Publishes a new sale.
- **Parameters**:
    - `product`: The product details.
    - `sale`: The sale details.
    - `product-images`: Array of product images.
- **Response**: Redirects to the successful view if the sale is published; otherwise, shows errors.

### GET /marketplace/sales/updateSale/{saleId}

- **Description**: Shows the form to update an existing sale.
- **Parameters**:
    - `saleId`: The ID of the sale to update.
- **Response**: A form view for updating the sale if the user is authorized; otherwise, redirects.

### POST /marketplace/sales/publishUpdatedSale/{saleId}

- **Description**: Updates an existing sale.
- **Parameters**:
    - `saleId`: The ID of the sale to update.
    - `product`: Updated product details.
    - `sale`: Updated sale details.
    - `product-images`: Array of new product images (optional).
- **Response**: Redirects to the successful view if the sale is updated; otherwise, shows errors.

### DELETE /marketplace/sales/deleteSale/{saleId}

- **Description**: Deletes a sale.
- **Parameters**:
    - `saleId`: The ID of the sale to delete.
- **Response**: A JSON response indicating success or failure, with a redirect URL.

### GET /marketplace/sales/sale/{saleId}

- **Description**: Displays details of a specific sale.
- **Parameters**:
    - `saleId`: The ID of the sale to display.
- **Response**: A view with the sale details.

## Notes

- All views are returned as `ModelAndView` objects.
- Responses may include redirections or JSON objects for asynchronous operations.
- Error handling is implemented for operations that require specific conditions (e.g., user authorization, valid data).

## `CartController`

The `CartController` manages operations related to the shopping cart, including displaying cart items, adding sales
products to the cart, updating cart line item quantities, and deleting cart line items.

## Endpoints

### GET /marketplace/cart

- **Description**: Displays all items in the user's cart.
- **Response**: A view with the cart and its line items.

### GET /marketplace/cart/{saleId}

- **Description**: Adds a product associated with a sale to the user's cart by sale ID.
- **Parameters**:
    - `saleId`: The ID of the sale whose product is to be added to the cart.
- **Response**: A view of the sale with an indication if it was added to the cart. Errors are displayed if the user
  tries to add their own sale or a sale already in the cart.

### PUT /marketplace/cart/updateCartLineItemQuantity/{cartLineItemId}

- **Description**: Updates the quantity of a specific cart line item.
- **Parameters**:
    - `cartLineItemId`: The ID of the cart line item to update.
    - `quantity`: The new quantity for the cart line item.
- **Response**: A dynamic section of the cart view with updated quantities.

### DELETE /marketplace/cart/delete/{cartLineItemId}

- **Description**: Deletes a specific cart line item by ID.
- **Parameters**:
    - `cartLineItemId`: The ID of the cart line item to delete.
- **Response**: A dynamic section of the cart view indicating success or failure of the deletion.

## Notes

- The controller uses `ModelAndView` to return views, ensuring that data can be passed to the templates for rendering.
- Error handling is implemented for operations that require specific conditions (e.g., user authorization, valid data).
- The controller interacts with `UserService`, `SaleService`, and `CartService` to manage cart operations.

## `OrderController`

The `OrderController` is responsible for handling operations related to creating orders from cart line items. It ensures
that orders are only created if the cart is not empty, the user has sufficient balance, and the request comes from the
cart page.

## Endpoints

### GET /marketplace/order

- **Description**: Creates an order from the cart line items of the logged-in user.
- **Preconditions**:
    - The request must originate from the cart page.
    - The user's cart must not be empty.
    - The user must have a balance greater than or equal to the total price of the cart.
- **Postconditions**:
    - An order is created with the current cart line items.
    - An email is sent to the user with the order details.
- **Response**:
    - If the preconditions are not met, redirects to the cart page with an appropriate error message.
    - If the order is successfully created, redirects to an order confirmation page with details of the order.

## Notes

- The controller uses `ModelAndView` to return views, ensuring that data can be passed to the templates for rendering.
- Error handling is implemented for operations that require specific conditions (e.g., cart not empty, sufficient user
  balance).
- The controller interacts with `UserService`, `OrderService`, and `OrderedUserSaleEmailService` to manage order
  operations.

## `AccountController`

The `AccountController` manages user account operations, including displaying user account details, updating user
information, and deleting user accounts.

## Endpoints

### GET /marketplace/account

- **Description**: Displays the account details of the logged-in user.
- **Response**: A view with the user's account details, including sales and orders.

### GET /marketplace/account/{username}

- **Description**: Displays the account details of a user by username.
- **Parameters**:
    - `username`: The username of the user whose account details are to be displayed.
- **Response**: A view with the specified user's account details if found; otherwise, an error message.

### POST /marketplace/account/updateAccount

- **Description**: Updates the account details of the logged-in user.
- **Parameters**:
    - `user`: The updated user details.
    - `credentials`: The updated credentials.
    - `confirm-password`: The confirmation of the new password.
- **Response**: Redirects to the account page with a success message if the update is successful; otherwise, displays
  errors.

### DELETE /marketplace/account/delete

- **Description**: Deletes the account of the logged-in user.
- **Response**: Redirects to the logout page if the deletion is successful; otherwise, displays an error message.

## Notes

- The controller uses `ModelAndView` to return views, ensuring that data can be passed to the templates for rendering.
- Error handling is implemented for operations that require specific conditions (e.g., valid data, user authorization).
- The controller interacts with services such as `UserService`, `SaleService`, `OrderService`, and `CredentialsService`
  to manage user account operations.
- Statistical data for the user's account is retrieved using the `StatsController`.

## `StatsController`

The `StatsController` manages operations related to the display of statistics, including data for charts, maps, sales
and orders calendars, and tabular data.

## Endpoints

### GET /marketplace/account/api/stats/chartData

- **Description**: Provides data for a chart, counting the sales made by the user in the current week.
- **Response**: A list of object arrays containing the data for the chart.

### GET /marketplace/account/api/stats/mapData

- **Description**: Provides data for a map visualization, counting users by nation based on filters such as online
  status, role, and registration date range.
- **Parameters**:
    - `isOnline`: User's online status (optional).
    - `role`: User's role (optional).
    - `registeredFrom`: Start date for the registration filter (optional).
    - `registeredTo`: End date for the registration filter (optional).
- **Response**: A list of object arrays containing the data for the map.

### GET /marketplace/account/api/stats/calendarData/sales

- **Description**: Provides sales data for a calendar view, listing all sales made by the logged-in user.
- **Response**: An iterable of `Sale` containing the sales data for the calendar.

### GET /marketplace/account/api/stats/calendarData/orders

- **Description**: Provides order data for a calendar view, showing all orders placed by the logged-in user.
- **Response**: A list of object arrays containing the data for the orders calendar.

### GET /marketplace/account/api/stats/tableData

- **Description**: Provides data for a tabular view, showing statistics on sales published by users.
- **Response**: A list of object arrays containing the data for the table.

## Notes

- The controller uses services such as `UserService`, `SaleService`, and `OrderService` to retrieve the necessary data
  for generating statistics.
- Responses may include JSON objects for asynchronous operations.
- Error handling is implemented for operations that require specific conditions (e.g., user authorization, valid data).

## Technologies and languages used 🧑‍💻

|     *Name*     |   *Version*    |
|:--------------:|:--------------:|
|      Java      |       17       |
|  Spring boot   |     3.2.5      |  
|     Maven      |     3.9.6      |
|   Hibernate    |     4.3.11     |
|     Junit      |       4        |
|   PostgreSQL   |      16.0      |
|   thymeleaf    |     3.0.14     |
| OAuth (Google) |      2.0       |
|    Firebase    |    v13.8.0     |
|      XML       |      1.1       |
|   Bootstrap    |     5.3.3      |
|  FontAwesome   |     5.15.4     |
|    Leaflet     |     1.9.4      |
|      HTML      |       5        |
|      CSS       |      4.15      |
|   Javascript   |      ES6       |
|     Axios      |     1.6.8      |
|    ChartJS     |     4.4.2      |
|  FullCalendar  |     6.1.11     |
|     JSPDF      |     2.3.1      |
|    Markdown    |      3.6       |
|    Windows     |       11       |
|      GIT       |     2.43.0     |
|     GITHUB     |     3.12.3     |
| IntelliJ IDEA  |     2024.1     |
|     Chrome     | 124.0.6367.201 |
| Microsoft EDGE | 123.0.2420.65  |
|     Opera      | 111.0.5168.15  |
|    Draw.io     |     24.4.6     |

## Project structure 🏠

- **`src/`**: This directory contains two subdirectories: main/ and test/.

    - **`src/main/`**: Contains all reusable components of the application.

    - **`src/main/java/`**: This directory contains the main source code and resources for your application, which are
      used in production.
      packages corresponding to your application's domain or feature areas.

    - **`src/main/resources/`**: This directory contains non-Java resources used by your application, such as properties
      files, XML configuration files, static assets, etc.

    - **`src/test/java`**:: Similar to src/main/java, this directory contains Java source code files specifically for
      testing purposes. It follows the same package structure as the main source code.
    - **`src/main/resources/application.properties`**:: Configuration files for your Spring Boot application. They
      contain properties to configure various aspects of your application, such as database connection settings, server
      port, logging configuration, etc.
    - **`src/main/java/com/market/marketnexus/MarketNexusApplication.java`**:: The main entry point of your Spring
      Boot application. This Java file typically contains the main method to start the Spring application context.
    - **`src/main/java/com/market/marketnexus/authentication`**: A directory (package) where there is the site auth
      configuration.
    - **`src/main/java/com/market/marketnexus/config`**: A directory (package) where there are Configuration Classes.
    - **`src/main/java/com/market/marketnexus/controller`**: A directory (package) where there are Site Controllers
      classes.
    - **`src/main/java/com/market/marketnexus/exception`**: A directory (package) where there are project custom
      Exceptions classes.
    - **`src/main/java/com/market/marketnexus/handler`**: A directory (package) where there are Event-Handler classes.
    - **`src/main/java/com/market/marketnexus/helpers`**: A directory (package) where there are project useful helpers
      with many static methods.
    - **`src/main/java/com/market/marketnexus/model`**: A directory (package) where there are project Entity Models
      classes.
    - **`src/main/java/com/market/marketnexus/repository`**: A directory (package) where there are project
      Repositories interface.
    - **`src/main/java/com/market/marketnexus/service`**:  A directory (package) where there are project Services
      classes.

- **`target/`**: This directory is a standard directory created by build tools like Maven or Gradle during the build
  process. It's not typically part of your source code repository and is generated dynamically. It contains the project
  JAR and HTML documentation.

- **`MarketNexus.sql`**: A SQL (PostGreSQL) script file that allows to create the database that I used for this App.

- **`pom.xml`**: This file is specific to Maven-based projects. It stands for "Project Object Model" and is used by
  Maven to manage the project's build configuration, dependencies, plugins, and other settings. The pom.xml file is
  written in XML format and contains information such as project metadata, dependencies on external libraries, build
  instructions, and profiles for different environments. It's the central configuration file for Maven projects and is
  crucial for building, testing, and deploying the application.

- **`README.md`**: Markdown documentation for this project.

## Author ©️

Made with ❤️ and a lot of hard work 🏋️‍♂️ by:

- **Matteo Lambertucci (matricola 578219, Roma TRE)**

    - [GitHub Profile (MattDEV02)](https://github.com/MattDEV02)

    - [Linkedin Profile](https://www.linkedin.com/in/matteo-lambertucci-134073211)

    - [Instagram Profile (_matte.02_)](https://www.instagram.com/_matte.02_/)

    - [Moodle Profile](https://ingegneriacivileinformaticatecnologieaeronautiche.el.uniroma3.it/user/profile.php?id=5522)

    - [mat.lambertucci@stud.uniroma3.it](mat.lambertucci@stud.uniroma3.it)

    - [matteolambertucci3@gmail.com](matteolambertucci3@gmail.com)

    - [matteo.lambertucci@it.ey.com](matteo.lambertucci@it.ey.com)

I am the only author of this beautiful site 😉

## Sources of inspiration 🤝

- [Vinted](https://www.vinted.it/)

- [Decathlon](https://www.decathlon.it/)

## License 🗒️

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for more details.

#### P.S. = Go to Releases section and download the JAR (latest release).