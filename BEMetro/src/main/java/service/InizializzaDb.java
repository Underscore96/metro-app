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
import db.dao.UtenteDAO;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Utente;
import presentation.pojo.PojoFermata;

public class InizializzaDb {
	private FermataDAO fermataDAO = new FermataDAO();
	private LineaDAO lineaDAO = new LineaDAO();
	private UtenteDAO utenteDAO = new UtenteDAO();

	public Object[] inizDb() {
		Object[] dbData = new Object[3];

		InputStream fermataInputStream = getClass().getClassLoader()
				.getResourceAsStream("fermata.json");

		InputStream lineaInputStream = getClass().getClassLoader()
				.getResourceAsStream("linea.json");

		InputStream utenteInputStream = getClass().getClassLoader()
				.getResourceAsStream("utente.json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Fermata> listaFermate = null;
		List<Linea> listaLinee = null;
		List<Utente> listaUtente = null;

		try {
			listaFermate = mapper.readValue(fermataInputStream,
					new TypeReference<List<Fermata>>() {
					});
			listaLinee = mapper.readValue(lineaInputStream,
					new TypeReference<List<Linea>>() {
					});

			listaUtente = mapper.readValue(utenteInputStream,
					new TypeReference<List<Utente>>() {
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

			for (Utente utente : listaUtente) {
				if (utenteDAO.leggiDaNomeUtente(utente.getNomeUtente())
						.isEmpty())
					utenteDAO.crea(utente);
			}

			dbData[0] = costruisciRelazione(listaFermate, listaLinee);
			dbData[1] = listaLinee;
			dbData[2] = listaUtente;

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
