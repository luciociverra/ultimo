package base.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import base.db.RsTable;
 
/**
 * Title:        Standard Internet Sanmarco per applicazioni Java.jsp
 * Description:  gestisce le proprieta' e i debug del systema corrente
 * Copyright:    Copyright (c) 2001
 * Company:     Civerra Lucio
 * @author
 * @version 1.0
 */

public class Sys extends BaseObj {
  // codice dell'azienda che sta eseguendo l'applicazione
  private String azienda ="";
  // root directory (fisica) dell'applicativo
  private String userDir;
  private String fsep = System.getProperty("file.separator");
  public Hashtable<String,String> tabBabel = new Hashtable<String,String>();
  private String fileCss;
  private String internetDir;
  private RsTable rsMenu;
 
  
  //dati per il debug
  private  final int MAX_LINE = 500;
  private Hashtable tabQuery=new Hashtable();
  private int cont=0;
  private int contAlt=0;
  
  private String currUser;
  private String transError="";
  private Properties permessi;
  private String currUserBrowser;
  private String linguaWeb;
  private String dbgArea="";
  private String dbgAreaAlt="";
  //strutture per caricare in memoria i dati di params.ini
  // e di classi.ini
  private Properties paramsBase;
  private Properties paramsAzienda;
  private Properties classiBase;
  private Properties classiAzienda;
  private boolean dbgOn=false;
  private boolean dbgOut=false;
  
  //
  public Sys() {
    cont=0;
    transError="";
  }

  public  void init() {
      azienda = "";
      userDir = "";
      fileCss = "";
      dbgOn=false;
      cont = 0;
  }
  public  void loadIniFiles(){
    try {
       this.paramsBase=new Properties();
       this.paramsAzienda=new Properties();
       this.classiBase=new Properties();
       this.classiAzienda=new Properties();
       if(Utils.existFile(this.getFileIni("lcom")))
          paramsBase.load(new FileInputStream(this.getFileIni("lcom")));
       paramsAzienda.load(new FileInputStream(this.getFileIni()));
       if(Utils.existFile(this.getFileClassi("lcom")))
          classiBase.load(new FileInputStream(this.getFileClassi("lcom")));
       classiAzienda.load(new FileInputStream(this.getFileClassi()));
    }
    catch (Exception e) {
        this.error("Sys.loadIniFiles :"+e.getMessage());
    }
  }
  /** leggo le classi configurate
   */
  public String getClasse(String aClasse) {
    try {
        return this.getClassiFile(aClasse,"");
    }
    catch (Exception x) {
      return "";
    }
  }

  /* dal file classi.ini estraggo il path assoluto per una servlet*/
  public String getServlet(String aClasse) {
    try {
      return internetDir + "/servlet/" +this.getClassiFile(aClasse,"NoServlet");
    }
    catch (Exception x) {
      return "";
    }
  }

  /* dal file classi.ini estraggo il path assoluto per una pagina jsp
   * se non trovata nel file ini del cliente leggo quello standard
   */
  public String getPage(String aPagina) {
    try {
      return internetDir + getClassiFile(aPagina, "errnopage.jsp");
    }
    catch (Exception x) {
      return "";
    }
  }
  
  /** Torna il percorso della pagina mappata su classi.ini 
   *  senza il context: indispensabile per le inclusioni dinamiche */
  public String getPageNoContext(String aPagina) {
    try 
	{
    	return getClassiFile(aPagina, "errnopage.jsp");
    }
    catch (Exception x) {
      return "";
    }
  }    

  /* dal file classi.ini estraggo il path assoluto per una pagina jsp
   * che deve errer oggetto di dispatcher di una servlet
   */
  public String getServletPage(String aPagina) {
    try {
        return getClassiFile(aPagina,"errnopage.jsp");
    }
    catch (Exception x) {
      return "";
    }
  }

  /* dal file classi.ini estraggo il path assoluto per una pagina jsp
   * che deve errer oggetto di dispatcher di una servlet
   */
  public String getServletServlet(String aPagina) {
    try {
      return "/servlet/" + getClassiFile(aPagina,"errnopage.jsp");
    }
    catch (Exception x) {
      return "";
    }
  }

  /** legge le informazioni-parametro da un file situato nella stessa directory del package
   *  java il file si chiama stparams.ini
   */
  public String getProp(String aKey) {
    try {
      return this.getPropFile(aKey, "");
    }
    catch (Exception x) {
      return "";
    }
  }

