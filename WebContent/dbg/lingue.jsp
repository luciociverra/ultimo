<%@ include file="base_dbg.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<%@ include file="/main/bean_db.jsp" %>
<%@ page import="com.smi.utility.*" %>
<%
String lista="";
String res="";
if (rqUtils.digit("btSys"))
   {
     sys.setLinguaWeb(rqUtils.req("txtSys"));
   }  

if (rqUtils.digit("btMloff"))
   {
   session.setAttribute("lngMaster","N");
   sys.setProperty("lngMaster","N");
   }  

 if (rqUtils.digit("btMl"))
   {
      session.setAttribute("lngMaster","S");
      sys.setProperty("lngMaster","S");
   }  

if (rqUtils.digit("btLista"))
   {
     lista="../services/listaVoci.jsp";
 }

 if (rqUtils.digit("btReload"))
   {
     Babylon.load(sys);
   }  

 if (rqUtils.digit("btFind"))
   {
    String q="SELECT * FROM "+ sys.getDbTabName("lng") + " WHERE NOMEWG LIKE '%" + rqUtils.req("txtLingua") + "%' " +
             "OR LN00WG LIKE '%" + rqUtils.req("txtLingua") + "%'";
             
    if(db.apri_db("MULTILINGUA"))
	 {
      	db.execQuery(q);  
      	res= "<table cellpadding=0 cellspacing=0 border=1 style='font-family: " +
                    "Verdana; font-size: 8 pt;background-color: White; color: black'>" +	db.getTextRs() + "</table>";
     db.closeQuery();
     db.closeDb();
    }  
  }  
%>
<html>
<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 6.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Lingua</title>
</head>
<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="20" leftmargin="20" background="../images/bg.jpg">
STATO ATTUALE ABILIT. BOTTONI...:<%=sys.getProperty("lngMaster")%><br>
<br>sys.getLinguaGestionale():<%=sys.getLinguaGestionale()%><br>
<br>sys.getLinguaWeb() (LNGXX) :<%=sys.getLinguaWeb()%><br>
<br><%=Babylon.translate("lng_righeordine",sys.getLinguaWeb(),"PIPPO",sys)%>
<br>
<form method="POST" name=form0>
imposta LNGXX a <input type="text" size=2 name="txtSys"><input type="submit"  value="imposta" name="btSys">

</form>

<form method="POST" name=form1>
  <input type="submit"   value="Mostra i campi multilingua come bottoni" name="btMl">
&nbsp;&nbsp;
   <input type="submit"   value="Mostra i campi multilingua normalmente" name="btMloff">
   <hr>
   <input type="submit"   value="Ricarica il Dizionario in memoria       " name="btReload">
   <input type="submit"   value="Cerca chiave" name="btFind"><input type="text"   value="" name="txtLingua" size=20><hr>
   &nbsp;<input type="submit"   value="Mostra lista voci " name="btLista" "></form>
<%=res%>
<iframe id="iframe" src="<%=lista%>" name="iframe" target="_self" width="100%" height="300"></iframe>
</body>

</html>