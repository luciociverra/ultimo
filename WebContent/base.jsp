<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isThreadSafe="false" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.google.gson.*" %>
<%@ page import="base.utility.*" %>
<%@ page import="base.db.*" %>
<%@ page import="base.sp.*" %>
<%@ page import="base.servlet.*" %>
<jsp:useBean id="sys" scope="session" class="base.utility.Sys" />
<%
 boolean creaSys=false;
 HttpSession test_ses = request.getSession (false);
 if (null == test_ses) { creaSys=true; }

 if (null == test_ses.getAttribute("sys")) {
	creaSys=true;
	System.out.println("MANCA SYS DALLA SESSIONE");
 } 
 if(creaSys) {
	  System.out.println("CREO SYS");
	  Sys newSys=new Sys();
	  session = request.getSession();	
	  session.setAttribute("sys",newSys);
	  sys=newSys;
  }
%>
<%!
Sys xsys;
PageContext pageCnt;
HttpServletRequest rq;
HttpServletResponse rs;

void setSys(Sys s)
{xsys=s;
}
void setPageContext(PageContext pageC)
{pageCnt=pageC;
 rq=(HttpServletRequest)pageC.getRequest();
 rs=(HttpServletResponse)pageC.getResponse();
}

void debug(String aMsg)
{xsys.debug(aMsg,"pink");
}
void error(String aMsg)
{xsys.error(aMsg);
}
String getProp(String aKey)
{ return xsys.getProp(aKey);
}
String getProp(String aKey,String aDef)
{ return xsys.getProp(aKey,aDef);
}
String getPage(String aPage)
{ return xsys.getPage(aPage);
}
String getServlet(String aPage)
{ return xsys.getServlet(aPage);
}

String  notNull(String aValue)
{ return  xsys.notNull(aValue);
}
String  zeroIfNull(String aValue)
{ return  String.valueOf(xsys.zeroIfNull(aValue));
}
boolean  notBlank(String aValue)
{ return  xsys.notBlank(aValue);
}
boolean  notZero(String aValue)
{ return  xsys.notZero(aValue);
}
boolean  isBlank(String aValue)
{ return notNull(aValue).equals("");
}
String  def(String aValue,String aDef)
{ return isBlank(aValue)?aDef:aValue;
}
String  checkIf(String aValue,String aDef)
{ return aValue.equals(aDef)?"checked":"";
}
String isSelected(String valFisso, String valMobile) {
	if (valFisso.equalsIgnoreCase(valMobile))
		return " selected ";
	return "";
}
String fromValues(String primo, String secondo) {
	try{
		if (! isBlank(primo)) return primo;
		return secondo;
	} catch(Exception e){
		return "";
	}
}
String readonly(String aKey)
{if (xsys.haPermesso(aKey)) 
    return "";
 else   
    return " readonly ";
}
public boolean digit(HttpServletRequest myReq, String aKey) {
	return null != myReq.getParameter(aKey);
}

public String req(HttpServletRequest myReq, String aKey) {
	try {
		return notNull((String) myReq.getParameter(aKey));
	} catch (Exception e) {
		return "";
	}
}
public int reqInt(HttpServletRequest myReq, String aKey) {
	try {
		return Integer.parseInt(((String) myReq.getParameter(aKey)));
	} catch (Exception e) {
		return 0;
	}
}
// ritorn http://www.
public String getUrl(HttpServletRequest request)
{
   String prot=request.getProtocol().startsWith("HTTP/") ?"http://":"https://";
   return prot+request.getServerName()+":"+ request.getServerPort();
}
void redirect(String aPage)
{try { rs.sendRedirect(aPage);} catch(Exception e) {}
}
String hoInput(String aKey)
{if (xsys.haPermesso(aKey)) 
    return "true";
 else   
    return "false";
}   
boolean haPermesso(String aAbilitaz)
{ 
 return xsys.haPermesso(aAbilitaz);
}    
String traduci(String aKey,String aDefault)
{ 
 try
 {return xsys.traduci(aKey,aDefault); }
 catch(Exception e){ return aDefault;}
}

String getPath()
{ 
	return rq.getContextPath();
} 

String pApic(String aStr)
{ 
	return "'" + aStr + "'";
} 

Map<String, String> getHeadersInfo(HttpServletRequest req ) 
{
	Map<String, String> map = new HashMap<String, String>();
	Enumeration headerNames = req.getHeaderNames();
	while (headerNames.hasMoreElements()) {
		String key = (String) headerNames.nextElement();
		map.put(key, req.getHeader(key));
	}
   return map;
}

