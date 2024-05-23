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

import db.entity.Linea;
import service.excel.GestoreStileCella;

public class TabellaLinee {

	private TabellaLinee() {
	}

	public static void genera(Sheet sheet, Workbook workbook,
			List<Linea> listaLinee) {

		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 6000);

		Integer numeroRiga = creaTitoli(sheet, workbook);
		creaRigheLinee(sheet, workbook, listaLinee, numeroRiga);
	}

	private static Integer creaTitoli(Sheet sh, Workbook wb) {
		Row rigaTitoli = sh.createRow(0);
		CellStyle stileTitoli = GestoreStileCella.setStileCellaTitoloBlu(wb);

		XSSFFont font = ((XSSFWorkbook) wb).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		stileTitoli.setFont(font);

		creaCella(stileTitoli, 0, rigaTitoli, "id linea");
		creaCella(stileTitoli, 1, rigaTitoli, "nome linea");
		creaCella(stileTitoli, 2, rigaTitoli, "destinazione");
		return 1;
	}

	public static Cell creaCella(CellStyle stileCella, Integer numCella,
			Row riga, String valore) {
		Cell cella = riga.createCell(numCella);
		cella.setCellValue(valore);
		cella.setCellStyle(stileCella);

		return cella;
	}

	private static void creaRigheLinee(Sheet sh, Workbook wb,
			List<Linea> listaLinee, Integer numRiga) {
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

		for (Linea linea : listaLinee) {
			Integer i = listaLinee.indexOf(linea);
			riga = sh.createRow(i + numRiga);

			attributi = linea.getClass().getDeclaredFields();

			for (Integer k = 0; k < attributi.length; k++) {
				cella = riga.createCell(k);

				attributi[k].setAccessible(true);

				try {
					if (k == (attributi.length - 2)) {
						break;
					} else {
						valore = attributi[k].get(linea);
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
