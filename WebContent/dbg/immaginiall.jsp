<%@ page import="base.utility.CompressJPEG" %>
<% 
boolean isMyPc=System.getProperty("user.home").indexOf("lcive") > 0;
//http://www.teorematoys.com/dbg/immaginiall.jsp?days=4
//http://localhost:82/hc/dbg/immaginiall.jsp?days=15
//http://localhost:82/hc/dbg/immaginiall.jsp?annomese=202311
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>ELABORA IMGS</title>
</head>
 <body>
  <%
  
   String folderOut = "E:/teoremastore/resources/catalogo/";
   String folderIn = "E:/teoremastore/resources/catalogoB";
   
   if(isMyPc){
	   folderOut ="D:/java/ProductHub/WebContent/catalogo/";  
       folderIn  ="D:/java/ProductHub/WebContent/catalogoB";  
   }
   CompressJPEG cp= new CompressJPEG();
   String retBase=cp.elabNuovi(folderIn,folderOut);
   out.write("FINE ELABORO_NUOVI");
   String ret="";
   if(null == request.getParameter("days") && null == request.getParameter("annomese") )
   {
	  out.write("ELABORO TUTTE LE IMMAGINI IN :"+folderIn);
      ret=cp.elab(folderIn,folderOut);
   }
   
   if(null != request.getParameter("days")) 
   {
	   int aGG=Integer.parseInt((String)request.getParameter("days"));
	   out.write("ELABORO IMMAGINE CARICATE NEI :"+aGG+" ULTIMI GIORNI ---- DA FOLDER:"+folderIn);
	   ret=cp.elab(folderIn,folderOut,(aGG *-1));
   }
   
   if(null != request.getParameter("annomese")) 
   {
	   String annoMese=(String)request.getParameter("annomese");
	   out.write("ELABORO IMMAGINE CARICATE NEL ANNOMESE :"+annoMese+"--- DA FOLDER:"+folderIn);
	   ret=cp.elabMese(folderIn,folderOut,annoMese);
   }
  %>
<h2><%=retBase%><br><%=ret%></h2>
</body>
</html>