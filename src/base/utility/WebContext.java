package base.utility;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NoInitialContextException;
import javax.sql.DataSource;

public class WebContext   {
 
    public static Hashtable<String,String> tabDati=new Hashtable<String,String>();

	public static String getEnv(String key)
	{
		String var = "";
		try
		{
			Context ctx = new InitialContext();
			if(ctx == null)
				throw new Exception("No Context");
			var = (String)ctx.lookup("java:comp/env/" + key);
			
		}
		catch(NoInitialContextException e2){
			return getProperty(key);
		}
		catch(Exception e) {
			System.out.println("Context.getEnv:"+ key+ "   " +e.getMessage());
		}
		return var;
	}
	public static boolean isDemoVersion() {
		
		return getEnv("DemoVersion").equals("Active");
	}
	public static String getPropEnv(String key)
	{
		String var = "";
		try
		{
			Context ctx = new InitialContext();
			if(ctx == null)
				throw new Exception("No Context");
			var = (String)ctx.lookup("java:comp/env/" + key);
			
		}
		catch(NoInitialContextException e2){
			return getProperty(key);
		}
		catch(Exception e) {
			System.out.println("Context.getEnv:"+ key+ "   " +e.getMessage());
		}
		return var;
	}
	
	public static void setProperty(String aKey,String aVal)
	  {
	    tabDati.put(aKey,aVal);
	  }
	public static String getProperty(String nomeVar)
	  {
	    if (tabDati.containsKey(nomeVar))
	       return tabDati.get(nomeVar);
	     else
	       return  "";
	  }
    
	  
	private static String getAppParmFind(String aFile, String aKey) {
	 		String ret=""; 
			String line = "";
			try {
				BufferedReader jobs = new BufferedReader(new FileReader(aFile));
				while ((line = jobs.readLine()) != null) {
					if (line.trim().equals("")) {
						continue;
					}
					if (line.substring(0, 1).equals("#")) {
						continue;
					}
					int pos = line.indexOf("=");
					String a = line.substring(0, pos);
					String b = line.substring(pos + 1);
					if(a.equalsIgnoreCase(aKey)) {
					   ret=b; break;	
					}
					}
				jobs.close();
				jobs = null;
				return ret;
			} catch (Exception x) {
				System.out.println("WebContext:  "+ line +" file:"+aFile+" key: "+ aKey);
				System.out.println(x.getMessage());
				return "";
			}
		}
	public static String getAppParm(String aFile, String aKey) {
 	    try 
 	    {
		    String result=getAppParmFind(aFile,aKey); 
		    if(result.indexOf("##")>=0)
		    {
		    	String realParam=Utils.getTag("##",result);  // ritorna il codice tra ## e ##
		    	String paramToReplace=getAppParmFind(aFile,realParam);
		    	return result.replaceAll("##"+realParam+"##", paramToReplace);
		    }
		    return result;	
		} catch (Exception x) {
			System.out.println("WebContext:  "+ aFile+" file:"+aFile+" key: "+ aKey);
			System.out.println(x.getMessage());
			return "";
		}
	}
	public static DataSource getDataSource(String dataSourceName)
	{
		DataSource ds = null;
		try
		{
			Context ctx = new InitialContext();
			if(ctx == null)
				throw new Exception("No Context");
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/"+dataSourceName);
			ctx.close();
		}
		catch(NoInitialContextException e2){
			return ds;
		}
		catch(Exception e) {
			System.out.println("WebContext.getDataSource:"+ dataSourceName+ "   " +e.getMessage());
		}
		return ds;
	}
}
