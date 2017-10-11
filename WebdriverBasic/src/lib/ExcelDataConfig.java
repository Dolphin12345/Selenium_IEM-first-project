package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelDataConfig {
	
	HSSFWorkbook wb;
	HSSFSheet sheet1;
	
	public ExcelDataConfig(String excelPath)
	{
		
		try
		{
			File src = new File(excelPath);
			FileInputStream fis = new FileInputStream(src);
			wb = new HSSFWorkbook(fis);
			FileOutputStream outFile =new FileOutputStream(src);
			wb.write(outFile);
			outFile.close();
				 
		}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
	}
	
	public String getData(int sheetNumber,int row, int column)
	{
		sheet1 = wb.getSheetAt(sheetNumber);
		String data = sheet1.getRow(row).getCell(column).getStringCellValue();
		return data;
	}
	
	public int getRowCount(int sheetIndex)
	{
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		row = row+1;
		return row;
	}
	
	
	public HSSFSheet getSheet(int sheetIndex) {
		return wb.getSheetAt(sheetIndex);
	}
	

}
