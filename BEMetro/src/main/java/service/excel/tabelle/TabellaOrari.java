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

import db.entity.Orario;
import service.excel.GestoreStileCella;

public class TabellaOrari {

	private TabellaOrari() {
	}

	public static void genera(Sheet sheet, Workbook workbook,
			List<Orario> listaOrari) {

		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 7000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);

		Integer numeroRiga = creaTitoli(sheet, workbook);
		creaRigheOrari(sheet, workbook, listaOrari, numeroRiga);
	}

	private static Integer creaTitoli(Sheet sh, Workbook wb) {
		Row rigaTitoli = sh.createRow(0);
		CellStyle stileTitoli = GestoreStileCella.setStileCellaTitoloBlu(wb);

		XSSFFont font = ((XSSFWorkbook) wb).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		stileTitoli.setFont(font);

		creaCella(stileTitoli, 0, rigaTitoli, "id orario");
		creaCella(stileTitoli, 1, rigaTitoli, "numero orario");
		creaCella(stileTitoli, 2, rigaTitoli, "numero fermata");
		creaCella(stileTitoli, 3, rigaTitoli, "orario previsto");
		creaCella(stileTitoli, 4, rigaTitoli, "orario ritardo");

		return 1;
	}

	public static Cell creaCella(CellStyle stileCella, Integer numCella,
			Row riga, String valore) {
		Cell cella = riga.createCell(numCella);
		cella.setCellValue(valore);
		cella.setCellStyle(stileCella);

		return cella;
	}

	private static void creaRigheOrari(Sheet sh, Workbook wb,
			List<Orario> listaOrari, Integer numRiga) {
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

		for (Orario orario : listaOrari) {
			Integer i = listaOrari.indexOf(orario);
			riga = sh.createRow(i + numRiga);

			attributi = orario.getClass().getDeclaredFields();

			for (Integer k = 0; k < attributi.length; k++) {
				cella = riga.createCell(k);

				attributi[k].setAccessible(true);

				try {
					if (k >= (attributi.length - 1)) {
						break;
					} else {
						valore = attributi[k].get(orario);
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
