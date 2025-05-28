package base.db;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import base.utility.Sys;
import base.utility.Utils;
 

public class RsTable {
	private ResultSetMetaData rmd;
	private Vector vettoreRighe = new Vector();
	private Vector<String> names;
	public  Vector<String> sqlTypes;
 	private int numRighe = 0;
	private int pos = -1;
	private int limit = -1;
	
	private boolean showAll = false;
	private RsTable subRs = null;
	private Gson gson = new Gson();
	//
	public Hashtable<String,Object> struttura=new Hashtable<String,Object>();
	// format. per esportazione
	public String[] arrayFields = null;
	public String[] typeFields = null;
	public String[] titleFields  = null;
	public String[] entIds  = null;
	public boolean namesToLower=false;
	public Hashtable<String,Integer> dataIndex=new Hashtable<String,Integer>();
	public Hashtable tabDati=new Hashtable();
	public int paginateMax=24;
	public int paginateCurrent=1;
	public int paginateLimit=-1;
	
	
	public RsTable()
	{
	}

	public RsTable(String[] array)   {
	  Vector n = new Vector();
	  n.add("");
	  for(String s : array){
		  n.add(s);
	  }
	  this.setNames(n);
	} 
	public RsTable(String aKeyField,String aValField)   {
		  Vector n = new Vector();
		  n.add("");
		  n.add(aKeyField);
		  n.add(aValField);
		  this.setNames(n);
	} 
    public String[] getArrayNames(){
    	try
    	{
    	String[] ret=new String[names.size()-1];
    	for(int i=1;i<names.size();i++){
   		  ret[i-1]=names.elementAt(i);
   	  }
     return ret;
    	} catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
	public RsTable getSubRs() {
		return subRs;
	}

	public void setSubRs(RsTable subRs) {
		this.subRs = subRs;
	}
 
	public int getNumColonne() {
		return names.size()-1;
	}
	/**
	 * Dato un resultset jdbc torna un RsTable riempito con i valori letti dal Database
	 */
	public RsTable getAllRow(ResultSet rs) {
		try {
			rmd = rs.getMetaData();
			names = new Vector<String>();
			sqlTypes = new Vector<String>();
			// Il nome in posizione 0 è sempre vuoto
			names.add("");
			for (int c = 1; c <= rmd.getColumnCount(); c++) {
				   names.add(namesToLower?rmd.getColumnLabel(c).toLowerCase():rmd.getColumnLabel(c));
				   sqlTypes.add(rmd.getColumnTypeName(c).toUpperCase());
			}
			while (rs.next()) {
				Hashtable colonna = new Hashtable();
				for (int i = 1; i <= rmd.getColumnCount(); i++) {
					// Mysql date time non valorizzari danno errori
					String fldName=namesToLower?rmd.getColumnLabel(i).toLowerCase():rmd.getColumnLabel(i);
					try {
						
						if (rs.getString(rmd.getColumnLabel(i)) != null)
							colonna.put(fldName,notNull(rs.getString(i)));
						else
							colonna.put(fldName, "");
					} catch (Exception ua) {
						colonna.put(fldName, "");
					}
				}
				vettoreRighe.add(colonna);
				numRighe++;
			}
			rs = null;
			rmd = null;
			return this;
		} catch (Exception e) {
			return null;
		}
	}

	/** Aggiungo una riga passata come Hashtable ad una RsTable esistente **/
	public void addRow(Hashtable newRiga) {
		try {
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}

	/** Aggiungo una riga ad una RsTable esistente **/
	public void addRow(String chiave1, Object aObject) {
		try {
			Hashtable newRiga = new Hashtable();
			newRiga.put(chiave1, aObject);
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	public void addRowPos(String valore1, String valore2) {
		try {
			Hashtable newRiga = new Hashtable();
			newRiga.put(names.elementAt(1),valore1);
			newRiga.put(names.elementAt(2),valore2);
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	public void addRow(String chiave1, String valore1, String chiave2,
			String valore2) {
		try {
			Hashtable newRiga = new Hashtable();
			newRiga.put(chiave1, valore1);
			newRiga.put(chiave2, valore2);
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	public void addRow(String chiave1, String valore1, String chiave2,
			Object valore2) {
		try {
			Hashtable newRiga = new Hashtable();
			newRiga.put(chiave1, valore1);
			newRiga.put(chiave2, valore2);
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	public void addRow(int pchiave1, String valore1, int pchiave2,String valore2) {
		try {
			Hashtable newRiga = new Hashtable();
			newRiga.put(names.elementAt(pchiave1), valore1);
			newRiga.put(names.elementAt(pchiave2), valore2);
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	public void addRow(String[] arrInfo) {
		try {
			Hashtable newRiga = new Hashtable();
			for (int i = 0; i < arrInfo.length; i++)  
			{
			newRiga.put(names.elementAt(i+1), arrInfo[i]);
			}
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	public void addRow(String str) {
		try {
			String[] arrInfo = str.split(";");
			Hashtable newRiga = new Hashtable();
			for (int i = 0; i < arrInfo.length; i++)  
			{
			newRiga.put(names.elementAt(i+1), arrInfo[i]);
			}
			vettoreRighe.add(newRiga);
			numRighe++;
		} catch (Exception e) {
		}
	}
	/** Aggiungo il valore della riga corrente **/
	public void updateRow(String chiave, String valore) {
		try {
			this.getCurrentRow().put(chiave,valore);
		} catch (Exception e) {
		}
	}
	private String notNull(String aVal) {

		if (null == aVal)
			return "";
		if (aVal.equals("null"))
			return "";
		else
			return aVal;
	}

	/** Come getNumRighe torna il numero di righe dell'attuale RsTable */
	public int size() {
		return numRighe;
	}

	/**
	 * Torna l'attuale posizione del cursore (pos=0 è il primo record pos=-1 è
	 * l'inizio del Database per il ciclo con next)
	 */
	public int getCurrentPos() {
		return pos;
	}

	/** Torna true se non ci sono righe nell'RsTable */
	public boolean isEmpty() {
		return (numRighe == 0);
	}

	/** Torna la prossima riga sottoforma di Hashatable */
	public Hashtable getNext() {
		if (pos < numRighe)
			pos++;
		return (Hashtable) vettoreRighe.elementAt(pos);
	}

	public String getField(String campo) {
		try {
			if (pos == -1) {
				pos = 0;
			}
			Hashtable x = (Hashtable) vettoreRighe.elementAt(pos);
			return notNull((String) x.get(campo)).trim();
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getFieldTrim(String campo) {
		try {
			return getField(campo).trim();
		} catch (Exception e) {
			return "";
		}
	}
	
	public double getFieldDouble(String campo) {
		try {
			return Double.parseDouble(getField(campo));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int getFieldInt(String campo) {
		try {
			return Integer.parseInt(getField(campo));
		} catch (Exception e) {
			return 0;
		}
	}
	public String getFieldValuta(String campo) {
		try {
			return "&euro; "+Utils.getValuta(getField(campo).trim());
		} catch (Exception e) {
			return "";
		}
	}
	public String getFieldNumber(String campo,String numericPattern) {
		try {
			return Utils.getNumero(getField(campo).trim(),numericPattern);
		} catch (Exception e) {
			return "";
		}
	}
	public String getFieldDate(String campo,String dtFormat) {
		try {
			int dataIn=Integer.parseInt(getField(campo));
	        if(dataIn==0) return "";
	        if(dtFormat.equals("gma")|| dtFormat.equals(""))
	        	return Utils.dateGMA(String.valueOf(dataIn), "/");
	        return "";        
		} catch (Exception e) {
			return "";
		}
	}
	
	public Vector getFieldVector(String campo) {
		try {
			if (pos == -1) {
				pos = 0;
			}
			Hashtable x = (Hashtable) vettoreRighe.elementAt(pos);
			return (Vector) x.get(campo);
		} catch (Exception e) {
			return new Vector();
		}
	}
	
	public Object getFieldObject(String campo) {
		try {
		 		if (pos == -1) {
					pos = 0;
				}
				Hashtable x = (Hashtable) vettoreRighe.elementAt(pos);
				return (Object) x.get(campo);
			 
		} catch (Exception e) {
			return null;
		}
	}

	public Hashtable getFieldHash(String campo) {
		try {
			if (pos == -1) {
				pos = 0;
			}
			Hashtable x = (Hashtable) vettoreRighe.elementAt(pos);
			return (Hashtable) x.get(campo);
		} catch (Exception e) {
			return new Hashtable();
		}
	}
	public String getFieldJson(String campo,String aKey) {
		try {
			String aStr=getFieldTrim(campo);
		    Hashtable<String,String> map = gson.fromJson(aStr, Hashtable.class);
			return map.get(aKey).trim();
		} catch (Exception e) {
			return "";
		}
	}
	public Hashtable getFieldJsonHash(String campo) {
		try {
			String aStr=getFieldTrim(campo);
		    Hashtable<String,String> map = gson.fromJson(aStr, Hashtable.class);
			return map;
		} catch (Exception e) {
			return new Hashtable();
		}
	}
	public Vector<Hashtable> getFieldJsonVector(String campo) {
		try {
			String aStr=getFieldTrim(campo);
		    Vector<Hashtable> rsTable=gson.fromJson(aStr, Vector.class);
			return rsTable;
		} catch (Exception e) {
			return new Vector();
		}
	}
	public ArrayList<Object> getFieldJsonArrayList(String campo) {
		try {
			String aStr=getFieldTrim(campo);
		    ArrayList<Object> map = gson.fromJson(aStr, ArrayList.class);
		    if(null==map) return new  ArrayList<Object>();
			return map;
		} catch (Exception e) {
			return new  ArrayList<Object>();
		}
	}
	public String[] getFieldJsonStringArray(String campo) {
		try {
			String aStr=getFieldTrim(campo);
			Type type = new TypeToken<List<String>>(){}.getType();
		    List<String> map = gson.fromJson(aStr, type);
		    if(null==map) return new String[]{};
			return map.toArray(new String[0]);
		} catch (Exception e) {
			return new String[]{};
		}
	}
	
	public RsTable getFieldRsTable(String campo) {
		try {
		 		if (pos == -1) {
					pos = 0;
				}
				Hashtable x = (Hashtable) vettoreRighe.elementAt(pos);
				return (RsTable) x.get(campo);
			 
		} catch (Exception e) {
			return null;
		}
	}

	public String getField(int indCampo) {
		try {
			if (pos == -1) {
				pos = 0;
			}
			String name = names.elementAt(indCampo);
			return getField(name);
		} catch (Exception e) {
			return "";
		}
	}
	public String getFieldTrim(int indCampo) {
		try {
			if (pos == -1) {
				pos = 0;
			}
			String name =names.elementAt(indCampo);
			return getFieldTrim(name);
		} catch (Exception e) {
			return "";
		}
	}
	public String getFieldLike(String nomeParziale) {
		try {
		 		for(String aName: this.names) {
					if(aName.indexOf(nomeParziale)>=0)
						return getFieldTrim(aName);
				}
		 		return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * restituisce vettoreRighe ovvero tutto il vettore di hashtable
	 * dell'oggetto
	 */

	public Vector getVectorRighe() {
		if (vettoreRighe == null)
			return new Vector();
		else
			return vettoreRighe;
	}
	public void setVectorRighe(Vector v) {
		vettoreRighe=v;
		this.numRighe=v.size();
		this.pos=0;
	}
	/** Avanza il cursore di una posizione */
	public boolean next() {
		numRighe=limit>=0 ? limit : numRighe;
		if (pos < numRighe-1) {
			pos++;
			return true;
		} else
			return false;
	}

	 
	/**
	 * imposta l'indice corrente del vettore di hashtable di cui è composto
	 * l'oggetto
	 */
	public void setPos(int aPos) {
		pos = aPos;
	}

	/** Sposta il cursore indietro di una posizione */
	public boolean previous() {
		if (pos > 0) {
			pos--;
			return true;
		} else
			return false;
	}

	/** Sposta il cursore all'inizio dell'RsTable (prima del primo elemento) */
	public boolean first() {
		pos = -1;
		return true;
	}

	public Hashtable getPrevious() {
		if (pos > 0)
			pos--;
		return (Hashtable) vettoreRighe.elementAt(pos);
	}

	public void sortByFieldStringComp(String aFieldName) {
		int aPos=0;
		String[] righe = new String[numRighe];
		while (aPos < numRighe) {
			Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
			righe[aPos] = (String) x.get(aFieldName);
			aPos++;
		}
		Arrays.sort(righe,new IntuitiveStringComparator<String>());
		sortByKeys(righe, aFieldName);
		this.first();
	}
	
	public void sortByField(String aFieldName) {
		int aPos=0;
		String[] righe = new String[numRighe];
		while (aPos < numRighe) {
			Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
			righe[aPos] = (String) x.get(aFieldName);
			aPos++;
		}
		Arrays.sort(righe);
		sortByKeys(righe, aFieldName);
		this.first();
	}
	
	public void sortByFieldAsc(String aFieldName) {
	      try {
	  		int aPos = 0;
	  		String[] righe = new String[numRighe];
	  		while (aPos < numRighe) {
	  		  Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
	  		  righe[aPos] = (String) x.get(aFieldName);
	  		  aPos++;
	  		}
	  		Arrays.sort(righe, new IntegerComparatorAsc());
	  		sortByKeys(righe, aFieldName);
	  		this.first();
	  	} catch (RuntimeException e) {
	  		System.out.println("errore sortByFieldDesc "+aFieldName+" "+ e.getMessage());
	  	}
	}
	public void sortByFieldDesc(String aFieldName) {
	      try {
	  		int aPos = 0;
	  		String[] righe = new String[numRighe];
	  		while (aPos < numRighe) {
	  		  Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
	  		  righe[aPos] = (String) x.get(aFieldName);
	  		  aPos++;
	  		}
	  		Arrays.sort(righe, new IntegerComparatorDesc());
	  		sortByKeys(righe, aFieldName);
	  		this.first();
	  	} catch (RuntimeException e) {
	  		System.out.println("errore sortByFieldDesc "+aFieldName+" "+ e.getMessage());
	  	}
	}

	public RsTable removeRow(int posToRemove) {
		RsTable ret=this.getCloneEmpty();   
		try {
	     	int aPos = 0;
	  		while (aPos < numRighe) {
	  		  if(aPos!=posToRemove) 
	  			 ret.addRow((Hashtable) vettoreRighe.elementAt(aPos));
	  		  aPos++;
	  		}
	  		return ret;
		}
	catch(Exception e) {
		return this;	
	  }
	}
	
	public RsTable sortByFieldDouble(String aFieldName,String verso) {
		RsTable ret=this.getCloneEmpty();   
		try {
	     	int aPos = 0;
	  		ArrayList<RsTableObj> righe = new ArrayList<RsTableObj>() ;
	  		while (aPos < numRighe) {
	  		  Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
	  		  double d=Utils.toDouble((String)x.get(aFieldName));  
	  		  righe.add(new RsTableObj(d,aPos));
	  		  aPos++;
	  		}
	  	  	Vector Righe = new Vector();
	  		
	  		
	  		Collections.sort(righe);
	  		if(verso.equals("desc"))
		  	  	  Collections.reverse(righe);
	  
	  	    for (RsTableObj obj : righe) {
	  	     	ret.addRow(this.getRowAt(obj.chiave));
			}
			
		//	ret.first();
		//	while(ret.next()) {System.out.println(ret.getField("price"));}

			ret.first();
	  		return ret;
	  	} catch (RuntimeException e) {
	  		e.printStackTrace();
	  		System.out.println("errore sortByFieldDoubleDesc "+aFieldName+" "+ e.getMessage());
	  		return ret;
	  	}
	}
	/**
	 * Ordina la Tabella RsTable rispetto al campo Field seguendo l'array di
	 * chiavi passate
	 */
	private void sortByKeys(String[] aKeysOrder, String aFieldName) {
		Vector Righe = new Vector();
		for (int i = 0; i < numRighe; i++) {
			String value = aKeysOrder[i];
			Hashtable r = this.selectRow(aFieldName, value);
			vettoreRighe.removeElement(r);
			Righe.add(r);
		}

		this.loadRighe(Righe);
	}
	/**
	 * Torna la prima riga di RsTable il cui campo Field e uguale a Value null
	 * se non trovata
	 */
	public Hashtable selectRow(String aFieldName, String aValue) {
		int aPos = 0;
		Hashtable ret = null;
		boolean trovato = false;
		while (aPos < numRighe) {
			Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
			String value = (String) x.get(aFieldName);
			if (value.trim().equals(aValue.trim())) {
				ret = x;
				trovato = true;
				break;
			}
			aPos++;
		}
		// prima di uscire mi riposiziono prima del primo elemento
		pos = -1;
		return (trovato ? ret : null);
	}
	/**
	 * Torna vero falso sul criterio di ricerca campo valorela prima riga di RsTable il cui campo Field e uguale a Value null
	 * se non trovata
	 */
	public boolean containsValue(String aFieldName, String aValue) {
		int aPos = 0;
		boolean trovato = false;
		while (aPos < numRighe) {
			Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
			String value = (String) x.get(aFieldName);
			if (value.trim().equals(aValue.trim())) {
				trovato = true;
				break;
			}
			aPos++;
		}
		return trovato;
	}
	/**
	 * Torna un RsTable sottoinsieme dell'RSTable originale con le righe che
	 * soddisfano alla condizione campo aFieldName=aValue
	 */
	public RsTable selectRows(String aFieldName, String aValue) {
		int aPos = 0;
		RsTable result = new RsTable();
		try
		{
		result.setNames(this.getNames());
		while (aPos < numRighe) {
			Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
			String value = (String) x.get(aFieldName);
			if (value.trim().equals(aValue.trim())) {
				result.addRow(x);
			}
			aPos++;
		}
		} catch(Exception e){}
		return result;
	}
	public int getPositionWhere(String aFieldName, String aValue) {
		int aPos = 0;
		try
		{
		while (aPos < numRighe) {
			Hashtable x = (Hashtable) vettoreRighe.elementAt(aPos);
			String value = (String) x.get(aFieldName);
			if (value.trim().equals(aValue.trim()))	return aPos;
			aPos++;
		}
		return -1;
		} catch(Exception e){
			return -1;
		}
		
	}
	/**
	 * Torna la riga corrente sottoforma di hastable null se RsTable vuoto
	 */
	public Hashtable getCurrentRow() {
		if (pos == -1) {
			pos = 0;
		}
		Hashtable x = null;
		try {
			x = (Hashtable) vettoreRighe.elementAt(pos);
		} catch (Exception e) {
		}
		return x;
	}

	/** Carica l'oggeto RsTable con il vector di righe passato */
	public void loadRighe(Vector VRighe) {
		int aNumRighe = VRighe.size();
		vettoreRighe = VRighe;
		numRighe = aNumRighe;
	}

	/** aggiunge al vettore corrente altro vettore (con medesima struttura) */
	public void appendRighe(Vector VRighe) {
		for (Enumeration e = VRighe.elements(); e.hasMoreElements();) {
			this.addRow((Hashtable) e.nextElement());
		}
	}

	/** Torna un oggetto clone del presente */
	public RsTable getClone() {
		int aPos;
		aPos = 0;

		Vector Righe = new Vector();

		while (aPos < numRighe) {
			Hashtable colonna = (Hashtable) vettoreRighe.elementAt(aPos);
			Righe.add(colonna);
			aPos++;
		}

		RsTable newRS = new RsTable();
		newRS.loadRighe(Righe);
		newRS.setNames(this.getNames());
		return newRS;
	}
	/** Torna un oggetto clone del presente solo struttura */
	public RsTable getCloneEmpty() {
		RsTable nuova = new RsTable();
		nuova.setNames(this.names);
		return nuova;
	}
	public String asOptions(String chiaveSel){
		String ret="<option></option>";
		try
		{
		int aPos=0;
		while (aPos < numRighe) {
		    Hashtable row = (Hashtable) vettoreRighe.elementAt(aPos);
		    String k=(String)row.get((String)names.elementAt(1));
		    String v=(String)row.get((String)names.elementAt(2));
		    String s=(k.equalsIgnoreCase(chiaveSel))?" selected":"";
			ret+="<option value='"+k+"' "+s+">"+v.trim()+"</option>";
		    aPos++;
		}
		} catch(Exception e){}
		finally {
	    return ret;
		}
	}
	public String asOptions(String chiaveSel,String campoChiave,String campoValore){
		String ret="<option></option>";
		try
		{
		int aPos=0;
		while (aPos < numRighe) {
		    Hashtable row = (Hashtable) vettoreRighe.elementAt(aPos);
		    String k=(String)row.get(campoChiave);
		    String v=(String)row.get(campoValore);
		    String s=(k.equalsIgnoreCase(chiaveSel))?" selected":"";
			ret+="<option value='"+k+"' "+s+">"+v.trim()+"</option>";
		    aPos++;
		}
		} catch(Exception e){}
		finally {
	    return ret;
		}
	}
	/** Torna un oggetto clone del presente */
	public String debug() {
		String ret="<table><tr>";
		try{
			for (int i=1;i<names.size();i++) {
				ret+="<td>"+ names.elementAt(i) +"</td>";
			}
			ret+="</tr>";
			int aPos=0;
			while (aPos < numRighe) {
			    Hashtable row = (Hashtable) vettoreRighe.elementAt(aPos);
			   	ret+="<tr>";
			   	for (int i=1;i<names.size();i++) {
					ret+="<td>"+ (String)row.get((String)names.elementAt(i)) +"</td>";
					}
			    ret+="</tr>";
			aPos++;
		}
		return ret+"</table>";
		} catch(Exception e)
		{ 
			return "";
		}
	}
	public void debug(Sys sys) {
		try {
	     sys.debug(this.debug());
	     this.first();
	    } catch(Exception e)
		{ 
		}
	}
	public String asTable(boolean nomiCampi,boolean firstRowHead,String classInfo) {
		String ret="<table border='1' class='"+classInfo+"'>";
		try{
			if(nomiCampi)
			{	
			ret+="<thead><tr>";	
				for (int i=1;i<names.size();i++) {
					ret+="<td>"+ names.elementAt(i) +"</td>";
				}
				ret+="</tr></thead>";
			}
			int aPos=0;
			if(firstRowHead)
			{	
			  ret+="<thead><tr>";
			  Hashtable row = (Hashtable) vettoreRighe.elementAt(aPos);
			  for (int i=1;i<names.size();i++) {
			 	ret+="<td>"+ (String)row.get(names.elementAt(i)) +"</td>";
			  }
			  ret+="</tr></thead>";
			  pos+=1;
			}
			
			while (aPos < numRighe) {
			    Hashtable row = (Hashtable) vettoreRighe.elementAt(aPos);
			   	ret+="<tbody><tr>";
			   	for (int i=1;i<names.size();i++) {
					ret+="<td>"+ (String)row.get((String)names.elementAt(i)) +"</td>";
					}
			    ret+="</tr>";
			aPos++;
		}
		return ret+"<tbody></table>";
		} catch(Exception e)
		{ 
			return "";
		}
	}
	/**
	 * restituisce un oggetto RsTable come risultato di concatenazione di due
	 * rstable (che anno i medesimi campi)
	 */
	public static RsTable concatTable(RsTable r1, RsTable r2) {
		RsTable rNew = r1.getClone();
		rNew.appendRighe(r2.getVectorRighe());
		return rNew;
	}

	/**
	 * restituisce un oggetto RsTable come risultato di concatenazione di tre
	 * rstable (che anno i medesimi campi)
	 */
	public RsTable concatTable(RsTable r1, RsTable r2, RsTable r3) {
		RsTable rNew = r1.getClone();
		rNew.appendRighe(r2.getVectorRighe());
		rNew.appendRighe(r3.getVectorRighe());
		return rNew;
	}
	public Vector getNames() {
		return names;
	}
	public String getNameAt(int pos) {
	 try{	
		return names.elementAt(pos);
	 } catch(Exception e){
		 return null;
	 }
	}
	public void setNames(Vector aNames) {
		names = aNames;
	}
	public void setProperty(String aKey,String aVal)
	  {
	    if (tabDati.containsKey(aKey)) tabDati.remove(aKey);
	    tabDati.put(aKey,aVal);
	  }
	public void setProperty(String aKey,Object aVal)
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
	  public Object getPropertyObj(String nomeVar)
	  {
	    if (tabDati.containsKey(nomeVar))
	       return  (Object)tabDati.get(nomeVar);
	     else
	       return  null;
	  }

	  public Hashtable getPropertyHash(String nomeVar)
	  {
	    if (tabDati.containsKey(nomeVar))
	       return  (Hashtable)tabDati.get(nomeVar);
	     else
	       return  null;
	  }
   
	  public void addRecId() {
		 try { 
    	  names.add("RECID");
    	  this.first();
    	  int prog=0;
    	  while(next()) {
    		  updateRow("RECID",String.valueOf(prog++));
    	  }
    	  first();
		 } catch(Exception e) {}
      }
    
	 private String haveApic(String type) {
    	  if(type.indexOf("CHAR")>0) return "'";
    	  if(type.indexOf("JSON")==0) return "'";
    	  if(type.indexOf("TEXT")==0) return "'";
    	  if(type.indexOf("INT")==0) return "z";
    	  if(type.indexOf("DEC")==0) return "z";
              	  
    	  return "";
      }
      public String asInsert() {
    	  String nam ="(";
    	  String val =" VALUES (";
    	  for (int i=1;i<names.size();i++) {
    		  String type=sqlTypes.elementAt(i);
  			  String apic=haveApic(type);
  			  String vl=getFieldTrim(i);
  			  nam+= names.elementAt(i)+",";
  			 if(apic.equals("z")) {
  			    if(vl.equals(""))
  				  { val+= "0,"; continue;}
  			    else
  			      { apic ="";}	
  			 }
  		      val+= apic+getFieldTrim(i)+apic+","; 		        
  	  		 // val+= apic+getFieldTrim(i)+"["+type+"]"+apic+","; 		        
  		  }
  		  nam=Utils.eliminaLastChar(nam)+")";
  		  val=Utils.eliminaLastChar(val)+")";
     	  return nam+val;
  	}
      public void addColumn(String aFieldName) {
        	this.names.add(aFieldName);  
        	int aPos = 0;
    		while (aPos < numRighe) {
    			((Hashtable) vettoreRighe.elementAt(aPos)).put(aFieldName,"");
    			aPos++;
    		}
      }   
      public void removeColumn(String aFieldName) {
      	this.names.remove(aFieldName);  
      	int aPos = 0;
  		while (aPos < numRighe) {
  			((Hashtable) vettoreRighe.elementAt(aPos)).remove(aFieldName);
  			aPos++;
  		}
     }   
     
     public void renameColumn(String  oldName,String newName) {
    	  int first_index = names.indexOf(oldName);
    	  names.set(first_index , newName);
    	  int aPos = 0;
  		  while (aPos < numRighe) {
  			 Hashtable h=(Hashtable) vettoreRighe.elementAt(aPos); 
  		 	 h.put(newName,h.remove(oldName));
  			 aPos++;
  		}
     }   
     // aggiunge una hashtable con valore campo (che si presuppone univoco) e posizione nella lista
     public void addDataIndex(String aFieldName) {
 	    dataIndex=new Hashtable<String,Integer>();
 	    int aPos = 0;
 		while (aPos < numRighe) {
 			String vString=(String)((Hashtable) vettoreRighe.elementAt(aPos)).get(aFieldName);
 			dataIndex.put(vString.trim(),aPos);
 			aPos++;
 		}
     }
     public boolean containsAtIndex(String aFieldValue) {
    	 return dataIndex.containsKey(aFieldValue);
     }   
     public Hashtable getRowAtIndex(String aFieldValue) {
    	 if(dataIndex.containsKey(aFieldValue))
    	 { 
    		 return getRowAt(dataIndex.get(aFieldValue)) ;
    	 }		 
       	 return new Hashtable();
     }   
     // clona se stessa con solo la riga selezionata all'indice
     public RsTable getRsTableAtIndex(String aFieldValue) {
    	 RsTable aRs=this.getCloneEmpty();
    	 if(dataIndex.containsKey(aFieldValue))
    	 { 
    		 aRs.addRow(getRowAt(dataIndex.get(aFieldValue)));
    	 }
    	 return aRs;
     }   
     // FINE ADD ON DATA INDEX
     public Hashtable getRowAt(int aPos) {
        	return (Hashtable) vettoreRighe.elementAt(aPos);
     } 
  	 public Hashtable<String,String> asHash(String fldChiave,String fldValore){
  		Hashtable<String,String> ret= new Hashtable<String,String>();
  		try
  		{
     		  while (next()) {
  		         ret.put(this.getField(fldChiave),this.getField(fldValore));
  	          }
            return ret;
  		} catch(Exception e)
  		{
  	     return ret;
  		}
  	}
  	 public void setLimit(int startPos, int maxNumRet) {
  		 this.pos=startPos;
  		 this.limit=maxNumRet;
  	 }
/*
 	public  int paginateMax=30;
 	private int paginateCurrent=1;
*/
  	public void setPage(String aPage) {
  		paginateCurrent=Integer.parseInt(aPage);
 	} 
 	public int getNextPage() {
  		 return paginateCurrent+1;
 	}
  	public void nextPage() {
  		paginateCurrent=paginateCurrent+1;
 	}
  	public void prevPage() {
  		paginateCurrent=paginateCurrent-1;
  		if(paginateCurrent<1)paginateCurrent=1;
 	}

  	public int getPrevPage() {
		int ret=paginateCurrent-1;
  		if(ret<1) return 1;
  		return ret;
	}

  	public boolean hasNextPage() {
  		try
  		{
    	 int nextStart=(paginateMax * paginateCurrent)+1;
  		 return this.size()>=nextStart;
  		} catch(Exception e)
  		{ return false; }
  	}
  	public boolean hasPrevPage() {
  		 return  paginateCurrent>1;
  	}
  	public void startPage() {
  		paginateLimit=this.size()<paginateMax ?this.size():paginateMax;
  		if(paginateCurrent==1) {this.first(); return;}
  		this.setPos(paginateMax * (paginateCurrent-1)-1);
  		paginateLimit=this.pos+(paginateMax+1);
  		if(paginateLimit>this.size()) paginateLimit=this.size();
  	}
  	public boolean nextInPage() {
		if (pos < paginateLimit-1) {
			pos++;
			return true;
		} else {
			return false;
		}
	}
  	// PAGINA 1 di N
  	public String currentPageInfo() {
		int pages=1;
		int max=this.size();
		if(max>paginateMax) 
		{
			pages=max/paginateMax;
			int resto=max%paginateMax;
			if(resto>0) pages+=1;
		}
		return "pagina "+paginateCurrent+" di "+pages;
    }
 	public String currentPageInfoSelect(Sys sys,boolean mostraCombo) {
		int pages=1;
		int max=this.size();
		if(max>paginateMax) 
		{
			pages=max/paginateMax;
			int resto=max%paginateMax;
			if(resto>0) pages+=1;
		}
		String retSelect="";
		if(mostraCombo) { 
			retSelect="<select class='selectPaginate' style='padding:3px'>";
			for (int i=1;i<(pages+1);i++) {
				String isSelect=i==paginateCurrent ?"selected":"";
				retSelect+="<option "+isSelect +" value='"+i+"'>"+i+"</option>";
			} 
			retSelect+="</select>";
		} else {
			retSelect+=paginateCurrent;
		}
		return sys.translate("pagina")+"&nbsp;"+retSelect+"&nbsp;&nbsp;"+sys.translate("di")+"&nbsp;&nbsp;"+pages;
    }

}