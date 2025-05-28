package base.utility;

/* 0 basso altro alto */
public class ErrorManager {
   public static void manage(String aStr,int gravity) {
	   System.out.println(aStr +" liv:"+gravity );
   }
   public static void manage(String aStr) {
	   manage(aStr,0);
   }

}
