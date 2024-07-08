package com.market.marketnexus.service.email;

import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderedUserSaleEmailService {
   private final static String FROM = GlobalValues.EMAIL_FROM;
   private final static String SUBJECT = "You have sold your " + GlobalValues.APP_NAME + " Sale";
   @Autowired
   private JavaMailSender javaMailSender;

   @Contract(pure = true)
   private static @NotNull String getHTMLText(@NotNull User userBuyer, @NotNull Sale sale) {
      String htmlTemplate = "%s has bought your %s.";
      return String.format(htmlTemplate, userBuyer.getCredentials().getUsername(), sale.getProduct().getName() + "X" + sale.getQuantity().toString());
   }

   public void sendEmail(@NotNull User userBuyer, @NotNull Sale sale) throws IOException, MessagingException {
      String emailTo = sale.getUser().getEmail();
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, GlobalValues.CHARSET);
      mimeMessageHelper.setFrom(OrderedUserSaleEmailService.FROM);
      mimeMessageHelper.setTo(emailTo);
      mimeMessageHelper.setSubject(OrderedUserSaleEmailService.SUBJECT);
      mimeMessageHelper.setReplyTo(OrderedUserSaleEmailService.FROM);
      mimeMessageHelper.setText(OrderedUserSaleEmailService.getHTMLText(userBuyer, sale), true);
      javaMailSender.send(mimeMessage);
   }
}
