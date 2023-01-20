package com.mpxds.mpbasic.util.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
//
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MpSendMail {
	//
	private String from;
	private String to;
	private String cc = "";
	private String subject;
	private String msg;
	//
    private Properties mailServerProperties;
    private Session getMailSession;
    private MimeMessage generateMailMessage;

	public MpSendMail(String from, String to, String subject, String msg) {
		this.from = from;
		this.to = to;
		// Trata cc (com c�pia) !
		if (this.to.indexOf(",") > 0) {
			this.cc = this.to.substring(this.to.indexOf(",")+1).trim();
			this.to = this.to.substring(0, this.to.indexOf(","));
		}
		//
		this.subject = subject;
		this.msg = msg;
	}

	public void send() {
		//
		SimpleEmail simpleEmail = new SimpleEmail();
		//
		try {
			//
			simpleEmail.setHostName("in-v3.mailjet.com"); // "smtp.gmail.com");
			// simpleEmail.setSslSmtpPort("465");
			// simpleEmail.setStartTLSRequired(true);
			// simpleEmail.setSSLOnConnect(true);
			simpleEmail.setSmtpPort(587); // 465!
			simpleEmail.setAuthenticator(new DefaultAuthenticator(
					"6d1e6ffc942ba4bd556d8e85ca28318d",
					"c06bade17126f12a49456be6b5844ec3"));
			// simpleEmail.setAuthenticator(new
			// DefaultAuthenticator("marcusvinciuspinheirorodrigues@gmail.com",
			// "brunnaxx"));
			//
			simpleEmail.setFrom(this.from);
			// simpleEmail.setDebug(true);
			simpleEmail.setSubject(this.subject);
			simpleEmail.setMsg(this.msg);
			simpleEmail.addTo(this.to);
			// Trata cc (com c�pia) !
			if (!this.cc.isEmpty())
				simpleEmail.addCc(this.cc);
			//
			simpleEmail.send();
			//
		} catch (EmailException e) {
			System.out.println("SendEmail Error:" + e.getMessage());
		}
		//
	}

	public void sendGmail() {
		//
		// Cuidado com o FIREWALL !
		//
		try {
			// Step1
			System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			// ----
			System.out
					.println("Mail Server Properties have been setup successfully.. ( "
							+ this.to + " / " + this.from);
			// Step2
			System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO,
																new InternetAddress(this.to));
			// Trata cc (com c�pia) !
			if (!this.cc.isEmpty())
				generateMailMessage.addRecipients(Message.RecipientType.CC, 
                    											InternetAddress.parse(this.cc));
			// generateMailMessage.addRecipient(Message.RecipientType.CC, new
			// InternetAddress("test2@crunchify.com"));
			generateMailMessage.setSubject(this.subject);
			generateMailMessage.setContent(this.msg, "text/html");
			// ----
			System.out.println("Mail Session has been created successfully..");

			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");

			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "mpxdsg@gmail.com", "brunnaxx");
			transport.sendMessage(generateMailMessage,
					generateMailMessage.getAllRecipients());
			// ----
			transport.close();
			//
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}