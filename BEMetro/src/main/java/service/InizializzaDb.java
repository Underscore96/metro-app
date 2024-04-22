package service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.dao.UtenteDAO;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Utente;
import presentation.pojo.PojoFermata;
import presentation.pojo.PojoMezzo;

public class InizializzaDb {
	private FermataDAO fermataDAO = new FermataDAO();
	private LineaDAO lineaDAO = new LineaDAO();
	private UtenteDAO utenteDAO = new UtenteDAO();
	private MezzoDAO mezzoDAO = new MezzoDAO();

	public Object[] inizDb() {
		Object[] dbData = new Object[4];

		InputStream fermataInputStream = getClass().getClassLoader()
				.getResourceAsStream("fermata.json");

		InputStream lineaInputStream = getClass().getClassLoader()
				.getResourceAsStream("linea.json");

		InputStream utenteInputStream = getClass().getClassLoader()
				.getResourceAsStream("utente.json");

		InputStream mezzoInputStream = getClass().getClassLoader()
				.getResourceAsStream("mezzo.json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Fermata> listaFermate = null;
		List<Linea> listaLinee = null;
		List<Utente> listaUtenti = null;
		List<Mezzo> listaMezzi = null;

		try {
			listaFermate = mapper.readValue(fermataInputStream,
					new TypeReference<List<Fermata>>() {
					});
			listaLinee = mapper.readValue(lineaInputStream,
					new TypeReference<List<Linea>>() {
					});

			listaUtenti = mapper.readValue(utenteInputStream,
					new TypeReference<List<Utente>>() {
					});

			listaMezzi = mapper.readValue(mezzoInputStream,
					new TypeReference<List<Mezzo>>() {
					});

			for (Fermata fermata : listaFermate) {
				if (fermataDAO.leggiDaNumFermata(fermata.getNumFermata())
						.isEmpty())
					fermataDAO.crea(fermata);
				else {
					FermataService.cancellaFermata(fermata.getNumFermata());
					fermataDAO.crea(fermata);
				}
			}

			for (Linea linea : listaLinee) {
				if (lineaDAO.leggiDaNomeLinea(linea.getNomeLinea()).isEmpty())
					lineaDAO.crea(linea);
				else {
					LineaService.cancellaLinea(linea.getNomeLinea());
					lineaDAO.crea(linea);
				}
			}

			for (Utente utente : listaUtenti) {
				if (utenteDAO.leggiDaNomeUtente(utente.getNomeUtente())
						.isEmpty())
					utenteDAO.crea(utente);
				else {
					UtenteService.cancellaUtente(utente.getNomeUtente());
					utenteDAO.crea(utente);
				}
			}

			for (Mezzo mezzo : listaMezzi) {
				if (mezzoDAO.leggiDaNumMezzo(mezzo.getNumMezzo()).isEmpty())
					mezzoDAO.crea(mezzo);
				else {
					MezzoService.cancellaMezzo(mezzo.getNumMezzo());
					mezzoDAO.crea(mezzo);
				}
			}

			dbData[0] = relazioniFermateLinee(listaFermate, listaLinee);
			dbData[1] = listaLinee;
			dbData[2] = listaUtenti;
			dbData[3] = relazioniMezzoFermata(listaMezzi);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dbData;
	}

	private List<PojoFermata> relazioniFermateLinee(List<Fermata> listaFermate,
			List<Linea> listalinee) {
		List<PojoFermata> listaPojoFermate = new ArrayList<>();
		String[] listaNomiFermate = {"Brignole", "De Ferrari", "Sarzano",
				"San Giorgio", "Darsena", "Piazza Principe", "Dinegro", "brin"};
		Integer numFermata;

		if (listaFermate != null && listalinee != null) {
			for (Fermata fermata : listaFermate) {
				numFermata = fermata.getNumFermata();
				if (numFermata <= listaNomiFermate.length)
					listaPojoFermate.add(FermataService
							.aggiornaRelazioneFermata(numFermata, "blu"));
				else
					listaPojoFermate.add(FermataService
							.aggiornaRelazioneFermata(numFermata, "verde"));
			}
		}

		return listaPojoFermate;
	}

	private List<PojoMezzo> relazioniMezzoFermata(List<Mezzo> listaMezzi) {
		List<PojoMezzo> listaPojoMezzi = new ArrayList<>();
		Integer numMezzo;

		if (listaMezzi != null) {
			for (Mezzo mezzo : listaMezzi) {
				numMezzo = mezzo.getNumMezzo();
				if (numMezzo == 1)
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(1,
							numMezzo, "aggiungi"));
				else
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(9,
							numMezzo, "aggiungi"));
			}
		}

		return listaPojoMezzi;
	}
}
