package base.db;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class RsToExcel {

	public String[] arrayFields = null;
	public String[] typeFields = null;
	public String[] titleFields  = null;
	private HSSFCellStyle styleBold=null;
	 
	private void addCell(Row row, int colonna,   String valore) {
		row.createCell(colonna-1).setCellValue(valore);
	}
	private void addCell(Row row, int colonna,   String valore,HSSFCellStyle aStyle) {
		Cell c=row.createCell(colonna-1);
		c.setCellValue(valore);
		c.setCellStyle(aStyle); 
	}

	private void addCell(Row row, int colonna,  double valore, HSSFCellStyle aStyle) {
		Cell c=row.createCell(colonna-1);
		c.setCellValue(valore);
		c.setCellStyle(aStyle); 
	}
	private void addCell(Row row, int colonna,  double valore) {
		row.createCell(colonna).setCellValue(valore);
	}

	public String toExcel(String tmpDir,RsTable rs) {
		try {
			long from=System.currentTimeMillis();
			Date b = new Date();
			String nomeFile=String.valueOf(b.getTime());
			String fileOut = tmpDir + nomeFile+ ".xlsx";
			String fileOutTmp = "tmp/" + nomeFile + ".xlsx";

			FileOutputStream file = new FileOutputStream(fileOut);
			HSSFWorkbook workbook = new HSSFWorkbook();
			//stile bold
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleBold = workbook.createCellStyle();
			styleBold.setFont(font);
			// stile numerico VALUTA
			//HSSFCellStyle valIt = workbook.createCellStyle();
		    //HSSFDataFormat df = workbook.createDataFormat();
		    //valIt.setDataFormat(df.getFormat("#,###,##0.00"));
		    //
			//HSSFCellStyle valUs = workbook.createCellStyle();
			//valUs.setDataFormat(df.getFormat("$#,##0.00"));
			//	    
			HSSFSheet sheet = workbook.createSheet("Data");
            Vector names=rs.getNames();
            int nCols=names.size();
			Row titleRow = sheet.createRow(0);
            for (int i = 1; i < nCols; i++)  
            	addCell(titleRow, i, (String)names.elementAt(i),styleBold);	
            
			rs.first();
			int posRow = 1;
			while (rs.next()) {
				Row dataRow = sheet.createRow(posRow);
				for (int i = 1; i < nCols; i++) {
						addCell(dataRow, i, rs.getFieldTrim((String)names.elementAt(i)));
				}
				posRow += 1;
			}
			// AUTO SIZE 
			for (int i = 1; i < nCols; i++) {
				sheet.autoSizeColumn(i); 
			}
			workbook.write(file);
			file.flush();
			file.close();
			return fileOutTmp;
		} catch (Exception e) {
			e.printStackTrace();
			return "ER";
		}
	}

}
