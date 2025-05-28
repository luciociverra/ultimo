/*
 * Created on 19-mag-2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package base.utility;

 

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class JAuthenticate extends Authenticator {

  String sUsername = null;
  String sPassword = null;
  
  public JAuthenticate(String username, String password){
    //   Assign username and password values passed in data from calling mail connection
    sUsername = username;
    sPassword = password;
  }
  
  protected PasswordAuthentication getPasswordAuthentication(){
    //   username/password get authenticated next line
    return new PasswordAuthentication(sUsername, sPassword);
  }
}