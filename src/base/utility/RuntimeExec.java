package base.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RuntimeExec {

	public static void main(String[] args) {
       try{
    	   String nomeDB= "DUGA";
     	   String nomeFile= nomeDB+"_"+new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date())+".dbase";
    	   System.out.println(nomeFile);   
    	   String s="C:/LCOM_java/postgresSql/pg10/bin/pg_dump.exe --host localhost --port 5432 --username "
    	   		+ "\"postgres\" --no-password  --format custom --blobs --verbose --file \"C:/LCOM_java/postgresSql/backups/"+
    	   		nomeFile+"\" \""+nomeDB+"\"";
    	   Runtime.getRuntime().exec(s);
       }
catch(Exception e) {}
	}

}