  /** con default impostato */
  public String getProp(String aKey, String aDefault) {
    try {
      return this.getPropFile(aKey, aDefault);
    }
    catch (Exception x) {
      return "";
    }
  }
  /* viene interrogato il params.ini per l'azienda se la chiave non 
  * viene trovate si ripete la ricerca su quello di base (smi)
  */
   public String getPropFile(String aKey, String aDefault) {
      try {
         if (paramsAzienda.containsKey(aKey))
             return  paramsAzienda.getProperty(aKey, aDefault).trim();
         else
             return  paramsBase.getProperty(aKey, aDefault).trim();
         }
   catch (Exception x) {return "";}
   } 
   public String getClassiFile(String aKey, String aDefault) {
    try {
        if (classiAzienda.containsKey(aKey))
           return  classiAzienda.getProperty(aKey, aDefault).trim();
        else
           return  classiBase.getProperty(aKey, aDefault).trim();
       }
    catch (Exception x) {
      return "";
    }
  }
public String getPropFile(String aFile, String aKey, String aDefault) {
    try 
	{
      Properties p = new Properties();
      FileInputStream input;
      p.load(input = new FileInputStream(aFile));
      String r = p.getProperty(aKey, aDefault).trim();
      input.close();
      if (r == null)
        return aDefault;
      else
        return r;
    }
    catch (Exception x) {
      return "";
    }
  }

  /** legge le informazioni-parametro da un file user.ini
   */
  public String getPropUser(String aKey) {
    try {
      Properties p = new Properties();
      FileInputStream input;
      p.load(input = new FileInputStream(getFileUser()));
      input.close();
      return p.getProperty(aKey).trim();
    }
    catch (Exception x) {
      return "";
    }
  }

  /** legge le informazioni-parametro da un file situato nella stessa directory del package
       *  java il file si chiama stparams.ini e le confronta con il valore richiesto
   */
  public boolean getPropEquals(String aKey, String aValue) {
    try {
      return aValue.equalsIgnoreCase(getProp(aKey));
    }
    catch (Exception x) {
      return false;
    }
  }

  /** legge le informazioni-parametro da un file default.ini
   */
  public String getDefault(String aKey) {
    try {
      Properties p = new Properties();
      FileInputStream input;
      p.load(input = new FileInputStream(getFileDefault()));
      input.close();
      return p.getProperty(aKey).trim();
    }
    catch (Exception x) {
      return "";
    }
  }

  /** legge le informazioni-parametro da un file
   *  le propriet� sono dati ripetuti separati da ;
   *  vengono caricati in un Vector
   */
  public Vector getPropVector(String aKey) {
    Vector v = new Vector();
    try {
      String aLine = getProp(aKey);
      StringTokenizer st = new StringTokenizer(aLine, ";");
      while (st.hasMoreElements())
        v.add(st.nextToken());
      return v;
    }
    catch (Exception e) {
      return v;
    }
  }

  /**/
  public String pathFiles() {
    return System.getProperty("user.dir");
  }

  public boolean existFile(String aFile) {
    try {
      File f = new File(aFile);
      return f.exists();
    }
    catch (Exception xx) {
      return false;
    }
  }

  /** il percorso fisico dell'applicazione Web */
  public void setUserDir(String newUserDir) {
    userDir = newUserDir;
  }

  public String getUserDir() {
    return userDir;
  }

  /** il codice azienda con cui l'utente opera */
  public void setAzienda(String aAzienda) {
    azienda = aAzienda;
  }

  public String getAzienda() {
    return azienda;
  }

 /** il nome della directory virtuale internet es: /stampe / ordini o blank
  *  se � la default root del server web
  */
  public void setInternetDir(String newInternetDir) {
    internetDir = newInternetDir;
  }

  public String getInternetDir() {
    return internetDir;
  }

  /** il nome del file dei parametri */
  private String getFileIni() {
    return getFolder("WEB-INF") + azienda + fsep + "params.ini";
  }
  private String getFileIni(String aAzienda) {
    return getFolder("WEB-INF") + aAzienda + fsep + "params.ini";
  }
  /** il nome del file dei parametri di default*/
  private String getFileDefault() {
    return getFolder("WEB-INF") + "default.ini";
  }

  /** il nome del file dei menu se non presente ritorno quello standard */
  public String getFileMenu() {
    String f = getFolder("WEB-INF") + azienda + fsep + "menu.ini";
    if (Utils.existFile(f))
      return f;
    else
      return getFolder("WEB-INF") + "smi" + fsep + "menu.ini";
  }

