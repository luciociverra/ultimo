package base.utility;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import base.db.RsTable;

 
public class FileUtility  extends BaseBean {

	private static final long K = 1024;
	private static final long M = K * K;
	private static final long G = M * K;
	private static final long T = G * K;

	public static String folderPartenza="";
	public static String criterio="";
	public static String listaOut="";
	public static int nFiles=0;
	public RsTable rsDir;
	public static void main(String[] args) throws IOException { 
		//new DaiFileUtils().getSize("C:/java");
	    avvia();
	}
	public FileUtility() {
	}
	public FileUtility(Sys aSys) {
		//this.setSys(aSys);
	}
	
	public static void avvia() throws IOException {
		nFiles=0;
		try {
			listaOut="";
		 
			File dir = new File("c:/appo");
			listaOut+="Getting all files in " + dir.getCanonicalPath() + "<br>";
			List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
			for (File file : files) {
				String fName=file.getCanonicalPath();
				 
				  System.out.println("file: " + fName);
				}
		} catch (Exception e) {
			System.out.println("ERRORE DaiFileUtils avvia() "+ folderPartenza);
			e.printStackTrace();
		}
	}

	
	public  String getSize(String aFolder)
	{
		long size = FileUtils.sizeOfDirectory(new File(aFolder));
		String dimension="Folder Size: " + (size/1000000)+ " MB";
		return dimension;
	}
	
