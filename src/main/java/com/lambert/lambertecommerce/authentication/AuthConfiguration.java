package com.lambert.lambertecommerce.authentication;

import com.lambert.lambertecommerce.helpers.constants.ControllerSuffixes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
//@EnableWebMvc
public class AuthConfiguration implements WebMvcConfigurer {

   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/"};
   @Autowired
   private DataSource dataSource;

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
              .addResourceLocations(AuthConfiguration.CLASSPATH_RESOURCE_LOCATIONS);
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth)
           throws Exception {
      auth.jdbcAuthentication()
              //use the autowired datasource to access the saved credentials
              .dataSource(this.dataSource)
              //retrieve username and role
              .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
              //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
              .usersByUsernameQuery("SELECT username, password, 1 AS enabled FROM credentials WHERE username=?");
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
              .csrf().disable().cors().disable()
              .authorizeHttpRequests(
                      auth -> auth
                              .requestMatchers(HttpMethod.GET, "/", "/registration", "/login", "/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
                              .requestMatchers(HttpMethod.POST, "/registerNewUser", "/login").permitAll()
                              .requestMatchers(HttpMethod.GET, "/" + ControllerSuffixes.DASHBOARD + "/**").authenticated()
                              .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")
                      .defaultSuccessUrl("/dashboard", true)
                      .failureUrl("/login?invalidCredentials=true")
                      .usernameParameter("username")
                      .passwordParameter("password")
                      .permitAll()
              )
              .logout(logout -> logout
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/")
                      .invalidateHttpSession(true)
                      .clearAuthentication(true)
                      .deleteCookies("JSESSIONID")
                      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .clearAuthentication(true)
                      .permitAll());
      return httpSecurity.build();
   }

}