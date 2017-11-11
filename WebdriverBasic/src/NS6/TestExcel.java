package NS6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class TestExcel {
	
	public static void main(String[] args) {
		try {
			FileInputStream file = new FileInputStream(new File("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\LoginUnsuccess - Copy.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				for (int j = 0; j <= row.getLastCellNum(); j++) {
					Cell cell = sheet.getRow(i).getCell(j);
					if (cell == null)
						continue;
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						System.out.print(cell.getNumericCellValue() + "|");
					} else {
						System.out.print(cell.getStringCellValue() + "|");
					}
				}
				System.out.println();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