  /** il nome del file dei parametri di default-utente x offline*/
  public String getFileUser() {
    return getFolder("WEB-INF") + azienda + fsep + "user.ini";
  }

  /** il nome del file del file classi.ini */
  public String getFileClassi() {
    return getFolder("WEB-INF") + azienda + fsep + "classi.ini";
  }

  /** il nome del file del file classi.ini da un'azienda specificata*/
  public String getFileClassi(String azi) {
    return getFolder("WEB-INF") + azi + fsep + "classi.ini";
  }

  /** il nome del folder dove scrivo i file pdf */
  public String getFolderTmp() {
    return getFolder("tmp");
  }

  /** il percorso di un folder letto dal file di configurazione */
  public String getFolderLog() {
    return userDir + "dbg" + fsep + "logs" + fsep;
  }

  /** il percorso del folder che contiene tutti i file.ini */
  public String getFolderAzienda() {
    return getFolder("WEB-INF") + azienda + fsep;
  }

  /** il percorso di un folder dei log*/
  public String getFolderStat() {
    return userDir + "pers" + fsep + "statistiche" + fsep;
  }

  /** il percorso di un folder letto dal file di configurazione */
  public String getFolderDbg() {
    return userDir + "dbg" + fsep;
  }

  /** il percorso di un folder */
  public String getFolder(String aFolder) {
    return userDir + aFolder + fsep;
  }

  /** il percorso di un folder letto dal file di configurazione */
  public String getFolderConf(String aFolderConf) {
    String f = Utils.replaceStr(getProp(aFolderConf), "\\", fsep);
    return userDir + fsep + f + fsep;
  }

  /** il testo del file css dell'applicazione*/
  public String getCss() {
    return getFileCss();
  }

  /** Carico il file css da usare nelle pagine*/
  public void setFileCss() {
    String a = internetDir + getProp("file-css");
    if (a.equals(""))
      fileCss = "";
    else
      fileCss = "<link rel='stylesheet' type='text/css' href='" + a + "'>";
  }

  public String getFileCss() {
    if (fileCss == null)
      setFileCss();
    return fileCss;
  }

  /** metodi di debug*/
  public void close() {
    tabDati = new Hashtable();
  }
  
  public void debugSql(String aVal) {
		if (!this.dbgOn)
			return;
		dbgArea += "<a href='javascript:dqry(" + cont
				+ ");'>try</a>&nbsp;<span id='q" + cont + "'>" + aVal
				+ "</span><br>";
		cont++;
		if (cont > MAX_LINE) {
			cont = 0;
			dbgArea = "";
		}
	}
	public void debugSql(String aVal,long from) {
		if (!this.dbgOn)
			return;
		long l = (System.currentTimeMillis() - from);
		
		dbgArea += "<a href='javascript:dqry(" + cont
				+ ");'>try</a>&nbsp;<span id='q" + cont + "'>" + aVal
				+ "</span>  in: <font color='yellow' size=1>" + l + "</font><br>";
		cont++;
		if (cont > MAX_LINE) {
			cont = 0;
			dbgArea = "";
		}
	}

 
  public void put(String nomeVar, String aString) {
    tabDati.put(nomeVar, (Object) aString);
  }

  public void put(String nomeVar, Float aFloat) {
    tabDati.put(nomeVar, (Object) aFloat);
  }

  public void put(String nomeVar, Integer aInt) {
    tabDati.put(nomeVar, (Object) aInt);
  }

  public void put(String nomeVar, Object aObject) {
    tabDati.put(nomeVar, aObject);
  }

  public void add(String nomeVar, String aString) {
    if (tabDati.containsKey(nomeVar)) {
      String oldData = this.getString(nomeVar);
      this.put(nomeVar, oldData + aString);
    }
    else {
      tabDati.put(nomeVar, (Object) aString);
    }
  }

  public void remove(String aKey) {
    if (tabDati.containsKey(aKey))
      tabDati.remove(aKey);
  }

  public Object get(String nomeVar) {
    if (tabDati.containsKey(nomeVar)) {
      return tabDati.get(nomeVar);
    }
    else {
      return "";
    }
  }

  public String getString(String nomeVar) {
    if (tabDati.containsKey(nomeVar))
      return (String) tabDati.get(nomeVar);
    else
      return "";
  }

  public String getDbgArea() {
      return dbgArea;
  }
  public String getDbgAreaAlt() {
      return dbgAreaAlt;
  }
  /** ritorna il catrattere separatore per le directory */
  public String getPathSeparator() {
    return fsep;
  }

