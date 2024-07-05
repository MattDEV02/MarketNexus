package com.market.marketnexus.service.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.market.marketnexus.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PublishedSaleNotificationService {

   @Autowired
   private FirebaseMessaging firebaseMessaging;

   public void sendNotificationToAllUsers(Sale sale) {
      try {
         List<String> tokens = Files.readAllLines(Paths.get("src/main/resources/static/txt/tokens.txt"));
         for (String token : tokens) {
            String title = "Published new Sale from " + sale.getUser().getCredentials().getUsername() + "!";
            String body = "Check out new Sale: " + sale.getProduct().getName() + " X" + sale.getQuantity() + " from " + sale.getUser().getCredentials().getUsername() + "!";
            Message message = Message.builder()
                    .setToken(token.trim())
                    .setNotification(Notification.builder()
                            .setImage("/static/images/logo/logo_512.png") // prova 1024
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();
            String response = this.firebaseMessaging.send(message);
            System.out.println("Successfully sent message: " + response);
         }
      } catch (IOException | FirebaseMessagingException e) {
         e.printStackTrace();
      }
   }
}
