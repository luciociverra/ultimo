<%@ include file="/main/bean_base.jsp" %>
<%@ include file="/main/bean_db.jsp" %>
<%
if (request.getParameter("btClear") !=null)
   {
     Runtime.getRuntime().gc();
   }  
 db.setSys(sys);
 db.apri_db("PROD");
 db.execQuery("SELECT * FROM WBCATE0J");
 db.closeQuery();
 db.closeDb();  
%>
<html>

<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>memory</title>
<style>
TD {
 font-family: Verdana; 
}
</style>

</head>

<body onload="go()" style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0" background="../images/bg.jpg">
<form method="POST">
  <p><input   type="submit" value="Runs the garbage collector" name="btClear">
  <input type="button"   value="Stop video refresh" name="btClear" onclick="ferma();">
  
</form>
<font color="Yellow">
Memoria Virtual Machine<br>
<%
  Runtime rt=Runtime.getRuntime();
 
  out.write(" Mem totale   " + sys.formatNumber(rt.totalMemory(),"###,###,##0"));
  out.write("<br>");
  out.write(" Mem libera   " + sys.formatNumber(rt.freeMemory(),"###,###,##0"));
  out.write("<br>");
  out.write(" Mem occupata " + sys.formatNumber((rt.totalMemory() - rt.freeMemory()),"###,###,##0"));
  
%>

</body>

</html>
<SCRIPT language="JavaScript">
var stop = false;
function go()
 {  
 	window.scrollTo(0,999999);
	timeoutID = setTimeout('reload()',3000);
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