	public  RsTable getFolderList(String aFolder){
	
		sys.debugWhite("DaiFileUtils.getFolderList:"+aFolder);
		Format formatter =new SimpleDateFormat("yyyMMdd");
		RsTable rs= new RsTable(new String[]{"NAME","PATH","DATE"});
		try{
		 	File directory = new File(aFolder);
	    	File[] files = directory.listFiles();
			    for (File f : files){
			        if (!f.isDirectory())  
			        {
			        	 Hashtable<String,String> row = new Hashtable<String,String>();
						 row.put("NAME",f.getName());
						 row.put("PATH",f.getAbsolutePath());
						 row.put("DATE",formatter.format(new Date(f.lastModified())));
						 rs.addRow(row);
				    }
			}
			sys.debugWhite("numero cartelle trovate:"+rs.size());
			return rs;
		}
		catch(Exception e)
		{
			sys.error(e);
			return rs;
		}
	}
	public  RsTable getFileList(String aFolder){
		Format formatter =new SimpleDateFormat("dd-MMM-yy HH:MM");
			
		sys.debugWhite("DaiFileUtils.getFileList:"+aFolder);
		RsTable rs= new RsTable(new String[]{"NAME","PATH","SIZE","SIZEMB","DATE","EXT"});
	 	rsDir= new RsTable(new String[]{"NAME","PATH"});
	 	
	 	Hashtable<String,String> rowFirst = new Hashtable<String,String>();
	 	rowFirst.put("NAME","..");
	 	rowFirst.put("PATH",aFolder);
		rsDir.addRow(rowFirst);
			   
		if(isBlank(aFolder)) return rs;
		try{
		 	File directory = new File(aFolder);
	    	File[] files = directory.listFiles();
			    for (File f : files)
			    {
			     Hashtable<String,String> row = new Hashtable<String,String>();
				 if (f.isDirectory())
			     {
			     row.put("NAME",f.getName());
				 row.put("PATH",f.getAbsolutePath());
				 rsDir.addRow(row);
			     }
			     else
			     {
			     long tempSize = f.length();
		         String fDate  =formatter.format(new Date(f.lastModified()));
			     row.put("NAME",f.getName());
			     row.put("PATH",f.getAbsolutePath());
			     row.put("SIZE",convertToStringRepresentation(tempSize));
			     row.put("SIZEMB",String.valueOf(tempSize/1000000)+" Mb");
				 row.put("DATE",fDate); 
			     row.put("EXT",FilenameUtils.getExtension(f.getName()));
		          
			     rs.addRow(row);
			    }
			  }   
			sys.debugWhite("numero di files trovati:"+rs.size());
			return rs;
		}
		catch(Exception e)
		{
			sys.error(e);
			return rs;
		}
	}
	
	
	public Object asJsonObj(RsTable rs){
		
		List<Object> folderContent =new ArrayList<Object>();
		rs.first();
	    while(rs.next()){
	    	  Map<String,String> fileDef=new HashMap<String,String>();
	    	  fileDef.put("file",rs.getFieldTrim("NAME"));
	    	  fileDef.put("thumb",rs.getFieldTrim("NAME"));
	    	  fileDef.put("changed",rs.getFieldTrim("DATE"));
	    	  fileDef.put("size",rs.getFieldTrim("SIZE"));
	    	  folderContent.add(fileDef);
	    }
	    Map<String,Object> folderDef=new HashMap<String,Object>();
		folderDef.put("baseurl","");
		folderDef.put("path","");
		folderDef.put("files",folderContent);
		
		Map<String,Object> defaultMap= new HashMap<String,Object>();
		defaultMap.put("default",folderDef);
		return defaultMap; 
		
	    /*
		"test": {
        "baseurl": "http://localhost:8181/tests/files/",
        "path": "",
        "files": [
            {
                "file": "artio.jpg",
                "thumb": "_thumbs\\artio.jpg",
                "changed": "07/07/2017 3:06 PM",
                "size": "53.50kB"
            }
          ]
        }
    */
		
	}
	// {"success":true,"time":"2018-06-13 08:18:41","data":{"sources":{"default":{"baseurl":"https:\/\/xdsoft.net\/jodit\/files\/","path":"","folders":[".","ceicom","test"]}},"code":220}}
	// {"success":true,"time":"2018-06-13 08:18:41","data":{"sources":{"default":{"baseurl":"https:\/\/xdsoft.net\/jodit\/files\/","path":"","files":[{"file":"1966051_524428741092238_1051008806888563137_o.jpg","thumb":"_thumbs\/1966051_524428741092238_1051008806888563137_o.jpg","changed":"06\/13\/2018 8:00 AM","size":"126.59kB","isImage":true},{"file":"Hello-World.xlsx","thumb":"_thumbs\/Hello-World.xlsx.png","changed":"06\/13\/2018 8:00 AM","size":"9.67kB","isImage":false},{"file":"5826239-bird-pictures.jpg","thumb":"_thumbs\/5826239-bird-pictures.jpg","changed":"06\/13\/2018 8:08 AM","size":"1.64MB","isImage":true},{"file":"images.jpg","thumb":"_thumbs\/images.jpg","changed":"06\/13\/2018 8:00 AM","size":"6.84kB","isImage":true},{"file":"ibanez-s520-443140.jpg","thumb":"_thumbs\/ibanez-s520-443140.jpg","changed":"06\/13\/2018 8:00 AM","size":"18.72kB","isImage":true},{"file":"6901229-free-serene-wallpaper.jpg","thumb":"_thumbs\/6901229-free-serene-wallpaper.jpg","changed":"06\/13\/2018 8:00 AM","size":"277.10kB","isImage":true},{"file":"download.jpg","thumb":"_thumbs\/download.jpg","changed":"06\/13\/2018 8:00 AM","size":"24.14kB","isImage":true},{"file":"g635i.jpg","thumb":"_thumbs\/g635i.jpg","changed":"06\/13\/2018 8:00 AM","size":"380.37kB","isImage":true},{"file":"PT_hero_42_153645159.jpg","thumb":"_thumbs\/PT_hero_42_153645159.jpg","changed":"06\/13\/2018 8:00 AM","size":"161.13kB","isImage":true},{"file":"woman.png","thumb":"_thumbs\/woman.png","changed":"06\/13\/2018 8:00 AM","size":"56.42kB","isImage":true},{"file":"funny-face.jpg","thumb":"_thumbs\/funny-face.jpg","changed":"06\/13\/2018 8:00 AM","size":"74.58kB","isImage":true},{"file":"Hello-world.docx","thumb":"_thumbs\/Hello-world.docx.png","changed":"06\/13\/2018 8:00 AM","size":"0.00B","isImage":false},{"file":"tola.png","thumb":"_thumbs\/tola.png","changed":"06\/13\/2018 8:00 AM","size":"143.07kB","isImage":true},{"file":"13828_985968411418511_2954558705761166512_n.jpg","thumb":"_thumbs\/13828_985968411418511_2954558705761166512_n.jpg","changed":"06\/13\/2018 8:00 AM","size":"48.86kB","isImage":true},{"file":"th.jpg","thumb":"_thumbs\/th.jpg","changed":"06\/13\/2018 8:00 AM","size":"14.26kB","isImage":true},{"file":"Hello.txt","thumb":"_thumbs\/Hello.txt.png","changed":"06\/13\/2018 8:00 AM","size":"6.00B","isImage":false},{"file":"profesor.2.jpg","thumb":"_thumbs\/profesor.2.jpg","changed":"06\/13\/2018 8:00 AM","size":"62.06kB","isImage":true},{"file":"db4ed-baros-maldives.jpg","thumb":"_thumbs\/db4ed-baros-maldives.jpg","changed":"06\/13\/2018 8:00 AM","size":"66.55kB","isImage":true},{"file":"793.jpg","thumb":"_thumbs\/793.jpg","changed":"06\/13\/2018 8:00 AM","size":"54.91kB","isImage":true},{"file":"timthumb.jpg","thumb":"_thumbs\/timthumb.jpg","changed":"06\/13\/2018 8:00 AM","size":"16.65kB","isImage":true},{"file":"podberi-sobaku-thumb.jpg","thumb":"_thumbs\/podberi-sobaku-thumb.jpg","changed":"06\/13\/2018 8:00 AM","size":"14.00kB","isImage":true},{"file":"car4.jpg","thumb":"_thumbs\/car4.jpg","changed":"06\/13\/2018 8:00 AM","size":"45.60kB","isImage":true},{"file":"12.jpg","thumb":"_thumbs\/12.jpg","changed":"06\/13\/2018 8:00 AM","size":"39.77kB","isImage":true},{"file":"bhart200.jpg","thumb":"_thumbs\/bhart200.jpg","changed":"06\/13\/2018 8:00 AM","size":"6.79kB","isImage":true},{"file":"Background-Image.jpg","thumb":"_thumbs\/Background-Image.jpg","changed":"06\/13\/2018 8:00 AM","size":"70.59kB","isImage":true}]}},"code":220}}

