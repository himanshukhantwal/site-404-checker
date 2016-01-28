package com.rhythmone.mailer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendSiteStatusMail
{
   private static SendSiteStatusMail sendSiteStatusMail;
   private String[] sendToMailList;
   private String sendFrom;
   private String smtpHost;
   private String emailSubject;
   private String sendFromPassward;
   private String smtpPort;

   public static SendSiteStatusMail getInstance() throws IOException {
      if(sendSiteStatusMail==null){
         sendSiteStatusMail=new SendSiteStatusMail();
         sendSiteStatusMail.init();
         return sendSiteStatusMail; 
      }else {
         return sendSiteStatusMail;
      }
   }

   private void init() throws IOException {
      InputStream mailConfig=new FileInputStream("~/site-404-checker/src/main/resources/mail-config.properties");
      Properties mailProperties=new Properties();
      mailProperties.load(mailConfig);
      sendToMailList= ((String)mailProperties.getProperty("sendMailTo")).split(",");
      sendFrom=mailProperties.getProperty("sendMailFrom");
      sendFromPassward=mailProperties.getProperty("sendFromPassward");
      smtpHost=mailProperties.getProperty("SMTPHost");
      smtpPort=mailProperties.getProperty("SMTPPort");
      emailSubject=mailProperties.getProperty("subjectOfEmail");
   }

   public void send(String htmlString) {
      // Get system properties
      Properties properties = System.getProperties();
      // Setup mail server
      properties.setProperty("mail.smtp.host", smtpHost);
      properties.put("mail.smtp.port", smtpPort);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties,new Authenticator() {

         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sendFrom, sendFromPassward);
         }

      });

      try{
         List<Address> addresses=new ArrayList<>();
         for(String email:sendToMailList){
            addresses.add(new InternetAddress(email));
         }
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(sendFrom));
         message.addRecipients(Message.RecipientType.TO, addresses.toArray(new InternetAddress[addresses.size()]));
         message.setSubject(emailSubject);

         // Send the complete message parts
         message.setContent(htmlString,"text/html");
         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}