Map<String, String> getRequestParams(HttpServletRequest req ) 
{
	Map<String, String> map = new HashMap<String, String>();
	Enumeration reqNames = req.getParameterNames();
	while(reqNames.hasMoreElements()){
	        String parameterName = (String)reqNames.nextElement();
	        map.put(parameterName, req.getParameter(parameterName).trim());
	}
    return map;
}
String allParams(HttpServletRequest req ) 
{
	String ret="";
	Enumeration reqNames = req.getParameterNames();
	while(reqNames.hasMoreElements()){
	        String parameterName = (String)reqNames.nextElement();
	        ret+=parameterName+"="+req.getParameter(parameterName).trim()+"&";
	}
    return Utils.eliminaLastChar(ret);
}
void debugRequest(Sys sys,HttpServletRequest req)
{ 
	Enumeration reqNames = req.getParameterNames();
	while(reqNames.hasMoreElements()){
	        String parameterName = (String)reqNames.nextElement();
	        sys.debugWhite("PARAM-->" +parameterName+"  :  <font color='yellow'>"+ req.getParameter(parameterName)+"</font>");
	}
}
void debugMap(Sys sys,Map<String,String> map)
{ 
	Utils.debugMap(sys,map);
}
void debugUrl(Sys sys,HttpServletRequest req)
{ 
	sys.debugWhite("<a href='"+req.getRequestURI() +"?"+allParams(req)+"' target='new'>URL</a>");
}
String pathBack(String aPath){
	try{
		String ret="";
		for (int z = 0; z < aPath.length(); ++z) {
			String c= String.valueOf(aPath.charAt(z));
            if(c.hashCode()==92)
            	ret+="/";
            else
		        ret+=c; 
		} 
		return ret;
	}
	catch (Exception e) {
	    return aPath;
	}
}
boolean deleteCookie(Sys sys,String aName,HttpServletRequest req, HttpServletResponse resp){
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if(cookie.getName().equalsIgnoreCase(aName)) {
	        	cookie.setValue("");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            resp.addCookie(cookie);
	            sys.debug(aName+ "  cookie cancellato ");
	            return true;
	            }
	        }
	    }
  sys.debug(aName+ "  cookie non trovato !");
  return true;
}
void deleteAllCookie(Sys sys,HttpServletRequest req, HttpServletResponse resp){
    Cookie[] cookies = req.getCookies();
    for (Cookie cookie : cookies) {
         cookie.setMaxAge(0);
         resp.addCookie(cookie);
       }
}
String readCookie(Sys sys,String aName,HttpServletRequest req){
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
        	//sys.debug("name:"+cookie.getName()+"_");
            if(cookie.getName().trim().equalsIgnoreCase(aName.trim())) {
               //sys.debug("Trovato:"+cookie.getValue());	
        	   return cookie.getValue();
            }
        }
     }
    sys.debug(aName+ "  cookie non trovato !");
    return "";
}
 
void reaAllCookie(Sys sys,HttpServletRequest req){
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
        	sys.debug("name:"+cookie.getName()+" = "+cookie.getValue());
        }
    }    
}
RsTable getRsTable(Sys sys,String sql)
{ 
	DbConn db = new DbConn(sys);
	db.connect("SP_DB");
    return db.getRsTable(sql,true);
} 
RsTable getRsTable(Sys sys,String dbDef,String sql) 
{ 
	DbConn db = new DbConn(sys);
	db.connect(dbDef);
    return db.getRsTable(sql,true);
} 
 
String json(Sys sys,HttpServletResponse myResp, Object esito)
{
 try
 {	
  myResp.flushBuffer();
  myResp.setContentType("application/json");
  Gson gson = new GsonBuilder().create();
  sys.debug(gson.toJson(esito));
  return gson.toJson(esito);
  }
  catch(Exception e) {
		sys.error(e);
		return "";
	}
 }
 
void outS(String t){
	System.out.println(t);
}
String encodeJS(String aValue) {
	try {
		aValue=aValue.replaceAll("\r\n"," "); // acapo
		String a, c = "";
		for (int z = 0; z < aValue.length(); ++z) {
			a = String.valueOf(aValue.charAt(z));
			if (a.equals("'")) {
				c += "\\'";
				continue;
			}
			c += a;
		}
		return c;
	} catch (Exception e) {
		return "";
	}
}

String getMsgEsito(String msg)
{
	if (msg.equals("edit_ok")) 
		return "<div class='alert alert-success' role='alert'>Record updated</div>";	
	if (msg.equals("add_ok")) 
		return "<div class='alert alert-success' role='alert'>Record added</div>";	
	if (msg.equals("delete_ok")) 
		return "<div class='alert alert-success' role='alert'>Record deleted</div>";
	return msg;	
}
%>
<%
/* Inizio elaborazione scriptlet comune  */
setSys(sys);
setPageContext(pageContext);
out.write ("<!-- " + request.getRequestURI() + " -->");
request.setCharacterEncoding("UTF-8");
response.setHeader("Pragma","No-cache");
response.setDateHeader("Expires",0);
response.setHeader("Cache-Control","No-cache");

/* Inizio elaborazione scriptlet comune  */
 String basePath = request.getContextPath() + "/"; // per CSS e JS se root diventera' /
 // usare per immagini esempio src="<%=basePath im/xx.gif
 String currPath = request.getRequestURI().substring(0,request.getRequestURI().lastIndexOf("/") + 1); // /dg/
 String currPage = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1); // /dg/
 String tmpSiteFolder=request.getRealPath("/").replace('\\','/')+"tmp/";
 String baseUrl=request.getRequestURL().toString();
	baseUrl=baseUrl.substring(0,baseUrl.indexOf(basePath))+basePath;
 String currUrl=currPath+currPage;	
 //
 boolean isMyPc=System.getProperty("user.home").indexOf("lcive") > 0;
 boolean isMyUser=sys.getProperty("userlogin").equals("lciverra@gmail.com") ||
		          sys.getProperty("userlogin").equals("lciver@gmail.com");
 // 
 Gson gson = new GsonBuilder().create();
 sys.debug("<u>[  "+currUrl+"  ]</u>","cyan");
 out.write("<!-- " + request.getRequestURI() + " -->");
%>
<script>
var isMyPc=<%=isMyPc%>;
var basePath=<%=basePath%>
</script>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="icon" href="images/logo_home.png">
  <title>Sui Passi</title>
    
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <link rel="stylesheet" href="<%=basePath%>js/style.css">
  <link rel="stylesheet" href="<%=basePath%>js/sp.css">
  <!-- Select2 CSS -->
 
  <link href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" rel="stylesheet">
</head>
<!-- end of footer -->
    <!-- Bootstrap 5 JS with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery (required for Select2) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Cropper.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.13/cropper.min.js"></script>
    <!-- Axios -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

  <script src="<%=basePath%>js/script.js"></script>
 
   
