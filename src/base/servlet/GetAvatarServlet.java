package base.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.utility.ImageGenerator;

@WebServlet("/getavatar")
public class GetAvatarServlet extends BaseServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean notImageFound=false;
		startBean(request);
		String idPeople = request.getParameter("id");
		if(isBlank(idPeople))
			notImageFound=true;
		else {
			boolean found=db.firstRecord("SELECT people_img FROM people WHERE id=" + idPeople + " AND people_img IS NOT NULL");
			notImageFound=! found;		
		}
			
		try {
		
		if(notImageFound) {
			db.closeDb();
			byte[] imageBytes =ImageGenerator.generateImageNotFoundBytes();
			response.setContentType("image/jpeg");
			response.setHeader("Cache-Control", "max-age=86400"); // Cache per un giorno
			OutputStream out = response.getOutputStream();
			out.write(imageBytes);
			return;
		}
		
		byte[] imageBytes = db.getBytes("people_img");
		db.closeDb();
		response.setContentType("image/jpeg");
		response.setHeader("Cache-Control", "max-age=86400"); // Cache per un giorno
		OutputStream out = response.getOutputStream();
		out.write(imageBytes);
		
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID immagine non valido");
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}