package base.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

 /*
 * ogni volta che gestisto un documento-cliente tratto una serie di destinazioni replicate
 * per aumentare la sicurezza e gestire lo sviluppo in eclispse
 */
public class FileManager extends BaseBean {
   
   private  HashMap<String, String> strFolders = null;
   private  String fsep = System.getProperty("file.separator");
   public FileManager(Sys aSys) {
   	   setSys(aSys);
 	   strFolders = new HashMap<String, String>();
	   strFolders.put("C:/A_space02/neon_workspaces/suipassi/WebContent/", "w"); // w= cartella reale di lavoro del server
	   strFolders.put("C:/A_space02/neon_workspaces/suipassi/WebContent/","");
   }
   public String getWebFolder(){
	   for(Map.Entry mapItem:strFolders.entrySet()){  
		   if(mapItem.getValue().equals("w")) return (String)mapItem.getKey();  
		  }  
	   return "";
   }

   public String getTmpFolder() {
	   return getWebFolder()+"tmp"+fsep; 
   }
   public String cropImage(Sys sys,String fileIn,String destFile,int x, int y, int w, int h) {
	   BufferedImage thumbImage = null;
	   try {
			BufferedImage image = ImageIO.read(new File(fileIn));
			thumbImage=Scalr.crop(image, x,y,w,h);
			/*
			sys.debug("Original image dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());

			BufferedImage SubImgage = originalImgage.getSubimage(x, y, w, h);
			sys.debug("Cropped image dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());

			File outputfile = new File(destFile);
			ImageIO.write(SubImgage, "jpg", outputfile);
			
			sys.debug("Image cropped successfully: "+outputfile.getPath());
			
			return outputfile.getName();
*/
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ImageIO.write(thumbImage, "jpg", outStream);
			FileOutputStream fos = new FileOutputStream(new File(destFile));
			outStream.writeTo(fos);
			outStream.close();
			return destFile;
		} catch (IOException e) {
			sys.error(e.getMessage());
			return "";
		}
	}
   public boolean storeFileImg(String aFile,String aType,String aId)
   {
	   try {
		   boolean realId=aId.length()<6;
		   File srcFile=new File(aFile);
		   String destFileIco="";
		   if(realId)
		      destFileIco=WebContext.getEnv("dep_zone")+aId+"/ico.png";
		   else
			  destFileIco+="workimage/"+aId+".png";
			 
			for(Map.Entry mapItem:strFolders.entrySet()){  
			   String destFolder=(String)mapItem.getKey();
			   String fullName=destFileIco;
			   Utils.deleteFile(destFileIco);
			   FileUtils.copyFile(srcFile,new File(fullName));
			   sys.debug("id:"+aId +" targetImage:"+aType +" nuovo file dest :"+fullName);
		   }  
		   srcFile.delete();
		   return true;
	} catch (Exception e) {
		sys.error(e);
		return false;
	}
   }
}
