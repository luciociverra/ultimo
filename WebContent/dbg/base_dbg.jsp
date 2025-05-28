<jsp:useBean id="sys" scope="session" class="base.utility.Sys" />
<%@ page import="base.db.*" %>
<%!
String readCookie(String aName,HttpServletRequest req){
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if(cookie.getName().trim().equalsIgnoreCase(aName.trim())) {
        	   return cookie.getValue();
            }
        }
     }
    return "non trovato";
}
%>
<%
  String aut = (String)session.getAttribute("dbg_autorized");
  boolean ok=false;
  try {
       if(aut.equals("SEGURO")) ok=true;
       }
  catch (Exception e) {}
  if(! ok) response.sendRedirect("index.htm");
  
  request.setAttribute("sourcePage", request.getRequestURI()); 
  response.addHeader("Pragma","No-cache");
  response.addHeader("Pragma","No-store");
  response.addHeader("Cache-Control", "must-revalidate"); 
  response.addHeader("Expires", "Mon, 1 Jan 2006 05:00:00 GMT");//in the past  
%>
