package base.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.db.RsTable;

@WebServlet("/slideservlet")
public class SlideServlet extends BaseServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	try {
             startBean(request);
             List<Map<String, Object>> immagini = new ArrayList<>();
        
     
               RsTable rs= db.getRsTable("SELECT * from story where person_id=61");
              
                   
                while (rs.next()) {
                    Map<String, Object> immagine = new HashMap<>();
                    
                    immagine.put("id", rs.getFieldInt("id"));
                    immagine.put("titolo", rs.getField("storytxt"));
                    immagine.put("descrizione", "DES");
                    
                    // Gestione della data (sia completa che solo anno)
                    /*
                    java.sql.Date dataSQL = rs.getDate("data");
                    if (dataSQL != null) {
                        immagine.put("data", dataSQL.toString());
                    }
                    
                    Integer anno = rs.getInt("anno");
                    if (!rs.wasNull()) {
                        immagine.put("anno", anno);
                    }
                    */
                    immagine.put("luogo", rs.getField("storylocation"));
                    
                    // Data di caricamento
                    /*
                    java.sql.Timestamp timestamp = rs.getTimestamp("data_caricamento");
                    if (timestamp != null) {
                        immagine.put("dataCaricamento", timestamp.toString());
                    }
                    */
                    immagini.add(immagine);
                }
            
            db.closeDb();
            // Imposta la risposta come JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // Usa la libreria Gson per serializzare la lista
            String json = gson.toJson(immagini);
            response.getWriter().write(json);
            
        } catch (Exception e) {
            // In caso di errore
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}