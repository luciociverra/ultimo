<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
<title>Utenti connessi</title>
<style>
<style>
body, html {
height:100%;
margin:0;
font-family: Comics, Arial, Verdana, sans-serif;
}

</style>
</head>
<body style="font-family: Tahoma; font-size: 10pt; font-weight: bold;fon-color: white;background-color:white"   topmargin="0" leftmargin="0">
<%
      Connection c = null;

      try {
         Class.forName("org.postgresql.Driver");   //DATABASE UTENTE PASSWORD
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lcom_base","postgres", "pg!adm@teo_rema");
         out.write("<br> APERTO !<br>") ;
       
         Statement stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery( "SELECT * FROM components");
       
         while (rs.next()) {
        	out.write(rs.getString(1));
         }
         
         c.close();
         out.write("Fine") ;
      } catch (Exception e) {
    	  
         out.write(e.getClass().getName()+": "+e.getMessage());
        
      }
      out.write("<br/>Opened database successfully");
 %>
</body>
 
</html>