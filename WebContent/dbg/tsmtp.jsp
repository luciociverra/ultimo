<%@ include file="base_dbg.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<%@ page import="com.smi.utility.*" %>

<%
String str_errori="";
if (rqUtils.digit("btOk"))
{
     Jmail mail = new Jmail();
     mail.setSys(sys);
     mail.setSmtp(rqUtils.req("txtServer"));
     mail.setSubject("TEST SMTP");
     mail.setMsg(rqUtils.req("txtMsg"));
     mail.setFrom(rqUtils.req("txtFrom"));
     mail.setTo(rqUtils.req("txtTo"));
     mail.sendMail();
     mail=null;
     str_errori=sys.getString("dbgArea");
}
%>
<html>
<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Test SMTP</title>
</head>
<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0" background="../images/bg.jpg">
<form method="POST">
  <p>&nbsp;</p>
  <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" width="50%" id="AutoNumber1">
    <tr>
      <td width="50%">&nbsp; SERVER..: </td>
      <td width="50%">
      <input type="text" name="txtServer" size="33" value="<%=sys.getProp("/smtp/server")%>"></td>
    </tr>
    <tr>
      <td width="50%">From </td>
      <td width="50%">
      <input type="text" name="txtFrom" size="35" value="<%=sys.getProp("/smtp/close-ord/from")%>"></td>
    </tr>
    <tr>
      <td width="50%">To</td>
      <td width="50%">
      <input type="text" name="txtTo" size="35" value=""></td>
    </tr>
    <tr>
      <td width="50%">Msg</td>
      <td width="50%">
      <input type="text" name="txtMsg" size="63" value="prova funzionamento smtp server web"></td>
    </tr>
  </table>
  <p>&nbsp;
  <input type="submit" value="Invia" name="btOk">&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </p>

</form>
<font color='red'><%=str_errori%></body></html>