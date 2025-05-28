package base.servlet;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import base.utility.Sys;

@WebServlet(urlPatterns = { "/downloadfile"})
public class DownloadFile  extends BaseServlet  {
@Override
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       processRequest(request, response);
  }
@Override
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   processRequest(request, response);
}

public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

 try {
	  HttpSession session = req.getSession();
	  Sys sys=(Sys)session.getAttribute("sys");
	  if(isBlank(sys.getProperty("userAblLogin")))
	  { 
		  sys.error("NESSUNA SESSIONE PRESENTE");
		  throw new Exception("invalid session");
	  }
	  sys.debug("DownloadFile----Inizio");
	  String aFile=(String)session.getAttribute("file_download");
	  String aParziale=(String)session.getAttribute("file_parziale");
	  sys.debug("DownloadFile---file reale:" + aFile+ "  file parziale in header " + aParziale);
	
	  res.setContentType("application/x-filler");
	  res.setHeader("Content-Disposition","attachment; filename="+aParziale+";");
	  ServletOutputStream stream = res.getOutputStream();
	  BufferedInputStream fif =new BufferedInputStream(new FileInputStream(aFile));

	  int data;
	  while((data = fif.read()) != -1) {
		stream.write(data);
	  }
		
	  fif.close();
	  stream.close();
	  stream.flush();
  }
catch (Exception e) 
  {
   PrintWriter out = res.getWriter();
   out.println("<html><head></head>");
   out.println("<body bgcolor=\"white\">");
   out.println("<h1>Invalid download operation</h1><br/><br/><br/>"+e.getMessage()+"</body></html>");
  }
}
}
