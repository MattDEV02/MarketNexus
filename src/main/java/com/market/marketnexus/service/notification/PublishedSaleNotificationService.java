package com.market.marketnexus.service.notification;

import com.google.firebase.messaging.*;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublishedSaleNotificationService {

   @Autowired
   private FirebaseMessaging firebaseMessaging;

   public void sendNotificationToAllUsers(Sale sale) {
      try {
         Path path = Paths.get("src/main/resources/static/txt/tokens.txt");
         List<String> tokens = Files.readAllLines(path);
         List<String> validTokens = new ArrayList<>();
         for (String token : tokens) {
            try {
               String title = "Published new Sale from " + sale.getUser().getCredentials().getUsername() + "!";
               String body = "Check out new Sale: " + sale.getProduct().getName() + " X" + sale.getQuantity() + " from " + sale.getUser().getCredentials().getUsername() + "!";
               token = token.trim();
               String salePageUrl = GlobalValues.APP_URL + "/" + APIPaths.SALES + "/sale/" + sale.getId().toString();
               Message message = Message.builder()
                       .setToken(token.trim())
                       .setNotification(Notification.builder()
                               .setImage("/static/images/logo/logo_192.png")
                               .setTitle(title)
                               .setBody(body)
                               .build())
                       .putData("url", salePageUrl)
                       .setWebpushConfig(WebpushConfig.builder()
                               .putHeader("TTL", "7200")
                               .build())
                       .build();
               String response = this.firebaseMessaging.send(message);
               System.out.println("Successfully sent message: " + response);
               validTokens.add(token);
            } catch (FirebaseMessagingException firebaseMessagingException) {
               System.err.println("Failed to send message to token: " + token + " - " + firebaseMessagingException.getMessage());
            }
         }
         // Sovrascrivi il file dei token con i token validi
         Files.write(path, validTokens);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

}
