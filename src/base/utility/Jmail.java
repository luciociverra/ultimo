package base.utility;

/**
 * Title:        Standard Internet Sanmarco per applicazioni Java.jsp
 * Description:  invio mail tramite protocollo smtp
 * Copyright:    Copyright (c) 2001
 * Company:      Sanmarco Informatica
 * @author       Roberto Trevisan
 * @version 1.0
 */

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Jmail extends BaseObj {

  private String msg ;
  private String from;
  private String to;
  private String smtp;
  private String subject;
  private String elencoAllegati="";
  private String nomiAllegati="";
  private Vector destinatari;
  private Vector tipoInvio;
  private String mail="";
  public  String pwd="";
  public  String usr="";
  public String bcc="";
  private boolean htmlFormat = false;
  public Jmail(){}
  public Jmail(Sys aSys){
	  this.setSys(aSys);
  }
  public String sendMail(String mailInfo,String dest,String elencoAllegati,String nomiAllegati,String oggetto,String testoMsg) {
  try
  {
	  sys.debug("Jmail mailInfo:"+mailInfo);	 
	  String[] info=mailInfo.split(";");
	  String smtp=info[0];
	  mail=info[1];
	  pwd=info[2];
	  sys.debug("Jmail smtp:"+smtp);	  
	  Properties props = new Properties();  
	  
	   
      if(smtp.equals("smtp.gmail.com"))
      {
	  props.put("mail.smtp.host", "smtp.gmail.com");    
      props.put("mail.smtp.socketFactory.port", "465");    
      props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
      props.put("mail.smtp.auth", "true");    
      props.put("mail.smtp.port", "465"); 
      //get Session   
      Session session = Session.getDefaultInstance(props,    
       new javax.mail.Authenticator() {    
       protected PasswordAuthentication getPasswordAuthentication() {    
       return new PasswordAuthentication(mail,pwd);  
       }    
      }); 
      
      MimeMessage message = new MimeMessage(session);    
      message.addRecipient(Message.RecipientType.TO,new InternetAddress(dest));    
      message.setSubject(oggetto);    
      message.setText("msg");   
      Multipart multipart = new MimeMultipart();

      //Corpo del messaggio
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(testoMsg);
      multipart.addBodyPart(messageBodyPart);
      //Allegati
      StringTokenizer elencoSt = new StringTokenizer(elencoAllegati, ";");
      StringTokenizer nomiStn = new StringTokenizer(nomiAllegati, ";");
      while (elencoSt.hasMoreTokens()){
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(elencoSt.nextToken());
        messageBodyPart.setDataHandler(new DataHandler(source));
        String nomeFile = "allegato";
        try {
          nomeFile = nomiStn.nextToken();
        }
        catch (Exception e){
          return "Mail.sendMail: Nome file mancante." ;
        }
        messageBodyPart.setFileName(nomeFile);
        multipart.addBodyPart(messageBodyPart);
      }

      //Metto le due parti nel messaggio
      message.setContent(multipart);
      //send message  
      Transport.send(message); 
      
      }
	  return "";
  }
  catch(Exception e)
  {
	  e.printStackTrace();
	  return e.getMessage();
  }
  }  
  public String sendMail() {

    try {
      // Testata
      debug("Jmail invio mail a:" + to + "  via " + smtp);
      Properties props = System.getProperties();
      props.put("mail.smtp.host", smtp);
      Session session = null;
      if (!this.usr.equals("")){
    	  //props.put("mail.smtp.host", "smtp.hostinger.com");
    	  props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.port", "465"); 
    	
          props.put("mail.smtp.auth","true");
          System.out.println("setto usr/pwd");
          JAuthenticate pAuth = new JAuthenticate(usr,pwd);
          session = Session.getInstance(props, pAuth);       
        }
        else {
          session = Session.getDefaultInstance(props, null);
        }
    
      MimeMessage message = new MimeMessage(session);
      //Se il mittente fosse erroneamente pi� di uno
      //prendo solo il primo
      if (from.indexOf(";")>0){
        from = from.substring(0,from.indexOf(";"));        
      }
      message.setFrom(new InternetAddress(from));
	   
      //Invio multiplo a piu' destinatari....
      if(isBlank(to) && destinatari.size()>0){
      	for(int i=0;i<destinatari.size();i++){
      		if(tipoInvio.elementAt(i).toString().equals("TO"))
      			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatari.elementAt(i).toString()));
      		else if(tipoInvio.elementAt(i).toString().equals("CC"))
      			message.addRecipient(Message.RecipientType.CC, new InternetAddress(destinatari.elementAt(i).toString()));
      		else if(tipoInvio.elementAt(i).toString().equals("BCC"))
      			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(destinatari.elementAt(i).toString()));
      	}
      }else{
      // o a un singolo destinatario
      	if(isBlank(to)){
      		error("non specificato destinatari nella mail");
      		return "Non specificato destinatario mail";
      	}
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      }
      if(notBlank(bcc))
    	  message.addRecipient(Message.RecipientType.BCC,new InternetAddress(bcc));
      if (htmlFormat)
    	  message.setContent(msg, "text/html");
	  else
	  	  message.setContent(msg, "text/plain");
    
      // message.setContent(msg, "text/plain");
      //Oggetto del messaggio
      message.setSubject(subject);

      Multipart multipart = new MimeMultipart();

      //Corpo del messaggio
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(msg);
      multipart.addBodyPart(messageBodyPart);

      //Allegati
      StringTokenizer st = new StringTokenizer(elencoAllegati, ";");
      StringTokenizer stn = new StringTokenizer(nomiAllegati, ";");
      while (st.hasMoreTokens()){
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(st.nextToken());
        messageBodyPart.setDataHandler(new DataHandler(source));
        String nomeFile = "allegato";
        try {
          nomeFile = stn.nextToken();
        }
        catch (Exception e){
          error("Mail.sendMail: Nome file mancante.");
        }
        messageBodyPart.setFileName(nomeFile);
        multipart.addBodyPart(messageBodyPart);
      }

      //Metto le due parti nel messaggio
      message.setContent(multipart);

      // Send message
      Transport.send(message);
      return "";
    }
    catch (Exception e) {
    	e.printStackTrace();
      error("Jmail.sendMail.Errori: smtp = " + smtp +
            ", from = " + from + ",to = " + to + " error:" + e.getMessage());
      return e.getMessage();
    }
  }

  public void setMsg(String newMsg) {
    msg = newMsg;
  }
  public void setSmtp(String newSmtp) {
    smtp = newSmtp;
  }
  public String getSmtp() {
    return smtp;
  }
  public void setFrom(String newFrom) {
    from = newFrom;
  }
  public String getFrom() {
    return from;
  }
  public void setTo(String newTo) {
    to = newTo;
  }
  public String getTo() {
    return to;
  }
  public String getMsg() {
    return msg;
  }
  public void setSubject(String newSubject) {
    subject = newSubject;
  }
  public String getSubject() {
    return subject;
  }
  public String getElencoAllegati() {
    return elencoAllegati;
  }
  public void setElencoAllegati(String elencoAllegati) {
    this.elencoAllegati = elencoAllegati;
  }
  public String getNomiAllegati() {
    return nomiAllegati;
  }
  public void setNomiAllegati(String nomiAllegati) {
    this.nomiAllegati = nomiAllegati;
  }
  public void setDestinatari(Vector destinatari) {
	this.destinatari = destinatari;
  }
  public void setTipoInvio(Vector tipoInvio) {
	this.tipoInvio = tipoInvio;
  }
  public void setHtmlFormatON() {
		this.htmlFormat = true;
  }
  public void setHtmlFormatOFF() {
	     this.htmlFormat = false;
  }
}