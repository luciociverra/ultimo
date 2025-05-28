<%@ page import="java.util.*" %>
<%
boolean isMyPc=System.getProperty("user.home").indexOf("lcive") > 0;
if(isMyPc)
{
  session.setAttribute("dbg_autorized","SEGURO");
  response.sendRedirect("dbg.jsp");
  return;
}

Calendar currentDate = Calendar.getInstance();
int oggi= currentDate.get(currentDate.DAY_OF_MONTH);
int month = (currentDate.get(Calendar.MONTH) + 1);
int year = currentDate.get(Calendar.YEAR);
String  currPwd="nexus"+String.valueOf(oggi);

String aut = (String)session.getAttribute("dbg_autorized");
String a="";
String p="";
String msg="";
String cc="";

Cookie cookie = null;
Cookie[] cookies = null;
cookies = request.getCookies();

if( cookies != null ) {
   for (int i = 0; i < cookies.length; i++) {
      cookie = cookies[i];
      String name=cookie.getName();
      String value=cookie.getValue();
      if(name.equals("dbg_autorized") && value.equals(currPwd))
      {
    		session.setAttribute("dbg_autorized","SEGURO");
    	    response.sendRedirect("dbg.jsp");
    	    return;
      }
   }
} 

try {
       if(aut.equals("SEGURO")) {
         response.sendRedirect("dbg.jsp");
       }
     }
catch (Exception e) {}
//
if (request.getParameter("btOk") !=null)
{
 a=request.getParameter("enter");
 p="nexus"+String.valueOf(oggi);
 if (a.equalsIgnoreCase(p))
    {
	 Cookie lngInfo = new Cookie("dbg_autorized", p);
	 lngInfo.setMaxAge(60*60*24); 
	 response.addCookie(lngInfo);
	 
	 session.setAttribute("dbg_autorized","SEGURO");
     response.sendRedirect("dbg.jsp");
     return;
    } 
 else
   {
     msg="<font color='yellow' size=3 face='Verdana'><b>The magic word,please</font>"; 
   }       
}  
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>DEBUG</title>
<base target="_top">
</head>
<SCRIPT LANGUAGE="javascript">
<!--
function keyp()
{
if (event.altKey && event.ctrlKey && event.keyCode==66)
{   self.location="/dbg/";
    return;
}

if (event.keyCode==13) {
    self.document.f1.submit();
    }
}
//-->
</script>
<body bgcolor="#000000" onload="self.document.f1.enter.focus();" onKeyDown="keyp();">
<form action="index.jsp" method="POST" name="f1">
<p><input type="password" size="20" name="enter" tabindex="1" value="<%=cc%>"><input type="submit" name="btOk" value="    OK    " tabindex="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<b><font color="#FFFF00" size="2" face="Arial"><%=oggi%>/<%=month%>/<%=year%></font></b></p>
</form>
<br><br><br><%=msg%>
</body>
</html>