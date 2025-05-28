<%@page import="base.api.WhoInsideManager"%>
<%@ include file="base_dbg.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="base.utility.*" %>
<%@ page import="base.api.*" %>
<%@ page import="base.servlet.Access" %>
<%!
Hashtable<String, String[]>  every = base.api.WhoInsideManager.getSessionUsers();
String showHash(Hashtable hash) {
	String ret = "";
	try {
		for (Enumeration e = hash.keys(); e.hasMoreElements();) {
			String k = (String) e.nextElement();
			String[] v=(String[]) hash.get(k);
			ret +="<tr><td>"+ k + "</td><td>" + v[0] +"</td><td>"+v[1] + " "+WhoInsideManager.getSessionUsers(k)+  v[2] +"</td></tr>";
		}
		return ret;
	} catch (Exception e) {
		return " " + e.getMessage();
	}
}
String showHashSp(Hashtable hash) {
	String ret = "";
	try {
		for (Enumeration e = hash.keys(); e.hasMoreElements();) {
			String k = (String) e.nextElement();
			String[] v=(String[]) hash.get(k);
			ret +="<tr><td>"+ k + " chi :"+ WhoInsideManager.getSessionUsers(k)+"</td><td>"+v[0]+"  status : "+v[1]+"</td></tr>";
		}
		return ret;
	} catch (Exception e) {
		return " " + e.getMessage();
	}
}
String showHashBasic(Hashtable hash) {
	String ret = "";
	try {
		for (Enumeration e = hash.keys(); e.hasMoreElements();) {
			String k = (String) e.nextElement();
			String v=(String) hash.get(k);
			ret +="<tr><td>"+ k + "      "+v+"</td></tr>";
		}
		return ret;
	} catch (Exception e) {
		return " " + e.getMessage();
	}
}
String showSingleHash(Hashtable hash) {
	long now=System.currentTimeMillis();
	String ret = "";
	try {
		for (Enumeration e = hash.keys(); e.hasMoreElements();) {
			String k = (String) e.nextElement();
			String[] v=(String[]) hash.get(k);
			long now2=Long.parseLong(v[0]);
			long limit=4000;
			String a="";
			if((now-now2)>(limit*1000))
			   WhoInsideManager.deregistra(k);
		
			 ret +="<tr><td>"+ k+"</td><td>" + v[0] + "</td>"+
			"<td>"+ (now-now2) / 1000 + "</td>"+
			"<td>"+ v[1] + "</td><td>"+v[2] + "</td>"+
			"<td>"+v[3]+"</td><td><button onClick='removeId("+k+")'>delete</button></td></tr>";
		}
		return ret;
	} catch (Exception e) {
		return " " + e.getMessage();
	}
}

String showHashMediator(Hashtable hash) {
	String ret = "";
	try {
		for (Enumeration e = hash.keys(); e.hasMoreElements();) {
			String k = (String) e.nextElement();
			Mediator m = (Mediator) hash.get(k);
			
			ret +="<tr><td>"+ k +"</td><td>"+m.getEvent()+"</td><td>"+m.getPage()+"</td><td><a href='javascript:void(0)' "+
			" onClick=\"deleteMediator('"+k+"');\">delete</a></td></tr>";
		}
		return ret;
	} catch (Exception e) {
		return " " + e.getMessage();
	}
}

String showDataFllowers(Vector<String[]> v) {
	String ret = "";
	try {
		for (String[] data : v) {
			ret +="<tr><td>"+ data[0] +"</td><td>"+data[1]+"   status:" +data[2]+"</td></tr>";
		}
		return ret;
	} catch (Exception e) {
		return " " + e.getMessage();
	}
}





%>
<%
if (request.getParameter("idd") != null)
{
    
}  
if (request.getParameter("resetAll") != null)
{
	Register.clear();
}  
if (request.getParameter("cleraActivityAll") != null)
{
  WhoInsideManager.deleteAll();
}
if (request.getParameter("removeId") != null)
{
  WhoInsideManager.deregistra(request.getParameter("removeId"));
}
if (request.getParameter("fermaGuida") != null)
{
   Register.stopToFollow(readCookie(Access.cookieAuth,request));
}
if (request.getParameter("pulisciCoda") != null)
{
   Register.clearQueue();
}
if (request.getParameter("creaMediator") != null)
{
   Register.createMediator("#1624572975766#1624572851120");
}
if (request.getParameter("deleteMediator") != null)
{
   String id=(String)request.getParameter("deleteMediator");
   Register.deleteMediator(id.substring(1,id.lastIndexOf("_")));
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
 box-shadow: 0px 2px 8px #888888; 
}

