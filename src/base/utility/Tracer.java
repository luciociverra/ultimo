package base.utility;

/**
 * Title:        Standard Internet Sanmarco per applicazioni Java.jsp
 * Description:  Scrive gli errori in un file
 * Copyright:    Copyright (c) 2001
 * Company:      San marco
 * @author Ebusiness Group -CIverra Lucio
 * @version 1.0
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Tracer extends BaseBean {
  private static TracerRemote remote = new TracerRemote();
  public Tracer() {
  }
    public static void writeLog (Sys sys,String msg) {
    String path;
    String fileName;
    
    path=sys.getFolderLog();
     try
        {
            String data;
            Date a = new Date();
            data = a.toString();
            fileName=path + Utils.getSpcDateAMG(0) + "_err.txt";
            FileWriter out = new FileWriter(fileName,true);
            out.write(sys.getCurrUser() + "," + data.substring(11,19) + ": "+ msg + "\r\n" );
            out.close();
            out = null;
            a = null;
        }
        catch(IOException e) {sys.debug("Tracer.writeLog :" + e.getMessage());}
  }

	public static void writeStat(Sys sys, String message) {
		String path;
		String riga = "";
		String strMessage = "";
		String fileName;
		String testata = sys.getProp("tracer_testata") + "\r\n";
		// stringa evento
		String tr_msg = sys.getProp("tracer_" + message,"");
		if(tr_msg.equals(""))
		{
			tr_msg = sys.getProp("tracer_LOGON");
			String newRow=tr_msg.substring(0,tr_msg.lastIndexOf(";"));
			tr_msg=newRow+";"+message;
		}
		if (tr_msg != null) {
			if (!tr_msg.equals("")) {
				strMessage = parseStatValue(sys, tr_msg);
			}
		}
		//Esco se non ho trovato questo evento
		if (strMessage.equals(""))
			return;
    
		path = sys.getFolderStat();
		try {
			String data;
			Date a = new Date();
			data = a.toString();
			FileWriter out;
			fileName = path	+ sys.getAzienda() + "_" +
                 Utils.getSpcDateAMG(0).substring(0, 6) + ".log";
			riga = strMessage	+ "\r\n";
			// prima volta per file mese
			if (!Utils.existFile(fileName)) {
				out = new FileWriter(fileName, true);
				out.write(testata);
				out.write(riga);
			} else {
				out = new FileWriter(fileName, true);
				out.write(riga);
			}

			out.close();
			out = null;
			a = null;
		} catch (IOException e) {
			sys.debug("Tracer.writeStat:" + e.getMessage());
		}
	}
  
  /** Traduce la stringa passata in un valore per le statistiche */
  private static String parseStatValue(Sys sys,String aStr){
    String ret = aStr;
    ret=Utils.replaceStr(ret,"<DATA>",Utils.getSpcDateGMA(0));
    ret=Utils.replaceStr(ret,"<UTENTE>",sys.getCurrUser().toUpperCase());
    ret=Utils.replaceStr(ret,"<ORA>",Utils.getTime());     
    return ret;
  }
  
  
}