package base.db;

import java.util.Comparator;

 public class IntegerComparatorAsc implements Comparator {
	public final int compare ( Object a, Object b )
    {
	    try {
			int x1=Integer.parseInt((String)a );
			int x2=Integer.parseInt((String)b );
			if( x1>x2) return 1;
			if( x1<x2) return -1;
			return 0;
		} catch (NumberFormatException e) {
			return 0;
		}
    } 
}

