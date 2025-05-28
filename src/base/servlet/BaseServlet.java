
package base.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import base.db.DbConn;
import base.utility.Jresponse;
import base.utility.Jresponse;
import base.utility.Sys;
import base.utility.Utils;

@SuppressWarnings("deprecation")
public class BaseServlet extends HttpServlet implements SingleThreadModel {
	protected Sys sys;
	protected DbConn db;
	protected Utils utils = new Utils();
	protected HttpSession session;
	protected HttpServletRequest request;
	protected Gson gson;
	protected Jresponse jr;
	protected static final String CONTENT_TYPE = "text/html";

	/** Initialize global variables */
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}

	/** Process the HTTP Get request */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/** Process the HTTP Post request */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		try {
		} catch (Exception e) {
		}
	}

	protected void out(String aElement) {
		System.out.println(aElement);
	}

	protected void outS(String aElement) {
		System.out.println(aElement);
	}

	protected boolean deleteCookie(String aName, HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(aName)) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					return true;
				}
			}
		}
		return true;
	}

	protected String readCookie(String aName, HttpServletRequest req) {
		String ret = "";
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				out("   --" + cookie.getName() + "---" + aName);
				if (cookie.getName().equalsIgnoreCase(aName)) {

					ret = cookie.getValue();
					break;
				}
			}
		}
		return ret;
	}

	/** Initialize global variables */
	protected void startBean(HttpServletRequest curr_request) {
		try {
			request = curr_request;
			gson = new Gson();
			session = request.getSession();
			sys = (Sys) session.getAttribute("sys");
			db = new DbConn(sys);
			db.connect("SP_DB");
			jr=new Jresponse();
		} catch (Exception e) {
		}
	}

	protected Map<String, String> getRequestParams(HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration reqNames = req.getParameterNames();
		while (reqNames.hasMoreElements()) {
			String parameterName = (String) reqNames.nextElement();
			map.put(parameterName, req.getParameter(parameterName).trim());
		}
		return map;
	}

	/**
	 * metodi comuni a tutte le sottoclassi uguali ai metodi disponibili per i beans
	 * derivati da smwebapp
	 */
	public void debug(String aStr, String aColor) {
		try {
			sys.debug(aStr, aColor);
		} catch (Exception ex) {
		}
	}

	void debugRequest(Sys sys, HttpServletRequest req) {
		Enumeration reqNames = req.getParameterNames();
		while (reqNames.hasMoreElements()) {
			String parameterName = (String) reqNames.nextElement();
			sys.debugWhite("PARAM-->" + parameterName + "  :  <font color='yellow'>" + req.getParameter(parameterName)
					+ "</font>");
		}
	}

	void outRequest(HttpServletRequest req) {
		System.out.println("BaseServlet outRequest-------------------------");
		Enumeration reqNames = req.getParameterNames();
		while (reqNames.hasMoreElements()) {
			String parameterName = (String) reqNames.nextElement();
			System.out.println(this.getClass().getCanonicalName() + "PARAM-->" + parameterName + " > "
					+ req.getParameter(parameterName));
		}
	}

	public boolean isBlank(String aObj) {
		return notNull(aObj).trim().equals("");
	}

	public String blankIfNull(String aValue) {
		try {
			if (null == aValue)
				return "";
			return aValue;
		} catch (Exception e) {
			return "";
		}
	}

	public void debug(String aStr) {
		try {
			sys.debug(aStr);
		} catch (Exception ex) {
		}
	}

	public void error(String aStr) {
	}

	public String notNull(String aValue) {
		if (aValue == null)
			return "";
		if (aValue.equalsIgnoreCase("null"))
			return "";
		else
			return aValue;
	}

	public boolean notBlank(String aValue) {
		if (notNull(aValue).trim().equals(""))
			return false;
		return true;
	}

	public String zeroIfNull(String aValue) {
		return Utils.zeroIfNull(aValue);
	}

	public String pApic(String aString) {
		return "'" + aString + "'";
	}

	public boolean digit(String aKey) {
		return (request.getParameter(aKey) != null);
	}

	public String req(String aKey) {
		return notNull(request.getParameter(aKey));
	}

	protected String getPage(String jspAlias) {

		return "";
	}

	protected String getServlet(String jspAlias) {
		return "";
	}

	/**
	 * ritorna il codice listino in uso nello stato corrente dell'applicazione del
	 * cliente se sto elaborando un ordine o dell 'utente applicativo
	 */

	/**
	 * traduce la stringa passata per il codice lingua in uso
	 */
	protected String traduci(String aKey) {
		// return Babel.translate(aKey,1,"");
		return aKey;
	}

	/** ridirezione flusso a pagina jsp configurata in classi.ini */
	protected void vaiA(HttpServletRequest request, HttpServletResponse response, String dove) {
		try {
			debug("Passaggio controllo a :" + dove, "red");
			RequestDispatcher disp = request.getRequestDispatcher(dove);
			if (disp != null) {
				disp.forward(request, response);
			} else {
				error("Search.class nessun dispatcher :" + dove + "->" + dove);
			}
		} catch (Exception e) {
			debug("BaseServlet.Errore forwarding " + dove + ":" + e.getMessage());
		}
	}

}