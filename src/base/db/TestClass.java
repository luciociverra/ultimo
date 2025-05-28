package base.db;
 
import java.util.ListIterator;

import base.utility.Sys;
public class TestClass {

	public static void main(String[] args) {
		double a=2;
		double b=1.2;
		System.out.println("INIZIO  "+(a>b));
		
		RsTable rsTable = new RsTable(new String[] {"dato"});
		rsTable.addRow("dato", String.format("%f", 2d));
		rsTable.addRow("dato", String.format("%f", 1.2d));
		rsTable.addRow("dato", String.format("%f", 4d));
		rsTable.addRow("dato", String.format("%f", 12d));
		rsTable.addRow("dato", String.format("%f", 0.5d));
		rsTable.addRow("dato", String.format("%f", 100d));
		System.out.println(rsTable.size());
 
		RsTable r=rsTable.sortByFieldDouble("dato", "desc");
		System.out.println("---------------------------------------------------------"+r.size());
		while(r.next()) {
			System.out.println("------------"+r.getField("dato"));
		}
		
		/*
		
		ArrayList<Object> arrIn= new ArrayList<Object>();
		String[] a= new String[]{"A","B"};
		String[] b= new String[]{"11","B"};
		String[] c= new String[]{"22","B"};
		arrIn.add(a);
		arrIn.add(b);
		arrIn.add(c);
		String[] z= new String[]{"A","B"};
		
		arrIn.removeIf(x -> x.equals(z));
		ListIterator<Object> iter =arrIn.listIterator();
		while(iter.hasNext()){
		    String[] zz=(String[])iter.next();
		    if(zz[0].equals("A"))
		        iter.remove();
		     
		}
		
		System.out.println(arrIn.size());
		 
		String[] params = new String[]{"org.postgresql.Driver","jdbc:postgresql://localhost:5432/lcom_team1","javauser","jesUs_33"};
		Sys sys= new Sys();
		 
        
		DbConn db = new DbConn(sys);
		System.out.println("rc"+db.connect(params));
		System.out.println("rc"+db.execQuery("SELECT * from webusers" )); db.nextRecord();
		System.out.println("rc"+db.getField(1));
	      
         
//      campo db,tipo dato,len max , puo' essere modificato (true) false usato solo in INSERT   
		/*db.startProc();
        db.addProc("machine","s","SERVER200",15,true);
        db.addProc("appname","s","SERVER200",15,true);
        db.addProc("nrelease","s","SERVER2111110",5,true);
        db.addProc("url","s","SERVER11",15,true);
        db.addProc("bottom_text","s","<b>banana software</b>",300,true);
        db.connect(params);
        int ret=db.execProc("INSERT", "SERVERS","");   	
	    System.out.println("rc"+ret);
	    */
	}

}