<%@ include file="/main/costanti.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<jsp:useBean id="stat" scope="page" class="com.smi.utility.Site_stat" />
<%@ page import="java.util.*" %>
<%@ page import="com.smi.utility.Utils" %>
<%
stat.setSys(sys);
%>
<html>
<%=sys.getFileCss()%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta http-equiv="Content-Language" content="it">
<title></title>
</head>
<body text="#000000" bgcolor="#999999" background="../images/check.gif" vlink="#0000FF" alink="#0000FF" >
<br>
ORA ATTUALE MACCHINA : <%=Utils.getTime()%>
<br>
<%=stat.ultimiAccessi()%>
</body>
</html>