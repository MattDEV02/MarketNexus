package com.lambert.lambertecommerce.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
public class AuthConfiguration {

   private DataSource dataSource;

   public void configureGlobal(AuthenticationManagerBuilder auth)
           throws Exception {
      auth.jdbcAuthentication()
              .dataSource(dataSource)
              .authoritiesByUsernameQuery("SELECT username, role FROM Credentials WHERE username = ?")
              .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM Credentials WHERE username = ?");
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
      httpSecurity.authorizeHttpRequests(
                      auth -> auth.requestMatchers("/login", "/registration").permitAll()
                              .requestMatchers("/users/**", "/apps/**").hasAuthority("ADMIN")
                              .requestMatchers("/myapps/**").hasAuthority("CLIENT")
                              .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")
                      .usernameParameter("username")
                      .defaultSuccessUrl("/", true)
                      .permitAll()
              )
              // .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."))
              .logout(logout -> logout.logoutUrl("/signout").permitAll());

      return httpSecurity.build();
   }
}