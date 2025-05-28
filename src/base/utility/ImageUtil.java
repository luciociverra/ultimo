package base.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

import base.utility.Scalr.Rotation;
public class ImageUtil {
	protected BufferedImage src;
	public boolean systemOut = true;
	public String messages = "";
    public int iHeight=0;
    public int iWidth=0;
    public String last_error="";
    
	/*
	public static void main(String[] args) {
		try {
			ImageUtil test = new ImageUtil();   ///  with height
			test.out("" + test.scaleImage(test.load("C:/appo/dai/tuta.jpg"), 40,
							40, false, "c:/appo/dai/tuta3.png"));
			
			test.out("" + test.rotateImg("C:/appo/dai/tuta.jpg", 90));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
    public void getImgInfo(String aFileName)
    {
    	try {
	    	BufferedImage bimg = ImageIO.read(new File(aFileName));
	    	iWidth  = bimg.getWidth();
	    	iHeight = bimg.getHeight();
	    } catch (Exception e) {
		}
    }
    
	public void out(String aLine) {
		messages += aLine;
		if (systemOut)
			System.out.println(aLine);
	}

	public BufferedImage load(String name) {
		BufferedImage i = null;

		try {
			i = ImageIO.read(new File(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public void save(BufferedImage image, String name) {
		try {
			ImageIO.write(image, "PNG", new FileOutputStream(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean scaleImage(BufferedImage image, int maxWidth, int maxHeight,
			boolean crop, String destFile) throws Exception {
		// Make sure the aspect ratio is maintained, so the image is not skewed
		BufferedImage thumbImage = null;
		try {
			if (crop) {
				thumbImage = Scalr.crop(image, maxWidth, maxHeight);
			} else {
				if (maxHeight == 0) {
					// Just used in custom size case. When 0 as specified as
					// height then it will be calculated automatically to fit
					// with expected width.
					thumbImage = Scalr.resize(image, Scalr.Mode.FIT_TO_WIDTH,
							maxWidth);
				} else if (maxWidth == 0) {
					// Just used in custom size case. When 0 as specified as
					// width then it will be calculated automatically to fit
					// with expected height.
					thumbImage = Scalr.resize(image, Scalr.Mode.FIT_TO_HEIGHT,
							maxHeight);
				} else {
					// BALANCED: Used to indicate that the scaling
					// implementation should use a scaling operation balanced
					// between SPEED and QUALITY
					thumbImage = Scalr.resize(image, Scalr.Method.BALANCED,
							maxWidth, maxHeight);
				}
			}
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ImageIO.write(thumbImage, "png", outStream);
			FileOutputStream fos = new FileOutputStream(new File(destFile));
			outStream.writeTo(fos);
			return true;
		} catch (Exception e) {
			out(e.getMessage());
			return false;
		}
	}
	 public String cropImage(Sys sys,String fileIn,String destFile,int x, int y, int w, int h) {
	 	   BufferedImage thumbImage = null;
	 	   try {
	 			BufferedImage image = ImageIO.read(new File(fileIn));
	 			thumbImage=Scalr.crop(image, x,y,w,h);
	 		 
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
	public boolean rotateImg(String aFile,int rotation) throws Exception {
		// Make sure the aspect ratio is maintained, so the image is not skewed
		BufferedImage thumbImage = null;
		try {
			Rotation real_rotation=null;
			if (rotation==90) real_rotation=Rotation.CW_90;
			if (rotation==180) real_rotation=Rotation.CW_180;
			thumbImage = Scalr.rotate(load(aFile), real_rotation);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ImageIO.write(thumbImage, "png", outStream);
			FileOutputStream fos = new FileOutputStream(new File(aFile));
			outStream.writeTo(fos);
			return true;
		} catch (Exception e) {
			out(e.getMessage());
			return false;
		}
	}
	// raccomando 0.5 0.7f ..
	 public  boolean compressJPG(String fIn,String fOut,float quality)  
	 { last_error="";
	    try {
	        File imageFile = new File(fIn);
	        File compressedImageFile = new File(fOut);
	 
	        InputStream is = new FileInputStream(imageFile);
	        OutputStream os = new FileOutputStream(compressedImageFile);
	 
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
	   	    return true;
	   	 } 
	     catch (Exception e) {
			out(e.getMessage());
			last_error=fIn+" "+fOut+ " "+e.getMessage();
    	    return false;	   		 
	   	 }
	}
}
