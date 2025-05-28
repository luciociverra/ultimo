<%@ include file="base_dbg.jsp" %>
<%
if (request.getParameter("btClear") !=null)
   {
     sys.clearDbgArea();
     response.sendRedirect("shdebug.jsp");
   }
   
sys.setDbgOn(true);
%>
<html>
<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Debug</title>
<style>
TD {
 font-family: monospace; font-size: 8px; 
}
.ERROR {
  color: yellow;
}
.FATAL {
  color: yellow;
}
.DEBUG {
  /*color: green;*/
}
.INFO {
  color: #FFEE11;
}

</style>

</head>

<body onload="go()" style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0" vlink="#0000FF">
<table>
<%=sys.getDbgArea()%>
</table>
<form method="POST">
  <p><input   type="submit" value="Clear" name="btClear">
  <input type="button"   value="Stop" name="btClear1" onclick="ferma();">
  <input type="button"   value="Start" name="btClear2" onclick="stop=false;reload();"><br><br>
  <span style="background-color: #FF0000" id="testo"><font color="#FFFFFF">ATTIVO</font></span></p>
  <%=sys.getCont()%>
</form>
</body>

</html>
<SCRIPT language="JavaScript">
var stop = false;
function go()
 {  
 	window.scrollTo(0,999999);
	timeoutID = setTimeout('reload()',4000);
   }
   
   
function reload()
 {  
   if(stop) return;
   var url = "shdebug.jsp";
   self.document.location=url;
 }
function ferma()
 {  
 stop = true;
 testo.innerText="STOPPATO"
 }
 function debugQuery(ind,src)
 {  
 stop = true;
 self.document.location="rquery.jsp?key=" + ind+"&src=" + src;
 }
 function debugProtocol(ind)
 {  
 stop = true;
 self.document.location="rprotocol.jsp?key=" + ind;
 }

</SCRIPT>