package base.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

import org.postgresql.util.PGobject;

import base.utility.Sys;
import base.utility.Utils;
public class SqlEnt {
	private String dataType="";
	private String dataField="";
	private String dataValue="";
	private byte[] aBytes;
	private int maxLength=0;
	private boolean canUpdate=true;
	private boolean use_brakets=false;

   public SqlEnt(String a,String b,String c,int aMaxL,boolean isUpdatable)
   {
	   this.dataField=a;
	   this.dataType=b;
	   this.dataValue=c;
	   this.maxLength=aMaxL;
	   this.canUpdate=isUpdatable;
   }
   public SqlEnt(String a,String b,byte[] aBytes)
   {
	   this.dataField=a;
	   this.dataType=b;
	   this.aBytes=aBytes;
   }
   
   public static String getSql(Vector<SqlEnt> entList,String azione,String tableName,String keyParam,String returningInfo)
   {
	   String sqlRet="";
	   String sqlA="";
	   String sqlB="";
	   
	   if(azione.equals("INSERT")) {
		   sqlRet="INSERT INTO "+tableName+" (";
		   for (SqlEnt ent : entList)
			{
			   sqlA+=""+ent.dataField+",";
			   sqlB+="?,";
			}
		    sqlA=Utils.eliminaLastChar(sqlA)+") VALUES (";
		    sqlB=Utils.eliminaLastChar(sqlB)+") "+returningInfo;
			
	   }
	   if(azione.equals("UPDATE")) {
		   sqlRet="UPDATE "+tableName+" SET ";
		   for (SqlEnt ent : entList)
			{
			   if(ent.canUpdate) sqlA+=""+ent.dataField+"=?,";
			}
		    sqlA=Utils.eliminaLastChar(sqlA)+" WHERE "+keyParam +returningInfo;
	   }
	   if(azione.equals("DELETE")) {
		   sqlRet="DELETE FROM "+tableName+" WHERE "+keyParam +returningInfo;
		   entList = new Vector<SqlEnt>();
	   }
	   return sqlRet+sqlA+sqlB;
   }
   
   public static PreparedStatement completeStatement(Sys sys,Vector<SqlEnt> entList,PreparedStatement ps,String azione)
   {
	   int psCounter=1;
	   String tipoDato="";
	   String valDato="";
	   String allDbg="";
	   String colName="";
	   try {
		for (SqlEnt ent : entList)
			{
			   tipoDato=ent.dataType;
			   valDato=ent.dataValue.trim();
			   colName=ent.dataField;
			   int maxSize=ent.maxLength;
			   if(azione.startsWith("U") && ! ent.canUpdate) continue; // da non AGGIORNARE
			   if(tipoDato.equals("s"))
			   { ps.setString(psCounter, Utils.limit(valDato,maxSize));}
			   if(tipoDato.equals("n"))
			   {   valDato=Utils.getNumero(valDato,"0");
			       if(valDato.equals("")) valDato="0";
				   ps.setInt(psCounter, Integer.parseInt(valDato));}
			   if(tipoDato.equals("by"))
			   {   
				   ps.setBytes(psCounter, ent.aBytes);}
			   
			   if(tipoDato.equals("f"))
			   {   valDato=Utils.getDoubleFromValuta(valDato);
			       if(valDato.equals("")) valDato="0";
				   ps.setDouble(psCounter, Double.parseDouble(valDato));}
			   if(tipoDato.equals("d"))
			   {
				   if(! valDato.equals("0"))
				   {
				     Calendar cal = Calendar.getInstance(); 
			         ps.setTimestamp(psCounter, new Timestamp(cal.getTimeInMillis())); 
				   } else 
				   {
					  ps.setNull(psCounter, java.sql.Types.TIMESTAMP);
				   }
				}
			   if(tipoDato.equals("o"))
			   {
				   PGobject jsonObject = new PGobject();
				   jsonObject.setType("json");
				   jsonObject.setValue(valDato);
				   ps.setObject(psCounter, jsonObject);
		   	   }
			   allDbg+=colName+" -> "+psCounter+"  ("+tipoDato+") "+valDato+"<br>";
			   psCounter+=1;
			}
		if(null != sys)
		   sys.debugWhite(allDbg);
		return ps;
	  } catch (SQLException e) {
		if(null != sys)
		{		
		   sys.error("errore :"+allDbg);  
		   sys.error(e);  
		 } else {
			System.out.println(e.getMessage());
			System.out.println(allDbg.replaceAll("<br>","/n"));
		 }
		 return null;
	  }
   }
 
   public static String getParmList(Vector<SqlEnt> entList)
   {
	   String allDbg="";
	   int psCounter=1; 
	   try {
		for (SqlEnt ent : entList)
			{
			   String tipoDato=ent.dataType;
			   String valDato=ent.dataValue.trim();
			   String colName=ent.dataField;
		       allDbg+=colName+" -> "+psCounter+"  ("+tipoDato+") "+valDato+"<br>";
			   psCounter+=1;
			}
		return allDbg;
	  } catch (Exception e) {
	    return allDbg+" "+ e.getMessage();
	  }
   }
   
}
