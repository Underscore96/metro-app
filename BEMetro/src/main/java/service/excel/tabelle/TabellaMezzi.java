package service.excel.tabelle;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import db.entity.Mezzo;
import service.excel.GestoreStileCella;

public class TabellaMezzi {

	private TabellaMezzi() {
	}

	public static void genera(Sheet sheet, Workbook workbook,
			List<Mezzo> listaMezzi) {

		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 7000);

		Integer numeroRiga = creaTitoli(sheet, workbook);
		creaRigheMezzi(sheet, workbook, listaMezzi, numeroRiga);
	}

	private static Integer creaTitoli(Sheet sh, Workbook wb) {
		Row rigaTitoli = sh.createRow(0);
		CellStyle stileTitoli = GestoreStileCella.setStileCellaTitoloBlu(wb);

		XSSFFont font = ((XSSFWorkbook) wb).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		stileTitoli.setFont(font);

		creaCella(stileTitoli, 0, rigaTitoli, "id mezzo");
		creaCella(stileTitoli, 1, rigaTitoli, "numero mezzo");
		creaCella(stileTitoli, 2, rigaTitoli, "passeggeri");
		creaCella(stileTitoli, 3, rigaTitoli, "stato");
		creaCella(stileTitoli, 4, rigaTitoli, "destinazione");

		return 1;
	}

	public static Cell creaCella(CellStyle stileCella, Integer numCella,
			Row riga, String valore) {
		Cell cella = riga.createCell(numCella);
		cella.setCellValue(valore);
		cella.setCellStyle(stileCella);

		return cella;
	}

	private static void creaRigheMezzi(Sheet sh, Workbook wb,
			List<Mezzo> listaMezzi, Integer numRiga) {
		Field[] attributi;
		Row riga;
		Cell cella;
		Object valore = null;
		CellStyle stileCella = GestoreStileCella.setStileCellaBianca(wb);

		XSSFFont font = ((XSSFWorkbook) wb).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setBold(false);
		stileCella.setFont(font);

		for (Mezzo mezzo : listaMezzi) {
			Integer i = listaMezzi.indexOf(mezzo);
			riga = sh.createRow(i + numRiga);

			attributi = mezzo.getClass().getDeclaredFields();

			for (Integer k = 0; k < attributi.length; k++) {
				cella = riga.createCell(k);

				attributi[k].setAccessible(true);

				try {
					if (k >= (attributi.length - 3)) {
						break;
					} else {
						valore = attributi[k].get(mezzo);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}

				caricaValoreCella(valore, cella);
				cella.setCellStyle(stileCella);
			}
		}
	}

	private static Cell caricaValoreCella(Object valore, Cell cella) {
		if (valore != null) {
			if (valore instanceof Number numero)
				cella.setCellValue((numero).doubleValue());
			else
				cella.setCellValue(valore.toString());
		} else {
			cella.setCellValue("");
		}

		return cella;
	}
}
