package base.servlet;

import java.io.IOException;
import java.util.Hashtable;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

//Access.cookieAuth
@WebServlet(urlPatterns = { "/otplogin" })
public class OtpLoginServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		request.setAttribute("secLcom","civerra_luci0");
		// read body part
		String requestData = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		Hashtable<String,String> paramsIn = gson.fromJson(requestData, Hashtable.class);
		request.setAttribute("paramsIn",paramsIn);
		
		System.out.println(paramsIn.get("code"));
	    
		request.getServletContext()
              .getRequestDispatcher("/code/otplogin.jsp").forward(request, response);
	}
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//response.setContentType("application/json");
	 	//response.setCharacterEncoding("UTF-8"); 
	 	//response.getWriter().write("nodata");
    	//https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/http/HttpServletResponse.html
	 	response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // errore 405
   } 
}
