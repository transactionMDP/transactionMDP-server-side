package com.bcp.mdp.service;


import com.bcp.mdp.dto.MailMessageDto;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


@Component
public class MailNotificationService {
	
    public static void sendSimpleMessage(MailMessageDto mailMessage) throws MessagingException, IOException  {

        // Put sender’s address
        String from = "from@example.com";
        final String username = "42e17ae48c4dbf";//username generated by Mailtrap
        final String password = "5569d9a5e82bfc";//password generated by Mailtrap

        // Paste host address from the SMTP settings tab in your Mailtrap Inbox
        String host = "smtp.mailtrap.io";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//it’s optional in Mailtrap
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");// use one of the options in the SMTP settings tab in your Mailtrap Inbox

        // Get the Session object.
        Session session = Session.getInstance(props,
           new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
      }
           });

        // Create a default MimeMessage object.
		  Message message = new MimeMessage(session);
   
		  // Set From: header field 
		  message.setFrom(new InternetAddress(from));
   
		  // firsty message to debit account
		  // Set To: header field
		  message.setRecipients(Message.RecipientType.TO,
		             InternetAddress.parse(mailMessage.getToDebitAccount()));
   
		  // Set Subject: header field
		  message.setSubject("Ordre de virement");
   
		  // Put the content of your message
		 
		  Multipart emailPart= new MimeMultipart() ;
		  // text body part
		  MimeBodyPart textBodyPart = new MimeBodyPart();
		  textBodyPart.setText("le virement d'un montant de "
		  						+ mailMessage.getAmountOfTransaction()
		  						+ " en faveur de "
		  						+ mailMessage.getNameOfCreditor()
		  						+ " a été bien exécuté"
		  						, "UTF-8");
		  
		  // add image on the email body
		  MimeBodyPart imgBodyPart = new MimeBodyPart();
		  //imgBodyPart.set
		  // attachment body part
		  MimeBodyPart attachBodyPart = new MimeBodyPart();
		  attachBodyPart.attachFile(new File(ClassLoader.getSystemResource("images/logoBP.png").getFile()));
		  // add part to body part
		  
		  emailPart.addBodyPart(textBodyPart);
		  emailPart.addBodyPart(attachBodyPart);
		  
		  // content of message
		  message.setContent(emailPart);
		  // Send message
		  Transport.send(message);
		  
		  // secondly the message to credit account
		  // we have to change the Recipient of message to email of credit account email
		  message.setRecipients(Message.RecipientType.TO,
		             InternetAddress.parse(mailMessage.getToCreditAccount()));
		  // we have to change a subject of message
		  message.setSubject("Virement en votre faveur");
		 
		  // We have to change text body part
		  textBodyPart.setText("Virement réçu d'un montant de "
					+ mailMessage.getAmountOfTransaction()
					+ " de la part de  "
					+ mailMessage.getNameOfDebitor()
					, "UTF-8");
		  
		 // content of message
		  message.setContent(emailPart);
		 // Send message
		  Transport.send(message);
		  

     }
     

}
