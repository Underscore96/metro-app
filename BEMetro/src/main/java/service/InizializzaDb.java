package service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.entity.Fermata;
import db.entity.Linea;
import presentation.pojo.PojoFermata;

public class InizializzaDb {
	private FermataDAO fermataDAO = new FermataDAO();
	private LineaDAO lineaDAO = new LineaDAO();

	public Object[] inizDb() {
		Object[] dbData = new Object[2];

		InputStream fermataInputStream = getClass().getClassLoader()
				.getResourceAsStream("fermata.json");

		InputStream lineaInputStream = getClass().getClassLoader()
				.getResourceAsStream("linea.json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Fermata> listaFermate = null;
		List<Linea> listaLinee = null;

		try {
			listaFermate = mapper.readValue(fermataInputStream,
					new TypeReference<List<Fermata>>() {
					});
			listaLinee = mapper.readValue(lineaInputStream,
					new TypeReference<List<Linea>>() {
					});

			for (Fermata fermata : listaFermate) {
				if (fermataDAO.leggiDaNumFermata(fermata.getNumFermata())
						.isEmpty())
					fermataDAO.crea(fermata);
			}

			for (Linea linea : listaLinee) {
				if (lineaDAO.leggiDaNomeLinea(linea.getNomeLinea()).isEmpty())
					lineaDAO.crea(linea);
			}

			dbData[0] = costruisciRelazione(listaFermate, listaLinee);
			dbData[1] = listaLinee;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dbData;
	}

	private List<PojoFermata> costruisciRelazione(List<Fermata> listaFermate,
			List<Linea> listalinee) {
		List<PojoFermata> listaPojoFermate = new ArrayList<>();

		Integer numFermata;
		String[] nomiFermate = {"Brignole", "Corvetto", "De Ferrari", "Sarzano",
				"San Giorgio", "Darsena", "Piazza Principe", "brin"};
		List<String> listaNomiFermate = Arrays.asList(nomiFermate);

		if (listaFermate != null && listalinee != null) {
			for (Fermata fermata : listaFermate) {
				numFermata = fermata.getNumFermata();
				if (numFermata <= listaNomiFermate.size())
					listaPojoFermate.add(FermataService
							.aggiornaRelazioneFermata(numFermata, "verde"));
				else
					listaPojoFermate.add(FermataService
							.aggiornaRelazioneFermata(numFermata, "blu"));
			}
		}

		return listaPojoFermate;
	}
}
