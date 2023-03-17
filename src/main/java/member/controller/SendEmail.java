package member.controller;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

	public void sendEmail(String toAddress, String subject, String message) throws MessagingException {

		try {
			// Set mail server properties
		    Properties properties = System.getProperties();
		    properties.put("mail.smtp.host", "smtp.gmail.com");
		    properties.put("mail.smtp.socketFactory.port", "465");
		    properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		    properties.put("mail.smtp.auth", "true");
		    properties.put("mail.smtp.port", "465");

		    // Create a mail session with the specified properties
		    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("fortibamejava@gmail.com", "tpswmfhiojvlwbcp");
		      }
		    });

		    // Create a new MimeMessage object
		    MimeMessage messageObj = new MimeMessage(session);
		    messageObj.setFrom(new InternetAddress("fortibamejava@gmail.com"));
		    messageObj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));

		    // Set the recipient address
//		    messageObj.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

		    // Set the subject and message body
		    messageObj.setSubject(subject);
		    messageObj.setText(message);

		    // Send the message
		    Transport.send(messageObj);

		    System.out.println("Email sent successfully!");
		}catch (MessagingException e) {
			// TODO: handle exception
			System.out.println("Failed to send email. Error message: " + e.getMessage());
		}
	    
	  }
	}




