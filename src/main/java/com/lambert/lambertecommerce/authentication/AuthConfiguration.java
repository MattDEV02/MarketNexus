package com.lambert.lambertecommerce.authentication;

import org.springframework.beans.factory.annotation.Autowired;
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

   @Autowired
   private DataSource dataSource;

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth)
           throws Exception {
      auth.jdbcAuthentication()
              .dataSource(dataSource)
              .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
              .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
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
      httpSecurity
              .authorizeHttpRequests(
                      auth -> auth.requestMatchers("/", "/registration", "/login", "/registerNewUser", "/afterLogin").permitAll()
                              .requestMatchers("/admin/**").hasAuthority("ADMIN")
                              .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")
                      .usernameParameter("username")
                      .passwordParameter("password")
                      .defaultSuccessUrl("/admin", true)
                      .permitAll()
              )

              .logout(logout -> logout.logoutUrl("/logout").permitAll());
      return httpSecurity.build();
   }

}