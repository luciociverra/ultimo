 
<%@ page import="java.sql.*" %>
<%@ page import="base.utility.Utils"%>
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
   stmt.executeQuery("SELECT * FROM registerjobs WHERE data_ev> "+Utils.getSpcDateAMG(-2)+
  		 " order by data_ev desc"
   		 );
   while (rs.next()) {
  	 out.write(
  	 rs.getString(1)
  	 +" "+ rs.getString(2) 
  	 +" " +rs.getString(3)
  	 +" " +rs.getString(4)
  	 +" " +rs.getString(5)
  	 +" " +rs.getString(6)
  	 +" " +rs.getString(7)+"<br/>"
  	 );
   }
   out.write("<hr>Fine") ;
   rs.close();
} catch (Exception e) {
   out.write(e.getClass().getName()+": "+e.getMessage());
}
c.close();
%>
</body>
</html>