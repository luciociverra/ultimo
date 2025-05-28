<%@ include file="/main/costanti.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<%@ include file="/main/bean_db.jsp" %>
<%
String bg,lastProd,numCols,tabella,sistema;
String imgLink = "<img src='..\\images\\sel.gif' border=0>";
tabella = request.getParameter("table");
sistema = request.getParameter("system");

db.setSys(sys);
db.apri_db(sistema);
db.execQuery("SELECT * FROM "+ tabella);
%>
<HTML>
<head>
<style>
.cPrd {BACKGROUND:red;font-family: MS Sans Serif; font-size: 10 pt; color: #FFFFFF;font-weight: bold;text-align: left}
.cTitle{BACKGROUND:orange;font-family: MS Sans Serif; font-size: 10 pt; color: #FFFFFF;font-weight: bold;text-align: left}
.cT1 {BACKGROUND: #BADCDC; font-family: Verdana; font-size:8 pt;}
.cT2 {BACKGROUND:#c0c0c0;font-family: Verdana; font-size: 8 pt;}
</style>
</head>
<BODY topmargin="0" leftmargin="0" bgcolor="white"><font face="Verdana" size="2"><b>
<%=tabella%>&nbsp;&nbsp;&nbsp;<a href="../services/scheda.jsp?bt_new=1" target="basso">NEW RECORD</a>
</font>
<%%>
<hr>
<div align ='left'><table border=0 width='100%'>
<table border=0><TR CLASS='cPrd'><td></td>
<%

 for (int i=1;i <= db.columnCount();++i)
		{out.write("<td width=" + db.getFieldSize(i) +">" + db.getNamePos(i) + "</td>");} 
%>
</tr>
<%
 int swcolor=0;
 for (int z=1;z <=15;++z)
 
 {
 
 if (db.nextRecord())
 {
  if (swcolor==0) 
      { bg ="class='cT1'";
        swcolor=1; }
   else
      { bg ="class='cT2'";
      swcolor=0;	}
%>      
<tr <%=bg%>><td width='1' align='left'><font face='Verdana' size=1>
<a href="scheda.jsp?key=<%=db.getFieldPos(1)%>" target="_self"><%=imgLink%></a></td>
<%
  for (int i=1;i <= db.columnCount();++i) {
	out.write("<td align='left' vAlign='top' width=" + db.getFieldSize(i) +">" + db.getFieldPos(i) +"</td>");
	}
%>
</tr> 
<%
 }   
 }   
db.closeQuery();
db.closeDb();
%>
</TABLE>
 
</BODY>