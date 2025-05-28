package base.utility;
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
public class MsgTag	extends BodyTagSupport {

		  private String str = null;
		  private String lngCode = "";
		  private String messaggio = "";
		  private String abilitazione = "";
		  private String gestLingua = "";
		  private boolean found = true;
		  private String uri ="code/lemma.jsp";
		  
		  private String chiave = "";
		  private Sys sys;
		  private String ls = System.getProperty("line.separator");
		
		  public void setStr(String value) {
		    str = value.trim().toUpperCase();
		  }
		 public String getStr() {
		    return (str);
		  }
		  public int doStartTag() {
		    try {
		      JspWriter out = pageContext.getOut();
		      HttpSession session = pageContext.getSession();
		      sys = (Sys) session.getAttribute("sys");
		      gestLingua = sys.getProperty("lngMaster");
		      messaggio = "";
		      chiave="";
		      if (str == null) {  // non uso chiave interna al tag quindi salto sempre 
		        return EVAL_BODY_BUFFERED;
		      }
		        String a = Babel.translate(str,getCurrentPositionLanguage());
		        chiave=str;
		        if (!a.equalsIgnoreCase("")) {
		          found = true;
		          messaggio = a;
		          sys.addBabelList(str, "S");
		        }
		        else
		        {
		          //out("Non ho trovato str su Babylon, la scrivo nel file");
		         // Tracer.writeLng(sys,str);
		        }
		    }
		    catch (Exception ex) {
		    }
		    return EVAL_BODY_BUFFERED;
		  }

		  /**
		   * Evento che si scatena quando incontro la fine del tag
		   */
		  public int doEndTag() {
		    try {
		      JspWriter out = pageContext.getOut();
		    }
		    catch (Exception ex) {
		      throw new Error("Multilingua: Errore valutamdo il tag");
		    }
		    return EVAL_PAGE;
		  }
		  private int getCurrentPositionLanguage()
		  { 
			try 
			{
			  return Integer.parseInt(sys.getProperty("userLng"));
			} catch(Exception e) {
				return 1;
			}
		  }
          
		  /**
		   * Evento che si scatena una volta letto l'inner html
		   */
		  public int doAfterBody() throws JspTagException {
		    String msgSito = "";
		    String risposta = "";
		    HttpSession session = pageContext.getSession();
		    BodyContent body = getBodyContent();
		    try {
		      JspWriter out = body.getEnclosingWriter();
		      msgSito = body.getString();
		      if(chiave.equals("")) chiave=msgSito;
		      // Se il multilingua è attivato entriamo qui sotto
		      if (found)
		      {
		        // se abbiamo cercato la frase all'interno della tabella tramite str
		        // e la frase trovata non è una stringa vuota stampiamo quella.
		        if(!messaggio.trim().equals(""))
		        {
		          risposta=messaggio;
		        }
		        // se la stringa messaggio è nulla procediamo alla ricerca del body all'interno
		        // di Babylon. Se troviamo una parola o frase non nulla stampiamo quella, altrimenti
		        // stampiamo semplicemente il body
		        else
		        {
		          String a = Babel.translate(msgSito,getCurrentPositionLanguage());
		          if(! a.trim().equals(""))
		          {
		        	if (gestLingua.equalsIgnoreCase("S"))  
		         	    sys.addBabelList(msgSito, "S");
		            risposta=a;
		          }
		          else
		          {
		          	if (gestLingua.equalsIgnoreCase("S"))  
		        	    sys.addBabelList(msgSito, "N");
		            risposta=msgSito;
		          }
		        }
		      }

		      // Se siamo giunti qui senza trovare l'attributo str allora andiamo a cercare
		      // il body nella tabella e se troviamo una frase che non sia nulla la stampiamo,
		      // altrimenti andiamo a scrivere il body stesso.
		      // Se il multilingua non è attivato ci limitiamo a stampare il body del tag
		      else
		      {
		        risposta = msgSito;
		      }
		      //Controllo se si tratta di una variabile multilingua tipo lnj_*
		      //usata per le parti javascript
		      if (gestLingua.equalsIgnoreCase("S")) {
		       
		        	String r="";
		        	String cPath=WebContext.getProperty("basePath");
		            boolean isFull=Babel.quota(chiave)[1].equals("S");
		        	if(isFull)
		               r = "&nbsp;<img style='cursor:pointer' title='translation' src='"+cPath+"assets/images/lngck_yes.png' ";
		            else  
		               r = "&nbsp;<img style='cursor:pointer' title='translation' src='"+cPath+"assets/images/lngck_no.png' ";
		     		              
		            r+=" onclick=\"event.preventDefault();window.open('" + cPath + uri + "?cod=" + replaceSpec(chiave) + "'" +
		              ",'','toolbar=no,width=700,height=300');return false;\"/>"; 
		          risposta = risposta+r;
		      }
		      out.print(risposta);
		      // Clear for next evaluation
		      body.clearBody();
		    }
		    catch (IOException ioe) {
		      throw new JspTagException("Errore in multilingua doAfterBody " + ioe);
		    }
		    return (SKIP_BODY);
		  }
		  private String replaceSpec(String aCampo) {
			    try {
			      String a, c = "";
			      for (int z = 0; z < aCampo.length(); ++z) {
			        a = String.valueOf(aCampo.charAt(z));
			        if (a.equals("'")) {
			          c += "%27";
			        }
			        else {
			          c += a;
			        }
			      }
			      return c;
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }
		  private String notNull(String aValue) {
		    if (aValue == null) {
		      return "";
		    }
		    if (aValue.equalsIgnoreCase("null")) {
		      return "";
		    }
		    else {
		      return aValue;
		    }
		  }
		  
		  public String drawButton(String chiave) {
			   try {
				   
		  	    String r="";
	        	String cPath=WebContext.getProperty("basePath");
	            boolean isFull=Babel.quota(chiave)[1].equals("S");
	        	if(isFull)
	               r = "&nbsp;<img style='cursor:pointer;height:20px' title='' src='"+cPath+"assets/images/lngck_yes.png' ";
	            else  
	               r = "&nbsp;<img style='cursor:pointer;height:20px' title='' src='"+cPath+"assets/images/lngck_no.png' ";
	     		              
	            r+=" onclick=\"event.preventDefault();window.open('" + cPath + uri + "?cod=" + replaceSpec(chiave) + "'" +
	              ",'','toolbar=no,width=700,height=300');return false;\"/>"; 
	            return chiave + r; 
			   }
			    catch (Exception e) {
			      return "";
			   } 
		  }
		  

		}
