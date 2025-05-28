<%@ include file="base_dbg.jsp" %>
<%@ include file="/main/bean_base.jsp" %>
<%@ page import="com.ibm.as400.access.*" %>
<%@page import="java.io.IOException"%>
<%@ page import="com.smi.webutils.*" %>
<%!public String run(String systemIp, String user, String password,
      String mainProgram, String inputParam, boolean convert) throws Exception {

    int INPUT_MAXLENGTH = 8192;
    int OUTPUT_MAXLENGTH = 8192;

    String outputParam = "";

    AS400 as400 = new AS400(systemIp, user, password);
    ProgramParameter[] parmList = new ProgramParameter[2];
    
    AS400Text parmConverter = new AS400Text (INPUT_MAXLENGTH);

    if (convert){
      parmList[0] = new ProgramParameter(parmConverter.toBytes(inputParam),INPUT_MAXLENGTH);
      parmList[1] = new ProgramParameter(parmConverter.toBytes(""),INPUT_MAXLENGTH);      
    }
    else {
      byte[] parmIn = new byte[INPUT_MAXLENGTH];
      for (int i = 0; i <= inputParam.length() - 1; ++i) {
        parmIn[i] = (byte) inputParam.charAt(i);
      }
      parmList[0] = new ProgramParameter(parmIn);    
      parmList[1] = new ProgramParameter(OUTPUT_MAXLENGTH);
    }

    ProgramCall pgm = new ProgramCall(as400, QSYSObjectPathName.toPath("%LIBL%", mainProgram ,"PGM"), parmList);

    try {
      if (pgm.run() != true) {
        AS400Message[] messageList = pgm.getMessageList();
        StringBuffer messages = new StringBuffer();
        for (int i = 0; i < messageList.length; i++) {
          messages.append(messageList[i].toString() + "\n");
        }
        throw new Exception(messages.toString());
      } else {
        if (convert)
          outputParam = (String)parmConverter.toObject(parmList[1].getOutputData());
        else
          outputParam = new String(parmList[1].getOutputData());
        as400.disconnectService(AS400.COMMAND);
      }
    } catch (ErrorCompletingRequestException e) {
      throw new Exception(e.getMessage());
    } catch (ObjectDoesNotExistException e) {
      throw new Exception(e.getMessage());
    } catch (AS400SecurityException e) {
      throw new Exception(e.getMessage());
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    } catch (InterruptedException e) {
      throw new Exception(e.getMessage());
    }

    return outputParam;

  }%>
<%
String nrSerie="";

String baseDataSource = "/iseries-connections/system[system-name='JCAL']";

String ip = sys.getProp(baseDataSource+"/ip-address");
String userName = sys.getProp(baseDataSource+"/username");
String pwd = CryptoUtils.decrypt(sys.getProp(baseDataSource+"/password")); 
String mainProgram = sys.getProp(baseDataSource+"/iface-protocol-pgm");

String inputParam="";
String outputParam="";
String errorMessage="";
boolean convert = false;

if (! (request.getParameter("bt") == null)) {
  
  if (request.getParameter("convert")!=null)
    convert = true;
  ip=request.getParameter("ip");
  userName=request.getParameter("userName");
  pwd=request.getParameter("pwd");
  mainProgram =request.getParameter("mainProgram");
  inputParam=request.getParameter("inputParam");
  
	 try{
	   outputParam = run(ip,userName,pwd,mainProgram,inputParam, convert);
	 }
	 catch (Exception e){
	   errorMessage="Errore: " + e.getMessage();
	 }
  
}
%>

<html>

<head>
<meta http-equiv="Content-Language" content="it">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Debug</title>
</head>
<body>
<hr>
<font face="Century Gothic" size="5">
<hr>

<form method="POST" action="t_call.jsp">
  <b>
  <font face="Century Gothic" size="5">TEST DI CHIAMATA PGM</font></b>
  <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="500" id="AutoNumber1">
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">ip</font></b></td>
      <td>  <input type="text" name="ip" size="20" value="<%=ip%>"></td>
    </tr>
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">user</font></b></td>
      <td><input type="text" name="userName" size="20" value="<%=userName%>"></td>
    </tr>
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">pwd</font></b></td>
      <td><input type="text" name="pwd" size="20" value="<%=pwd%>"></td>
    </tr>
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">pwd</font></b></td>
      <td><input type="text" name="mainProgram" size="20" value="<%=mainProgram%>"></td>
    </tr>    
    <tr>
      <td bgcolor="#C0C0C0" bordercolor="#000000"><b><font face="Arial">Convert</font></b></td>
      <td><input type="checkbox" name="convert" <%=convert?"checked":""%>></td>
    </tr>     
    <tr>
      <td>
        Stringa ingresso
      </td>
      <td>
        <textarea rows="4" cols="80" name="inputParam" ><%=inputParam%></textarea>
      </td>
    </tr>
    <% if (!errorMessage.trim().equals("")){ %>
    <tr>
      <td colspan="2"><font color="red"><%=errorMessage%></font></td>    		
    <% } %>
    <tr>
      <td>
        Stringa uscita
      </td>
      <td>
        <textarea rows="4" cols="80" ><%=outputParam%></textarea>
      </td>
    </tr>    
  </table>
  <p>
  
  
  <input type="submit" value="Invia" name="bt"></p>
</form>
<p>&nbsp;</p>
</body>
</html>