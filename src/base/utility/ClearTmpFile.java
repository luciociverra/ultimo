package base.utility;

import java.io.File;
import java.util.Date;

 

public class ClearTmpFile extends BaseBean {

/** data una cartella leggo tutti i file in essa contenuti se sono stati prodotti nel giorno
/* precedente vengono eliminati
*/
public void delete(Sys sys)
{
    try  {

         String aExt,aBody;
         long startDate;
         String aFolder=sys.getFolderTmp();
         long datediff = (1 * 24 * 60 * 60 * 1000);
         long oggi = new Date().getTime();
	 startDate = oggi - datediff;
         File currentDir = new File(aFolder);
	 String[] temp = currentDir.list();
	 for(int i = 0; i < temp.length; i++)
         {
	    String aFile = temp[i];

            int z =aFile.lastIndexOf(".") + 1;
	    if (z>0)
	    {
		aExt = aFile.substring(z,aFile.length()).toUpperCase();
	        aBody = aFile.substring(0,aFile.length()-4);
            	 if (aExt.equals("PDF") || aExt.equals("ZIP") || aExt.equals("XLS"))
                    {
                    sys.debug(aExt + "  " + aBody + " " + startDate);
                    if (Long.parseLong(aBody) < startDate)
                        deleteFile(aFolder + aFile);
		    }
	    }
	 }
    }
    catch (Exception xx) { sys.error("Clear tmp file : " + xx.getMessage());
      }
   }

  private static void deleteFile(String aFile)
 {
 try  {
      File f = new File(aFile);
      if(f.exists())
         f.delete();
      }
catch (Exception xx)
      {
      }
  }

}