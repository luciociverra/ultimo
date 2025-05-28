<%@ include file="base_dbg.jsp" %>
<%@ page import="base.utility.*" %>
<%
if (request.getParameter("btClear") !=null)
   {
     Runtime.getRuntime().gc();
   }  
%>
<html>
<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>memory</title>
<style>
TD {
 font-family: Verdana; 
}
.sysTable {
 font-size: 1.0em;
 color: white;
}
</style>

</head>

<body onload="go()" style="font-family: Tahoma; font-size: 14pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0" background="../images/bg.jpg">
<form method="POST">
  <p><input   type="submit" value="Runs the garbage collector" name="btClear">
  <input type="button"   value="Stop video refresh" name="btClear" onclick="ferma();">
</form>
<font color="Yellow">
Memoria Virtual Machine<br>
<%
  Runtime rt=Runtime.getRuntime();
 
  out.write(" Mem totale   " + Utils.getNumero(rt.totalMemory(),"###,###,##0"));
  out.write("<br>");
  out.write(" Mem libera   " + Utils.getNumero(rt.freeMemory(),"###,###,##0"));
  out.write("<br>");
  out.write(" Mem occupata " + Utils.getNumero((rt.totalMemory() - rt.freeMemory()),"###,###,##0"));
  
%>
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
   var url = "shmem.jsp";
   self.document.location=url;
 }
 function ferma()
 {  
 stop = true;
// testo.innerText="STOPPATO"
 }

</SCRIPT>