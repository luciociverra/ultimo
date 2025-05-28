<%@ page import="java.util.*" %>
<%@ page import="base.utility.*" %>
<%
     Runtime rt=Runtime.getRuntime();
	 String memStato= Utils.getNumero((rt.totalMemory() - rt.freeMemory())/1000000,"0")+
			 " su  "+ Utils.getNumero((rt.maxMemory()/1000000),"0");

	 StringBuffer myMail = new StringBuffer();
	 myMail.append("<table border=1 width='98%'><tr><td width='100%' style='background-color:white;color:red'>&nbsp;LCOM STATUS  "+Utils.getDayToday()+ "/" 
	 + Utils.getMonthToday() +"/"+Utils.getYearToday()+"    "+ Utils.getTime() +"</td></tr>");
      myMail.append("<tr style='text-align:center;'><td>"+
                  " (Mb) "+memStato+"</td></tr>");
    
    myMail.append("</table>");
    out.write(myMail.toString()); 
 %>   