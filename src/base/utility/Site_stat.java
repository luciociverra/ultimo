package base.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import base.db.RsTable;

/**
 * Title: Standard Internet Sanmarco per applicazioni Java.jsp Description:
 * formattazione layout file statistiche Copyright: Copyright (c) 2001 Company:
 * San marco
 * 
 * @author @version 1.0
 */

public class Site_stat extends BaseBean {

	private String mese;
	private String anno;
	private java.util.Hashtable riga = new Hashtable();
	private String fileElab;
	private RsTable rsRighe=new RsTable();

	public Site_stat() {
	}

	public void esegui() {
		String line;
		if (!trovaFile())
			return;
		try {
			BufferedReader jobs = new BufferedReader(new FileReader(fileElab));
			// Carica gli elementi
			while ((line = jobs.readLine()) != null) {
				if (line.trim().equals(""))
					continue;
				//Carico la riga in vettore
				Vector celle = new Vector();
				caricaVettore(line, riga.size(), celle);
				if (riga.containsKey(celle.firstElement().toString())) {
					Vector celle2 = new Vector();
					celle2 =
						((Vector) riga.get(celle.firstElement().toString()));
					celle = celle2;
					//Controllo variazioni
					caricaVettore(line, riga.size(), celle);
					riga.remove(celle.firstElement().toString());
				}
				riga.put(celle.firstElement().toString(), celle);
			}
			jobs.close();
			
			//Instestazioni
			Vector intestazioni=(Vector)riga.get("Utente");
			
			// Creo RsTable
			rsRighe=new RsTable();
			for (Enumeration e = riga.elements() ; e.hasMoreElements();) {  	
		        Vector rigaTmp=(Vector)e.nextElement();
		        if(!( rigaTmp.firstElement().toString().equals("Utente"))){
		        	 Hashtable rigaRsTmp=new Hashtable();
		        	 rigaRsTmp.put(intestazioni.elementAt(0).toString().trim(),rigaTmp.elementAt(0).toString().trim());
		        	 rigaRsTmp.put(intestazioni.elementAt(1).toString().trim(),rigaTmp.elementAt(1).toString().trim());
		         	 rigaRsTmp.put(intestazioni.elementAt(2).toString().trim(),rigaTmp.elementAt(2).toString().trim());
		         	 rsRighe.addRow(rigaRsTmp);
		        }
			}

			rsRighe.first();
			rsRighe.sortByField("Utente");
			
		} catch (Exception x) {
			error("Sitestat.esegui : " + x.getMessage());
		}
	}

	private void caricaVettore(String line, int rigaSize, Vector celle) {
		String token;
		StringTokenizer st = new StringTokenizer(line, ";");
		int numItems = st.countTokens();
		for (int x = 0; x < numItems; x++) {
			token = st.nextToken().trim();
			//Salto data e ora
			if (x != 1) {
				if (celle.size() - 1 < (x == 0 ? x : x - 1))
					celle.addElement(token);
				else if (x > 1 && rigaSize > 0) {
					celle.setElementAt(
						String.valueOf(
							Integer.parseInt(celle.elementAt(x - 1).toString())
								+ Integer.parseInt(token)),
						x - 1);
				}
			}
		}
	}

	private boolean trovaFile() {
		try {
			fileElab =
				sys.getFolderStat()
					+ sys.getAzienda()
					+ "_"
					+ anno
					+ mese
					+ ".log";
            debug("Site_stat apro file:"+fileElab); 
			File currentDir = new File(fileElab);
			if (!(currentDir.exists()))
				return false;
			return true;
		} catch (Exception x) {
			error("Sitestat.trovaFile : " + x.getMessage());
			return false;
		}
	}

	public void setMese(String newMese) {
		mese = newMese;
	}

	public void setAnno(String newAnno) {
		anno = newAnno;
	}

	public java.util.Hashtable getRiga() {
		return riga;
	}

	public void setFileElab(String newFileElab) {
		fileElab = newFileElab;
	}

	public String getFileElab() {
		return fileElab;
	}

	public String ultimiAccessi() {
		String line;
		int cont = 0;
		try {
			String aa=String.valueOf(Utils.getYearToday());
			this.setAnno(aa);
            String mm=String.valueOf(Utils.getMonthToday() + 1);
            this.setMese(Utils.getNumero(mm,"00"));
			if (!trovaFile())
				return "nessun accesso in " + mese + " " + anno;

			BufferedReader jobs = new BufferedReader(new FileReader(fileElab));
			Vector righe = new Vector();
			while ((line = jobs.readLine()) != null) {
				if (line.trim().equals(""))
					continue;
				//Carico la riga in vettore
				righe.add(line);
				cont++;
			}
			jobs.close();
			String ret="<hr>\\statistiche\\" +  sys.getAzienda()+
			           "_" + aa+ mm+ ".log<hr>";
			try {ret+=righe.elementAt(cont - 4)	+ "<br>";}
			catch(Exception e){}
			try {ret+=righe.elementAt(cont - 3)	+ "<br>";}
			catch(Exception e){}
			try {ret+=righe.elementAt(cont - 2)	+ "<br>";}
			catch(Exception e){}
			try {ret+=righe.elementAt(cont - 1)	+ "<br>";}
			catch(Exception e){}
			
			return ret;
		} catch (Exception x) {
			error("Sitestat.esegui : " + x.getMessage());
			return "Errore: " + x.getMessage();
		}
	}

	
	public RsTable getRsRighe() {
		return rsRighe;
	}
}