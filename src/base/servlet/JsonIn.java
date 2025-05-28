package base.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import base.utility.Jresponse;

@WebServlet(urlPatterns = { "/jsonin" })
public class JsonIn extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response,"GET");
	}
	/** Process the HTTP Post request */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response,"POST");
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response,String method)
			throws IOException
	{
	    
		try {
			// 1. get received JSON data from request
			BufferedReader br = 
			new BufferedReader(new InputStreamReader(request.getInputStream()));
			
			String json = "";
			if(br != null){
				json = br.readLine();
				System.out.println("DATI---"+json);
			}
			Gson gson = new Gson();
		try {	 
			Jresponse jr = gson.fromJson(json,Jresponse.class);
			// 4. Set response type to JSON
			response.setContentType("application/json");	
			jr.msg="OK";
			jr.rc=1;
	     	
			//
			PrintWriter out = response.getWriter();
			out.write(gson.toJson(jr)) ;
			out.flush();
		    } catch (Exception e) {
		    	PrintWriter out = response.getWriter();
		    	out.write("<h1>Invalid request</h1>") ;
		    	out.flush();
		    }
		
		} catch (IOException e) {
			PrintWriter out = response.getWriter();
			out.write("<h1>Invalid request</h1>") ;
			out.flush();
		}
	}
}