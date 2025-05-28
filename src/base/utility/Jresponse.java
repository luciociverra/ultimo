package base.utility;
/*
 *   lettura lato javascript   jresp.tabDati["jobdes"]  o jresp.msg
 * 
 */
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
public class Jresponse {
	public int rc;
	public String lastId;
	public String signature;
	public String msg;    
	public String keyVal;
	public String fobbs;  // elenco campi obbligatori #campo1,#campo2
	public String fincorrects;  // elenco digitati ma in maniera non corretta
	
	public String fileOut;
	public String targetId;
	public String action;
	public String id;
	public String text;

	public Map<String, String> tabDati = new HashMap<String, String>();
    // letto in web con jresp.tabDati["aKey"]
	public void setProperty(String aKey, String aVal) {
		if (tabDati.containsKey(aKey))
			tabDati.remove(aKey);
		tabDati.put(aKey, aVal);
	}

	public String getProperty(String nomeVar) {
		if (tabDati.containsKey(nomeVar))
			return (String) tabDati.get(nomeVar);
		else
			return "";
	}
	public Jresponse() {
	}
	public Jresponse(int aRc, String aMsg) {
		this.rc=aRc;
		this.msg=aMsg;
	}
	public void error(String aMsg) {
		this.rc=0;
		this.msg=aMsg;
	}
	public void ok(String aMsg) {
		this.rc=1;
		this.msg=aMsg;
	}
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
