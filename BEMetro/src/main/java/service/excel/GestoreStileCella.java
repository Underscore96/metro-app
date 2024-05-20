package service.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class GestoreStileCella {

	private GestoreStileCella() {
	}

	public static CellStyle setStileCellaBianca(Workbook wb) {
		CellStyle style = wb.createCellStyle();

		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		BorderStyle borderStyle = BorderStyle.THIN;
		short borderColor = IndexedColors.BLACK.getIndex();

		style.setAlignment(HorizontalAlignment.LEFT);

		style.setBorderTop(borderStyle);
		style.setBorderRight(borderStyle);
		style.setBorderBottom(borderStyle);
		style.setBorderLeft(borderStyle);

		style.setTopBorderColor(borderColor);
		style.setRightBorderColor(borderColor);
		style.setBottomBorderColor(borderColor);
		style.setLeftBorderColor(borderColor);
		return style;
	}

	public static CellStyle setStileCellaTitoloBlu(Workbook wb) {
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		BorderStyle borderStyle = BorderStyle.THIN;
		short borderColor = IndexedColors.BLACK.getIndex();

		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		headerStyle.setBorderTop(borderStyle);
		headerStyle.setBorderRight(borderStyle);
		headerStyle.setBorderBottom(borderStyle);
		headerStyle.setBorderLeft(borderStyle);

		headerStyle.setTopBorderColor(borderColor);
		headerStyle.setRightBorderColor(borderColor);
		headerStyle.setBottomBorderColor(borderColor);
		headerStyle.setLeftBorderColor(borderColor);

		return headerStyle;
	}
}