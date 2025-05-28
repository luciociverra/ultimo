<%@ page import="base.utility.CompressJPEG" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Spool-viewer</title>
</head>
<body>
 <%
   String folderIn  = "D:/java/ProductHub/WebContent/catalogoB";
   String folderOut = "D:/java/ProductHub/WebContent/catalogo/";
   CompressJPEG cp= new CompressJPEG();
   String ret="";
   if(null == request.getParameter("days"))
   {
      ret=cp.elab(folderIn,folderOut,1);
   }
   else
   {
	   int aGG=Integer.parseInt((String)request.getParameter("days"));
	   ret=cp.elab(folderIn,folderOut,aGG*-1);
   }
   
   %>
<h2><%=ret%></h2>
</body>
</html>