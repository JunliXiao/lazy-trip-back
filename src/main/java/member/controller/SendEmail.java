package member.controller;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

	public static void sendEmail(String toAddress, String subject, String message) throws MessagingException {

	    // Set mail server properties
	    Properties properties = System.getProperties();
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.auth", "true");

	    // Create a mail session with the specified properties
	    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	      protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication("g23821208@gmail.com", "pdqjpkqpmbndtmhy");
	      }
	    });

	    // Create a new MimeMessage object
	    MimeMessage messageObj = new MimeMessage(session);

	    // Set the recipient address
	    messageObj.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

	    // Set the subject and message body
	    messageObj.setSubject(subject);
	    messageObj.setText(message);

	    // Send the message
	    Transport.send(messageObj);

	    System.out.println("Email sent successfully!");
	  }
	}




