<%@ include file="/main/bean_base.jsp" %>
<jsp:useBean id="db" scope="page" class="com.smi.db.DbConn" />
<html>
<html>

<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Debug</title>


<base target="principale">


</head>

<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" background="../images/bg.jpg">
<form name="form1" method="POST">
 Sistema:<input type="text" name="txtSystem" size="15" value="AS400"> Tabella:
 <input type="text" size="12" name="txtTable"><input type="button" onClick="go();" value="Load" name="btExec"><input type="submit" value="Torna al debug" name="btBack">
 </form>
</body>
</html>
<SCRIPT language="JavaScript">
function go()
{
  self.parent.sotto.location="tb_list.jsp?system=" + form1.txtSystem.value + "&table=" + form1.txtTable.value;
}
</SCRIPT>