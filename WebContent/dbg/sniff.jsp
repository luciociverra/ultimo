<%@ include file="base_dbg.jsp" %>
<%@ include file="../main/bean_base.jsp" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%
String baseDataSource = "/iseries-connections/system[system-name='JCAL']";

String myIp="";
if (rqUtils.req("ip").equals("")) 
   myIp=sys.getProp(baseDataSource+"/ip-address");
else   
   myIp=rqUtils.req("ip");
%>
<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="20" leftmargin="20" background="../images/bg.jpg">
<form name="f1" method="POST">
  <p>IP:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="ip" size="20" value="<%=myIp%>">     PORT:&nbsp; <input type="text" name="port" size="5">&nbsp;&nbsp;&nbsp;
  <input type="submit" value=" TEST   >>>" name="btOk"></p>
  <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" width="100%" bgcolor="#FFFFFF" id="table1">
    <tr>
      <td><b><font size="2" color="#FF0000">porte x as: <br>
      <input type="button" value="449" name="449" onclick="f1.port.value=this.name;">
      <input type="button" value="8470" onclick="f1.port.value=this.value;">
      <input type="button" value="8471" onclick="f1.port.value=this.value;">
      <input type="button" value="8472" onclick="f1.port.value=this.value;">
      <input type="button" value="8473" onclick="f1.port.value=this.value;">
      <input type="button" value="8474" onclick="f1.port.value=this.value;">
      <input type="button" value="8475" onclick="f1.port.value=this.value;">
      <input type="button" value="8476" onclick="f1.port.value=this.value;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		PROTOCOLLO SAMBA (smb://)
		<b><font size="2" color="#FF0000">
      <input type="button" value="445" name="445" onclick="f1.port.value=this.name;">
      <input type="button" value="139" name="139" onclick="f1.port.value=this.name;">

		</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td></tr>
  </table>
  
</form>
<%

Socket connect=null;
String host = rqUtils.req("ip");
if (rqUtils.digit("btOk"))
{
out.write("<hr>RESPONSO  :<hr><font size=16>");
if (rqUtils.req("port").equals("")) return;
int iCount = Integer.parseInt(rqUtils.req("port"));
try
 {
    connect = new Socket(host, iCount);
    out.write("Port "+iCount+" Active.");
 }
 catch (UnknownHostException e)
 {
     out.write("<font color=orange size=16>Could not resolve the Host Name you specified!");
 }
 catch (IOException e)
 {
      out.write("<font color=orange size=16>Port "+iCount+" Timed Out.");
 }
 finally
 {
 try
 {
 out.write("<hr>");
 connect.close();
 }
 catch (Exception e) {}
 }
 
}
%>
</body>