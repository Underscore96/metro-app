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

import db.entity.Fermata;
import service.excel.GestoreStileCella;

public class TabellaFermate {

	private TabellaFermate() {
	}

	public static void genera(Sheet sheet, Workbook workbook,
			List<Fermata> listaFermate) {

		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);

		Integer numeroRiga = creaTitoli(sheet, workbook);
		creaRigheFermate(sheet, workbook, listaFermate, numeroRiga);
	}

	private static Integer creaTitoli(Sheet sh, Workbook wb) {
		Row rigaTitoli = sh.createRow(0);
		CellStyle stileTitoli = GestoreStileCella.setStileCellaTitoloBlu(wb);

		XSSFFont font = ((XSSFWorkbook) wb).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		stileTitoli.setFont(font);

		creaCella(stileTitoli, 0, rigaTitoli, "id fermata");
		creaCella(stileTitoli, 1, rigaTitoli, "direzione");
		creaCella(stileTitoli, 2, rigaTitoli, "nome");
		creaCella(stileTitoli, 3, rigaTitoli, "numero fermata");
		creaCella(stileTitoli, 4, rigaTitoli, "orario attuale");
		creaCella(stileTitoli, 5, rigaTitoli, "posizione mezzo");
		creaCella(stileTitoli, 6, rigaTitoli, "previsione meteo");

		return 1;
	}

	public static Cell creaCella(CellStyle stileCella, Integer numCella,
			Row riga, String valore) {
		Cell cella = riga.createCell(numCella);
		cella.setCellValue(valore);
		cella.setCellStyle(stileCella);

		return cella;
	}

	private static void creaRigheFermate(Sheet sh, Workbook wb,
			List<Fermata> listaFermate, Integer numRiga) {
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

		for (Fermata fermata : listaFermate) {
			Integer i = listaFermate.indexOf(fermata);
			riga = sh.createRow(i + numRiga);

			attributi = fermata.getClass().getDeclaredFields();

			for (Integer k = 0; k < attributi.length; k++) {
				cella = riga.createCell(k);

				attributi[k].setAccessible(true);

				try {
					if (k >= (attributi.length - 2)) {
						break;
					} else {
						valore = attributi[k].get(fermata);
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
