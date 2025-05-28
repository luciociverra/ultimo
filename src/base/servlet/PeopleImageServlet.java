package base.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/peopleimageservlet")
@MultipartConfig
public class PeopleImageServlet extends BaseServlet {
    
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
        	startBean(request);
       	    
            // Ottieni i parametri dalla richiesta
            Part imagePart = request.getPart("image");
            String currId = request.getParameter("currId");
            // Leggi l'immagine come byte array
            InputStream imageStream = imagePart.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            byte[] imageBytes = outputStream.toByteArray();
        	
           	Connection conn = db.getConnection();
            String sql = "UPDATE people set people_img=? WHERE id="+currId;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBytes(1, imageBytes);
            outS("ESITO UPDATE..:"+stmt.executeUpdate());
            jr.ok("Immagine salvata con successo");    
            db.closeDb();
            
            // Invia risposta di successo
            response.setContentType("application/json");
            response.getWriter().write(jr.toJson());
            
        } catch (Exception e) {
            // In caso di errore
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            jr.error(e.getMessage());
            response.getWriter().write(jr.toJson());
        }
    }
    
}