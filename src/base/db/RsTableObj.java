package base.db;
	
public class RsTableObj implements Comparable<RsTableObj>{
   public double valore;
   public String valoreStr;
   public int chiave;
   public RsTableObj () {}
   
   public RsTableObj (double parteValore,int parteChiave) {
	   this.valore=parteValore;
	   this.chiave=parteChiave;
   }
 
   public int compareTo(RsTableObj arrayCompare) {
       double compValue= ((RsTableObj)arrayCompare).valore;
       return Double.compare(valore,compValue);
       //  For Ascending order
       //if( valore>compValue) return 1;
	   //if( valore<compValue) return -1;
       //return 0;
 
       // For Descending order do like this
       // return compValue-this.valore;
   }
}
