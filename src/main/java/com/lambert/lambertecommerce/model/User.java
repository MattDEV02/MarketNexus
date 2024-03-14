package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.lambert.lambertecommerce.helpers.constants.FieldSizes.*;

@Entity
@Table(name = "Users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @NotBlank
   @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
   @Column(name = "name")
   private String name;

   @NotBlank
   @Size(min = SURNAME_MIN_LENGTH, max = SURNAME_MAX_LENGTH)
   @Column(name = "surname")
   private String surname;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @Past(message = "La data deve essere nel passato")
   @Column(name = "birthdate", nullable = true)

   private Date birthDate;

   @NotBlank
   @Email
   @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH)
   @Column(name = "email", unique = true)
   private String email;

   @NotNull
   @Max(10000)
   @Min(0)
   @Column(name = "balance")
   private Float balance;

   @ManyToOne
   @JoinColumn(name = "nation", nullable = false)
   private Nation nation;

   @Column(name = "inserted_at")
   // @Past(message = "La data deve essere nel passato")
   private LocalDateTime insertedAt;

   @Column(name = "updated_at")
   //@Past(message = "La data deve essere nel passato")
   private LocalDateTime updatedAt;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public Date getBirthDate() {
      return this.birthDate;
   }

   public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
   }

   public Float getBalance() {
      return this.balance;
   }

   public void setBalance(Float balance) {
      this.balance = balance;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Nation getNation() {
      return this.nation;
   }

   public void setNation(Nation nation) {
      this.nation = nation;
   }

   public LocalDateTime getInsertedAt() {
      return this.insertedAt;
   }

   public void setInsertedAt(LocalDateTime insertedAt) {
      this.insertedAt = insertedAt;
   }

   public LocalDateTime getUpdatedAt() {
      return this.updatedAt;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
   }

   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
      if (this.updatedAt == null) {
         this.updatedAt = this.insertedAt;
      }
   }

   @PreUpdate
   public void preUpdate() {
      this.updatedAt = LocalDateTime.now();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(this.getId(), user.getId()) || Objects.equals(this.getEmail(), user.getEmail());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId());
   }


   @Override
   public String toString() {
      return "User = {" +
              "id = " + id +
              ", name = '" + name + '\'' +
              ", surname = '" + surname + '\'' +
              ", birthDate = " + birthDate +
              ", email = '" + email + '\'' +
              ", balance = " + balance +
              ", nation = " + nation +
              ", insertedAt = " + insertedAt +
              ", updatedAt = " + updatedAt +
              '}';
   }

}
