<%@ include file="base_dbg.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="base.api.*" %>
<%
String lastMsg="";
if (request.getParameter("id") != null)
{
	lastMsg=WhoInsideManager.removeId((String)request.getParameter("id"));
}  
if (request.getParameter("delAll") != null)
{
   lastMsg=WhoInsideManager.deleteAll();
}  
%>
<html>
<head>
<title>Utenti connessi</title>
<style>
<style>
body, html {
height:100%;
margin:0;
font-family: Comics, Arial, Verdana, sans-serif;
}

#header{
position:fixed;
height:40px;
width:100%;
color:red;
border:0px solid black;
background:white;
/* margine sx altezz sfuocatura */
box-shadow: 0px 5px 8px #888888; 
}

#contenuti {
float: right;
width: 99%;
margin-right:8px;
margin-top: 45px;	
 		/* sotto l'header */
border:1px solid black;
}
button { width: 200px;font-family: Tahoma; font-size: 12pt;border-radius:'4px';cursor: pointer; }
table { border-collapse: collapse; width: 100%;font-family: Tahoma; font-size: 10pt; }
th, td { padding: 2px; text-align: left; border-bottom: 1px solid #ddd; border-right: 1px solid #ddd;}
tr:hover {background-color:#f5f5f5;}
tr:nth-child(even){background-color: #f2f2f2;}

</style>
</head>
<body style="font-family: Tahoma; font-size: 10pt; font-weight: bold"   topmargin="0" leftmargin="0">
 
<div id="contenitore">
<div id="header">
<table><tr>
<td><button onClick="delAll();">Elimina tutti</button></td>	 
</tr></table>	 
</div>	

<div id="contenuti">
<h2>Utenti attivi</h2> SELECT * FROM WEBACTIVITY where web_info->>'disconnect' = ''  
<h3><%=lastMsg%></h3>
<table>
<thead><tr><td></td><td>Id</td><td>login</td><td>app</td><td>abl</td><td>codice</td><td></td></tr>
</thead>
<tbody>
<%
DbConn db = new DbConn(sys);
db.connect("DBASE");
RsTable rs=db.getRsTable("SELECT * FROM WEBACTIVITY where web_info->>'disconnect' =''",true);
while(rs.next()){
	%>
	<tr>
	<td><a href="javascript:del(<%=rs.getField("idunique")%>);">DELET</a></td>
	<td><%=rs.getField("idunique")%></td>
	<td><%=rs.getFieldJson("web_info","login")%></td>
	<td><%=rs.getFieldJson("web_session","userName")%></td>
	<td><%=rs.getFieldJson("web_session","userAppId")%></td>
	<td><%=rs.getFieldJson("web_session","userAbl")%></td>
   <td></td></tr>
 <%
}
%>
 </table>
</div>
</div>

</body>
<script>
function del(aId){
	self.document.location="who.jsp?idd="+aId;
}
function delAll(aId){
	self.document.location="who.jsp?delAll=x";
}

</script>
</html>