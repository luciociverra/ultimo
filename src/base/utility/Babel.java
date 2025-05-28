package base.utility;

import java.util.Hashtable;

import base.db.DbConn;
import base.db.RsTable;

public class Babel {

	public static Hashtable<String, String[]> getDizionario() {
		return dizionario;
	}
 
	public static String getTableName() {
		return tableName;
	}


	

	private static Hashtable<String, String[]> dizionario;
	private static Hashtable<Integer, String> strutturaLingue;
	public static String tableName = "babel";
	public static Hashtable<String,String[]> flags;
	public static Hashtable<Integer,String> lngShort;
	

	/** LINGUE GESTITE E BANDIERE **/
	public static  Hashtable<String,String[]> getFlags() {
	  return flags;
	}
	
	public static void loadFlags(RsTable rs) {
		  flags = new Hashtable<String,String[]>();
		  lngShort = new Hashtable<Integer,String>();
		  
		  strutturaLingue=new Hashtable<Integer,String>();
		 // flags.put("0",new String[]{"/flags/it.png","Italiano"});
		  while(rs.next()) {
			  String des=rs.getFieldTrim("longcode");
			  flags.put(rs.getFieldTrim("shortcode"),des.split(";"));
			  lngShort.put(rs.getFieldInt("shortcode"),des.split(";")[2]);
			  
		  } 
	  }	
	/***/
	public static boolean isEmpty() {
		try {
			return dizionario.isEmpty();
		} catch (Exception d) {
			return true;
		}
	}

	public static int size() {
		try {
			return dizionario.size();
		} catch (Exception e) {
			return 0;
		}
	}
	

    // POSSO CARICARE BABEL DA + DATABASE BASTA DEFINIRLI IN context.xml
	public static void loadAll() {
		try {
			dizionario = new Hashtable<String, String[]>();
		 
			String dbReal=WebContext.getEnv("DBLNG");
			DbConn db = new DbConn(new Sys());
			db.connect(dbReal);
			RsTable rs = db.getRsTable("SELECT * FROM " + tableName, true); // chiudo connessione
			while (rs.next()) {
				String aKey = rs.getFieldTrim(1);
				String[] aDati = { rs.getFieldTrim(1), rs.getFieldTrim(2), rs.getFieldTrim(3), rs.getFieldTrim(4),
						rs.getFieldTrim(5), rs.getFieldTrim(6), rs.getFieldTrim(7), rs.getFieldTrim(8),
						rs.getFieldTrim(9), rs.getFieldTrim(10) };
				dizionario.put(aKey, aDati);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static DbConn getDb() {
		try {
		    String dbReal=WebContext.getEnv("DBLNG");
			DbConn db = new DbConn(new Sys());
			db.connect(dbReal);
		    return db;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static String translate(String aLemma) {
		if (dizionario.containsKey((Object) aLemma)) {
			String[] voci = dizionario.get((Object) aLemma);
			return (String) voci[0];
		} else {
			return aLemma;
	   }
	}

	public static String translate(String aLemma, int position) {
		try {
			if (dizionario.containsKey((Object) aLemma)) {
				String[] voci = dizionario.get((Object) aLemma);
				return voci[position];
			} else {
				return "";
			}

		} catch (Exception ex) {
			return "";
		}
	}

	public static String translate(String aLemma, int position, String aDef) {
		try {
			String ret = translate(aLemma, position);
			if (ret.equals(""))
				return aDef;
			return ret;
		} catch (Exception ex) {
			return aDef;
		}
	}

	public static boolean isTranslated(String aLemma) {
		try {
			return dizionario.containsKey(aLemma);
		} catch (Exception e) {
			return false;
		}
	}

	public static void update(String ln1, String ln2, String ln3, String ln4, String ln5, String ln6, String ln7,
			String ln8, String ln9, String lng10) {
		try {
			String[] voci = new String[] { ln1, ln2, ln3, ln4, ln5, ln6, ln7, ln8, ln9, lng10 };
			dizionario.put(ln1, voci);
		} catch (Exception ex) {
		}
	}

	public static void clear() {
		dizionario = null;
	}
	public static boolean updateDb(Sys sys, Hashtable<Integer, String> diz) {
		try {
			DbConn db = new DbConn(sys);
			db.connect(WebContext.getEnv("DBLNG"));
			db.startProc();
			db.addProc("lng00", "s", diz.get(0), 250, true);
			db.addProc("lng01", "s", diz.get(1), 250, true);
			db.addProc("lng02", "s", diz.get(2), 250, true);
			db.addProc("lng03", "s", diz.get(3), 250, true);
			db.addProc("lng04", "s", diz.get(4), 250, true);
			db.addProc("lng05", "s", diz.get(5), 250, true);
			db.addProc("lng06", "s", diz.get(6), 250, true);
			db.addProc("lng07", "s", diz.get(7), 250, true);
			db.addProc("lng08", "s", diz.get(8), 250, true);
			db.addProc("lng09", "s", diz.get(9), 250, true);
			int retCode = 0;
			if (dizionario.containsKey(diz.get(0)))
				retCode = db.execProc("UPDATE", tableName, "lng00='" + diz.get(0)+"'");
			else
				retCode = db.execProc("INSERT", tableName, "");
			db.closeDb();
			update(diz.get(0), diz.get(1), diz.get(2), diz.get(3), diz.get(4), diz.get(5), diz.get(6), diz.get(7),
					diz.get(8), diz.get(9));
			return retCode == 1;
		} catch (Exception e) {
			System.out.println("err babel updateDb"+e.getMessage());
			return false;
		}
	}
	
	// secondo parametro S tutte le voci sono state completate 
	public static String[] quota(String aLemma) {
		try {
			int notFound=0;
			int numeroLingueGestite=Babel.getFlags().size();
			if (dizionario.containsKey((Object) aLemma)) {
				String[] voci = dizionario.get((Object) aLemma);
				String ret="";
				for(int i=0;i<numeroLingueGestite;i++)
				{
				  if(voci[i].trim().equals("")) 
					  notFound+=1;
				  else
					  ret+=lngShort.get(i)+" ";
				}
				return new String[]{ret.trim(),notFound>0?"N":"S"};
			} else {
				return new String[]{"","N"};
			}

		} catch (Exception ex) {
			return  new String[]{"","N"};
		}
	}
	
 
}