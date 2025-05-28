package base.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import base.utility.Babel;
import base.utility.WebContext;

public class SiteStartup extends HttpServlet {
 private static final String CONTENT_TYPE = "text/html";

 public void init(ServletConfig config) throws ServletException {
    super.init(config);
    System.out.println(WebContext.getEnv("DBDOCS"));
 
  	Babel.loadAll();
  
 }
 public void destroy()  {
    System.out.println("SiteStart DESTROY");
  }
}