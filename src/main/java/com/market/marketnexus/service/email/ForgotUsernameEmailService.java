package com.market.marketnexus.service.email;

import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.service.UserService;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ForgotUsernameEmailService {
   private final UserService userService;
   private OkHttpClient client;

   public ForgotUsernameEmailService(UserService userService) {
      this.client = new OkHttpClient().newBuilder().build();
      this.userService = userService;
   }

   public void sendEmail(String email, String username) throws IOException {
      String from = "mailtrap@mailtrap.club";
      String to = email;
      String subject = GlobalValues.APP_NAME + " username forgotten";
      String category = "Integration Test";
      String text = "Your " + GlobalValues.APP_NAME + " is " + username;
      MediaType mediaType = MediaType.parse("application/json");
      String content = "{\"from\":{\"email\":\"" + from + "\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"" + to + "\"}],\"subject\":\"" + subject + "\",\"text\":\"" + text + "\",\"category\":\"" + category + "\"}";
      RequestBody body = RequestBody.create(content, mediaType);
      Request request = new Request.Builder()
              .url("https://send.api.mailtrap.io/api/send")
              .method("POST", body)
              .addHeader("Authorization", "Bearer ********67a4")
              .addHeader("Content-Type", "application/json")
              .build();
      Response response = client.newCall(request).execute();
      System.out.println(response.message());
      response.close();
      System.out.println(response.message());
   }
}
