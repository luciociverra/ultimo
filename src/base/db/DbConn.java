package base.db;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

import base.utility.BaseBean;
import base.utility.Sys;
import base.utility.WebContext;
 
public class DbConn extends BaseBean {

	public String str_messaggi = "";
	public String str_trace = "";
	public String str_errori = "";
	public String str_rows = "";
	public int columnCount;
	protected String last_connect;

	protected static final int MAX_RECS = 10;
	protected ResultSetMetaData rsmd;
	protected ResultSetMetaData[] arr_rsmd = new ResultSetMetaData[MAX_RECS];
	protected ResultSet rs;
	protected ResultSet[] arr_rs = new ResultSet[MAX_RECS];
	protected Connection connection;
	protected Statement stm_query;
	protected Statement[] stm_query_array = new Statement[MAX_RECS];
	protected CallableStatement cs;
	protected PreparedStatement ps;
	protected int psCounter = 1;
	protected DatabaseMetaData dmd;
	protected String aConnLoader = "1";
	protected String forceLoader = "";
	private int entProg=0;
	private Vector<SqlEnt> entList;
 

	/**
	 * Numero attuale di tentativo esecuzione query con riapertura connessione
	 * di tipo 3
	 */
	protected int tentativo = 0;
	private String sourceName;


	public DbConn() {
	}

	public DbConn(Sys aSys) {
		setSys(aSys);
	}
	public DbConn(Sys aSys,String aDbName) {
		setSys(aSys);
		connect(aDbName);
	}
	 public boolean connect(String aDef) {
		   if(null==sys) this.setSys(new Sys());
		   long from = System.currentTimeMillis();
		   try 
		   {
		        DataSource ds = WebContext.getDataSource(aDef);
		        connection = ds.getConnection();
				setAutoCommit(true);
				//DbConn.monitor="Ultima connessione Ok "+Utils.getTimbro() +" ms: "+(System.currentTimeMillis() - from);
				//timeForOpen="Connection obtained in:  " + getExecTime(from);
				//DbConn.countOpen=DbConn.countOpen+1;
				return true;
			} catch (Exception e) {
				addErr("connectJndi", aDef);
				addErr("connectJndi", e.getMessage());
				getTrace(e);
				return false;
			}
		}
	/**
	 * Esegue query di estrazione dati
	 */
	public boolean execQuery(String strQuery) {
		String cmd = strQuery;
		try {
			str_errori = "";
			long from = System.currentTimeMillis();
			debug(cmd);
			stm_query = connection.createStatement();
			rs = stm_query.executeQuery(cmd);
			rsmd = rs.getMetaData();
			if(null!=sys)
			   debug(getExecTime(from) + addQueryDbg(cmd, sourceName));
			tentativo = 0;
			return true;
		} catch (Exception e) {
			addErr("execQuery ", e.getMessage() + cmd);
			if(null!=sys)
		      debug(sys.addQueryDbg(cmd, sourceName));
			return false;
		}
	}

