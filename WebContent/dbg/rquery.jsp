<%@ include file="base_dbg.jsp" %>
<%
if(sys.getAzienda().equals(""))
   out.write("<hr>SYS non inizializzato</br>");
DbConn db = new DbConn(sys);
String res="";
String src = "DBDEFS";
String sql = "SELECT * FROM ";
String tracciato="";
boolean qryExec=false;
//
if (request.getParameter("txtQuery") !=null)
    sql=request.getParameter("txtQuery");
if (request.getParameter("txtSystem") !=null)
    src=request.getParameter("txtSystem");	

if (request.getParameter("btQuery") !=null)
   {
    if(db.connect(src))
	 {
	  	db.execQuery(sql);  
      	res=  	db.getTextRs()  ;
		qryExec=true;			
       db.closeQuery();
       db.closeDb();
     }
   }  
else
if (request.getParameter("btEx") !=null)
   {
    if(db.connect(src))
	 {
	    res=db.getConnStatus()+"<br>"; 
	  	res += "Nr. records updates " + db.executeUpdate(sql);
		qryExec=true;
        db.closeDb();
     }
   }  
else
if (request.getParameter("btExtrans")!=null)
   {
    if(db.connect(src))
	 {
	    db.startTrans();  
	  	res= "Nr. records updates " + db.executeUpdate(sql);
		qryExec=true;
	    db.commitTrans();
        db.closeDb();
     }
   }
 
if(request.getParameter("key") != null && (! qryExec))
{
  sql=sys.getQueryDbg(request.getParameter("key"));  
  src=request.getParameter("src"); 
}

String excel_file="";
if (request.getParameter("toExcel") !=null)
   {
    if(db.connect(src))
	 {
	 
      	RsTable rst=db.getRsTable(sql,true)  ;
		RsToExcel rste= new RsToExcel();
		excel_file=rste.toExcel("c:/appo/allan/",rst);
      	qryExec=true;			
     }
   } 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>Query</title>
<!--  jquery-3.5.0.js -->
<style>
textarea, select, input {font-family: Verdana,Tahoma; font-size: 9pt;}
</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="../js/jquery.table2excel.js"></script>

<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0">
<form method="POST" name="f1">
  DB Definition :<input type="text" name="txtSystem" size="15" value="SP_DB">&nbsp;&nbsp;Vis.tracciato record: &nbsp;<input type="text" name="txtTracciato" size="20" value="<%=tracciato%>"><input type="submit" value="View" name="btTracciato">
  <input type="checkbox" name="toExcel" value="S">Esporta in excel
  <br>
  <textarea rows="6" name="txtQuery" cols="150"><%=sql%></textarea></p>
  <input type="submit" value="Query" name="btQuery">&nbsp;&nbsp;&nbsp;
  <input type="submit" value="Update" name="btEx">&nbsp;&nbsp;&nbsp;
<input type="submit" value="Update with transaction" name="btExtrans">
<input type="text" size=30  readonly value="FETCH FIRST 1 ROW ONLY(AS400)"><button onclick="f1.txtQuery.value+=' FETCH FIRST 10 ROW ONLY';return false;">+</button> tables?
<input type="text" size=30  readonly value="LIMIT 1 (MYSQL)"><button onclick="f1.txtQuery.value+=' LIMIT 1';return false;">+</button> tables?
 
</form>
<table class='table2excel'  cellpadding=0 cellspacing=0 border=1 style='font-family: Verdana; font-size: 9pt;background-color: White; color: black'>
<%=res%>
</table>
<hr><font color='red'><%=db.str_errori%><hr>  
<button class="exportToExcel">Export to XLS</button>
</body>
<script language='javascript'>
var preQry = new Array();

$(function() {
	$(".exportToExcel").click(function(e){
		 var table = $(this).prev('.table2excel');
		 alert(table.length);
		if(table && table.length){
			var preserveColors = (table.hasClass('table2excel_with_colors') ? true : false);
			$(table).table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: "Export" + new Date().toISOString().replace(/[\-\:\.]/g, "") + ".xls",
				fileext: ".xlsx",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true,
				preserveColors: preserveColors
			});
		}
	});
	
});
 </script>
</html>