<%@ page import="base.utility.CompressJPEG" %>
<%@ page import="base.utility.Sys" %>

<html>
<head>
<title>IMMAGINI</title>
</head>
<body style="font-family: Tahoma; font-size: 18pt;">
<%
 CompressJPEG c= new CompressJPEG();
  
 out.write(c.go());

 %>
</body>
 
</html>