package base.servlet;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getimmagine")
public class GetImmagineServlet extends BaseServlet {
    //http://localhost:8080/ultimo/getimmagine?id=1
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        startBean(request);
        String idParam = request.getParameter("id");
        try {
            int id = Integer.parseInt(idParam);
            db.firstRecord("SELECT storyimg, storytxt FROM story WHERE id ="+idParam);
            byte[] imageBytes = db.getBytes("storyimg");
            String titolo = db.getField("storytxt");
            db.closeDb();
            
            if (imageBytes != null) {
                response.setContentType("image/jpeg");
                response.setHeader("Cache-Control", "max-age=86400"); // Cache per un giorno
                if (titolo != null && !titolo.isEmpty()) {
                       String fileName = titolo.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
                       response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
                  }
                                // Scrivi i byte dell'immagine nella risposta
                  OutputStream out = response.getOutputStream(); 
                  out.write(imageBytes);
                 } else {
                   response.sendError(HttpServletResponse.SC_NOT_FOUND, "Immagine non trovata");
                 }
             
        
        }  catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
      }
 }