  public boolean contains(String nomeVar) {
    return tabDati.containsKey(nomeVar);
  }

  /**
   * metodo standard per riempire l'area di debug\errore -> log errore
   */
  public void error(String aVal) {
    try {
    	 if(dbgOut) System.out.println(aVal);
         if(! this.dbgOn) return;	
     
		 this.debug(aVal, "YELLOW");
		 Tracer.writeLog(this, aVal);
   }
    catch (Exception e) {}
  }
  public void errorAlt(String aVal) {
	    try {
	    	 if(dbgOut) System.out.println(aVal);
	         this.debugAlt(aVal);
			 Tracer.writeLog(this, aVal);
	   }
	    catch (Exception e) {}
	  }
  public void error(Exception aEx) {
		try {
			StringWriter sw = new StringWriter();
			aEx.printStackTrace(new PrintWriter(sw));
			error(aEx.getMessage());
			error(sw.toString());
		} catch (Exception e) {
		}
	}
  public void error(Object aObj,String aVal) {
	    try {
	     if(! this.dbgOn) return;	
	      this.debug(aObj.getClass().getName()+" - "+aVal, "YELLOW");
	      Tracer.writeLog(this, aVal);
	    }
	    catch (Exception e) {}
	  }

  public void debug(String aVal) {
	if(this.dbgOut) System.out.println(aVal);
	if(! this.dbgOn) return;	
	dbgArea+= aVal + "<br>";
    cont++;
    if (cont > MAX_LINE) {
      cont = 0;
      dbgArea="";
    }
  }
  public void debug(int aValInt) {
	     debug(String.valueOf(aValInt));
  }
  public void debugAlt(String aVal) {
	if(this.dbgOut) System.out.println(aVal);
	if(! this.dbgOn) return;	
	dbgAreaAlt+= "<font color='white'>" + aVal + "</font><br>";
    contAlt++;
    if (contAlt > MAX_LINE) {
      contAlt = 0;
      dbgAreaAlt="";
    }
  }
  public void setDbgOn(boolean aFlag)
  {
   dbgOn=aFlag;    
  }
  public boolean getDbgOn()
  {
   return dbgOn;    
  }
  public void debugWhite(String aVal) {
	   this.debug(aVal,"white");
  }
  public void debug(String aVal, String aColor) {
    this.debug("<font color='" + aColor + "'>" + aVal + "</font>");
  }
  /** pulizia area di debug */
  public void clearDbgArea() {
    cont = 0;
    dbgArea="";
    tabQuery=new Hashtable();
  }
  public void clearDbgAreaAlt() {
	    contAlt = 0;
	    dbgAreaAlt="";
  }
  /** aggiunta testo di debug: query eseguita*/
  public String addQueryDbg(String aQuery,String aSourceName) {
    tabQuery.put(String.valueOf(cont), aQuery);
    return "&nbsp;<a href=\"javascript:debugQuery(" + cont + ",'"+ aSourceName +"');\">try it</a>";
  }
 /** aggiunta testo di debug: chiamata pgm eseguita*/
   

  public String getQueryDbg(String aInd) {
    return (String) tabQuery.get(aInd);
  }

