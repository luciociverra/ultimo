package base.test;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.db.RsTable;
public class GsonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		RsTable rs = new RsTable(new String[] {""});
		 Vector chiavi=new Vector();
		  chiavi.add("");chiavi.add("Data");chiavi.add("Testo");
		  rs.setNames(chiavi);
          rs.addRow("Data", "valore1", "Testo"," valore2");
          rs.addRow("Data", "valore11", "Testo"," valore22");
          rs.addRow("Data", "valore111", "Testo"," valore222");
          
          Gson gson = new GsonBuilder().create();
        
   	      System.out.println(gson.toJson(rs));
	}

}
