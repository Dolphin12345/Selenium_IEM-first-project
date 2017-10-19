package lib;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class GetCellToString {
	public static String getCellValue(HSSFCell cell) {
		String value = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		default:
			value = cell.getStringCellValue();
			break;
		}
		return value;
}

}