  public void writeNota(String msg) {
		String path = this.internetDir + "/dbg/logs/";
		String fileName;
		try {
			fileName = path + "ANNOTAZIONI_"+Utils.getYearToday() + ".txt";
			FileWriter out = new FileWriter(fileName, true);
			out.write(currUser + "," + Utils.getTime()+ ": " + msg + "\r\n");
			out.close();
			out = null;
			this.debug("scritta nota in "+fileName);
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}
	public void writeStat(String idEvento) {
		String path = this.internetDir + "/dbg/stats/";
		this.debug("write stat:" + idEvento);
		String fileName;
		try {
			String data;
			Date a = new Date();
			data = a.toString();
			fileName = path + Utils.getSpcDateAMG(0).substring(0, 6) + ".txt";
			FileWriter out = new FileWriter(fileName, true);
			out.write(Utils.getSpcDateAMG(0) + ";" +  currUser	+ ";" + data.substring(11, 19) + ";" + idEvento.toUpperCase() + "\r\n");
			out.close();
			out = null;
			a = null;
		} catch (IOException e) {
			//debug("writeStat :" + e.getMessage());
		} catch (Exception e) {
			//debug("writeStat :" + e.getMessage());
		}
	}
  /** scrittura file statistiche */
   public void writeId(String aInfo) {
     try {
       //Tracer.writeId(this, aInfo);
     }
     catch (Exception e) {}
   }

  public String getCont() {
    return String.valueOf(cont);
  }

  public String getContAlt() {
    return String.valueOf(cont);
  }
  public void setCurrUser(String newCurrUser) {
    currUser = newCurrUser;
  }

  public String getCurrUser() {
    return currUser;
  }

  public void setTransError(String newTransError) {
    transError = newTransError;
  }

  public void addTransError(String aDes) {
    transError += aDes + "<br>";
  }

  public void clearTransError() {
    transError = "";
  }

  public String getTransError() {
    return transError;
  }

  public void setPermessi(Properties newPermessi) {
    permessi = newPermessi;
  }

  public Properties getPermessi() {
    return permessi;
  }

  public boolean haPermesso(String aPermesso) {
    try {
		return permessi.containsKey(aPermesso);
	} catch (Exception e) {
		return false;
	}
  }

  public String valorePermesso(String aPermesso) {
    if (permessi.containsKey(aPermesso))
      return (String) permessi.get(aPermesso);
    else
      return "";

  }

  public void setCurrUserBrowser(String newCurrUserBrowser) {
    currUserBrowser = newCurrUserBrowser;
  }

  public String getCurrUserBrowser() {
    return currUserBrowser;
  }

  public void setLinguaWeb(String newLinguaWeb) {
    linguaWeb = newLinguaWeb;
  }

  public String getLinguaWeb() {
    return linguaWeb;
  }

  public String traduci(String aKey, String aDefault) {
    try {
        
       return Babel.translate(aKey, getPropertyInt("userLng"), aDefault);
    }
    catch(Exception e)
    {
      return aDefault;
    }
  }

 
/** metodo per caduta server web o fine sessione */
  public void finalize() {
	error("Sys Finalize");  
     
  }
  
  public void mailError(String aMsg){
  	try 
		{
	    String MAIL_IP ="";
  	    String MAIL_FROM=" ";
  	   Jmail mail = new Jmail();
       mail.setSys(this);
       mail.setSmtp(MAIL_IP);   
       mail.setFrom(MAIL_FROM); 
      
       mail.setSubject("Mail from SYS  msg-error");
       mail.setMsg("logon_user:"+"this.logonUser"+"  current_user:"+this.currUser+"<hr>"+aMsg);
       mail.setTo("lciverra@gmail.com");
       mail.sendMail();
		} catch(Exception e){
		}
	  }
  public void mailGeneric(String aTo,String aObj,String aMsg){
  	try 
		{
		 String MAIL_IP ="";
  	     String MAIL_FROM="service@dainese.com";
  	   Jmail mail = new Jmail();
       mail.setSys(this);
       mail.setSmtp(MAIL_IP);   
       mail.setFrom(MAIL_FROM); 
       
       mail.setSubject(aObj);
       mail.setMsg("<hr>logon_user:"+"this.logonUser"+"  current_user:"+this.currUser+"<hr><br><br>"+aMsg);
       mail.setTo(aTo);
       mail.sendMail();
		} catch(Exception e){
		}
	  }
  public void setOutOn() {
		this.dbgOut=true;
	  }
  public static String syshello() {
		return "Hello i'm SYS";
	  }
  // MULTILINGUA 
  public String addSmDbg(String aArea) {
	    //tabProtocol.put(String.valueOf(cont), aArea);
	    //return "&nbsp;<a href='javascript:debugProtocol(" + cont + ");'>show it</a>";
	  return "";
	    }
  public void clearBabelList(){
  	this.tabBabel= new Hashtable<String,String>();
  }
  public void addBabelList(String aVal,String aFlag){
  	this.tabBabel.put(aVal,aFlag);
  }
  public String translate(String aKey) {
      try
      {
        String ret=Babel.translate(aKey.trim(),  getPropertyInt("userLng"));
        if(this.getProperty("lngMaster").equals("S"))
      	  addBabelList(aKey.trim(),ret.equals("")?"N":"S");
        if(ret.equals("")) return aKey;
        return ret;
      }
      catch(Exception e)
      {
        return aKey;
      }
    }
  public String translate(String aKey,String aDef) {
      try
      {
        if(this.getProperty("lngMaster").equals("S"))
         	  addBabelList(aKey,"S");	
        return Babel.translate(aKey, this.getPropertyInt("userLng"),aDef);
      }
      catch(Exception e)
      {
        return aDef;
      }
    }
 
   

}
 