package com.chasemaster.ws.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;

public class EmailSender {
   public void send(String emailTo, String text) {

      // Recipient's email ID needs to be mentioned.
//      String to = "abcd@gmail.com";

      // Sender's email ID needs to be mentioned
      String emailFrom = "delazic@gmail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);
      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(emailFrom));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));

         // Set Subject: header field
         message.setSubject("Chasemaster confirmation link");

         // Now set the actual message
         message.setText("Click on the following link: " + text);

         // Send message
         Transport.send(message);
         System.out.println("E-mail sent successfully.");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}