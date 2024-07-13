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