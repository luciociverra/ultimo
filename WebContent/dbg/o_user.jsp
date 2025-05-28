<%@ include file="/main/costanti.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
 
<%@ page import="com.smi.db.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>utente</title>
<style>
TD { font-family: Verdana; font-size: 12px; font-weight: bold} 
</style>
</head>
<body>

<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" width="100%" id="table1">
 
<tr><td>-</td><td>-</td></tr>

<tr><td>getLoginName</td><td><%=myUser.getLoginName()%></td></tr> 
<tr><td>getCodiceGestionale</td><td><%=myUser.getCodiceGestionale()%></td></tr> 
<tr><td>getCodiceAgenteGestionale()</td><td><%=myUser.getCodiceAgenteGestionale()%></td></tr>  

<tr><td>getTipoOrdine()</td><td><%=myUser.getTipoOrdine()%></td></tr>  
<tr><td>getRagioneSociale()</td><td><%=myUser.getRagioneSociale()%></td></tr>  
<tr><td>getIndirizzo()</td><td><%=myUser.getIndirizzo()%></td></tr> 
<tr><td>getListino()</td><td><%=myUser.getListino()%></td></tr> 
<tr><td>getDescLinguaWeb()</td><td><%=myUser.getDescLinguaWeb()%></td></tr> 
<tr><td>getFla1()</td><td><%=myUser.getFla1()%></td></tr> 
<tr><td>getFla2()</td><td><%=myUser.getFla2()%></td></tr> 
<tr><td>getFla3()</td><td><%=myUser.getFla3()%></td></tr> 
<tr><td>getFla4()</td><td><%=myUser.getFla4()%></td></tr> 
<tr><td>getListaMagazzini()</td><td><%=myUser.getListaMagazzini()%></td></tr> 

<tr><td>getCodiceLinguaWeb()</td><td><%=myUser.getCodiceLinguaWeb()%></td></tr> 

<tr><td>LINGUA VIDEO Corrente !! getLinguaGestionale()</td><td><%=sys.getLinguaGestionale()%></td></tr>  

<tr><td>getDescLinguaWeb()</td><td><%=myUser.getDescLinguaWeb()%></td></tr> 

<tr><td>getCodiceLinguaGestionale()</td><td><%=myUser.getCodiceLinguaGestionale()%></td></tr> 
<tr><td>getDescLinguaGestionale()</td><td><%=myUser.getDescLinguaGestionale()%></td></tr> 
<tr><td>isCliente()</td><td><%=myUser.isCliente()%></td></tr> 
<tr><td>isAgente()</td><td><%=myUser.isAgente()%></td></tr> 




<tr><td>&nbsp;</td><td>&nbsp;</td></tr> 
<tr><td>myUserLogon.getRagioneSociale()</td><td><%=myUserLogon.getRagioneSociale()%></td></tr> 
<tr><td>myUserLogon.getTipoAbilitazioni()</td><td><%=myUserLogon.getTipoAbilitazioni()%></td></tr> 

<tr><td>myUser.getTipoAbilitazioni()</td><td><%=myUser.getTipoAbilitazioni()%></td></tr> 
<tr><td>Abilitazione vis sotto-agenti?</td><td><%=sys.getProp("/misc/usa_sottoagenti")%></td></tr> 
<tr><td>myUser.getElencoSottoAgenti()</td><td><%=myUser.getElencoSottoAgenti()%></td></tr> 
<% if(myUser.getSuperiori().size() > 0) { %>
<tr><td>myUser.getSuperiori().get(0).getLoginName()</td><td><%=myUser.getSuperiori().get(0).getLoginName()%></td></tr> 
<%} else {%>
<tr><td>myUser.getSuperiori()</td><td>Struttura utenti superiori vuota</td></tr> 

<%} %>

</table>
<hr>
PERMESSI:<br><font face ="Verdana" size=1><b>
<%

try{
Properties p =  sys.getPermessi();
 for (Enumeration e = p.keys() ; e.hasMoreElements() ;) {
         out.write((String)e.nextElement()+"<br>");
     }
} catch (Exception e)
{ out.write("PERMESSI NON CARICATI"); }
%>
</b></font>

</body>

</html>
