package com.market.marketnexus.service.email;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ForgotUsernameEmailService {
   private OkHttpClient client;

   public ForgotUsernameEmailService() {
      this.client = new OkHttpClient().newBuilder().build();
   }

   public void sendEmail(String from, String to, String subject, String text, String category) throws IOException {
      MediaType mediaType = MediaType.parse("application/json");
      RequestBody body = RequestBody.create(mediaType,
              "{\"from\":{\"email\":\"" + from + "\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"" + to + "\"}],\"subject\":\"" + subject + "\",\"text\":\"" + text + "\",\"category\":\"" + category + "\"}");
      Request request = new Request.Builder()
              .url("https://send.api.mailtrap.io/api/send")
              .method("POST", body)
              .addHeader("Authorization", "Bearer ********67a4")
              .addHeader("Content-Type", "application/json")
              .build();
      Response response = client.newCall(request).execute();
   }
}
