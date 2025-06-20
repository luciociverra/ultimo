package base.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import base.db.RsTable;

public class CompressImages {

	public static void main(String[] args) {
			
	    	try {
				String folderIn = "C:\\Advanced\\img_other";
				String folderOut = "C:\\Advanced\\img_other2\\";
				
				//folderIn = "c:/appo/imin";
				//folderOut = "c:/appo/imout/";
				
				FileUtility fu = new FileUtility();
				fu.setSys(new Sys());
				int c=0;
				RsTable rs = fu.getFolderList(folderIn);
				while (rs.next()) {
					c++;
					 compress(rs.getField("PATH"), folderOut + rs.getField("NAME"));
				}
				
			} catch (Exception e) {
				//
			}
	    	
	    
	    

	}
	public static boolean compress(String fileIn,String fileOut)  {
	   	 try  {
	        File imageFile = new File(fileIn);
	        File compressedImageFile = new File(fileOut);
	 
	        InputStream is = new FileInputStream(imageFile);
	        OutputStream os = new FileOutputStream(compressedImageFile);
	 
	        float quality = 0.9f;
	 
	        // create a BufferedImage as the result of decoding the supplied InputStream
	        BufferedImage image = ImageIO.read(is);
	 
	        // get all image writers for JPG format
	        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
	 
	        if (!writers.hasNext())
	            throw new IllegalStateException("No writers found");
	 
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
	        System.out.println("OK"+fileOut);
	   	    return true;
	   	 }
	 catch(Exception e) {
		 return false;
	    }
	 }

}
