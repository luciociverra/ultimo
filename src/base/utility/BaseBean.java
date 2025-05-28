package base.utility;

import java.util.Enumeration;
import java.util.Hashtable;

import base.db.DbConn;

 


public class BaseBean {

  protected DbConn db;
  protected Sys sys;

  public Hashtable tabDati=new Hashtable();

  public BaseBean() {
  }

  public void createDb() {
    db = new DbConn();
    db.setSys(sys);
  }

  public void createDb(Sys sys) {
    db = new DbConn();
    db.setSys(sys);
  }

 /** apertura del bean di lettura database il parametro e' un alias definito nel file parametri */
 public boolean apri_db(String aDbName) {
   if (db == null)
     createDb();
   return db.apri_db(sys.getProp(aDbName));
 }

  /** metodo da richiamare la prima volta che si utilizza un bean di sessione
  */
  public void setSys(Sys aSys) {
    sys = aSys;
  }

  protected static void outS(String aElement) {
		System.out.println(aElement);
	}
  public Sys getSys() {
    return sys;
  }

  public void debug(String aStr) {
    try {
      sys.debug(aStr);
    }
    catch (Exception ex) {}
  }

  public void debug(String aStr, String aColor) {
    try {
      sys.debug(aStr, aColor);
    }
    catch (Exception ex) {}
  }

  public void debug(boolean aStr) {
    this.debug(String.valueOf(aStr));
  }

  public void debug(int aStr) {
    this.debug(String.valueOf(aStr));
  }

 public void error(String aVal)
  {
   try {
        sys.error(aVal);
       }
   catch(Exception e)
       { }
  }
   
 public void addTransError(String aDes)
  {
    sys.addTransError(aDes);
  }

  public String pApic(String aString)
  {
   return "'" + aString + "'";
   }
/** se e get di propriet� associate ad ogni singolo oggetto
 *  gestite im maniera completamente libera
 */
  public void setProperty(String aKey,String aVal)
  {
    if (tabDati.containsKey(aKey)) tabDati.remove(aKey);
    tabDati.put(aKey,aVal);
  }

 public void setProperty(String aKey,Hashtable aDiz)
  {
    if (tabDati.containsKey(aKey)) tabDati.remove(aKey);
    tabDati.put(aKey,aDiz);
  }
   public void clearProperty()
  {
    tabDati=new Hashtable();
  }
   public void removeProperty(String aKey)
  {
    if (tabDati.containsKey(aKey)) tabDati.remove(aKey);
  }
  public String  getProperty(String nomeVar)
  {
    if (tabDati.containsKey(nomeVar))
       return  (String)tabDati.get(nomeVar);
     else
       return  "";
  }

  public Hashtable getPropertyHash(String nomeVar)
  {
    if (tabDati.containsKey(nomeVar))
       return  (Hashtable)tabDati.get(nomeVar);
     else
       return  null;
  }

  public String  notNull(String aValue)
  {
    if (aValue == null)
       return  "";
    if (aValue.equalsIgnoreCase("null"))
       return "";
    else
       return  aValue;
  }
  /** il valore passato � diverso sia da null che da blank
   *  trim incluso
   */
   public boolean  notBlank(String aValue)
  {
    if (notNull(aValue).trim().equals("")) return  false;
    return  true;
  }
   public boolean  isBlank(String aValue)
   {
     return  notNull(aValue).equals("");
   }
  /** torna zero(stringa) sia che il valore sia null o blank trim incluso
  */
   public String  zeroIfNull(String aValue)
  {
    if (aValue == null)
       return  "0";
    if (aValue.equalsIgnoreCase("null")) return "0";
    if (aValue.trim().equals(""))
       return "0";
    else
       return  aValue;
  }

  protected String getProp(String aKey)
  {
   return sys.getProp(aKey);
  }

   protected String getProp(String aKey,String aDefault)
  {
   return sys.getProp(aKey,aDefault);
  }

  protected String getExecTime(long from)
  {
   long l = (System.currentTimeMillis() - from);
   return "<font color='yellow' size=1>"+l+"</font>";
  }
/* Scrivo in debug il contenuto della collezione di proprieta' */
   public void debugProperty()
   {
   for (Enumeration e=tabDati.keys(); e.hasMoreElements();)
     {
      String nK = (String)e.nextElement();
      String nVal="";
      try { nVal = (String)tabDati.get(nK); }
      catch(Exception ex) {nVal = "not displayable";}
      debug(nK  + " -->" +  nVal);
    }

   }
  /** traduce la stringa passata per il codice lingua in uso
   *  se non trovato cerco per il base o do una segnalazione
  */
   

   
   public boolean haPermesso(String aPermesso) {
          return sys.haPermesso(aPermesso);
   }

 }