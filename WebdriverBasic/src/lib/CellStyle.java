package lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class CellStyle {
//	public static HSSFWorkbook wb;
//	HSSFSheet sheet1;

	public static void setCellStyle(HSSFCell cell) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet spreadsheet = workbook.createSheet("Fontstyle");
		HSSFRow row = spreadsheet.createRow(2);
		// Create a new font and alter it.
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 30);
		font.setFontName("IMPACT");
		font.setItalic(true);
		font.setColor(HSSFColor.ORANGE.index);
		// Set font into style
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		// Create a cell with a value and set style to it.
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("Font Style");
		cell1.setCellStyle(style);
		FileOutputStream out = new FileOutputStream(new File("fontstyle.xlsx"));
		workbook.write(out);
		out.close();
		System.out.println("fontstyle.xlsx written successfully");
	}
}
