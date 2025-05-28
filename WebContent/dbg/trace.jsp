<%@ page import="java.sql.*" %>
<%@ page import="base.utility.Utils"%>
<%!
  String formatOra(String aData) {
	try 
	{
	int stringSize=aData.length();
	 
	if(stringSize==6)
	{
		return aData.substring(0,2)+":"+aData.substring(2,4)+":"+aData.substring(4,6);
	}
	if(stringSize==5)
	{
		return "0"+aData.substring(0,1)+":"+aData.substring(1,3)+":"+aData.substring(3,5);
	}
	if(stringSize==4)
	{
		return "0"+aData.substring(0,1)+":"+aData.substring(1,3);
	}
	return aData;
	} catch(Exception e){
		return e.getMessage();
	}
}

String getSpan(String aData) {
	if(aData.equals("0")) return "<span style='color:red'>";
	return "<span style='color:black'>";
}
%>
<%
/*
SELECT id, customer, project, data_ev, ora_ev, status, message
FROM public.registerjobs;
*/
%>
<html>
<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title></title>
<style>
body { font-family: Helvetica, Consolas, sans-serif; }
</style>
</head>
<body >
<%
Connection c = null;
try {
   Class.forName("org.postgresql.Driver");   //DATABASE UTENTE PASSWORD
   c = DriverManager.getConnection("jdbc:postgresql://5.249.144.105:5432/lcom","postgres", "2-postgres-3");
   out.write("APERTO !<br/>") ;
   Statement stmt = c.createStatement();
   ResultSet rs = 
   stmt.executeQuery("SELECT * FROM registerjobs WHERE data_ev> "+Utils.getSpcDateAMG(-7) 
		   +" and messaGE LIKE '%dump%'"+
  		 " order by data_ev desc"
   		 );
   out.write("<h2>DUMP ultimi 7 GG</h2>");
   while (rs.next()) {
  	 out.write(
  	 getSpan(rs.getString(6))		 
  	 + rs.getString(1)
  	 +" "+ rs.getString(2) 
  	 +" " +rs.getString(3)
  	 +" " +rs.getString(4)
	 +" " +formatOra(rs.getString(5))
  	 +" " +rs.getString(6)
  	 +" " +rs.getString(7)+"</span><br/>"
  	 );
   }
   out.write("<hr>") ;
   rs.close();
   out.write("<h2>ALLINEAMENTO ultimi 7 GG</h2>");
   rs = 
		   stmt.executeQuery("SELECT * FROM registerjobs WHERE data_ev> "+Utils.getSpcDateAMG(-7) 
				   +" and messaGE LIKE '%allineamento%'"+
		  		 " order by data_ev desc"
		   		 );
   while (rs.next()) {
	  	 out.write(
	  	 getSpan(rs.getString(6))			 
	  	 + rs.getString(1)
	  	 +" "+ rs.getString(2) 
	  	 +" " +rs.getString(3)
	  	 +" " +rs.getString(4)
	  	 +" " +formatOra(rs.getString(5))
	  	 +" " +rs.getString(6)
	  	 +" " +rs.getString(7)
	  	 +"<br/>"
	  	 );
	   }
	   out.write("<hr>") ;
	   rs.close();
	  
} catch (Exception e) {
   out.write(e.getClass().getName()+": "+e.getMessage());
}
c.close();
c=null;
%>
</body>
</html>