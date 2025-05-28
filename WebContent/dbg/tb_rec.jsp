<%@ include file="/main/costanti.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<%@ include file="/main/bean_db.jsp" %>
<%
 String msg_out,str,pagina;
 
 String aLib = sys.getString("currLib");
 String aTable =sys.getString("currTable");
 String tabella =aLib + "." + aTable;
  
 String strWhere = "NOMEWG='" +  request.getParameter("key") + "'";
   str ="SELECT * FROM "+ tabella + " WHERE NOMEWG='"+request.getParameter("key")+"'";
 msg_out="";


 db.setSys(sys);
 db.apri_db("UTENTI");

 
 if (request.getParameter("bt_upd") != null)
  {  
     db.execQuery(str);
    String[] campi = new String[db.columnCount()];
    for (int i=0;i <= db.columnCount()-1;++i)	{
         campi[i]=request.getParameter(db.getNamePos(i+1)).trim();
         }
	db.updateRecordValues(tabella,campi,strWhere);
	response.sendRedirect("listaVoci.jsp"); 	
  } 

//--------------------------- inserimento record

 if (request.getParameter("bt_add") != null)
  {  
    db.execQuery(str);
    str ="INSERT INTO " + tabella+ " VALUES(";
    for (int i=1;i <= db.columnCount();++i)	{
         str +="'"+request.getParameter(db.getNamePos(i)).trim() +"'";
         if  (i < db.columnCount()) str += ",";
         }
 	str += ")";
	db.executeUpdate(str);
	response.sendRedirect("listaVoci.jsp");
  } 

//--------------------------- inserimento record

 if (request.getParameter("bt_del") != null)
  {  
   db.execQuery(str);
   str ="DELETE FROM " + tabella +" WHERE " + strWhere; 
   db.executeUpdate(str);
   response.sendRedirect("listaVoci.jsp");
  } 

//--------------------------- nuovo record

 if (request.getParameter("bt_new") != null)
   pagina=db.getInputNewRecord(aLib,aTable);
else
  { db.execQuery(str);
    pagina = db.getInputRs();  }
%>
<html>

<head>
<style>
.cTitolo {BACKGROUND:red;font-family: MS Sans Serif; font-size: 10 pt; color: #FFFFFF;font-weight: bold;text-align: left}
.cTit {BACKGROUND:red;font-family: MS Sans Serif; font-size: 8 pt; color: #FFFFFF;font-weight: bold;text-align: right}
.cPrd {BACKGROUND:#008080;font-family: MS Sans Serif; font-size: 10 pt; color: #FFFFFF;font-weight: bold;text-align: left}
.cT2 {BACKGROUND: c0c0c0; font-family: Verdana;font-weight: bold;color: red; font-size: 8 pt;text-align: right}
TD {BACKGROUND: #BADCDC; font-family: Verdana; font-size: 8 pt;text-align: left}
</style>
<title></title>
</head>
<body bgcolor="orange" topmargin="0" leftmargin="0">
<FORM NAME='form1' method="POST">
<table border=0 width='100%'><tr><td>
  <table border=0 width='100%' cellspacing="0" cellpadding="2">
  <TR><TD width='100%' CLASS='Ct2'><p align="center"><font size=3><%=msg_out%></font></TD></TR>
  <TR><TD width='100%' CLASS='Ct2'><p align="center">
  <input type="submit" name="bt_upd" value="Update">
  <input type="submit" name="bt_del" value="Delete">
  <input type="submit" name="bt_add" value="New">
  <input type="button" name="bt_cancel" value="Annulla" onClick="javascript:window.history.back();">
  <input type="hidden" name="key" value="<%=request.getParameter("key")%>">
  <input type="hidden" name="table" value="<%%>">
 </TD></TR></TABLE>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
 <%=pagina%>
 </table></TD></TR></TABLE>
  </form><p>
  <%//=db.getPrimaryKeys()%>
</body>