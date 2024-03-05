package com.lambert.lambertecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@SpringBootApplication
public class LambertEcommerceApplication {
   // CTRL + FN + F9
   public static void main(String[] args) {
      SpringApplication.run(LambertEcommerceApplication.class, args);
   }

}
