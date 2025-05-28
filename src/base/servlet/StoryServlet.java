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

@WebServlet("/storyservlet")
@MultipartConfig
public class StoryServlet extends BaseServlet {
    
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
        	startBean(request);
       	    
            // Ottieni i parametri dalla richiesta
            Part imagePart = request.getPart("immagine");
            String currId = request.getParameter("currId");
            String titolo = request.getParameter("titolo");
            String descrizione = request.getParameter("descrizione");
            
            // Leggi l'immagine come byte array
            InputStream imageStream = imagePart.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            byte[] imageBytes = outputStream.toByteArray();
        	
            
        	db.startProc();
        	int retCode=0;
        	//
        	db.addProc("person_id",       "n",currId,10,true);
        	db.addProc("storyposition",   "n",1,3,true);
        	db.addProc("storytxt",        "s",titolo,9999,true);
        	db.addProc("storyLocation",   "s",descrizione,50,true);
        	db.addProc("storyvisibility","n",0,2,true);
        	db.addProc("storyDate",       "s","",20,true);
        	db.addProc("storyimg","by",imageBytes);
        	retCode=db.execProc("INSERT", "story",""); 
        	outS(db.str_errori);
            jr.ok("Immagine salvata con successo "+retCode);    
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