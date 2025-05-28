<%@ include file="base_dbg.jsp" %>

<html>

<head>
<style>
TD {
 font-family: Verdana; 
}
</style>

</head>

<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0" background="../images/bg.jpg">
<%
if (request.getParameter("reload_all") !=null)
   {
     Struttura.reloadAll();
   }
out.write(Struttura.getStatus());
%>
 

</body>

</html>
<SCRIPT language="JavaScript">

</SCRIPT>