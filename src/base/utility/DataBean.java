/*
 * Created on 7-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package base.utility;

 

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class DataBean extends BaseBean {
  
  /** Tipo dei campi. Chiave = NOMECAMPO, Valore=N,A */
  protected Hashtable fieldsDef = new Hashtable();
  /** Valori dei campi se la query ha trovato una riga.
   *  Chiave = NOMECAMPO, Valore=Valore del campo */
  protected Hashtable fieldsVal = new Hashtable();
  /** Nomi dei campi della tabella ordinati (partendo da zero) */
  protected Vector fieldsNames = new Vector();
  /** Elenco delle chiavi (partendo da zero)*/
  protected Vector fieldsKey = new Vector();
  /** Nome della tabella da recuperare da params (Es. "tb_wbotep0f") */
  protected String tableName= "";
  /** Nome del database da recuperare da params (Es. "DB_ORDINI") */
  protected String dbName = "";  
  /** Condizione con cui fare la query di init (Es. " NROROR=-1 ")*/
  protected String initSql = "";
  
  
  
  public boolean init() {
    fieldsDef = new Hashtable();
    fieldsVal = new Hashtable();
    fieldsNames = new Vector(); 
    try {
      if (!apri_db(dbName)) {
        error("DataBean.init: Impossibile aprire il DB");
        return false;
      }
      String str = "SELECT * FROM " + getProp(tableName) + 
                   " WHERE " + initSql;
      db.execQuery(str);
      ResultSetMetaData rmd = db.getRs().getMetaData();
      for (int i = 1; i <= rmd.getColumnCount(); i++) {
        String nomeCampo = rmd.getColumnLabel(i);
        String tipoCampo = rmd.getColumnTypeName(i);
        fieldsNames.add(nomeCampo);        
        if (tipoCampo.equals("CHAR") || tipoCampo.equals("VARCHAR")){     
          fieldsVal.put(nomeCampo,"");
          fieldsDef.put(nomeCampo,"A");
        }  
        else{
          fieldsVal.put(nomeCampo,"0");         
          fieldsDef.put(nomeCampo,"N");
        }  
      }
      rmd=null;
      db.closeDb();
      return true;
    }
    catch (Exception e) {
      error("DataBean.init " + e.getMessage());
      return false;
    }    
  }  
  
  public boolean reload() {
    try {
      if (!apri_db(dbName)) {
        error("DataBean.reload: Impossibile aprire il DB");
        return false;
      }
      String str = "SELECT * FROM " + getProp(tableName) +
                   " WHERE " + composeWhereKeysToken();
      db.execQuery(str);
      ResultSet rs = db.getRs();
      ResultSetMetaData rmd = rs.getMetaData();
      while (rs.next()){
        for (int i = 1; i <= rmd.getColumnCount(); i++) {
          String nomeCampo = rmd.getColumnLabel(i);
          if (rs.getString(nomeCampo) != null)
            fieldsVal.put(nomeCampo, notNull(rs.getString(i)));
          else
            fieldsVal.put(nomeCampo, "");          
        }
      }
      rs=null;
      rmd=null;
      db.closeDb();
      return true;
    }
    catch (Exception e) {
      error("DataBean.reload " + e.getMessage());
      return false;
    }    
  }    

  public boolean delete() {
    try {
      if (!apri_db(dbName)) {
        error("DataBean.delete: Impossibile aprire il DB");
        return false;
      }
      String str = "DELETE FROM " + getProp(tableName) +
                   " WHERE " + composeWhereKeysToken();
      db.executeUpdate(str);
      db.closeDb();
      return true;
    }
    catch (Exception e) {
      error("DataBean.delete " + e.getMessage());
      return false;
    }    
  } 
  
  public boolean write() {
    try {
      if (!apri_db(dbName)) {
        error("DataBean.write: Impossibile aprire il DB");
        return false;
      }
      String str = "INSERT INTO " + getProp(tableName) +
                   " VALUES (";
      for (int i=0;i<fieldsNames.size();i++){
        String nomeCampo = (String)fieldsNames.get(i);        
        if (i>0)
          str+=",";
        str += formatFieldForDb(nomeCampo);  
      }
      str += ")";
      db.executeUpdate(str);
      db.closeDb();
      return true;
    }
    catch (Exception e) {
      error("DataBean.delete " + e.getMessage());
      return false;
    }    
  }    
  
  public boolean update() {
    try {
      if (!apri_db(dbName)) {
        error("DataBean.update: Impossibile aprire il DB");
        return false;
      }
      String str = "UPDATE " + getProp(tableName) + " SET ";
      for (int i=0;i<fieldsNames.size();i++){
        String nomeCampo = (String)fieldsNames.get(i);        
        if (i>0)
          str+=",";
        str += nomeCampo + "=" + formatFieldForDb(nomeCampo);  
      }
      str += " WHERE " + composeWhereKeysToken();
      db.executeUpdate(str);
      db.closeDb();
      return true;
    }
    catch (Exception e) {
      error("DataBean.Update " + e.getMessage());
      return false;
    }    
  }   
  
  public String getField(String fieldName){
    if (fieldsVal==null)
      return "";
    if (!fieldsVal.containsKey(fieldName)){
      error("Campo " + fieldName + " non trovato!");
      return "";
    }  
    String field =(String) fieldsVal.get(fieldName);
    return field.trim();
  }
  
  public long getFieldLong(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      return 0;
    else{
      try{
        return Long.parseLong(field);
      }
      catch(Exception e){
        error("DataBean.getFieldLong: campo " + fieldName + " non valido.");
        return 0;
      }
    }
  }

  public long getFieldInt(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      return 0;
    else{
      try{
        return Integer.parseInt(field);
      }
      catch(Exception e){
        error("DataBean.getFieldInt: campo " + fieldName + " non valido.");
        return 0;
      }
    }
  }  

  public double getFieldDouble(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      return 0;
    else{
      try{
        return Double.parseDouble(field);
      }
      catch(Exception e){
        error("DataBean.getFieldLong: campo " + fieldName + " non valido.");
        return 0;
      }
    }
  }  
  
  public String getFieldAsValuta(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      field = "0";
    return Utils.getValuta(field);
  }
  
  public String getFieldAsValutaForEdit(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      field = "0";
    return Utils.getNumero(field,"#0.00####");
  }
  
  public String getFieldAsQta(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      field = "0";
    return Utils.getNumero(field,"###,###.#");
  }  

  public String getFieldAsQtaForEdit(String fieldName){
    String field = getField(fieldName);
    if (field.equals(""))
      field = "0";
    return Utils.getNumero(field,"#.#");
  }    
  
  protected String formatFieldForDb(String fieldName){
    String ret=""; 
    String tipoCampo = (String)fieldsDef.get(fieldName);      
    
    if (tipoCampo.equals("N")){      
      String temp = zeroIfNull(getField(fieldName));
      temp = temp.replace(',','.');
      //ret = Utils.getDoubleFromValuta(zeroIfNull(temp));
      ret = temp;
    }  
    else {
      ret = "'" + Utils.eliminaCarSpec(getField(fieldName)) + "'";
    }  
    return ret;
  }
  
  protected String composeWhereKeysToken(){
    String sqlWhere = " ";
    for (int i=0 ; i<fieldsKey.size();i++){
      String nomeChiave = (String)fieldsKey.get(i);
      if (!fieldsDef.containsKey(nomeChiave))
        error("Campo chiave " + nomeChiave + " non presente nella tabella");
      if (i>0)
        sqlWhere += " AND ";
      sqlWhere += nomeChiave + "=" + formatFieldForDb(nomeChiave);
    }
    sqlWhere += " ";
    return sqlWhere;
  }
  
  public String getTableName() {
    return tableName;
  }
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  public void setInitSql(String initSql) {
    this.initSql = initSql;
  }
  
  /** Setta l'elenco delle chiavi come Vector che inizia da zero */
  public void setFieldsKey(Vector fieldsKey) {
    this.fieldsKey = fieldsKey;
  }  

  /** Setta l'elenco delle chiavi ricevendo una stringa come
   *  elenco di chiavi separate da virgola */
  public void setFieldsKey(String fieldsKeyList) {
    fieldsKey = new Vector();
    StringTokenizer st = new StringTokenizer(fieldsKeyList,",");
    while(st.hasMoreTokens()){
      fieldsKey.add(st.nextToken());
    }  
  }
  
  
  public void setField(String fieldName, String value) {
    //Controllare coerenza numerico/alfa facendo metodi
    //con firme diverse
    fieldsVal.put(fieldName,value);
  }  
  public void setDbName(String dbName) {
    this.dbName = dbName;
  }
  public String getDbName() {
    return dbName;
  }
}
