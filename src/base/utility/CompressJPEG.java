package base.utility;



	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.util.Iterator;
	 
	import javax.imageio.IIOImage;
	import javax.imageio.ImageIO;
	import javax.imageio.ImageWriteParam;
	import javax.imageio.ImageWriter;
	import javax.imageio.stream.ImageOutputStream;
import javax.mail.Flags.Flag;

import org.apache.poi.hssf.util.HSSFColor.GOLD;

import base.db.RsTable;
	 
	public class CompressJPEG {
		public static void main(String[] args) {
			try {
		
				CompressJPEG compressJPEG = new CompressJPEG();
				System.out.println(
						compressJPEG.elabFile("D:/java/ProductHub/WebContent/catalogoB/260.jpg","c:/appo/a/260.jpg",1.0f)
						);		
				System.out.println(						compressJPEG.log);
	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    public String log="";
	    
	    public String go() {
	    	
	    	try {
				String folderIn = "E:/teoremastore/resources/catalogo";
				String folderOut = "E:/teoremastore/resources/catalog/";
				
				//folderIn = "c:/appo/imin";
				//folderOut = "c:/appo/imout/";
				float quality = 0.9f;
				
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				while (rs.next()) {
					c++;
					this.compress(rs.getField("PATH"), folderOut + rs.getField("NAME"),quality);
				}
				return "FINE "+c;
			} catch (Exception e) {
				return e.getMessage();
			}
	    	
	    }
	    
       public String elab(String folderIn,String folderOut) {
	    	
	    	try {
	    		log="INIZIO elab<br>";
			    float quality = 0.9f;
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				log+=" in input" + rs.size()+"<br>";
				
				while (rs.next()) {
					c++;
					boolean result = this.compress(rs.getField("PATH"), folderOut + rs.getField("NAME"),quality);
					// errore compressione lascio originale
					if(! result)
					{
						result=fu.copyFile(rs.getField("PATH"), folderOut + rs.getField("NAME"));
					    log+="<span color='red'> lascio file originale " + result+"</span><br>";
					}
				}
				return "FINE numero immagini elaborate"+c +"  ratio:"+quality;
			} catch (Exception e) {
				return "ERRORE "+e.getMessage();
			}
	    	
	    }
       // tutti da una data di creazione in poi
       public String elab(String folderIn,String folderOut,int ggAgo) {
	    	int fromDate=Integer.parseInt(Utils.getSpcDateAMG(ggAgo));
	    	try {
	    		log="INIZIO elab da data :"+fromDate +"<br/>";
			    float quality = 0.9f;
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				log+=" in input " + rs.size()+"<br>";
				
				while (rs.next()) {
					//formato 20220105
					if(rs.getFieldInt("DATE") < fromDate) continue;
					log+="data file:"+rs.getFieldInt("DATE")+" data :"+fromDate +"   "+(rs.getFieldInt("DATE") < fromDate) +"<br/>";
					c++;
					boolean result = this.compress(rs.getField("PATH"), folderOut + rs.getField("NAME"),quality);
					// errore compressione lascio originale
					if(! result)
					{
						result=fu.copyFile(rs.getField("PATH"), folderOut + rs.getField("NAME"));
					    log+="<span color='red'> lascio file originale " + result+"</span><br>";
					}
				}
				return log+ "<br/>FINE numero immagini elaborate"+c +"  ratio:"+quality;
			} catch (Exception e) {
				return "ERRORE "+e.getMessage();
			}
	    }
       // formato AAAAMM o AAAAMMGG
       public String elabMese(String folderIn,String folderOut,String annoMese) {
	    	try {
	    		log="INIZIO elab ANNO_MESE :"+annoMese +"<br/>";
			    float quality = 0.9f;
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				log+=" in input " + rs.size()+"<br>";
				
				while (rs.next()) {
					if(rs.getField("DATE").startsWith((annoMese))) 
					{
					c++;
					boolean result = this.compress(rs.getField("PATH"), folderOut + rs.getField("NAME"),quality);
					// errore compressione lascio originale
					if(! result)
					{
						result=fu.copyFile(rs.getField("PATH"), folderOut + rs.getField("NAME"));
					    log+="<span color='red'> lascio file originele " + result+"</span><br>";
					}
				   }
				}
				return log+ "<br/>FINE numero immagini elaborate"+c +"  ratio:"+quality;
			} catch (Exception e) {
				return "ERRORE "+e.getMessage();
			}
	    }
       //solo i mancanti
       public String elabNuovi(String folderIn,String folderOut) {
	    	
	    	try {
	    		log="INIZIO elab<br>";
			    float quality = 0.9f;
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				log+=" in input" + rs.size()+"<br>";
				
				while (rs.next()) {
					if (Utils.existFile(folderOut + rs.getField("NAME"))) continue;
					c++;
					boolean result = this.compress(rs.getField("PATH"), folderOut + rs.getField("NAME"),quality);
					// errore compressione lascio originale
					if(! result)
					{
						result=fu.copyFile(rs.getField("PATH"), folderOut + rs.getField("NAME"));
					    log+="<span color='red'> lascio file originale " + result+"</span><br>";
					}
				}
				return "FINE numero immagini elaborate"+c +"  ratio:"+quality;
			} catch (Exception e) {
				return "ERRORE "+e.getMessage();
			}
	    	
	    }
	    
       public String elabNome(String folderIn,String folderOut,String iniziaPer) {
	    	try {
	    		log="INIZIO elabNome "+iniziaPer +"<br>";
			    float quality = 0.9f;
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				log+=" in input" + rs.size()+"<br>";
				
				while (rs.next()) {
					if (rs.getField("NAME").startsWith(iniziaPer)) {
					c++;
					boolean result = this.compress(rs.getField("PATH"), folderOut + rs.getField("NAME"),quality);
				    log+="<span>Elaborato " + rs.getField("NAME")+"</span><br>";
				  }
				}
				return "FINE numero immagini elaborate"+c +"  ratio:"+quality;
			} catch (Exception e) {
				return "ERRORE "+e.getMessage();
			}
	    	
	    }
	     
       public String elabFile(String fileIn,String fileOut, float quality) {
	      try {
	    		log="INIZIO elabFile<br>";
	    		FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
	    		boolean result = this.compress(fileIn, fileOut,quality);
			   // errore compressione lascio originale
				if(! result)
				{
					result=fu.copyFile(fileIn,fileOut);
				    log+="<span color='red'> lascio file originale " + result+"</span><br>";
				}
				return "FINE   ratio:"+quality;
			} catch (Exception e) {
				return "ERRORE elabFile "+e.getMessage();
			}
	    	
	    }
       
 	    
	    public boolean compress(String fileIn,String fileOut,float quality )  {
	   	 try  {
	        File imageFile = new File(fileIn);
	        File compressedImageFile = new File(fileOut);
	 
	        InputStream is = new FileInputStream(imageFile);
	        OutputStream os = new FileOutputStream(compressedImageFile);
	        // create a BufferedImage as the result of decoding the supplied InputStream
	        BufferedImage image = ImageIO.read(is);
	 
	        // get all image writers for JPG format
	        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
	 
	        if (!writers.hasNext())
	        {
	        	log+=" errore No writers found" + fileIn+"<br>";
	        	throw new IllegalStateException("No writers found");
	        }
	        ImageWriter writer = (ImageWriter) writers.next();
	        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	        writer.setOutput(ios);
	 
	        ImageWriteParam param = writer.getDefaultWriteParam();
	 
	        // compress to a given quality
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        param.setCompressionQuality(quality);
	 
	        // appends a complete image stream containing a single image and
	        //associated stream and image metadata and thumbnails to the output
	        writer.write(null, new IIOImage(image, null, null), param);
	 
	        // close all streams
	        is.close();
	        os.close();
	        ios.close();
	        writer.dispose();
	        log+=" OK "+fileOut+"<br>";
	        //System.out.println("OK"+fileOut);
	   	    return true;
	   	 }
	 catch(Exception e) {
		 // e.printStackTrace();
		  log+="err "+fileIn+ "  "+e.getMessage() + "<br>";
		  return false;
	    }
	 }
}