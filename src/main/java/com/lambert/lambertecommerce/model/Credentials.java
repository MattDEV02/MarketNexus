package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "Credentials")
public class Credentials {

   public static final String DEFAULT_ROLE = "DEFAULT";
   public static final String ADMIN_ROLE = "ADMIN";

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;

   @NotBlank
   @Column(name = "username", unique = true)
   private String username;

   @NotBlank
   @Column(name = "password")
   private String password;

   @NotBlank
   @Column(name = "role")
   private String role;

   @OneToOne
   @JoinColumn(name = "_user", nullable = false, unique = true)
   @Column(name = "_user")
   private User user;

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }
}
