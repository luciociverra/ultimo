package base.utility;

import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import base.db.RsTable;

public class BaseObj {
	protected Gson gson = new GsonBuilder().create();
	protected Hashtable<String, Object> tabDati = new Hashtable<String, Object>();
	protected Sys sys;
	protected String ls = System.getProperty("line.separator");
	protected String fs = System.getProperty("file.separator"); 

	public String notNull(String aValue) {
		if (null == aValue)
			return "";
		return aValue;
	}

	public int zeroIfNull(String aValue) {
		try {
			if (null == aValue)
				return 0;
			if (aValue.equals(""))
				return 0;
			return Integer.parseInt(aValue);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public String zeroIfBlank(String aValue) {
		if (null == aValue)
			return "0";
		if (aValue.equals(""))
			return "0";
		try {
			double tmpVal = Double.parseDouble(aValue);
		} catch (NumberFormatException e) {
			aValue = "0";
		}
		return aValue;
	}

	public void setSys(Sys aSys) {
		sys = aSys;
	}

	public Sys getSys() {
		return sys;
	}

	/* tabDati StART */
	public void setProperty(String aKey, String aVal) {
		tabDati.put(aKey, aVal);
	}
	public void setProperty(String aKey, Hashtable aDiz) {
		tabDati.put(aKey, aDiz);
	}
	public void setProperty(String aKey, String[] array) {
		tabDati.put(aKey, array);
	}
	public void setProperty(String aKey, RsTable rs) {
		tabDati.put(aKey, rs);
	}
	public void clearProperty() {
		tabDati = new Hashtable<String, Object>();
	}

	public void removeProperty(String aKey) {
		if (tabDati.containsKey(aKey))
			tabDati.remove(aKey);
	}

	public String getProperty(String nomeVar) {
		try {
			if (tabDati.containsKey(nomeVar))
				return (String) tabDati.get(nomeVar);
			else
				return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	public int getPropertyInt(String nomeVar) {
		if (tabDati.containsKey(nomeVar))
			return Integer.parseInt((String) tabDati.get(nomeVar));
		else
			return 0;
	}
	public Hashtable getPropertyHash(String nomeVar) {
		if (tabDati.containsKey(nomeVar))
			return (Hashtable) tabDati.get(nomeVar);
		else
			return null;
	}
	public String[] getPropertyArray(String nomeVar) {
	try {
		if (tabDati.containsKey(nomeVar))
			return (String[]) tabDati.get(nomeVar);
		else
			return new String[] {"","","",""};
	} catch(Exception e) {
		return new String[] {"","","",""};
	}
	}
	
	public RsTable getPropertyRsTable(String nomeVar) {
		try {
			if (tabDati.containsKey(nomeVar))
				return (RsTable) tabDati.get(nomeVar);
			else
				return new RsTable();
		} catch(Exception e) {
			return new RsTable();
		}
	}
	
	public String debugProperty() {
		String ret = "";
		for (Enumeration e = tabDati.keys(); e.hasMoreElements();) {
			String nK = (String) e.nextElement();
			String nVal = "";
			try {
				nVal = (String) tabDati.get(nK);
			} catch (Exception ex) {
				nVal = "not displayable";
			}
			ret += nK + " -->" + nVal + "<br>";
		}
		return ret;
	}

	/* tabDati END */
	public boolean notBlank(String aObj) {
		return !notNull(aObj).trim().equals("");
	}
	public boolean notZero(String aObj) {
		return ! (Utils.toDouble(aObj)==0);
	}
	public boolean isBlank(String aObj) {
		return notNull(aObj).trim().equals("");
	}

	protected String getExecTime(long from) {
		long l = (System.currentTimeMillis() - from);
		return "<font color='yellow' size=1>" + l + "</font>";
	}

	/* gestione errori */
	public void debug(String aStr) {
		try {
			sys.debug(aStr);
		} catch (Exception ex) {
		}
	}

	public void debug(String aStr, String aColor) {
		try {
			sys.debug(aStr, aColor);
		} catch (Exception ex) {
		}
	}

	public void debug(boolean aStr) {
		this.debug(String.valueOf(aStr));
	}

	public void debug(int aStr) {
		this.debug(String.valueOf(aStr));
	}

	public void error(String aStr) {
		try {
			if (null != sys)
				sys.error(aStr);
			ErrorManager.manage(aStr);
		} catch (Exception e) {
		}
	}

	public void error(String aStr, int gravity) {
		try {
			if (null != sys)
				sys.error(aStr + " gravity:" + gravity);
			ErrorManager.manage(aStr, gravity);
		} catch (Exception e) {
		}
	}

	public static void errorS(String aStr, int gravity) {
		try {
			ErrorManager.manage(aStr, gravity);
		} catch (Exception e) {
		}
	}

	public static void errorS(String aStr) {
		try {
			ErrorManager.manage(aStr);
		} catch (Exception e) {
		}
	}

	/*  */
	public String pApic(String aString) {
		return "'" + aString + "'";
	}

	protected void out(String aElement) {
		System.out.println(aElement);
	}

	protected static void outS(String aElement) {
		System.out.println(aElement);
	}

	protected String getXmlData(Element mySrc, String aName) {
		try {
			return mySrc.getElementsByTagName(aName).item(0).getTextContent();
		} catch (Exception e) {
			return "";
		}
	}

	protected String getXml(Element mySrc, String aName) {
		try {
			return mySrc.getElementsByTagName(aName).toString();
		} catch (Exception e) {
			return "";
		}
	}

	/* Utility json */
	public String[] jsonStringToArray(String aVal) {
		if (notNull(aVal).equals(""))
			return new String[0];
		Type type = new TypeToken<List<String>>() {
		}.getType();
		List<String> list = gson.fromJson(aVal, type);
		return list.toArray(new String[0]);
	}

	public String jsonArrayToString(List aVal) {
		if (null == aVal)
			return "";
		return gson.toJson(aVal);
	}

	/** da oggetto json ad hashtable **/
	public Hashtable<String, String> stringToHashString(String aStr) {
		Hashtable<String, String> hash = gson.fromJson(aStr, Hashtable.class);
		return hash;
	}
}
