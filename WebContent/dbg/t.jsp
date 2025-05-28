<%@ include file="base_dbg.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<jsp:useBean id="db" scope="page" class="com.smi.db.DbConn" />
<%@ page import="com.ibm.as400.access.*" %>
<%!
%>
<%
AS400 sysAs;
sysAs = new AS400("80.18.78.98","GALILEO52","SMI");
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
VERSIONE AS :
<%
out.write(String.valueOf(sysAs.getVersion()));
%>
<hr>
SYSTEM NAME :
<%
sysAs.disconnectService(AS400.COMMAND);
%>
<hr>
</body>
</html>