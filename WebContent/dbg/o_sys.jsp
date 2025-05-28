<jsp:useBean id="sys" scope="session" class="base.utility.Sys" />
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="base.utility.*" %>



<style>
body { font-family: Verdana; font-size: 10px; font-weight: bold} 
td   { font-family: Verdana; font-size: 10px; font-weight: bold} 
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title></title>
</head>
<body>

<%
if("1".equals((String)request.getParameter("remove"))) {
    out.write("SYS REMOVED");
	session.removeAttribute("sys");
	sys=null;
}
%>
<%  if(null==sys) { %>
 <h2>SYS A NULL</h2>
<%  } else { %>

<%=sys.debugProperty()%>
<hr>
 <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" width="100%" id="table1">
<tr><td>getAzienda()</td><td><%=sys.getAzienda()%></td></tr>  
<tr><td>getUserDir()</td><td><%=sys.getUserDir()%></td></tr>  
<tr><td>getLinguaWeb()</td><td><%=sys.getLinguaWeb()%></td></tr>  
<tr><td>getInternetDir()</td><td><%=sys.getInternetDir()%></td></tr>  
<tr><td>getCurrUserBrowser()</td><td><%=sys.getCurrUserBrowser()%></td></tr>  
<tr><td>debug attivo ?</td><td><%=sys.getDbgOn()%></td></tr>  
 <tr><td>followUser</td><td><%=sys.getProperty("followUser")%></td></tr>  
</table>
<%  }  %>
<br/><br/><br/><br/>
<a href="javascript:reset();">REMOVE SYS FROM SESSION</a>
<hr>
<button type="button" onclick="disconnect()">DISCONNECT VIA ACCESS SERVLET</button> 
<hr>
<%
double sizeC = new File("C:\\").getUsableSpace() / (1024.0 * 1024 * 1024);
double sizeAlt = new File("E:\\").getUsableSpace() / (1024.0 * 1024 * 1024);

sizeC=Utils.round(sizeC,2);
sizeAlt=Utils.round(sizeAlt,2);
out.write("<h2>C:------------"+sizeC+"</h2>");
out.write("<h2>E:------------"+sizeAlt+"</h2>");



%>

 </body>
<script src="jquery.js"></script> 
<script>
function reset(){
	self.document.location="o_sys.jsp?remove=1";
}

function removeAll() {
  createCookie("cauth","",-1); // delete
  document.forms["form1"].submit();
}
function createCookie(name,value,days) {
    var expires = "";
    var date = new Date();
    date.setTime(date.getTime() + (days*24*60*60*1000));
    expires = "; expires=" + date.toUTCString();
    document.cookie = name + "=" + value + expires + "; path=/";
}

function disconnect(){
	var url="<%=request.getContextPath()%>/access?action=usr_disconnect";
	$.post( url, function( data ) {
		  alert( "FATTO" );
		  self.document.location="o_sys.jsp";
	});
}

</script>
</html>
