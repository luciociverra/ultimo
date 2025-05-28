 
package base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/neonati" })
public class Qrneonati extends BaseServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			vaiA(req, resp, "/qrcode/neonati.jsp");
			return;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}