	public Object asFolderJsonObj(RsTable rs,boolean canBack) {
		List<Object> folderContent =new ArrayList<Object>();
		Map<String,String> folders=new HashMap<String,String>();
    	//fileDef.put("file",rs.getFieldTrim("NAME"));
     	rs.first();
		Vector<String> folderArray= new Vector<String>();
	    while(rs.next()){
	    	  if((! canBack) && rs.getFieldTrim("NAME").equals("..")) continue;
	    	  folderArray.addElement(rs.getFieldTrim("NAME"));
	    }
	    Map<String,Object> folderDef=new HashMap<String,Object>();
		folderDef.put("baseurl","https:\\xdsoft.net/jodit/files/");
		folderDef.put("path","");
		folderDef.put("folders",folderArray);
		Map<String,Object> defaultMap= new HashMap<String,Object>();
		defaultMap.put("default",folderDef);
		
		return defaultMap; 
		
	}
	
	
	
	
	
	
	
	
	public boolean copyFile(String remoteFileName,String localFile){
	try {	
		FileUtils.copyFile(new File(remoteFileName),new File(localFile));
	    return true;
	} catch(Exception e){
		return false;
	}
	}
	public boolean createFolder(String newFolder){
		try{
			sys.debugWhite("DaiFileUtils.createFolder:"+newFolder);
			FileUtils.forceMkdir(new File(newFolder));
			sys.debug(newFolder+" Cartella creata? "+new File(newFolder).exists());
			return true;
		}
		catch(Exception e)
		{
			sys.error(e);
			return false;
		}
	}
	public static boolean deleteFile(String aFile) {
		try {
			File f = new File(aFile);
			if (f.exists() && f.isFile()) {
				f.delete();
				f=null;
				return true;
			} else {
				return false;
			}
		} catch (Exception xx) {
			return false;
		}
	}
	
	public static boolean deleteEmptyFolder(String aFolder) {
		try {
			File f = new File(aFolder);
			if (f.exists() && f.isDirectory()) {
				f.delete();
				f=null;
				return true;
			} else {
				return false;
			}
		} catch (Exception xx) {
			return false;
		}
	}
	
	public   String convertToStringRepresentation(final long value){
	    final long[] dividers = new long[] { T, G, M, K, 1 };
	    final String[] units = new String[] { "Tb", "Gb", "Mb", "Kb", "B" };
	    if(value < 1)
	        throw new IllegalArgumentException("Invalid file size: " + value);
	    String result = null;
	    for(int i = 0; i < dividers.length; i++){
	        final long divider = dividers[i];
	        if(value >= divider){
	            result = format(value, divider, units[i]);
	            break;
	        }
	    }
	    return result;
	}

	private   String format(final long value,
	    final long divider,
	    final String unit){
	    final double result =divider > 1 ? (double) value / (double) divider : (double) value;
	    return new DecimalFormat("#,##0.#").format(result) + " " + unit;
	}

    public static String outFile(String aPath){
    	try {
    		String ret="";
    		String line="";
    		BufferedReader br = new BufferedReader(new FileReader(aPath));
    		while ((line = br.readLine()) != null) {
    			ret+=line;
    		}
    		br.close(); 
    		br=null;
    		return ret;
    	}
    	catch(Exception e){
    		return "";
    	}
    }
}


