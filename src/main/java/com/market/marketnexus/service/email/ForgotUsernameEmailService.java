package com.market.marketnexus.service.email;

import com.market.marketnexus.helpers.constants.GlobalValues;
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
public class ForgotUsernameEmailService {

   private final static String FROM = "mat.lambertucci@stud.uniroma3.it";
   private final static String SUBJECT = GlobalValues.APP_NAME + " username forgotten";
   @Autowired
   private JavaMailSender emailSender;

   @Contract(pure = true)
   private static @NotNull String getHTMLText(String username) {
      return """
              <!DOCTYPE html>
              <html>
              <head>
                <meta charset="utf-8">
                <meta http-equiv="x-ua-compatible" content="ie=edge">
                <title>MarketNexus username forgotten</title>
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <style type="text/css">
                /**
                 * Google webfonts. Recommended to include the .woff version for cross-client compatibility.
                 */
                @media screen {
                  @font-face {
                    font-family: 'Source Sans Pro';
                    font-style: normal;
                    font-weight: 400;
                    src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');
                  }
                  @font-face {
                    font-family: 'Source Sans Pro';
                    font-style: normal;
                    font-weight: 700;
                    src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');
                  }
                }
                /**
                 * Avoid browser level font resizing.
                 * 1. Windows Mobile
                 * 2. iOS / OSX
                 */
                body,
                table,
                td,
                a {
                  -ms-text-size-adjust: 100%; /* 1 */
                  -webkit-text-size-adjust: 100%; /* 2 */
                }
                /**
                 * Remove extra space added to tables and cells in Outlook.
                 */
                table,
                td {
                  mso-table-rspace: 0pt;
                  mso-table-lspace: 0pt;
                }
                /**
                 * Better fluid images in Internet Explorer.
                 */
                img {
                  -ms-interpolation-mode: bicubic;
                }
                /**
                 * Remove blue links for iOS devices.
                 */
                a[x-apple-data-detectors] {
                  font-family: inherit !important;
                  font-size: inherit !important;
                  font-weight: inherit !important;
                  line-height: inherit !important;
                  color: inherit !important;
                  text-decoration: none !important;
                }
                /**
                 * Fix centering issues in Android 4.4.
                 */
                div[style*="margin: 16px 0;"] {
                  margin: 0 !important;
                }
                body {
                  width: 100% !important;
                  height: 100% !important;
                  padding: 0 !important;
                  margin: 0 !important;
                }
                /**
                 * Collapse table borders to avoid space between cells.
                 */
                table {
                  border-collapse: collapse !important;
                }
                a {
                  color: #1A82E2;
                }
                img {
                  height: auto;
                  line-height: 100%;
                  text-decoration: none;
                  border: 0;
                  outline: none;
                }
                                  
                .confirm-button:hover {
                	opacity: 0.8;
                }
               
                </style>
                    
              </head>
              <body style="background-color: #e9ecef;">
                    
                <!-- start preheader -->
                <div class="preheader" style="display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;">
                </div>
                <!-- end preheader -->
                    
                <!-- start body -->
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    
                  <!-- start logo -->
                  <tr>
                    <td align="center" bgcolor="#e9ecef">
                      <!--[if (gte mso 9)|(IE)]>
                      <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                      <tr>
                      <td align="center" valign="top" width="600">
                      <![endif]-->
                      <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                        <tr>
                          <td align="center" valign="top" style="padding: 36px 24px;">
                            <a href="https://matteolambertucci.altervista.org/marketnexus/logo/logo.png" target="_blank" style="display: inline-block;">
                              <img src="https://matteolambertucci.altervista.org/marketnexus/logo/logo.png" alt="Logo" border="0" width="120" style="display: block; width: 120px; max-width: 120px; min-width: 120px;">
                            </a>
                          </td>
                        </tr>
                      </table>
                      <!--[if (gte mso 9)|(IE)]>
                      </td>
                      </tr>
                      </table>
                      <![endif]-->
                    </td>
                  </tr>
                  <!-- end logo -->
                    
                  <!-- start hero -->
                  <tr>
                    <td align="center" bgcolor="#e9ecef">
                      <!--[if (gte mso 9)|(IE)]>
                      <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                      <tr>
                      <td align="center" valign="top" width="600">
                      <![endif]-->
                      <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                        <tr>
                          <td align="left" bgcolor="#ffffff" style="padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;">
                            <h1 style="margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px; text-align: center">MarketNexus username forgotten</h1>
                          </td>
                        </tr>
                      </table>
                      <!--[if (gte mso 9)|(IE)]>
                      </td>
                      </tr>
                      </table>
                      <![endif]-->
                    </td>
                  </tr>
                  <!-- end hero -->
                    
                  <!-- start copy block -->
                  <tr>
                    <td align="center" bgcolor="#e9ecef">
                      <!--[if (gte mso 9)|(IE)]>
                      <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                      <tr>
                      <td align="center" valign="top" width="600">
                      <![endif]-->
                      <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                    
                        <!-- start copy -->
                        <tr>
                          <td align="left" bgcolor="#ffffff" style="padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;">
                            <p style="margin: 0;">Your <a target="_blank" href="http://localhost:8080/">MarketNexus</a> username is""" + " " + username + """
              .</p>
                                                </td>
                                              </tr>
                                              <!-- end copy -->
                                              <!-- start button -->
                                              <tr>
                                                <td align="left" bgcolor="#ffffff">
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tr>
                                                      <td align="center" bgcolor="#ffffff" style="padding: 12px;">
                                                        <table border="0" cellpadding="0" cellspacing="0">
                                                          <tr>
                                                            <td align="center" bgcolor="#1a82e2" class="confirm-button" style="border-radius: 7px;">
                                                              <a target="_blank" href="http://localhost:8080/login" target="_blank"  style="display: inline-block; padding: 15px 28px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 19px; color: #ffffff; text-decoration: none; border-radius: 6px; font-weight: bold;">Go to MarketNexus</a>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <!-- end button -->
                                          
                                              <!-- start copy -->
                                              <tr>
                                                <td align="left" bgcolor="#ffffff" style="padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf">
                                                  <p style="margin: 0;">Cheers,<br> MarketNexus.</p>
                                                </td>
                                              </tr>
                                              <!-- end copy -->
                                          
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]>
                                            </td>
                                            </tr>
                                            </table>
                                            <![endif]-->
                                          </td>
                                        </tr>
                                        <!-- end copy block -->
                                          
                                        <!-- start footer -->
                                        <tr>
                                          <td align="center" bgcolor="#e9ecef" style="padding: 24px;">
                                            <!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                                            <tr>
                                            <td align="center" valign="top" width="600">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                                          
                                              <!-- start permission -->
                                              <tr>
                                                <td align="center" bgcolor="#e9ecef" style="padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;">
                                                  <p style="margin: 0;">You received this email because we received a request for username forgotten for your account. If you didn't request email confirmation you can safely delete this email.</p>
                                                </td>
                                              </tr>
                                              <!-- end permission -->
                                          
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]>
                                            </td>
                                            </tr>
                                            </table>
                                            <![endif]-->
                                          </td>
                                        </tr>
                                        <!-- end footer -->
                                          
                                      </table>
                                      <!-- end body -->
                                          
                                    </body>
                                    </html>
                                          
                                    <!--
                                    <h2>Confirm your signup</h2>
                                          
                                    <p>Follow this link to confirm your user:</p>
                                    <p><a href="{{ .ConfirmationURL }}">Confirm your mail</a></p>
                                          
                                    -->
                                    <!--
                                    <h2>Confirm your signup</h2>
                                          
                                    <p>Follow this link to confirm your user:</p>
                                    <p><a href="{{ .ConfirmationURL }}">Confirm your mail</a></p>
                                          
                                    -->
                            """;
   }

   public void sendEmail(String email, String username) throws IOException, MessagingException {
      String to = email;
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, GlobalValues.CHARSET);
      helper.setFrom(ForgotUsernameEmailService.FROM);
      helper.setTo(to);
      helper.setSubject(ForgotUsernameEmailService.SUBJECT);
      helper.setReplyTo(ForgotUsernameEmailService.FROM);
      helper.setText(ForgotUsernameEmailService.getHTMLText(username), true);
      emailSender.send(message);
   }
}
