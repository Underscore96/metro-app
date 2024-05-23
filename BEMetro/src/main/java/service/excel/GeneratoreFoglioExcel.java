package service.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import db.dao.CorsaDAO;
import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.dao.OrarioDAO;
import db.entity.Corsa;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Orario;
import exception.CustomException;
import jakarta.ws.rs.core.Response;
import service.excel.tabelle.TabellaCorse;
import service.excel.tabelle.TabellaFermate;
import service.excel.tabelle.TabellaLinee;
import service.excel.tabelle.TabellaMezzi;
import service.excel.tabelle.TabellaOrari;

public class GeneratoreFoglioExcel {
	private static LineaDAO lineaDAO = new LineaDAO();
	private static FermataDAO fermataDAO = new FermataDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static OrarioDAO orarioDAO = new OrarioDAO();
	private static CorsaDAO corsaDAO = new CorsaDAO();

	private GeneratoreFoglioExcel() {
	}

	public static void scaricaExcel() {
		Workbook workbook = new XSSFWorkbook();
		Sheet foglioLinee = workbook.createSheet("Linee");
		Sheet foglioFermate = workbook.createSheet("Fermate");
		Sheet foglioMezzi = workbook.createSheet("Mezzi");
		Sheet foglioOrari = workbook.createSheet("Orari");
		Sheet foglioCorse = workbook.createSheet("Corse");

		List<Linea> listaLinee = lineaDAO.trovaTutteLeLinee();
		TabellaLinee.genera(foglioLinee, workbook, listaLinee);

		List<Fermata> listaFermate = fermataDAO.trovaTutteLeFermate();
		TabellaFermate.genera(foglioFermate, workbook, listaFermate);

		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();
		TabellaMezzi.genera(foglioMezzi, workbook, listaMezzi);

		List<Orario> listaOrari = orarioDAO.trovaTuttiGliOrari();
		TabellaOrari.genera(foglioOrari, workbook, listaOrari);

		List<Corsa> listaCorse = corsaDAO.trovaTutteLeCorse();
		TabellaCorse.genera(foglioCorse, workbook, listaCorse);

		LocalDateTime dataOdierna = LocalDateTime.now();
		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

		String name = "Reportistica metro Genova  " + dataOdierna
				.format(formatter).replace('/', '.').replace(':', '.')
				+ ".xlsx";

		// cartella di salvataggio modificabile
		String resourcePath = "C:/sviluppo/java_workspaces/Jersey/metro-app/BEMetro/src/main/resources/file/"
				+ name;

		try {
			FileOutputStream outpuStream = new FileOutputStream(resourcePath);
			workbook.write(outpuStream);
			workbook.close();

		} catch (IOException e) {
			throw new CustomException(e.getMessage(), Response.Status.CONFLICT);
		}
	}
}
