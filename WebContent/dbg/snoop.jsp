<html>
<!--
  Copyright (c) 1999 The Apache Software Foundation.  All rights 
  reserved.
-->

<body bgcolor="white">
<h1> Request Information </h1>
<font size="4">
JSP Request Method: <%= request.getMethod() %>
<br>
Request URI: <%= request.getRequestURI() %>
<hr>
<%
String prot=request.getProtocol().startsWith("HTTP/") ?"http://":"https://";
String a=prot+request.getServerName()+":"+ request.getServerPort();

out.write(a);
%>
<hr>
Request Protocol: <%= request.getProtocol() %>
<br>
Servlet path: (request.getServletPath())<%= request.getServletPath() %>
<br>
Path info: <%= request.getPathInfo() %>
<br>
Path translated: <%= request.getPathTranslated() %>
<br>
Query string: (request.getQueryString())<%= request.getQueryString() %>
<br>
Content length: <%= request.getContentLength() %>
<br>
Content type: <%= request.getContentType() %>
<br>
Server name: <%= request.getServerName() %>
<br>
Server port: <%= request.getServerPort() %>
<br>
Remote user: <%= request.getRemoteUser() %>
<br>
Remote address: <%= request.getRemoteAddr() %>
<br>
Remote host: <%= request.getRemoteHost() %>
<br>
Authorization scheme: <%= request.getAuthType() %> 
<br>
Locale: <%= request.getLocale() %>
<hr>
The browser you are using is <%= request.getHeader("User-Agent") %>
<hr>
Sessione: <%=session.getId()%>
<hr>

</font>
</body>
</html>