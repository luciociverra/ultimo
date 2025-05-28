<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="base.utility.*" %>
<%!
public String eliminate="";
String fs = System.getProperty("file.separator");
public void delTree(String dir) 
{
  try
  {
        int cont=0;
        File currentDir = new File(dir);
        String[] temp = currentDir.list();
        for(int i = 0; i < temp.length; i++) {
           String aName = temp[i];
           eliminate+=aName+"<br>";
           File f = new File(dir + fs + aName);
           f.delete();
           cont++;
         }
     }
  catch(Exception e)
  {}
}

String struttura="";
public void listDir(File dir, String indent) {
	     String apic = "\"";
	      File[] dirs = dir.listFiles();
	    for (File f : dirs) {
	      if (f.isDirectory()) {
	        struttura+=indent + "<a href='javascript:del("+apic + Utils.replaceChar(f.getAbsolutePath(),"\\","/") +apic +")'>"+f.getName()+"</a><br>";
	        listDir(f, indent + "&nbsp;&nbsp;&nbsp;");
	      }
	    }
  }
%>
<%
struttura="";
eliminate="";
String headElimino="";
if(request.getParameter("deltree")!=null)
{ 
  headElimino="Eliminate da:<br><i>"+request.getParameter("deltree")+"</i><br>";
  delTree((String)request.getParameter("deltree"));
}

String fsep= System.getProperty("file.separator");
String myPath=request.getContextPath();
if(myPath.equals("")) 
   myPath=fsep+"_";
else
   myPath=Utils.replaceChar(myPath,"/",fsep);
String aFolder= System.getProperty("catalina.home") + fsep+"work"+fsep+"Catalina"+fsep+"localhost"+myPath;
listDir(new File(aFolder),"&nbsp;");
%>
<html>

<head>
<title>Who</title>
</head>
<body style="font-family: Tahoma; font-size: 8pt; font-weight: bold" bgcolor="#000000" text="#00FF00" topmargin="0" leftmargin="0" background="../images/bg.jpg">
<left>
<table border=1 style="font-family: Tahoma; font-size: 8pt; font-weight: bold" width="400">
<tr><td bgcolor="#FFFFFF"><font color="#FF0000"><%=aFolder%></font> </td></tr>
<tr><td bgcolor="#FFFFFF"><font color="#FF0000"><%=struttura%></font> </td></tr>
</table>
<%
if(! headElimino.equals("")) { %>
<div style="position: absolute;visibility: visible;left:402px;top:0px;z-index: 1; width:200px; 
 border-collapse: collapse; border: 1px ridge #000000; background-color:#FFFFFF" id='divDati'>
<table border=1 style="font-family: Tahoma; font-size: 8pt; font-weight: bold" width="200">
<tr><td bgcolor="#FFFF99"><font color="black"><%=headElimino%><%=eliminate%></font> </td></tr>
</table>
</div>
<%}%>
</body>
<script language="javascript">
function del(aFolder)
{
 self.location="delete_work.jsp?deltree="+ aFolder ;
}
</script>
</html>