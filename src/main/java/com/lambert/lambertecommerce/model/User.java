package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import com.lambert.lambertecommerce.helpers.constants.Global;
import com.lambert.lambertecommerce.helpers.constants.TemporalFormats;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "Users", schema = Global.SQL_SCHEMA_NAME)
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Unsigned
   @Min(1)
   private Long id;

   @NotBlank
   @Size(min = FieldSizes.NAME_MIN_LENGTH, max = FieldSizes.NAME_MAX_LENGTH)
   @Column(name = "name", nullable = false)
   private String name;

   @NotBlank
   @Size(min = FieldSizes.SURNAME_MIN_LENGTH, max = FieldSizes.SURNAME_MAX_LENGTH)
   @Column(name = "surname", nullable = false)
   private String surname;

   @DateTimeFormat(pattern = TemporalFormats.DATE_FORMAT)
   @Past(message = "The birth date must be in the past.")
   @Column(name = "birthdate", nullable = true)
   @Temporal(TemporalType.DATE)
   private Date birthDate;

   @NotBlank
   @Email(message = "Invalid email format.")
   @Size(min = FieldSizes.EMAIL_MIN_LENGTH, max = FieldSizes.EMAIL_MAX_LENGTH)
   @Column(name = "email", unique = true, nullable = false)
   private String email;
   @Min(FieldSizes.BALANCE_MIN_VALUE)
   @Max(FieldSizes.BALANCE_MAX_VALUE)
   @Column(name = "balance", nullable = false)
   private Float balance;

   @ManyToOne
   @JoinColumn(name = "nation", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "users_nations_fk"))
   private Nation nation;

   @OneToOne
   @JoinColumn(name = "credentials", referencedColumnName = "id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "users_credentials_fk"))
   private Credentials credentials;

   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = TemporalFormats.DATE_TIME_FORMAT)
   private LocalDateTime insertedAt;

   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = TemporalFormats.DATE_TIME_FORMAT)
   private LocalDateTime updatedAt;

   public User() {

   }

   public User(String name, String surname, String email, Float balance, Credentials credentials, Nation nation) {
      this.name = name;
      this.surname = surname;
      this.email = email;
      this.balance = balance;
      this.credentials = credentials;
      this.nation = nation;
   }

   public User(String name, String surname, String email, Float balance, Credentials credentials, Nation nation, Date birthDate) {
      this.name = name;
      this.surname = surname;
      this.birthDate = birthDate;
      this.email = email;
      this.balance = balance;
      this.credentials = credentials;
      this.nation = nation;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSurname() {
      return this.surname;
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

   public Credentials getCredentials() {
      return this.credentials;
   }

   public void setCredentials(Credentials credentials) {
      this.credentials = credentials;
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
   public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) return false;
      User user = (User) object;
      return Objects.equals(this.getId(), user.getId()) || Objects.equals(this.getEmail(), user.getEmail()) || Objects.equals(this.getCredentials(), user.getCredentials());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getEmail(), this.getCredentials());
   }


   @Override
   public String toString() {
      return "User: {" +
              "id = " + this.getId().toString() +
              ", name = '" + this.getName() + '\'' +
              ", surname = '" + this.getSurname() + '\'' +
              ", birthDate = " + this.getBirthDate().toString() +
              ", email = '" + this.getEmail() + '\'' +
              ", balance = " + this.getBalance().toString() +
              ", credentials = " + this.getCredentials().toString() +
              ", nation = " + this.getNation().toString() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              ", updatedAt = " + this.getUpdatedAt().toString() +
              " }";
   }
}
