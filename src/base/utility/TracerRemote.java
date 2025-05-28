package base.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
/**
 * Title:        Standard Internet Sanmarco per applicazioni Java.jsp
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      San marco
 * @author
 * @version 1.0
 */
public class TracerRemote extends Thread {
  protected Sys sys;
  protected String msg;
  private String usr;
  public TracerRemote() {
  }
  public void setSys(Sys aSys)
  {
    sys=aSys;
   }

 public void setMsg(String newMsg)
  {
    msg=newMsg;
   }

  public void run()
 {
 try {
     callUrl();
     }
   catch (Exception e)
  {
    //error("send mail " + e.getMessage());
  }
 }

/** spedisce il job al server tramite chiamata a pagina ASP o Jsp su server remoto */
private void callUrl()
{
String content ="";
try{
    String url = sys.getProp("remote_tracer_url");
    if (url.equals("")) return;
    URL baseURL = new URL (url);
    URLConnection con = baseURL.openConnection();
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setUseCaches(false);
    con.setRequestProperty ("Content-Type", "application/x-www-form-urlencoded");
    DataOutputStream printout = new DataOutputStream(con.getOutputStream());
    content = "cl=" + URLEncoder.encode(sys.getAzienda())
            + "&err=" + URLEncoder.encode(msg)
            + "&time="+ URLEncoder.encode(new Date().toString())
            + "&usr=" + URLEncoder.encode(usr) + "_" + sys.getCurrUserBrowser();
    printout.writeBytes(content);
    printout.flush();
    printout.close();
    BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream ()));
    input.close ();
    }
 catch (Exception e){
                     //System.out.println("Remote.." + e.getMessage() + content);
                    }
 }
  public void setUsr(String newUsr) {
    usr = newUsr;
  }
  public String getUsr() {
    return usr;
  }

}