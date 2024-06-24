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
