<%@ include file="base_dbg.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<%@ page import="com.ibm.as400.access.*" %>
<%
String baseDataSource = "/iseries-connections/system[system-name='JCAL']";
String ipAS400 = sys.getProp(baseDataSource+"/ip-address");

String nrSerie="";
AS400 sysAs = new AS400();
if (! (request.getParameter("bt") == null))
{
sysAs = new AS400(request.getParameter("p1"),request.getParameter("p2"),request.getParameter("p3"));
sysAs.connectService(AS400.COMMAND);
SystemValue qsysval = new SystemValue(sysAs, "QSRLNBR");
nrSerie=qsysval.getValue().toString();
sysAs.disconnectService(AS400.COMMAND);
}
%>
<html>

<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Debug</title>
</head>
<body>
<hr>
<font face="Century Gothic" size="5">
<%
if (! (request.getParameter("bt") == null))
{
out.write("VERSIONE AS : ");
out.write(String.valueOf(sysAs.getVersion()));
out.write("<br>");

out.write("RELEASE AS : ");
out.write(String.valueOf(sysAs.getRelease()));
out.write("<br>");

out.write("MODIFICATION AS : ");
out.write(String.valueOf(sysAs.getModification()));
out.write("<br>");

out.write("SYSTEM NAME  : " + String.valueOf(sysAs.getSystemName()));
out.write("<br>");
out.write("SERIAL NUMBER: " + nrSerie);

 
sysAs.disconnectService(AS400.COMMAND);
%>
<hr>
<%
}
%>
<form method="POST" action="t_system.jsp">
  <b>
  <font face="Century Gothic" size="5">TEST DI APERTURA AS (CALL PROPGRAM) <br>
  restituisce il numero di versione del sysop AS</font></b><table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="500" id="AutoNumber1">
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">ip</font></b></td>
      <td>  <input type="text" name="p1" size="20" value="<%=ipAS400%>"></td>
    </tr>
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">user</font></b></td>
      <td><input type="text" name="p2" size="20"></td>
    </tr>
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">pwd</font></b></td>
      <td><input type="text" name="p3" size="20"></td>
    </tr>
  </table>
  <p>
  
  
  <input type="submit" value="Invia" name="bt"></p>
</form>
<p>&nbsp;</p>
</body>
</html>