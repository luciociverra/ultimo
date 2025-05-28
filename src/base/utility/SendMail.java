package base.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public static void main(String[] args) {
		String to = "lciverra@gmail.com";
		String from = "customercare@teorematoys.com";
		 
		 
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "88.147.95.73");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.ssl.trust", "88.147.95.73");

		// Get the Session object
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("customercare@teorematoys.com", "Teorema131008");
			}
		});

		try {
			// Create a default MimeMessage object
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject
			message.setSubject("Hi JAXenter");

			// Put the content of your message
			message.setText("Hi there,we are just experimenting with JavaMail here");

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}