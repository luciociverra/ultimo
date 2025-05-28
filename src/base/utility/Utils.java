package base.utility;

/**
 * Title:         Utils
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Sanmarco Informatica - EBusiness
 * @author        Francesco Zorzan \ Civerra Lucio
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.gson.Gson;

public class Utils {

	private Hashtable lingue;
	// finestre temporanee di blocco-sito
	private Hashtable timew;

	private Sys sys;
	private BaseBean smweb = new BaseBean();

	public Utils() {
	}

	public void setSys(Sys aSys) {
		sys = aSys;
	}

	/** funzioni {@link SuppressWarnings} stringhe */

	public static String limit(String aVal, int aLen) {
		try {
			if (aVal.length() <= aLen)
				return aVal;
			return aVal.substring(0, aLen);

		} catch (Exception e) {
			return aVal;
		}
	}

	public static String removeLastChar(String aVal) {
		try {
			return aVal.substring(0, aVal.length() - 1);
		} catch (Exception e) {
			return aVal;
		}
	}

	public static boolean notBlank(String aObj) {
		return !notNull(aObj).trim().equals("");
	}

	public static String notNull(String aValue) {
		if (null == aValue)
			return "";
		return aValue;
	}

	public static String zeroIfNull(String aValue) {
		if (aValue == null)
			return "0";
		if (aValue.equalsIgnoreCase("null"))
			return "0";
		if (aValue.trim().equals(""))
			return "0";
		else
			return aValue;
	}

	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

	// al contrario
	public static String reverse(String input) {
		byte[] strAsByteArray = input.getBytes();
		byte[] result = new byte[strAsByteArray.length];
		// result byte[]
		for (int i = 0; i < strAsByteArray.length; i++)
			result[i] = strAsByteArray[strAsByteArray.length - i - 1];
		return new String(result);
	}

	public static boolean isDateAMG(String aDtIn) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);
		return sdf.parse(aDtIn, new ParsePosition(0)) != null;
	}

	// salta il tipo di separatore A M G
	public static String getDateAMG(String inDate) {
		try {
			return inDate.substring(6, 10) + inDate.substring(3, 5) + inDate.substring(0, 2);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			return "";
		}
	}

	public static String getDateAMG_Html5(String inDate) {
		try {
			if (inDate.trim().equals(""))
				return "0";
			String aData = inDate.replaceAll("-", "");
			return Utils.getDateAMG(aData);
		} catch (Exception ex) {
			return "0";
		}
	}

	public static String dateGMA(String dateIn) {
		try {
			String a = (dateIn.substring(0, 4));
			String m = (dateIn.substring(4, 6));
			String g = (dateIn.substring(6, 8));
			return g + "/" + m + "/" + a;
		} catch (Exception ex) {
			return "";
		}

	}

	// Da gg/mm/aaaa a aaaammgg
	public static String dateToAMG(String dateIn) {
		try {
			String aas = (dateIn.substring(6, 10));
			String mms = (dateIn.substring(3, 5));
			String ggs = (dateIn.substring(0, 2));
			return aas + mms + ggs;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String dateAMG(String dateIn) {
		try {
			String aas = (dateIn.substring(0, 4));
			String mms = (dateIn.substring(5, 7));
			String ggs = (dateIn.substring(8, 10));
			return aas + mms + ggs;
		} catch (Exception ex) {
			return "";
		}

	}

	public static String dateSqlGMA(String dateIn) {
		try {
			String aas = (dateIn.substring(0, 4));
			String mms = (dateIn.substring(5, 7));
			String ggs = (dateIn.substring(8, 10));
			if (!aas.equals("0000"))
				return ggs + "/" + mms + "/" + aas;
			return "-/-/-";
		} catch (Exception ex) {
			return "";
		}

	}

	public static String dateGMA(int gg, String sep) {
		try {
			String dateIn = getSpcDateAMG(gg);
			String aas = (dateIn.substring(0, 4));
			String mms = (dateIn.substring(4, 6));
			String ggs = (dateIn.substring(6, 8));
			return ggs + sep + mms + sep + aas;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String dateAMG(int gg, String sep) {
		try {
			String dateIn = getSpcDateAMG(gg);
			String aas = (dateIn.substring(0, 4));
			String mms = (dateIn.substring(4, 6));
			String ggs = (dateIn.substring(6, 8));
			return aas + sep + mms + sep + ggs;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String dateGMA(String dateIn, String sep)

	{
		try {
			String aas = (dateIn.substring(0, 4));
			String mms = (dateIn.substring(4, 6));
			String ggs = (dateIn.substring(6, 8));
			return ggs + sep + mms + sep + aas;
		} catch (Exception ex) {
			return "";
		}

	}

	public static String dateGMA(Sys sys, String dateIn)

	{
		try {
			String a = (dateIn.substring(0, 4));
			String m = (dateIn.substring(4, 6));
			String g = (dateIn.substring(6, 8));
			if (g.equals("00") && m.equals("00"))
				return "--/--/" + a;
			return g + "/" + m + "/" + a;
		} catch (Exception ex) {
			return "";
		}

	}

	/**
	 * Classe getDateGMA il formato di inDate sar� yyyyMMdd il formato di
	 * getDateAMG sar� dd/mm/yyyy la routine spezza la data in entrata e recuper
	 * gg mm aaaa poi testa con un catch se la data � corretta se non � corretta
	 * restituisce blank altrimenti la data in foramto dd/mm/yyyy
	 */
	public static String getDateGMA(String inDate)

	{
		String ggs = new String();
		String mms = new String();
		String aas = new String();
		String dateIn = new String(inDate);

		try {
			if (Float.parseFloat(inDate) == 0) {
				return "";
			}

			aas = (dateIn.substring(0, 4));
			mms = (dateIn.substring(4, 6));
			ggs = (dateIn.substring(6, 8));

			SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
			String dataStringa = new String(ggs + "/" + mms + "/" + aas);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			String convData = new String((df2.format(df1.parse(inDate))));
			return dataStringa;
		} catch (ParseException ex) {
			return "";
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getDateGM(String dateIn, String sep) {
		try {
			String aas = (dateIn.substring(0, 4));
			String mms = (dateIn.substring(5, 7));
			String ggs = (dateIn.substring(8, 10));
			return ggs + sep + mms;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * Restituisce la data in formato giorno gg/mm/anno weekday
	 */

	public static String getDateGMAW(String inDate)

	{
		int weekDay = 0;

		String nomiDayW[] = { "Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab" };

		String ggs = new String();
		String mms = new String();
		String aas = new String();
		String dateIn = new String(inDate);

		ggs = (dateIn.substring(0, 2));
		mms = (dateIn.substring(3, 5));
		aas = (dateIn.substring(6, 10));

		GregorianCalendar cal = new GregorianCalendar();

		cal.set(Integer.parseInt(aas), (Integer.parseInt(mms) - 1), (Integer.parseInt(ggs)));
		weekDay = cal.get(cal.DAY_OF_WEEK) - 1;
		return (inDate + " " + nomiDayW[weekDay]);
	}

	/**
	 * il numeo del giorno odierno 1..31
	 */
	public static int getDayToday() {
		Calendar currentDate = Calendar.getInstance();
		return currentDate.get(currentDate.DAY_OF_MONTH);
	}

	public static int getMonthToday() {
		Calendar currentDate = Calendar.getInstance();
		return currentDate.get(currentDate.MONTH) + 1;
	}

	public static int getYearToday() {
		Calendar currentDate = Calendar.getInstance();
		return currentDate.get(currentDate.YEAR);
	}

	public static int getDateAMG(int num) {
		return Integer.parseInt(Utils.getSpcDateAMG(num));
	}

	public boolean isDataPassata(int num, String aDataAMG) {
		try {
			return getDateAMG(num) > Integer.parseInt(aDataAMG);
		} catch (Exception e) {
			return false;
		}
	}

	public static String getYearAA() {
		return new SimpleDateFormat("yyyy").format(new Date()).substring(2);
	}

	public static String getDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/** dato un anno e mese restituisce l'ultimo giorno */
	public String ultimoGiorno(int aa, int mm) {
		try {
			if (mm == 4 || mm == 6 || mm == 9 || mm == 11) {
				return "30";
			}
			if (mm == 2) {
				if ((aa % 4) == 0)
					return "29";
				else
					return "28";
			}
			return "31";
		} catch (Exception e) {
			return "1";
		}
	}

	/**
	 * cftDateGMA Confronta 2 date e restituisce TRUE se la prima � inferiore alla
	 * seconda altrimenti FALSE Le date devono entrare nel formato gg/mm/aaaa e
	 * vengono convertitein aaaammgg per effettuare i confronti In uscita si riceve
	 * TRUE o FALSE
	 */

	public static boolean cftDateGMA(String p1Data, String p2Data) {

		p1Data = Utils.getDateAMG(p1Data);
		p2Data = Utils.getDateAMG(p2Data);

		Long n1Data = Long.valueOf(p1Data);
		Long n2Data = Long.valueOf(p2Data);

		if (n2Data.longValue() < n1Data.longValue()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Classe getSpcDateAMG la routine recupera la data corrente in base al numero
	 * di nnGG (numero di giorni) +/- calcola il giorno es. se richiamo
	 * Utils.getSpcDateAMG(0) ottengo giorno corrente formato di output AAAAMMGG
	 */
	public static String getSpcDateAMG(int nnGG) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(currentDate.DAY_OF_MONTH, nnGG);
		int month = (currentDate.get(currentDate.MONTH) + 1);
		int year = currentDate.get(currentDate.YEAR);
		int day = currentDate.get(currentDate.DAY_OF_MONTH);
		int all = (year * 10000) + (month * 100) + day;
		return String.valueOf(all);
	}

	public static long getLongSpcDateAMG(int nnGG) {
		return Long.parseLong(getSpcDateAMG(nnGG));
	}

	public static String getTimbro() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	// in 18/05/2018 14:22 out 2018-04-23 18:58:54
	public static String getTimbroFromString(String dataIn) {
		try {
			String[] arr = dataIn.split(" ");
			String data = arr[0].trim();
			String time = arr[1].trim();
			String ggs = (data.substring(0, 2));
			String mms = (data.substring(3, 5));
			String aas = (data.substring(6, 10));
			return aas + "-" + mms + "-" + ggs + " " + time;
		} catch (Exception e) {
			return "";
		}
	}

	public static String getStringFromTimbro(String dataIn) {
		try {
			String[] arr = dataIn.split(" ");
			String data = arr[0].trim();
			String time = arr[1].trim();
			String ggs = (data.substring(8, 10));
			String mms = (data.substring(5, 7));
			String aas = (data.substring(0, 4));
			return ggs + "-" + mms + "-" + aas + "  " + time.substring(0, 5);
		} catch (Exception e) {
			return "";
		}
	}

	// es: dd-MM-yy HH:MM
	public static String getTodayAs(String aFormatExp) {
		try {

			Format formatter = new SimpleDateFormat(aFormatExp);
			return formatter.format(new Date());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Classe getSpcDateGMA la routine recupera la data corrente in base al numero
	 * di nnGG (numero di giorni) +/- calcola il giorno es. se richiamo
	 * Utils.getSpcDateAMG(0) ottengo giorno corrente formato di output GG/MM/AAAA
	 */
	public static String getSpcDateGMA(int nnGG) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(currentDate.DAY_OF_MONTH, nnGG);
		int month = (currentDate.get(currentDate.MONTH) + 1);
		int year = currentDate.get(currentDate.YEAR);
		int day = currentDate.get(currentDate.DAY_OF_MONTH);

		// Formatto i giorni
		Long lngday = Long.valueOf(Integer.toString(day));
		DecimalFormat nrFormatterday = new DecimalFormat("00");
		String fmtday = nrFormatterday.format(lngday);

		// Formatto i mesi
		Long lngmonth = Long.valueOf(Integer.toString(month));
		DecimalFormat nrFormattermonth = new DecimalFormat("00");
		String fmtmonth = nrFormattermonth.format(lngmonth);

		String all = (fmtday + "/" + fmtmonth + "/" + (String.valueOf(year)));
		return (all);
	}

	public static String getSpcDateYMG(int nnGG) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(currentDate.DAY_OF_MONTH, nnGG);
		int month = (currentDate.get(currentDate.MONTH) + 1);
		int year = currentDate.get(currentDate.YEAR);
		int day = currentDate.get(currentDate.DAY_OF_MONTH);

		// Formatto i giorni
		Long lngday = Long.valueOf(Integer.toString(day));
		DecimalFormat nrFormatterday = new DecimalFormat("00");
		String fmtday = nrFormatterday.format(lngday);

		// Formatto i mesi
		Long lngmonth = Long.valueOf(Integer.toString(month));
		DecimalFormat nrFormattermonth = new DecimalFormat("00");
		String fmtmonth = nrFormattermonth.format(lngmonth);
		return (String.valueOf(year)) + "-" + fmtmonth + "-" + fmtday;
	}

	/**
	 * GetTime Recupera l'ora attuale e la mette in formato hh.mm
	 */

	public static String getTime() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(currentDate.DAY_OF_MONTH, 0);
		int ore = currentDate.get(currentDate.HOUR_OF_DAY);
		int min = currentDate.get(currentDate.MINUTE);
		int sec = currentDate.get(currentDate.SECOND);

		// Ora
		Long lngore = Long.valueOf(Integer.toString(ore));
		DecimalFormat nrFormatterore = new DecimalFormat("00");
		String fmtore = nrFormatterore.format(lngore);

		// minuti
		Long lngmin = Long.valueOf(Integer.toString(min));
		DecimalFormat nrFormattermin = new DecimalFormat("00");
		String fmtmin = nrFormattermin.format(lngmin);

		// secondi
		Long lngsec = Long.valueOf(Integer.toString(sec));
		DecimalFormat nrFormattersec = new DecimalFormat("00");
		String fmtsec = nrFormattersec.format(lngsec);

		String all = (fmtore + "." + fmtmin + "." + fmtsec);
		return (all);
	}

	public static String getTimeTmpFile() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(currentDate.DAY_OF_MONTH, 0);
		int ore = currentDate.get(currentDate.HOUR_OF_DAY);
		int min = currentDate.get(currentDate.MINUTE);
		int sec = currentDate.get(currentDate.SECOND);
		int msec = currentDate.get(currentDate.MILLISECOND);

		// Ora
		Long lngore = Long.valueOf(Integer.toString(ore));
		DecimalFormat nrFormatterore = new DecimalFormat("00");
		String fmtore = nrFormatterore.format(lngore);

		// minuti
		Long lngmin = Long.valueOf(Integer.toString(min));
		DecimalFormat nrFormattermin = new DecimalFormat("00");
		String fmtmin = nrFormattermin.format(lngmin);

		// secondi
		Long lngsec = Long.valueOf(Integer.toString(sec));
		DecimalFormat nrFormattersec = new DecimalFormat("00");
		String fmtsec = nrFormattersec.format(lngsec);

		String all = (fmtore + "" + fmtmin + "" + fmtsec + "" + msec);
		return (all);
	}

	/**
	 * Classe getListGiorni restituisce OPTION dei GIORNI
	 */
	public static String getListaGiorni() {

		Calendar currentDate = Calendar.getInstance();
		int day = currentDate.get(currentDate.DAY_OF_MONTH);

		String fmtGG = "";
		String listaGG = "";

		for (int intGG = 1; intGG <= 31; ++intGG) {
			DecimalFormat nrFormattermonth = new DecimalFormat("00");
			fmtGG = nrFormattermonth.format(Long.valueOf(Integer.toString(intGG)));
			if (day != intGG) {
				listaGG = (listaGG) + ("<option value='" + fmtGG + "'>" + fmtGG + "</option>") + "\n";
			} else {
				listaGG = (listaGG) + ("<option value='" + fmtGG + "' selected>" + fmtGG + "</option>") + "\n";
			}
		}
		return listaGG;

	}
	public static String getListaGiorni(int day) {
		Calendar currentDate = Calendar.getInstance();
		if(day==0)
		   day = currentDate.get(currentDate.DAY_OF_MONTH);

		String fmtGG = "";
		String listaGG = "";

		for (int intGG = 1; intGG <= 31; ++intGG) {
			DecimalFormat nrFormattermonth = new DecimalFormat("00");
			fmtGG = nrFormattermonth.format(Long.valueOf(Integer.toString(intGG)));
			if (day != intGG) {
				listaGG = (listaGG) + ("<option value='" + fmtGG + "'>" + fmtGG + "</option>") + "\n";
			} else {
				listaGG = (listaGG) + ("<option value='" + fmtGG + "' selected>" + fmtGG + "</option>") + "\n";
			}
		}
		return listaGG;

	}
	public static String getListaGiorniFrom(String giornoStart) {
		String fmtGG = "";
		String listaGG = "";
		DecimalFormat nrFormattermonth = new DecimalFormat("00");

		for (int intGG = 1; intGG <= 31; ++intGG) {
			fmtGG = nrFormattermonth.format(Long.valueOf(Integer.toString(intGG)));
			if (Integer.parseInt(giornoStart) != intGG) {
				listaGG = (listaGG) + ("<option value='" + fmtGG + "'>" + fmtGG + "</option>") + "\n";
			} else {
				listaGG = (listaGG) + ("<option value='" + fmtGG + "' selected>" + fmtGG + "</option>") + "\n";
			}
		}
		return listaGG;

	}

	/**
	 * Classe getListaMesi restituisce OPTION dei MESI Selected
	 */

	public String getListaMesi() {

		String nomiMesi[] = getArrayMesiShort();
		Calendar currentDate = Calendar.getInstance();
		int month = (currentDate.get(currentDate.MONTH) + 1);

		String fmtMM = "";
		String listaMM = "";

		for (int intMM = 1; intMM <= 12; ++intMM) {
			DecimalFormat nrFormattermonth = new DecimalFormat("00");
			fmtMM = nrFormattermonth.format(Long.valueOf(Integer.toString(intMM)));
			if (month != intMM) {
				listaMM = (listaMM) + ("<option value='" + fmtMM + "'>" + nomiMesi[intMM - 1] + "</option>" + "\n");
			} else {
				listaMM = (listaMM)
						+ ("<option value='" + fmtMM + "' selected>" + nomiMesi[intMM - 1] + "</option>" + "\n");
			}
		}

		return listaMM;

	}

	/**
	 * restituisce OPTION dei MESI con selezionato il mese passato
	 */
	public String getListaMesiFrom(String meseStart) {
		int x;
		x = Integer.parseInt(meseStart);
		return getListaMesiFrom(x);
	}

	/**
	 * restituisce OPTION dei MESI con selezionato il mese passato il secondo
	 * parametro e' il, codice lingua corrente
	 */
	public static String getListaMesiFrom(int meseStart) {
		try {
			String nomiMesi[] = getArrayMesiShort();
			int fmtMM = 0;
			String listaMM = "";
			Calendar currentDate = Calendar.getInstance();
			if(meseStart==0)
				meseStart = (currentDate.get(currentDate.MONTH) + 1);

			for (int intMM = 1; intMM <= 12; ++intMM) {
				DecimalFormat nrFormattermonth = new DecimalFormat("00");
				fmtMM = Integer.parseInt(nrFormattermonth.format(Long.valueOf(Integer.toString(intMM))));
				String mm=Utils.getNumero(intMM,"00");
				if (fmtMM != meseStart) {
					listaMM = (listaMM)
					+ ("<option value='" + mm(fmtMM) + "'>" + mm + "</option>" + "\n");
				} else {
					listaMM = (listaMM) + ("<option value='" + mm + "' selected>" + mm + "</option>" + "\n");
				}
			}
			return listaMM;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return "";
		}
	}
    
	public static String dataFull(String name,int ggStart,int mmStart) {
    	String ret="";
    	ret+="<select id='"+name+"GG'>"+ Utils.getListaGiorni(ggStart)+"</select>";
    	ret+="<select id='"+name+"MM'>"+ Utils.getListaMesiFrom(mmStart)+"</select>";
    	ret+="<select id='"+name+"AA'>"+ Utils.getListaAnni(0,1)+"</select>";
    	return ret;
    }
	public static String dal_al(String name) {
    	String ret="";
    	ret+="<input type='text' size=2 id='"+name+"DA' onkeypress='return onlyNumberKey(event)'>&nbsp;";
    	ret+="<input type='text' size=2 id='"+name+"AL' onkeypress='return onlyNumberKey(event)'>";
    	return ret;
    }
	public static String[] getArrayMesiShort() {
		String arrMesi[] = new String[12];
		arrMesi[0] = "Gen";
		arrMesi[1] = "Feb";
		arrMesi[2] = "Mar";
		arrMesi[3] = "Apr";
		arrMesi[4] = "Mag";
		arrMesi[5] = "Giu";
		arrMesi[6] = "Lug";
		arrMesi[7] = "Ago";
		arrMesi[8] = "Set";
		arrMesi[9] = "Ott";
		arrMesi[10] = "Nov";
		arrMesi[11] = "Dic";
		return arrMesi;
	}

	public String[] getArrayMesiShortTrad() {
		String aLng = sys.getLinguaWeb();
		String arrMesi[] = new String[12];
		arrMesi[0] = traduci("lng_gennaio", "Gennaio").substring(0, 3);
		arrMesi[1] = traduci("lng_febbraio", "Febbraio").substring(0, 3);
		arrMesi[2] = traduci("lng_marzo", "Marzo").substring(0, 3);
		arrMesi[3] = traduci("lng_aprile", "Aprile").substring(0, 3);
		arrMesi[4] = traduci("lng_maggio", "Maggio").substring(0, 3);
		arrMesi[5] = traduci("lng_giugno", "Giugno").substring(0, 3);
		arrMesi[6] = traduci("lng_luglio", "Luglio").substring(0, 3);
		arrMesi[7] = traduci("lng_agosto", "Agosto").substring(0, 3);
		arrMesi[8] = traduci("lng_settembre", "Settenbre").substring(0, 3);
		arrMesi[9] = traduci("lng_ottobre", "Ottobre").substring(0, 3);
		arrMesi[10] = traduci("lng_novembre", "Novembre").substring(0, 3);
		arrMesi[11] = traduci("lng_dicembre", "Dicembre").substring(0, 3);
		return arrMesi;
	}

	public String traduci(String aKey, String aDefault) {
		smweb.setSys(sys);
		// return smweb.traduci(aKey, aDefault);
		return aKey;
	}

	/**
	 * Classe MM restituisce il numero passato con due cifre 0 iniziale per numeri
	 * C(1..9) validio per formattare mesi e giorni es MM(1) -> 01
	 */

	public static String mm(String mese) {
		return mm(Integer.parseInt(mese));
	}

	public static String mm(int mese) {
		String sMese;
		int lung;
		sMese = "0" + String.valueOf(mese);
		lung = sMese.length();
		return sMese.substring(lung - 2, lung);
	}

	public static String putDateSepGMA(String dateIn) {
		try {
			String aas = (dateIn.substring(4, 8));
			String mms = (dateIn.substring(2, 4));
			String ggs = (dateIn.substring(0, 2));
			return ggs + "/" + mms + "/" + aas;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getAnnoFromAMG(String dateIn) {
		try {
			return (dateIn.substring(0, 4));
		} catch (Exception e) {
			return "";
		}
	} // fine metodo

	public static String getMeseFromAMG(String dateIn) {
		try {
			return (dateIn.substring(4, 6));
		} catch (Exception e) {
			return "";
		}
	}

	public static String getGiornoFromAMG(String dateIn) {
		try {
			return (getNumero(dateIn.substring(6, 8), "#0"));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Classe getListaAnni restituisce OPTION degli anni -5 e +1 rispetto
	 * all'attuale
	 */

	public static String getListaAnni() {
		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR) - 5;
		int yearNow = currentDate.get(Calendar.YEAR);

		String fmtAA = "";
		String listaAA = "";

		for (int intAA = year; intAA <= year + 6; ++intAA) {
			DecimalFormat nrFormatteryear = new DecimalFormat("0000");
			fmtAA = nrFormatteryear.format(Long.valueOf(Integer.toString(intAA)));
			if (yearNow != intAA) {
				listaAA = (listaAA) + ("<option value='" + fmtAA + "'>" + fmtAA + "</option>" + "\n");
			} else {
				listaAA = (listaAA) + ("<option value='" + fmtAA + "' selected>" + fmtAA + "</option>" + "\n");

			}
		}
		return listaAA;
	}
	public static String getListaAnni(int meno,int piu) {
		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR) - meno;
		int yearNow = currentDate.get(Calendar.YEAR);
		String fmtAA = "";
		String listaAA = "";

		for (int intAA = year; intAA <= year + piu; ++intAA) {
			DecimalFormat nrFormatteryear = new DecimalFormat("0000");
			fmtAA = nrFormatteryear.format(Long.valueOf(Integer.toString(intAA)));
			if (yearNow != intAA) {
				listaAA = (listaAA) + ("<option value='" + fmtAA + "'>" + fmtAA + "</option>" + "\n");
			} else {
				listaAA = (listaAA) + ("<option value='" + fmtAA + "' selected>" + fmtAA + "</option>" + "\n");
			}
		}
		return listaAA;
	}
	/** la stessa funzione di prima dando come selected un determ. anno */
	public static String getListaAnni(String aAnno) {
		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR) - 5;
		int yearNow = Integer.parseInt(aAnno);
		String fmtAA = "";
		String listaAA = "";
		try {
			for (int intAA = year; intAA <= year + 6; ++intAA) {
				DecimalFormat nrFormatteryear = new DecimalFormat("0000");
				fmtAA = nrFormatteryear.format(Long.valueOf(Integer.toString(intAA)));
				if (yearNow != intAA) {
					listaAA += "<option value='" + fmtAA + "'>" + fmtAA + "</option>\n";
				} else {
					listaAA += "<option value='" + fmtAA + "' selected>" + fmtAA + "</option>\n";
				}
			}
			return listaAA;
		} catch (Exception e) {
			System.out.println(">>>>>>" + e.getMessage());
			return "";
		}
	}

	/**
	 * la stessa funzione di prima dando come selected un determ. anno parto
	 * dall'anno corrente e mostro l'anno successivo solo gli ultimi due decimali
	 */
	public static String getListaAnniShort(String aAnno) {
		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(currentDate.YEAR);
		int yearNow = Integer.parseInt(aAnno);
		String fmtAA = "";
		String listaAA = "";
		try {
			for (int intAA = year; intAA <= year + 1; ++intAA) {
				fmtAA = String.valueOf(intAA).substring(2, 4);
				if (yearNow != intAA) {
					listaAA += "<option value='" + intAA + "'>" + fmtAA + "</option>\n";
				} else {
					listaAA += "<option value='" + intAA + "' selected>" + fmtAA + "</option>\n";
				}
			}
			return listaAA;
		} catch (Exception e) {
			System.out.println(">>>>>>" + e.getMessage());
			return "";
		}
	}

	public static String getListaAnni(String daAnno, String adAnno, String AnnoSelected) {
		return getListaAnni(Integer.parseInt(daAnno), Integer.parseInt(adAnno), Integer.parseInt(AnnoSelected));
	}

	public static String getListaAnni(int daAnno, int adAnno, int AnnoSelected) {
		String fmtAA = "";
		String listaAA = "";

		for (int intAA = daAnno; intAA <= adAnno; ++intAA) {
			DecimalFormat nrFormatteryear = new DecimalFormat("0000");
			fmtAA = nrFormatteryear.format(Long.valueOf(Integer.toString(intAA)));
			if (AnnoSelected != intAA) {
				listaAA = (listaAA) + ("<option value='" + fmtAA + "'>" + fmtAA + "</option>" + "\n");
			} else {
				listaAA = (listaAA) + ("<option value='" + fmtAA + "' selected>" + fmtAA + "</option>" + "\n");

			}
		}

		return listaAA;
	}

	/**
	 * Classe getNumero Formatta il numero in entrata e restituisce una stringa
	 * formattata Parametri in entrata : numero (in double o Long O stringa) pattern
	 * (vedi come visual basic)
	 *
	 * Parametro in uscita: getNumero in String
	 */

	public static String getNumero(String nImporto, String nPattern) {
		try {
			// da usare solo per dati provenienti da AS400

			Locale loc = new Locale("it", "IT");
			NumberFormat nf = NumberFormat.getNumberInstance(loc);
			DecimalFormat df = (DecimalFormat) nf;
			df.applyPattern(nPattern);
			String separatori = ",.";
			nImporto = nImporto.replace(separatori.charAt(0), separatori.charAt(1));
			Double risulta = new Double(Double.parseDouble(nImporto));
			return (df.format(risulta)).toString();
		} catch (Exception ex) {
			return "0";
		}

	}

	public static String getNumero(int nImporto, String nPattern) {
		try {
			return getNumero(String.valueOf(nImporto), nPattern);
		} catch (Exception ex) {
			return "";
		}

	}

	public static String getNumero(double nImporto, String nPattern) {
		try {
			return getNumero(String.valueOf(nImporto), nPattern);
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getValoreValuta(double nImporto) {
		try {
			return "&euro; " + getNumero(String.valueOf(nImporto), "###,##0.00");
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getValoreValuta(double nImporto, String aValuta) {
		try {
			return aValuta + " " + getNumero(String.valueOf(nImporto), "###,##0.00");
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getValoreValuta(String nImporto, String aValuta) {
		try {
			return getValoreValuta(Double.parseDouble(nImporto), aValuta);
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getNumero(long nImporto, String nPattern) {
		try {
			DecimalFormat nrFormatter = new DecimalFormat(nPattern);
			return nrFormatter.format(nImporto);
		} catch (Exception ex) {
			return "";
		}

	}

	// * formatta un dato stringa di prezzo */
	public static String getValuta(String nImporto) {
		String nPattern = "###,##0.00####";
		Locale loc = new Locale("it", "IT");
		NumberFormat nf = NumberFormat.getNumberInstance(loc);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(nPattern);
		String separatori = ",.";
		nImporto = nImporto.replace(separatori.charAt(0), separatori.charAt(1));
		Double risulta = new Double(Double.parseDouble(nImporto));
		return (df.format(risulta)).toString();
	}

	// * formatta un dato stringa di prezzo */
	public static String getValuta(String nImporto, int nDecimal) {
		try {
			String nPattern = "###,##0.";
			for (int i = 1; i <= nDecimal; i++) {
				nPattern += "0";
			}
			Locale loc = new Locale("it", "IT");
			NumberFormat nf = NumberFormat.getNumberInstance(loc);
			DecimalFormat df = (DecimalFormat) nf;
			df.applyPattern(nPattern);
			String separatori = ",.";
			nImporto = nImporto.replace(separatori.charAt(0), separatori.charAt(1));
			Double risulta = new Double(Double.parseDouble(nImporto));
			return (df.format(risulta)).toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	// * formatta un dato stringa valuta a numero per inserimento in db */
	public static String getDoubleFromValuta(String nImporto) {
		String a, c = "";
		for (int z = 0; z < nImporto.length(); ++z) {
			a = String.valueOf(nImporto.charAt(z));
			if (a.equals(",")) {
				c += ".";
			} else if (!a.equals(".")) {
				c += a;
			}
		}
		return c;
	}

	// * formatta un dato stringa valuta a numero per inserimento in db */
	public static double toDouble(String aVal) {
		try {
			if(null==aVal) return 0;
			return Double.parseDouble(getDoubleFromValuta(aVal));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Allinea testo
	 */
	public static String align(String aCampo, int lung, String pos) {
		int rLung = aCampo.length();
		String spazi = "";
		for (int i = rLung; i < lung; i++) {
			spazi = spazi + " ";
		}
		if (pos.toUpperCase().equals("L")) {
			aCampo = aCampo + spazi;
		} else {
			aCampo = spazi + aCampo;

		}
		return aCampo;
	}

	public static String fillNbsp(String aCampo, int lung, String pos) {
		int rLung = aCampo.length();
		String spazi = "";
		for (int i = rLung; i < lung; i++) {
			spazi = spazi + "&nbsp;";
		}
		if (pos.toUpperCase().equals("L")) {
			aCampo = aCampo + spazi;
		} else {
			aCampo = spazi + aCampo;

		}
		return aCampo;
	}

	// ** toglie gli spazi sostituendoli con &nbsp;"
	public String nbsp(String aCampo) {
		try {
			String a, c = "";
			for (int z = 0; z < aCampo.length(); ++z) {
				a = String.valueOf(aCampo.charAt(z));
				if (a.equals(" ")) {
					c += "&nbsp;";
				} else {
					c += a;
				}
			}
			return c;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * pulitura stringa pe immissione in database ignora i carattei ascii < 31 che
	 * arrivano se si adoperano le <TEXTAREA>
	 *
	 */
	public static String eliminaCarSpec(String aCampo) {
		try {
			String a, c = "";
			for (int z = 0; z < aCampo.length(); ++z) {
				a = String.valueOf(aCampo.charAt(z));
				if (a.equals("'")) {
					c += "''";
				} else if (a.hashCode() < 31) {
					c += "";
				} else {
					c += a;
				}
			}
			return c;
		} catch (Exception e) {
			return "";
		}
	}

	public static String encodeJava(String aCampo) {
		try {
			String a, c = "";
			for (int z = 0; z < aCampo.length(); ++z) {
				a = String.valueOf(aCampo.charAt(z));
				if (a.equals("\"")) {
					c += "\\\"";
					continue;
				}
				if (a.equals("\\")) {
					c += "\\\\";
					continue;
				}
				c += a;
			}
			return c;
		} catch (Exception e) {
			return "";
		}
	}

	public static String encodeHTML(String aCampo) {
		try {
			String a, c = "";
			for (int z = 0; z < aCampo.length(); ++z) {
				a = String.valueOf(aCampo.charAt(z));
				if (a.equals("\"")) {
					c += "&quot;";
					continue;
				}
				if (a.equals("&")) {
					c += "&amp;";
					continue;
				}
				if (a.equals(">")) {
					c += "&gt";
					continue;
				}
				if (a.equals("<")) {
					c += "&lt";
					continue;
				}
				c += a;
			}
			return c;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Nella composizione dei parametri stringa delle funzioni Jscript codifica
	 * l'apice singolo in \'
	 * 
	 * @param aCampo
	 * @return
	 */
	public static String encodeJScriptString(String aCampo) {
		try {
			String a, c = "";
			for (int z = 0; z < aCampo.length(); ++z) {
				a = String.valueOf(aCampo.charAt(z));
				if (a.equals("'")) {
					c += "\\'";
					continue;
				}
				c += a;
			}
			return c;
		} catch (Exception e) {
			return "";
		}
	}

	// *** replace string tag deve essere un valore di un carattere solo */

	public static String replaceChar(String str, String tag, String newValue) {
		try {
			final StringBuffer result = new StringBuffer();

			final StringCharacterIterator iterator = new StringCharacterIterator(str);
			char character = iterator.current();
			while (character != StringCharacterIterator.DONE) {
				if (character == tag.charAt(0))
					result.append(newValue);
				else
					result.append(character);
				character = iterator.next();
			}
			return result.toString();
		} catch (RuntimeException e) {
			return str;
		}
	}

	public static String replaceStr(String str, String tag, String newValue) {
		try {
			int idx = str.indexOf(tag);
			if (!(tag.equals(newValue))) {
				while (idx >= 0) {
					String newStr = str.substring(idx + tag.length());
					str = str.substring(0, idx) + newValue + newStr;
					idx = str.indexOf(tag);
				}
			}
			return str;
		} catch (RuntimeException e) {
			return str;
		}
	}

	// prezzo netto con sconti a scalare */
	public static double getPrezzoNetto(double przin, double sc1, double sc2, double sc3) {
		try {
			double somma = 0;
			if (sc1 != 0) {
				somma = (somma + sc1) - (somma * (sc1 / 100));
			}
			if (sc2 != 0) {
				somma = (somma + sc2) - (somma * (sc2 / 100));
			}
			if (sc3 != 0) {
				somma = (somma + sc3) - (somma * (sc3 / 100));
			}
			return przin - ((przin / 100) * somma);
		} catch (Exception e) {
			return 0;
		}
	}

	/** */
	public static double[] getPrezzoNettoArr(double przin, double sc1, double sc2, double sc3) {
		try {
			double somma = 0;
			if (sc1 != 0) {
				somma = (somma + sc1) - (somma * (sc1 / 100));
			}
			if (sc2 != 0) {
				somma = (somma + sc2) - (somma * (sc2 / 100));
			}
			if (sc3 != 0) {
				somma = (somma + sc3) - (somma * (sc3 / 100));

			}
			return new double[] { przin - ((przin / 100) * somma), somma };
		} catch (Exception e) {
			return new double[] { 0, 0 };
		}
	}

	/**
	 * utility relative ai file il file esiste ??
	 */
	public static boolean existFile(String aFile) {
		try {
			File f = new File(aFile);
			return f.exists();
		} catch (Exception xx) {
			return false;
		}
	}

	public static String encode(String aStr) {
		return URLEncoder.encode(aStr);

	}

	/** utility relative ai file eliminazione file */
	public static boolean deleteFile(String aFile) {
		try {
			File f = new File(aFile);
			if (f.exists()) {
				f.delete();
				return true;
			} else {
				return false;
			}
		} catch (Exception xx) {
			return false;
		}
	}

	/**
	 * creazione file zip 1=nome del file all'interno dello zip 2=file in input
	 * 3=file creato
	 */
	public boolean makeZip(String entryName, String inFilename, String outFilename) {
		try {
			FileInputStream in = new FileInputStream(inFilename);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));

			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(entryName));

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			out.close();
			in.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// carico i dati relativi alle lingue
	public void datiLingue(Sys sys) {
		try {
			lingue = new Hashtable();
			for (int i = 0; i < 9; i++) {
				String aLine = sys.getProp("lng_" + getNumero(i, "00"));
				if (!aLine.equalsIgnoreCase("")) {
					StringTokenizer st = new StringTokenizer(aLine, ";");
					String wcod = st.nextToken();
					String img = st.nextToken();
					String cgal = st.nextToken();
					String des = st.nextToken();
					lingue.put(cgal.trim(), new String[] { wcod, img, cgal, des });
				}
			}
		} catch (Exception xx) {
		}
	}

	/**
	 */

	public String getValLng(String aCode, int aParm) {
		try {
			if (!lingue.containsKey(aCode)) {
				return "";
			}
			String[] r = (String[]) lingue.get(aCode);
			return r[aParm];
		} catch (Exception xx) {
			return "";
		}
	}

	public Hashtable getLingue() {
		return lingue;
	}

	/**
	 * verifica che il sito non abbia un blocco temporaneo specificato per ogni
	 * giorno della settimana sono date due possibilit�
	 */
	public String bloccoATempo(Sys sys) {
		try {
			String ret = "";
			Calendar cal = GregorianCalendar.getInstance();
			int i = cal.get(Calendar.DAY_OF_WEEK) - 1;

			String aLine = sys.getProp("timew1_" + i);
			if (!aLine.equals("")) {
				ret = verificaBlocco(aLine, sys);
			}
			if (!ret.equals("")) {
				return ret;
			}

			aLine = sys.getProp("timew2_" + i);
			if (!aLine.equals("")) {
				return verificaBlocco(aLine, sys);
			}

			return "";
		} catch (Exception e) {
			sys.debug("Finestre.tempo " + e.getMessage());
			return "";
		}
	}

	/** ritorno blank=ok ora ripartenza */
	private String verificaBlocco(String aLine, Sys sys) {
		try {
			StringTokenizer st = new StringTokenizer(aLine, ";");
			String p_from = st.nextToken();
			String p_to = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(p_from, ":");
			int h_from = Integer.parseInt(st1.nextToken());
			int m_from = Integer.parseInt(st1.nextToken());
			long t_from = totalSeconds(h_from, m_from, 0);
			StringTokenizer st2 = new StringTokenizer(p_to, ":");
			int h_to = Integer.parseInt(st2.nextToken());
			int m_to = Integer.parseInt(st2.nextToken());
			long t_to = totalSeconds(h_to, m_to, 0);

			Calendar currentDate = Calendar.getInstance();
			currentDate.add(Calendar.DAY_OF_MONTH, 0);
			int ore = currentDate.get(Calendar.HOUR_OF_DAY);
			int min = currentDate.get(Calendar.MINUTE);
			int sec = currentDate.get(Calendar.SECOND);
			long t_now = totalSeconds(ore, min, sec);
			if ((t_now >= t_from) && (t_now < t_to)) {
				return p_to;
			}

			return "";
		} catch (Exception e) {
			sys.debug("Finestre.tempo " + e.getMessage());
			return "";
		}
	}

	private long totalSeconds(int hh, int mm, int ss) {
		return (hh * 3600) + (mm * 60) + ss;
	}

	public String getRicercheAltre(Sys sys, String codLng) {
		try {
			String scelte = "";
			String s = "";
			for (int i = 0; i < 5; i++) {
				String aLine = sys.getProp("ricerche_" + getNumero(i, "00"));
				if (!aLine.trim().equals("")) {
					StringTokenizer st = new StringTokenizer(aLine, ";");
					String cod = st.nextToken();
					String des = st.nextToken();
					String lng = st.nextToken();
					s = "";
					if (i < 1) {
						s = "SELECTED";
					}
					String trad = sys.traduci(lng, des);
					if (trad.equals("")) {
						trad = des;
					}
					scelte += "<option value='" + cod + "' " + s + ">" + trad + "</option>";
				}
			}
			return scelte;
		} catch (Exception e) {
			sys.error("Util_getRicercheAltre " + e.getMessage());
			return "";
		}
	}

	/** ritorna il nome della macchina SERVER */
	public static String getMachineName() {
		try {
			InetAddress x = InetAddress.getLocalHost();
			return x.getHostName();
		} catch (Exception e) {
			return "UNDEFINED";
		}
	}

	/** indirizzo IP SERVER */
	public static String getMachineIp() {
		try {
			InetAddress x = InetAddress.getLocalHost();
			String str = x.toString();
			return str.substring(str.indexOf("/") + 1);
		} catch (Exception e) {
			return "UNDEFINED";
		}
	}

	public String aggEuro(String aLine) {
		return Utils.replaceStr(aLine, "�", "&euro;");
	}

	public static String trattaJs(String stringa) {
		String ritorno = stringa.replaceAll("'", " ");
		ritorno = ritorno.replaceAll("[ ]{1,}", " ");
		ritorno.trim();
		return ritorno;
	}

	public static String dateGMAShort(String dateIn)

	{
		try {
			String aas = (dateIn.substring(2, 4));
			String mms = (dateIn.substring(4, 6));
			String ggs = (dateIn.substring(6, 8));
			return ggs + "/" + mms + "/" + aas;
		} catch (Exception ex) {
			return "";
		}

	}

	/**
	 * Metodo getWeekOfYear la routine recupera il numero della settimana dell'anno
	 * in base al numero dateIn = data in formato AAAAMMGG se dateIn==0 considera il
	 * giorno corrente
	 */
	public static int getWeekOfYear(int dateIn) {
		try {
			Calendar cal = Calendar.getInstance();

			if (dateIn != 0) {
				String strDate = String.valueOf(dateIn);
				cal.set(Calendar.YEAR, Integer.parseInt(strDate.substring(0, 4)));
				cal.set(Calendar.MONTH, Integer.parseInt(strDate.substring(4, 6)) - 1);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDate.substring(6, 8)));
				int y = cal.get(Calendar.YEAR);
				int d = cal.get(Calendar.DAY_OF_MONTH);
				int m = cal.get(Calendar.MONTH);
			}

			return cal.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception ex) {
			return 0;
		}
	}

	public static void debugHash(Sys sys, Hashtable hash) {
		try {
			sys.debug("--------------------------------inizio debug hash");
			for (Enumeration e = hash.keys(); e.hasMoreElements();) {
				String k = String.valueOf((Object) e.nextElement());
				sys.debug(k + " | " + (Object) hash.get(k));
			}
			sys.debug("--------------------------------");
		} catch (Exception e) {
			sys.debug("Utils.debugHash Error :" + e.getMessage());
		}
	}

	public static void debugMap(Sys sys, Map map) {
		try {
			Set set = map.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				String k = (String) me.getKey();
				sys.debug(k + " | " + (String) map.get(k));
			}
		} catch (Exception e) {
			sys.debug("Utils.debugHash Error :" + e.getMessage());
		}
	}
	public static void debugArray(Sys sys, String[] map) {
		try {
			for(int i=0;i<map.length;i++) {
				sys.debug(i + " | " + map[i]);
			}
		} catch (Exception e) {
			sys.debug("Utils.debugArray Error :" + e.getMessage());
		}
	}
	public static void outHash(Hashtable hash) {
		try {
			System.out.println("--------------------------------inizio out hash");
			for (Enumeration e = hash.keys(); e.hasMoreElements();) {
				String k = String.valueOf((Object) e.nextElement());
				System.out.println(k + " | " + (Object) hash.get(k));
			}
			System.out.println("--------------------------------");
		} catch (Exception e) {
			System.out.println("Utils.debugHash Error :" + e.getMessage());
		}
	}

	public static String writeHash(Hashtable hash, String aName) {
		String ret = "";
		try {
			ret = (aName + " --------------------------------<br>");
			for (Enumeration e = hash.keys(); e.hasMoreElements();) {
				String k = (String) e.nextElement();
				ret += k + " : " + (String) hash.get(k) + "<br>";
			}
			return ret + "-------------------------------<br>";
		} catch (Exception e) {
			return " " + e.getMessage();
		}
	}

	public static String writeHash(Hashtable hash) {
		return writeHash(hash, "");
	}

	public static Vector<String> getSortedKeys(Hashtable list) {
		try {
			List<String> v = new ArrayList<String>(list.keySet());
			Collections.sort(v);
			return new Vector<String>(v);
		} catch (Exception e) {
			return new Vector<String>();
		}
	}

	public static Vector<Integer> getSortedIntKeys(Hashtable list) {
		try {
			List<Integer> v = new ArrayList<Integer>(list.keySet());
			Collections.sort(v);
			return new Vector<Integer>(v);
		} catch (Exception e) {
			return new Vector<Integer>();
		}
	}

	// la stringa contiene lettere ?
	public static boolean hasLetters(String s) {
		if (s == null)
			return false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isLetter(c))
				return true;
		}
		return false;
	}

	public static int deleteOldFile(String curr_folder, int secTrascorsi) {
		try {
			int nrCanc = 0;
			File currentDir = new File(curr_folder);
			String[] temp = currentDir.list();
			Date dataPuntuale = new Date();
			for (int i = 0; i < temp.length; i++) {
				String aName = temp[i];
				File tempFile = new File(curr_folder + "/" + aName);
				if (tempFile.isDirectory())
					continue;
				Date dataCreazione = new Date(tempFile.lastModified());
				long millisDiff = dataPuntuale.getTime() - dataCreazione.getTime();
				int seconds = (int) (millisDiff / 1000);
				if (seconds > secTrascorsi) {
					tempFile.delete();
					nrCanc += 1;
					// System.out.println(aName+" "+seconds);
				} else {
					// System.out.println(aName+" "+seconds);
				}
				tempFile = null;
			}
			return nrCanc;
		} catch (Exception e) {
			return 0;
		}
	}

	public static Map<String, String> sortAscValue(Hashtable map) {
		Map<String, String> retMap = new HashMap<String, String>();
		Set<Entry<String, String>> set = map.entrySet();
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		for (Map.Entry<String, String> entry : list) {
			System.out.println(entry.getKey() + " ==== " + entry.getValue());
			retMap.put(entry.getKey(), entry.getValue());
		}
		return retMap;
	}

	public static String eliminaLastChar(String aStr) {
		try {
			return aStr.substring(0, (aStr.length() - 1));
		} catch (Exception e) {
			return aStr;
		}
	}

	public static String eliminaLastChar(String aStr, String aCar) {
		try {
			return aStr.substring(0, (aStr.lastIndexOf(aCar)));
		} catch (Exception e) {
			return aStr;
		}
	}

	public static String firstUpper(String vIn) {
		try {
			String[] strArr = vIn.split(" ");
			String ret = "";
			for (int i = 0; i < strArr.length; i++) {
				String v = strArr[i];
				String g1 = v.substring(0, 1).toUpperCase();
				String g2 = v.substring(1).toLowerCase();
				ret += g1 + g2 + " ";
			}
			return ret.trim();
		} catch (Exception e) {
			return vIn.trim();
		}

	}

	public static String getFileNumericDate(String aFile) {
		try {
			Format formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
			return formatter.format(new Date(new File(aFile).lastModified()));
		} catch (Exception e) {
			return "";
		}
	}

	public static String getFileDate(String aFile) {
		try {

			Format formatter = new SimpleDateFormat("dd-MM-yy HH:MM");
			return formatter.format(new Date(new File(aFile).lastModified()));
		} catch (Exception e) {
			return "";
		}
	}

	public static String freeMemory() {
		try {
			Runtime.getRuntime().gc();
			return "Util.freeMory memory ok";
		} catch (Exception e) {
			return "Util.freeMory memory err";
		}
	}

	public static String pathToBack(String aPath) {
		try {
			String ret = "";
			for (int z = 0; z < aPath.length(); ++z) {
				String c = String.valueOf(aPath.charAt(z));
				if (c.hashCode() == 92)
					ret += "/";
				else
					ret += c;
			}
			return ret;

		} catch (Exception e) {
			return aPath;
		}
	}

	public static int hoursDifference(Date date1, Date date2) {

		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
	}

	public static double hoursDiff(Sys sys, String date1Str, String date2Str) {
		double ret = 0;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date1 = formatter.parse(date1Str);
			Date date2 = formatter.parse(date2Str);
			 
			long diff = date2.getTime() - date1.getTime();
		 
			int diffhours = (int) (diff / (60 * 60 * 1000));
			int diffmin = (int) (diff / (60 * 1000));
			int restmin = (diffmin - 60);
			double dec = (((double) restmin * 60) / (double) 3600);
			String[] strDec = String.valueOf(dec).split("\\.");
			ret = Double.parseDouble(diffhours + "." + strDec[1]);
			if (null != sys)
				sys.debugWhite(" diffhours:" + diffhours + "  dec:" + dec + " return:" + ret);
			return ret;
		} catch (Exception e) {
			if (null != sys)
				sys.error(e);
			return 0;
		}
	}
	public static String hhmmDiff(Sys sys, String date1Str, String date2Str) {
		double ret = 0;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date1 = formatter.parse(date1Str);
			Date date2 = formatter.parse(date2Str);
			 
			long diff = Math.abs(date2.getTime() - date1.getTime());
			int diffmin = (int) (diff / (60 * 1000));
			return ""+diffmin;
		} catch (Exception e) {
			if (null != sys)
				sys.error(e);
			return "not avail";
		}
	}
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String getTag(String aTagName, String aArea) {
		try {
			int x, y;
			int aLen = aTagName.length();
			x = aArea.indexOf(aTagName) + aLen;
			y = aArea.lastIndexOf(aTagName);
			String aVal = aArea.substring(x, y);
			return aVal;
		} catch (Exception e) {
			return aArea;
		}
	}

	public static String getTag(String aTagStart, String aTagEnd, String aArea) {
		try {
			int x, y;
			int aLen = aTagStart.length();
			x = aArea.indexOf(aTagStart) + aLen;
			y = aArea.lastIndexOf(aTagEnd);
			String aVal = aArea.substring(x, y);
			return aVal;
		} catch (Exception e) {
			return aArea;
		}
	}

	public static String drawOptions(LinkedHashMap<String, String> list, String currVal) {
		String ret = "";
		if (currVal.equals(""))
			ret = "<option value='' selected> -- seleziona un'opzione --</otion>";
		for (String k : list.keySet()) {
			String v = list.get(k);
			if (v.equals(""))
				v = k;
			String s = k.equalsIgnoreCase(currVal) ? " selected " : "";
			ret += "<option value='" + k + "' " + s + ">" + v + "</option>";
		}
		return ret;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static int diffDays(String data1, String data2) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date firstDate = sdf.parse(data1);
			Date secondDate = sdf.parse(data2);

			long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			return Integer.parseInt("" + diff);
		} catch (Exception e) {
			return 0;
		}
	}
	public static String json(Object aData)
	{
    	try {
    		return new Gson().toJson(aData);
    	} catch(Exception e) {
    		return e.getMessage();
    	}
	}
	// somma ad una data passata col formato YYYYMMGG
	public static int getSpcDateAMG_after(String partenza,int ggAdd) {
		try {
			LocalDate date = LocalDate.parse(partenza);
			LocalDate date2 = date.plusDays(ggAdd);
			return Integer.parseInt(date2.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		} catch(Exception e) {
			return 0;
		}
	}
	// ora che sto verificando ora di paragone
	public static boolean isTimeBefore(String oraA,String oraB) {
		 try {
		 String [] arA=oraA.split(":");
		 String [] arB=oraB.split(":");
		 
		 LocalTime timeA = LocalTime.of(Integer.parseInt(arA[0]),Integer.parseInt(arA[1]),Integer.parseInt(arA[2]));
		 LocalTime timeB = LocalTime.of(Integer.parseInt(arB[0]),Integer.parseInt(arB[1]),Integer.parseInt(arB[2]));
	     return timeA.isBefore(timeB);
		 
		 } catch (Exception e) {
			 e.printStackTrace();
			 return false;
		 }
	}
	
	public static String two(String aVar) {
		if(aVar.length()==1) return "0"+aVar;
		return aVar;
	}
	public static String two(int aVar) {
		if(aVar < 10) return "0"+aVar;
		return ""+aVar;
	}
}
