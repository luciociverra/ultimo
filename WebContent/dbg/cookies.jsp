<%@ include file="base_dbg.jsp" %>
<style>
  body {font-family: Tahoma; font-size: 8pt; font-weight: bold;background-color:#000000;color:#00FF00}
</style>
<%
 if(request.getParameter("btDel") != null){
	 
		    Cookie[] cookies = request.getCookies();
		    for (Cookie cookie : cookies) {
		    	if(cookie.getName().equals(request.getParameter("cName")))
		         { 
		    		cookie.setMaxAge(0);
		         }
		          
		    }
	 
 }
if(request.getParameter("btADD") != null){
    Cookie cookie = new Cookie(request.getParameter("cName"),request.getParameter("cValue"));
    cookie.setPath("/");
	response.addCookie(cookie);
}


%>
<body>
-1 cookie legato alla sessione
<table border=1>
  <tr>
  <td></td>
   <td>Nome</td><td>Valore</td><td>Dominio</td>
   <td>getMaxAge() returns the maximum age in seconds</td>
   <td>setPath() instruct browser to send cookie to a particular resource only.</td>
 </tr>

<%
Cookie[] cookies = request.getCookies();
if (cookies != null) 
{
    for (Cookie cookie : cookies) {
%>
   <tr>
   <td><button type="button" onclick="del('<%=cookie.getName()%>');">DEL</button>
   </td>
   <td><%=cookie.getName()%></td><td><%=cookie.getValue()%></td>
   <td><%=cookie.getDomain()%></td><td><%=cookie.getMaxAge()%></td>
   <td><%=cookie.getPath()%></td>
   </tr>
 <%       
    }
}
%>
</table>
<hr>
locali da javascript<br>
<textarea name="locali" id="locali" cols=70 rows=15 style="width:900px"></textarea>
<form>
<button type="submit" name="btADD">ADD</button>
<input type="text" size="30" name="cName"><input type="text" size="30" name="cValue">

</form>
<script>
 function del(aName){
	 self.document.location="cookies.jsp?btDel=1&cName="+aName;
 }
 var allC=document.cookie.split(';').join(';\r\n');
 document.getElementById("locali").value=allC;
</script>
</body>