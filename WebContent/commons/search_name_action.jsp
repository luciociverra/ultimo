<%@ include file="../base.jsp" %>
<%
debugRequest(sys,request);
String pgm=req(request,"pgm"); 

if(pgm.equals("selectPeople"))
{
	response.flushBuffer();
	out.clear();
	String like=req(request,"like");
	response.setContentType("application/json");
	String sql="SELECT id,p_surname,p_name,born_location as loc,substr(CAST(born_date AS VARCHAR),1,4) as data_nascita  "+
	           "FROM people where UPPER(p_surname) LIKE UPPER('%"+like+"%') OR UPPER(p_name) LIKE UPPER('%"+like+"%')";
	RsTable rs=getRsTable(sys,sql);
	sys.debugWhite("records:->"+rs.size());
	while(rs.next()) {
		 if(rs.getFieldInt("born_dateflag")==2)
		   rs.getCurrentRow().put("data",rs.getField("data").substring(0,4));
		 else
		   rs.getCurrentRow().put("data",Utils.getDateGMA(rs.getField("data")));	 
	}
	String respOut=gson.toJson(rs.getVectorRighe());
	sys.debugWhite(respOut);
	out.write(respOut);
}
if(pgm.equals("selectTies"))
{
	response.flushBuffer();
	out.clear();
	String like=req(request,"like");
	response.setContentType("application/json");
	String sql="SELECT id,p_surname,p_name,born_location as loc,substr(CAST(born_date AS VARCHAR),1,4) as data  "+
	           "FROM people where UPPER(p_surname) LIKE UPPER('%"+like+"%') OR UPPER(p_name) LIKE UPPER('%"+like+"%')";
	RsTable rs=getRsTable(sys,sql);
	sys.debugWhite("records:->"+rs.size());
	while(rs.next()) {
		 if(rs.getFieldInt("born_dateflag")==2)
		   rs.getCurrentRow().put("data",rs.getField("data").substring(0,4));
		 else
		   rs.getCurrentRow().put("data",Utils.getDateGMA(rs.getField("data")));	 
	}
	String respOut=gson.toJson(rs.getVectorRighe());
	sys.debugWhite(respOut);
	out.write(respOut);
}
%>