#contenuti {
 float: right;
 width: 99%;
 margin-right:8px;
 margin-top: 55px;	
 border:1px solid black;
}
button { width: 100px;font-family: Tahoma; font-size: 12pt;border-radius:'4px';cursor: pointer; }
table { border-collapse: collapse; width: 50%;font-family: Tahoma; font-size: 10pt; }
th, td { padding: 2px; text-align: left; border-bottom: 1px solid #ddd; border-right: 1px solid #ddd;}
th { background-color: #f7f7f7}
tr:hover {background-color:#f5f5f5;}
tr:nth-child(even){background-color: #ebf0f5;}

</style>
</head>
<body style="font-family: Tahoma; font-size: 10pt; font-weight: bold;fon-color: white;background-color:white"   topmargin="0" leftmargin="0">
 
<div id="contenitore">
<div id="header">

<table><tr>
<%
String myId=readCookie(Access.cookieAuth,request);
String currentABL=WhoInsideManager.getAbilitUsers(myId);


%>
<td>register.jsp-I AM <%=myId%> (<%=currentABL%>)   I'm following: <%=sys.getProperty("followUser")%>
 
 
sto guidando:<%=sys.getProperty("guidedId")%>

userAblLogin 
 
</td>
<td><button onClick="resetAll();">Reset All</button></td>	 
</tr></table>	 
</div>	

<div id="contenuti">
<h2>WhoInsideManager getSessionUsers()</h2>
<button onClick="cleraActivityAll();">Reset All</button>
<p> de registra aggiorna il record di webactivity -> disconnesso</p>
<table border=1>
<tr><td>mio id</td><td>las time</td><td>lived from</td><td>login id</td><td>3</td><td>4</td><td>4</td></tr>
<%=showSingleHash(base.api.WhoInsideManager.getSessionUsers())%>
</table>

<h2>Register.getDataFollower() - dataFollower</h2>
<table>
<tr><th>id chiave </th><th>è seguito da forma:  N=Silente S=Attivo</th><th></th><tr>
<%=showDataFllowers(Register.getDataFollower())%>
</table>


<h2>DataFlow utente ultima azione Register.getDataFlow()</h2>
<table>
<%=showHash(Register.getDataFlow())%>
</table>
<h2>DataFlow utente ultimo evento Register.getDataEvents()</h2>
<table>
<%=showHash(Register.getDataEvents())%>
</table>

<button onClick="fermaGuida();">FERMA GUIDA</button>

<button onClick="pulisciCoda();">PULISCI CODA</button>

<h2>Register.switchAdmin</h2>
<table>
<%=showHashBasic(Register.getSwitchAdmin())%>
</table>
<h2>Register.mediator</h2>
<table>
<%=showHashMediator(Register.getDataMediator())%>
</table>


<h2>Messaggi ad utenti Register.getMessageQue()</h2>
<table>
<tr><th>a chi</th><th>Testo</th><th>Da chi</th><tr>
<%=showHashSp(Register.getMessageQue())%>
</table>


<button onClick="test1()">GO 1</button>
</div>
</div>

</body>
<script>
function del(aId){
	self.document.location="who.jsp?idd="+aId;
}
function resetAll(aId){
	self.document.location="register.jsp?resetAll=x";
}
function removeId(aId){
	self.document.location="register.jsp?removeId="+aId;
}

function cleraActivityAll(){
	self.document.location="register.jsp?cleraActivityAll=1";
}
function fermaGuida(){
	self.document.location="register.jsp?fermaGuida=1";
}
function pulisciCoda(){
	self.document.location="register.jsp?pulisciCoda=1";
}
function deleteMediator(aId){
	self.document.location="register.jsp?deleteMediator="+aId;
}
</script>
</html>