<%@ page import="base.utility.CompressJPEG" %>
<% 
boolean isMyPc=System.getProperty("user.home").indexOf("lcive") > 0;
//http://www.teorematoys.com/dbg/immaginiall.jsp?days=4
%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Spool-viewer</title>
</head>
 <body>
  <%
   String folderIn = "E:/teoremastore/resources/catalogoB";
   String folderOut = "E:/teoremastore/resources/catalogo/";
   CompressJPEG cp= new CompressJPEG();
   // solo i mancanti
   String ret=cp.elabNuovi(folderIn,folderOut);
  %>
<h2><%=ret%></h2>
</body>
</html>