<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>

<style>
TD { font-family: Verdana; font-size: 9px; font-weight: normal} 
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title></title>
</head>
<body style=" font-family: Verdana; font-size: 11px; font-weight: normal">
<%
try {
      InetAddress x = InetAddress.getLocalHost();
      String str = x.toString();
      out.write ("IP  :"+ str.substring(str.indexOf("/") + 1));
    }
    catch (Exception e) {
      out.write ("UNDEFINED<br>");
    }
  
try{
          String hostName = InetAddress.getLocalHost().getHostName();
    	  InetAddress addrs[] = InetAddress.getAllByName(hostName);

    	  String myIp = "UNKNOWN";
    	  for (InetAddress addr: addrs) {
    	  out.write ("addr.getHostAddress() = " + addr.getHostAddress()+"<br>");
    	  out.write ("addr.getHostName() = " + addr.getHostName()+"<br>");
    	  out.write ("addr.isAnyLocalAddress() = " + addr.isAnyLocalAddress()+"<br>");
    	  out.write ("addr.isLinkLocalAddress() = " + addr.isLinkLocalAddress()+"<br>");
    	  out.write ("addr.isLoopbackAddress() = " + addr.isLoopbackAddress()+"<br>");
    	  out.write ("addr.isMulticastAddress() = " + addr.isMulticastAddress()+"<br>");
    	  out.write ("addr.isSiteLocalAddress() = " + addr.isSiteLocalAddress() +"<br>");
    	  out.write ("<hr>");

    	  if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
    	  myIp = addr.getHostAddress();
    	  }
    	  }
    	  out.write ("\nIP = " + myIp);

    	  out.write ("");  out.write ("");  out.write ("");
    }
catch(Exception e){}    

%> 

<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" width="100%" id="table1">
<%
 for (Enumeration e = System.getProperties().keys(); e.hasMoreElements();)
 {
    		  String k=(String)e.nextElement();
    		  String v=System.getProperty(k);
 %>
<tr><td width="20%"><%=k%></td><td width="80%"><%=v%></td></tr>
<%  }   %> 

</table>
</body>
</html>
