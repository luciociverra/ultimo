package base.utility;


/**
 * civerra
 * indirizzo di spedizione o singolo o multipli separati da ;
 */
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer extends BaseBean {

  private String msg ;
  private String from;
 
  private String smtp;

  public String to="";
  public String bcc="";
  public String subject="";
  
  public  String elencoAllegatiImg="";
  public  String nomiAllegatiImg="";
  public  String cidAllegatiImg="";
  //
  public  String elencoAllegatiFile="";
  public  String nomiAllegatiFile="";
  public  String pwd="";
  public  String usr="";
  private boolean htmlFormat = false;
  public String errMsg="";

  public boolean sendMail() {

    try {
      // Testata
      debug("Jmail invio mail a:" + to + "  via " + smtp);
      errMsg="";
      Properties props = System.getProperties();
      props.put("mail.smtp.host", smtp);
      props.put("mail.smtp.port","25");
      Session session = null;
      if (!this.usr.equals("")){
          props.put("mail.smtp.auth","true");
          System.out.println("setto usr/pwd");
          JAuthenticate pAuth = new JAuthenticate(usr,pwd);
          session = Session.getInstance(props, pAuth);       
        }
        else {
          session = Session.getDefaultInstance(props, null);
        }
      
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.setSubject(subject);
      if(to.indexOf(";")>0)
      {
          String[] arrTo=to.split(";");
          for(int i=0;i<arrTo.length;i++)
          { 
        	  message.addRecipient(Message.RecipientType.TO, new InternetAddress(arrTo[i]));
        	  sys.debugWhite("Jmail aggiunto indirizzo spedizione:"+arrTo[i]);
          }
      } else {
              message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      }
      
      if(notBlank(bcc))
    	  message.addRecipient(Message.RecipientType.BCC,new InternetAddress(bcc));
      if (htmlFormat)
    	  message.setContent(msg, "text/html");
	  else
	  	  message.setContent(msg, "text/plain");
      
      //Corpo del messaggio
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      if (htmlFormat){
        messageBodyPart.setContent(msg, "text/html");
      }
      else {
        messageBodyPart.setText(msg, "UTF-8");
      }

      Multipart multipart;
      if (htmlFormat)
        multipart = new MimeMultipart("related");
      else
        multipart = new MimeMultipart();        
     
      multipart.addBodyPart(messageBodyPart);

    
      //Allegati di tipo immagine
      if(notBlank(cidAllegatiImg))
      {
      StringTokenizer st = new StringTokenizer(elencoAllegatiImg, ";");
      StringTokenizer stn = new StringTokenizer(nomiAllegatiImg, ";");
      StringTokenizer stcid = new StringTokenizer(cidAllegatiImg, ";");
      
      while (st.hasMoreTokens()){
    	  String sourceFile=st.nextToken();
    	  String nomeFile = stn.nextToken();
    	  String cidFile = stcid.nextToken();
    	  
          messageBodyPart = new MimeBodyPart();
          DataSource source = new FileDataSource(sourceFile);
          messageBodyPart.setDataHandler(new DataHandler(source));
          messageBodyPart.setHeader("Content-ID", cidFile);
          messageBodyPart.setFileName(nomeFile);


          multipart.addBodyPart(messageBodyPart);
       }
      }

      if(notBlank(elencoAllegatiFile))
      {
      StringTokenizer st = new StringTokenizer(elencoAllegatiFile, ";");
      StringTokenizer stn = new StringTokenizer(nomiAllegatiFile, ";");
        
      while (st.hasMoreTokens()){
    	  String sourceFile=st.nextToken();
    	  String nomeFile = stn.nextToken();
     	  
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(sourceFile);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(nomeFile);
        multipart.addBodyPart(messageBodyPart);
       }
      }
      message.setContent(multipart);
      // Send message
      Transport.send(message);
      return true;
    }
    catch (Exception e) {
      error("Jmail.sendMail.Errori: smtp = " + smtp +
            ", from = " + from + ",to = " + to + " error:" + e.getMessage());
      sys.error(e);
      this.errMsg=e.getMessage();
      return false;
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
   
  public void setHtmlFormatON() {
		this.htmlFormat = true;
  }
  public void setHtmlFormatOFF() {
	     this.htmlFormat = false;
  }
}