	/**
	 * Esegue query di estrazione dati
	 */
	public int execQueryMax(String table, String field,String where, boolean flagClose) {
		String cmd = "SELECT MAX(" + field + ") FROM " + table+ where;
		int ret = 0;
		try {
			str_errori = "";
			long from = System.currentTimeMillis();
			debug(cmd);
			stm_query = connection.createStatement();
			rs = stm_query.executeQuery(cmd);
			debug(getExecTime(from) + addQueryDbg(cmd, sourceName));
			if (rs.next())
				ret = rs.getInt(1);
			rs.close();
			return ret;
		} catch (Exception e) {
			addErr("execQuery", e.getMessage() + cmd);
			addQueryDbg(cmd, sourceName);
			return 0;
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}
	 private String addQueryDbg(String aQuery,String aSourceName) {
		 if(null!= sys)
		   return sys.addQueryDbg(aQuery, sourceName);
		 return "";
	 }
	/**
	 * Esegue query di estrazione dati
	 */
	public boolean firstRecord(String strQuery) {
		try {
			if (!execQuery(strQuery))
				return false;
			return this.nextRecord();
		} catch (Exception e) {
			addErr("firstRecord", e.getMessage() + strQuery);
			return false;
		}
	}

	public boolean firstRecord(String strQuery, int prog) {
		try {
			if (!execQuery(strQuery, prog))
				return false;
			return this.nextRecord(prog);
		} catch (Exception e) {
			addErr("firstRecord mult.", e.getMessage() + strQuery);
			return false;
		}
	}
	public String firstRecord(String strQuery, String nameFld) {
		try {
			if (!execQuery(strQuery))
				return "";
			if(this.nextRecord())
				return rs.getString(nameFld);
			return "";
		} catch (Exception e) {
			addErr("firstRecord mult.", e.getMessage() + strQuery);
			return "";
		}
	}
	/**
	 * libera le risorse allocate dalla Query
	 */
	public void closeQuery() {
		try {
			stm_query.close();
			rs.close();
			rs = null;
		} catch (Exception e) {
			addErr("closeQuery", e.getMessage());
		}
	}

	/**
	 * testa se la connessione � chiusa
	 */
	public boolean isConnected() {
		try {
			return connection.isClosed();
		} catch (Exception e) {
			addErr("isConnected", e.getMessage());
			return false;
		}
	}

	/**
	 * imposta l'autocommit
	 */
	public void setAutoCommit(boolean aFlag) {
		try {
			connection.setAutoCommit(aFlag);
		} catch (Exception e) {
			addErr("setAutoCommit.:" + aFlag + " err:", e.getMessage());
		}
	}

	/**
	 * ritorna informazioni utili sullo stato della connessione corrente
	 */
	public String getConnStatus() {
		try {
			String ret = "getAutoCommit .:" + String.valueOf(connection.getAutoCommit()) + "<br>";
			ret += "getTransactionIsolation :" + String.valueOf(connection.getTransactionIsolation()) + "<br>";
			ret += "isReadOnly  : " + String.valueOf(connection.isReadOnly());
			return ret;
		} catch (Exception e) {
			addErr("getAutoCommit.", e.getMessage());
			return "getAutoCommit. errore";
		}
	}

	/**
	 * Esegue query di estrazione dati query multiple
	 */
	public boolean execQuery(String strQuery, int prog) {
		String cmd = strQuery;
		try {
			str_errori = "";
			long from = System.currentTimeMillis();
			debug(cmd);
			stm_query_array[prog] = connection.createStatement();
			arr_rs[prog] = stm_query_array[prog].executeQuery(cmd);
			arr_rsmd[prog] = arr_rs[prog].getMetaData();
			debug(getExecTime(from) + addQueryDbg(cmd, sourceName));
			return true;
		} catch (Exception e) {
			addErr("execQuery_mult", e.getMessage() + cmd);
			debug(addQueryDbg(cmd, sourceName));
			return false;
		}
	}

	/**
	 * libera le risorse allocate dalla Query - versione multipla
	 */
	public void closeQuery(int prog) {
		try {
			stm_query_array[prog].close();
			arr_rs[prog].close();
		} catch (Exception e) {
			addErr("closeQuery_mult", e.getMessage());
		}
	}

	/**
	 * ritorna il numero di colonne presenti nella query corrente
	 */
	public int columnCount() {
		try {
			return rsmd.getColumnCount();
		}

		catch (Exception e) {
			addErr("columnCount", e.getMessage());
			return 0;
		}
	}

	/**
	 * ritorna il numero di colonne presenti nella query corrente
	 */
	public int columnCount(int prog) {
		try {
			return arr_rsmd[prog].getColumnCount();
		} catch (Exception e) {
			addErr("columnCount", e.getMessage());
			return 0;
		}
	}

	/**
	 * Esegue query di aggiornamento,inseirmento o cancellazione ritorna il
	 * numero di righe che sono state elaborate
	 */
	public int executeUpdate(String strQueryUpd) {
		try {
			int x;
			str_errori = "";
			debug(strQueryUpd);
			debug(addQueryDbg(strQueryUpd, sourceName));
			Statement exec = connection.createStatement();
			x = exec.executeUpdate(strQueryUpd);
			exec.close();
			return x;
		} catch (Exception e) {
			addErr("executeUpdate", e.getMessage() + strQueryUpd);
			return 0;
		}
	}

	public int executeUpdate(String strQueryUpd, boolean flagClose) {
		try {
			int x;
			str_errori = "";
			debug(strQueryUpd);
			debug(addQueryDbg(strQueryUpd, sourceName));
			Statement exec = connection.createStatement();
			x = exec.executeUpdate(strQueryUpd);
			exec.close();
			sys.debug("nr records elab." + x);
			return x;
		} catch (Exception e) {
			addErr("executeUpdate", e.getMessage() + strQueryUpd);
			return 0;
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}

	/**
	 * Esegue query di aggiornamento,inseirmento o cancellazione causa eccezione
	 * nel chiamante in caso di errore
	 */
	public void executeUpdateThrow(String strQueryUpd) throws Exception {
		debug("executeUpdateThrow : " + strQueryUpd);
		Statement exec = connection.createStatement();
		exec.executeUpdate(strQueryUpd);
		exec.close();
	}

	/**
	 * Esegue query di aggiornamento,inseirmento o cancellazione ritorna il
	 * numero di righe che sono state elaborate
	 */
	public boolean createCallableProcedure(String nomeProc, int numParms) {
		try {
			String strProc = "";
			str_errori = "";
			for (int i = 1; i <= numParms; ++i) {
				strProc += "?";
				if (i < numParms)
					strProc += ",";
			}
			cs = connection.prepareCall("{call " + nomeProc + "(" + strProc + ")}");
			return true;
		} catch (Exception e) {
			addErr("createProcedures", e.getMessage());
			return false;
		}
	}

	/**
	 * aggiunge un parametro ad una stored procedure
	 */
	public boolean addParIn(int numPar, String typePar, String valuePar) {
		try {
			String aType = typePar.toUpperCase();
			// debug ("tipo: "+ aType + " valore " + valuePar );
			if (valuePar.equals("NULL")) {
				cs.setNull(numPar, java.sql.Types.NULL);
				return true;
			}
			if (aType.equals("VARCHAR"))
				cs.setString(numPar, valuePar);
			if (aType.equals("INTEGER"))
				cs.setInt(numPar, Integer.parseInt(valuePar));
			if (aType.equals("DATE")) {
				int a, m, g;
				a = Integer.parseInt(valuePar.substring(6, 10));
				m = Integer.parseInt(valuePar.substring(3, 5));
				g = Integer.parseInt(valuePar.substring(0, 2));
				// il mese � da 0 a 11
				m = m - 1;
				a = a - 1900;
				cs.setDate(numPar, new java.sql.Date(a, m, g));

			}
			return true;
		} catch (Exception e) {
			addErr("addParam", e.getMessage());
			return false;
		}
	}

	//
	public boolean executeCallableProcedure() {
		try {
			rs = cs.executeQuery();
			rsmd = rs.getMetaData();
			return true;
		} catch (Exception e) {
			addErr("executeProcedure", e.getMessage());
			return false;
		}
	}

	public boolean createPS(String sqlIn) {
		try {
			ps = connection.prepareStatement(sqlIn);
			this.psCounter = 1;
			return true;
		} catch (Exception e) {
			addErr("createPS", e.getMessage());
			return false;
		}
	}

	public boolean addPs(String sqlVar) {
		try {
			ps.setString(psCounter, sqlVar);
			sys.debug("String COUNTER:" + psCounter + "  val:" + sqlVar);
			psCounter += 1;
			return true;
		} catch (Exception e) {
			addErr("addPs str", sqlVar + " contatore: " + this.psCounter + "  " + e.getMessage());
			return false;
		}
	}
	public boolean addPsTime(String sqlVar) {
		try {
			if(sqlVar.equals("")) {
			   ps.setNull(psCounter, java.sql.Types.TIMESTAMP);
			}
			else
			{
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
				cal.setTime(sdf.parse(sqlVar));// all done
				ps.setTimestamp(psCounter, new Timestamp(cal.getTimeInMillis()));	
			}
			sys.debug("Timestamp COUNTER:" + psCounter + "  val:" + sqlVar);
			psCounter += 1;
			return true;
		} catch (Exception e) {
			addErr("addPs str", sqlVar + " contatore: " + this.psCounter + "  " + e.getMessage());
			return false;
		}
	}

	public boolean addPs(int sqlVar) {
		try {
			ps.setInt(psCounter, sqlVar);
			sys.debug("INT COUNTER:" + psCounter + "  val:" + sqlVar);
			psCounter += 1;
			return true;
		} catch (Exception e) {
			addErr("addPs int", sqlVar + " contatore: " + this.psCounter + "  " + e.getMessage());
			return false;
		}
	}
	public boolean addPs(double sqlVar) {
		try {
			ps.setDouble(psCounter, sqlVar);
			sys.debug("DOUBLE COUNTER:" + psCounter + "  val:" + sqlVar);
			psCounter += 1;
			return true;
		} catch (Exception e) {
			addErr("addPs double", sqlVar + " contatore: " + this.psCounter + "  " + e.getMessage());
			return false;
		}
	}
	public boolean addPsObject(String sqlVar) {
		try {
			 PGobject jsonObject = new PGobject();
			 jsonObject.setType("json");
			 jsonObject.setValue(sqlVar);
			
			ps.setObject(psCounter, jsonObject);
			sys.debug("Object COUNTER:" + psCounter + "  val:" + sqlVar);
			psCounter += 1;
			return true;
		} catch (Exception e) {
			addErr("addPs double", sqlVar + " contatore: " + this.psCounter + "  " + e.getMessage());
			return false;
		}
	}
	public int execPs(boolean flagClose) {
		try {
			int x = ps.executeUpdate();
			ps.close();
			return x;
		} catch (Exception e) {
			addErr("execPs", e.getMessage());
			return 0;
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}

	//
	public String getOutPar(int aNum) {
		try {
			rs.next();
			return String.valueOf(rs.getString(aNum));
		} catch (Exception e) {
			addErr("getOutPar ", e.getMessage());
			return "";
		}
	}

	// chiusi db
	public void closeDb() {
		try {
		 
			 
					connection.close();
		 
		} catch (SQLException e) {
			addErr("closeDb", e.getMessage());
		}
	}

	public String getConnId() {
		String s;
		try {
			s = connection.toString();
		} catch (NullPointerException _ex) {
			s = "none";
		}
		return s;
	}

	 

	public boolean nextRecord() {
		try {
			if (!rs.next())
				return false;
			return true;
		} catch (Exception e) {
			addErr("nextRecord", e.getMessage());
		 
			return false;
		}
	}

	public boolean nextRecord(int prog) {
		try {
			if (!arr_rs[prog].next())
				return false;
			return true;
		} catch (Exception e) {
			addErr("nextRecord", e.getMessage());
			;
			return false;
		}
	}

	/**
	 * dato un nome colonna ritorno il valore in stringa
	 */
	public String getFieldName(String fldName) {
		try {
			int x = posCampo(rsmd, fldName);
			return this.getRealValue(rs, x, rsmd.getColumnTypeName(x).toUpperCase());
		}

		catch (Exception e) {
			addErr(fldName + " getFieldName", e.getMessage());
		 	return "";
		}
	}

	public String getFieldName(String fldName, int prog) {
		try {
			int x = posCampo(arr_rsmd[prog], fldName);
			return this.getRealValue(arr_rs[prog], x, arr_rsmd[prog].getColumnTypeName(x).toUpperCase());
		}

		catch (Exception e) {
			addErr(fldName + " getFieldName", e.getMessage());
			;
			return "";
		}
	}

	/** data una posizione colonna ritorno il valore in stringa */
	public String getFieldPos(int fldPos) {
		try {
			return this.getRealValue(rs, fldPos, rsmd.getColumnTypeName(fldPos).toUpperCase());
		} catch (Exception e) {
			addErr("getFieldPos ind:" + String.valueOf(fldPos), e.getMessage());
			return "";
		}
	}

	public String getFieldPos(int fldPos, int prog) {
		try {
			return this.getRealValue(arr_rs[prog], fldPos, arr_rsmd[prog].getColumnTypeName(fldPos).toUpperCase());
		} catch (Exception e) {
			addErr("getFieldPos ind:" + String.valueOf(fldPos), e.getMessage());
			return "";
		}
	}
    
	public byte[] getBytes(String fldName) {
		try 
		{
		   return rs.getBytes(fldName);
		} catch (Exception e) {
			addErr("getBytes  name:" + fldName, e.getMessage());
			return null;
		}
	}

	public String getField(String fldName) {
		return this.getFieldName(fldName).trim();
	}

	/** data una posizione colonna ritorno il nome -database del campo */
	public String getNamePos(int fldPos) {
		try {
			String value = rsmd.getColumnLabel(fldPos);
			return value;
		}

		catch (Exception e) {
			addErr("getNamePos", e.getMessage());
			return "";
		}
	}

	public String getNamePos(int fldPos, int prog) {
		try {
			String value = arr_rsmd[prog].getColumnLabel(fldPos);
			return value;
		}

		catch (Exception e) {
			addErr("getNamePos", e.getMessage());
			return "";
		}
	}

	/** ritorno dato numerico double */
	public long getFieldLong(String fldName) {
		try {
			return rs.getLong(fldName);
		} catch (Exception e) {
			addErr("getFielLong-byname", e.getMessage());
			return 0;
		}
	}

	public long getFieldLong(int fldPos) {
		try {
			return rs.getLong(fldPos);
		}

		catch (Exception e) {
			addErr("getFielLong-bynum", e.getMessage());
			return 0;
		}
	}

	/** ritorno dato numerico double */
	public double getFieldDouble(String fldName) {
		try {
			return rs.getDouble(fldName);
		}

		catch (Exception e) {
			addErr(fldName + " :getFielDouble-byname", e.getMessage());
			return 0;
		}
	}

	public double getFieldDouble(String fldName, int prog) {
		try {
			return arr_rs[prog].getDouble(fldName);
		}

		catch (Exception e) {
			addErr(fldName + " :getFielDouble-byname", e.getMessage());
			return 0;
		}
	}

	public double getFieldDouble(int fldPos) {
		try {
			return rs.getDouble(fldPos);
		}

		catch (Exception e) {
			addErr("getFielDouble-bynum", e.getMessage());
			return 0;
		}
	}

	public double getFieldDouble(int fldPos, int prog) {
		try {
			return Double.parseDouble(
					this.getRealValue(arr_rs[prog], fldPos, arr_rsmd[prog].getColumnTypeName(fldPos).toUpperCase()));
		} catch (Exception e) {
			addErr("getFielDouble-bynum", e.getMessage());
			return 0;
		}
	}

	/** da una posizione ritorno la dimensione del campo */
	public int getFieldSize(int fldPos) {
		try {
			return rsmd.getColumnDisplaySize(fldPos);
		} catch (Exception e) {
			addErr("getFieldSize", e.getMessage());
			return 0;
		}
	}

	public int getFieldSize(int fldPos, int prog) {
		try {
			return arr_rsmd[prog].getColumnDisplaySize(fldPos);
		} catch (Exception e) {
			addErr("getFieldSize", e.getMessage());
			return 0;
		}
	}

	/** da una posizione ritorno il tipo dato del campo */
	public String getFieldType(int fldPos) {
		try {
			return rsmd.getColumnTypeName(fldPos);
		} catch (Exception e) {
			addErr("getFieldType", e.getMessage());
			return "";
		}
	}

	public String getFieldType(int fldPos, int prog) {
		try {
			return arr_rsmd[prog].getColumnTypeName(fldPos);
		} catch (Exception e) {
			addErr("getFieldType", e.getMessage());
			return "";
		}
	}

	public ResultSet getRs() {
		return rs;
	}
	/** tutto un recordset come array di stringhe */
	public String[] recordAsArray() {
		try {
			String[] arr = new String[columnCount - 1];
			for (int i = 1; i <= columnCount; ++i) {
				// arr[i-1] = rs.getString(i);
			}
			return arr;
		} catch (Exception e) {
			addErr("record as array  ", e.getMessage());
			return null;
		}
	}

	/**
	 * tutto un recordset come hastable stringhe partendo dall'ultima query
	 * eseguita dall'oggetto db
	 */
	public RsTable getRsTable() {
		try {
			RsTable rst = new RsTable();
			return rst.getAllRow(rs);
		} catch (Exception e) {
			addErr("record as RsTable  ", e.getMessage());
			return null;
		}
	}

	/** tutto un recordset come hastable stringhe */
	public RsTable getRsTable(int progr) {
		try {
			RsTable rst = new RsTable();
			return rst.getAllRow(this.arr_rs[progr]);
		} catch (Exception e) {
			addErr("record as RsTable  ", e.getMessage());
			return null;
		}
	}

	/**
	 * tutto un recordset come hastable stringhe dall'istruzione sql passata in
	 * caso di errore torna una rstable vuota
	 */
	public RsTable getRsTable(String aQuerySql) {
		RsTable rst = new RsTable();
		try {
			if (!this.execQuery(aQuerySql))
				return rst;
			rst.getAllRow(rs);
			this.closeQuery();
			return rst;
		} catch (Exception e) {
			outS("record as RsTable  "+e.getMessage());
			return rst;
		}
	}

	
	
	public JSONArray getJsonArray(String aQuerySql, boolean flagClose) {
		JSONArray json = new JSONArray();
		try {
			if (!this.execQuery(aQuerySql))
				return json;
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()) {
			  int numColumns = rsmd.getColumnCount();
			  JSONObject obj = new JSONObject();
			  for (int i=1; i<=numColumns; i++) {
			    String column_name = rsmd.getColumnName(i);
			    obj.put(column_name, rs.getObject(column_name));
			  }
			  json.put(obj);
			}
			return json;
		} catch (Exception e) {
			addErr("getJsonArray  ", e.getMessage());
			return json; 
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}
	
	
	
	public RsTable getRsTable(String aQuerySql, boolean flagClose) {
		RsTable rst = new RsTable();
		try {
			if (!this.execQuery(aQuerySql))
				return rst;
			rst.getAllRow(rs);
			this.closeQuery();
			sys.debug("Num.record estratti: " + rst.size());
			return rst;
		} catch (Exception e) {
			addErr("record as RsTable  ", e.getMessage());
			return rst;
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}
	public RsTable getRsTable(String aQuerySql, boolean flagClose,boolean alwaysNamesLowerCase) {
		RsTable rst = new RsTable();
		try {
			if (!this.execQuery(aQuerySql))
				return rst;
			rst.namesToLower=alwaysNamesLowerCase;
			rst.getAllRow(rs);
			this.closeQuery();
			sys.debug("Num.record estratti: " + rst.size());
			return rst;
		} catch (Exception e) {
			addErr("record as RsTable  ", e.getMessage());
			return rst;
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}
	public RsTable getRsTablePS(boolean flagClose) {
		RsTable rst = new RsTable();
		try {
			rs = ps.executeQuery();
			rsmd = ps.getMetaData();
			//if (!this.executeProcedure())
			//	return rst;
			rst.getAllRow(rs);
		 	sys.debug("Num.record estratti: " + rst.size());
			return rst;
		} catch (Exception e) {
			addErr("record as RsTable  ", e.getMessage());
			return rst;
		} finally {
			if (flagClose)
				this.closeDb();
		}
	}
  
	private String getRealValue(ResultSet rs, int aPos, String aType) {
		try {
			// if (rs.wasNull()) return "";
			if (aType.equalsIgnoreCase("DATE")) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				return format.format(rs.getDate(aPos));
			}
			String aValue = rs.getString(aPos);
			if (aValue == null)
				return "";
			if (aType.equals("NUMERIC") | aType.equals("DECIMAL") | aType.equals("BIGDECIMAL") | aType.equals("INTEGER")
					| aType.equals("DOUBLE PRECISION") | aType.equals("DOUBLE") | aType.equals("FLOAT"))
				return aValue.replace('.', ',');

			return aValue;
		} catch (Exception e) {
			addErr("getRealValue", e.getMessage() + " posizione: " + aPos + " type:" + aType);
			return "";
		}
	}

	private int posCampo(ResultSetMetaData rsmd, String aValue) {
		try {
			int x = 0;
			for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
				if (aValue.toUpperCase().equals(rsmd.getColumnName(i)))
					x = i;
			}
			return x;
		} catch (Exception e) {
			addErr("posCampo", e.getMessage());
			return 0;
		}
	}

	// * torna true se � presente nel resultset la colonna col nome passato*/
	public boolean containsRow(String aValue) {
		try {
			int x = 0;
			for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
				if (aValue.toUpperCase().equals(rsmd.getColumnName(i)))
					x = i;
			}
			if (x > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			addErr("contains row", e.getMessage());
			return false;
		}
	}

	/** si sono verificati errori ? */
	public boolean hasError() {
		if (str_errori.equals(""))
			return false;
		else
			return true;
	}

	/** LA CONNESSIONE � chiusa ? */
	public boolean isClosed() {
		try {
			return connection.isClosed();
		} catch (Exception e) {
			return true;
		}

	}

	/**
	 * imposta la possibilit� di fare commit o rollback su una specifica
	 * transazione
	 */
	public void startTrans() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			addErr("startTrans", e.getMessage());
		}
	}

	//
	public void commitTrans() {
		try {
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			addErr("commitTrans", e.getMessage());
		}
	}

	//
	public void rollbackTrans() {
		try {
			connection.rollback();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			addErr("commitTrans", e.getMessage());
		}
	}

	//
	public void setLoader(String aVal) {
		forceLoader = aVal;
	}

	/**
	 * trasforma la riga corrente in una hashtable con chiave nome-campo e
	 * valore valore-campo
	 */

	public Hashtable recToHash() {
		try {
			String aVal;
			Hashtable h = new Hashtable();
			for (int i = 1; i <= columnCount(); ++i) {
				h.put(getNamePos(i), (Object) getFieldPos(i));
			}
			return h;
		} catch (Exception e) {
			return null;
		}
	}

	/* segnalazione errori standard + stringa interna */
	protected void addErr(String aStep, String aError) {
		error(aStep + " -> " + aError);
		str_errori += aStep + " -> " + aError + "\n";
	}

	protected void getTrace(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		StringBuffer error = stringWriter.getBuffer();
		str_trace += "stack--->" + error.toString();
	}

	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * il nome dell' id del database che sto usando es: AS400,MYSQL da cui si
	 * ricavano in params.ini le caratteristiche della connessione
	 */

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getTextRs() {
		try {
			return RsText.getRs(rs);
		} catch (Exception e) {
			addErr("getTextRs", e.getMessage());
			return "";
		}
	}
	public void startProc() {
		entProg = 0;
		entList = new Vector<SqlEnt>();
	}

	public void addProc(String a, String b, HttpServletRequest req,int aMaxL) {
		entList.add(new SqlEnt(a, b, req.getParameter(a),aMaxL,true));
	}
	public void addProc(String a, String b, String c,int aMaxL,boolean isUpdatable) {
		entList.add(new SqlEnt(a, b, c,aMaxL,isUpdatable));
	}
	public void addProc(String a, String b, String c,int aMaxL) {
		entList.add(new SqlEnt(a, b, c,aMaxL,true));
	}
	public void addProc(String a, String b, int c,int aMaxL,boolean isUpdatable) {
		entList.add(new SqlEnt(a, b, String.valueOf(c),aMaxL,isUpdatable));
	}
	public void addProc(String a, String b, double c,int aMaxL,boolean isUpdatable) {
		entList.add(new SqlEnt(a, b, String.valueOf(c),aMaxL,isUpdatable));
	}
	public void addProc(String a, String b,  byte[] aBytes) {
		entList.add(new SqlEnt(a, b,  aBytes));
	}
	
	public int execProc(String azione, String tableName, String keyParam, String returningInfo) {
	    int ret=0; 
		try {
			String sqlIn = SqlEnt.getSql(this.entList, azione, tableName,keyParam,returningInfo);
			sys.debug(sqlIn);
			PreparedStatement ps = connection.prepareStatement(sqlIn);
			ps = SqlEnt.completeStatement(sys,this.entList, ps,azione);
			if(null==ps)
			{	sys.error(SqlEnt.getParmList(this.entList));
				return 0;
			}
			ps.execute();
			if(notBlank(returningInfo))
			{
			  ResultSet last_updated  = ps.getResultSet();
			  last_updated.next();
			  ret=last_updated.getInt(1);
			} else {
				ret=1;
			}
			ps.close();
			ps = null;
			return ret;
		} catch (SQLException e1) {
			sys.error("execProcedure.sqlex:" + e1.getMessage());
			str_errori="execProcedure.sqlex:" + e1.getMessage();
			return 0;
		} catch (Exception e) {
			sys.error(e);
			str_errori="execProcedure:" + e.getMessage();
			return 0;
		} finally {
			 
		}
	}
	public int execProc(String azione, String tableName, String keyParam) {
       return execProc(azione,tableName,keyParam,"");
	}
	 

}