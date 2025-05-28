<html>
 <style>
 </tyle>

<body bgcolor="white" style="font-family: Verdana; font-size: 12px; font-weight: normal">
<h3> Request Information </h3>
 
JSP Request Method: </style>
<%= request.getMethod() %>
<br>
Request URI: <%= request.getRequestURI() %>
<br>
Request Protocol: <%= request.getProtocol() %>
<br>
Servlet path: <%= request.getServletPath() %>
<br>
Path info: <%= request.getPathInfo() %>
<br>
Path translated: <%= request.getPathTranslated() %>
<br>
Query string: <%= request.getQueryString() %>
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