package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "Credentials")
public class Credentials {
   public static String[] Roles = {"DEFAULT", "ADMIN"};
   public static final String DEFAULT_ROLE = Roles[0];
   public static final String ADMIN_ROLE = Roles[1];
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @NotBlank
   @Column(name = "username", unique = true)
   private String username;
   @NotBlank
   @Column(name = "password")
   private String password;
   @NotNull
   @Column(name = "role")
   @NotBlank
   private String role;
   @OneToOne
   @JoinColumn(name = "_user", nullable = false, unique = true)
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
      return this.role;
   }

   public void setRole(String role) {
      this.role = role;
   }

}
