package service;

import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.dao.OrarioDAO;
import db.dao.UtenteDAO;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Orario;
import db.entity.Utente;
import presentation.pojo.PojoFermata;
import presentation.pojo.PojoLinea;
import presentation.pojo.PojoMezzo;
import presentation.pojo.PojoUtente;
import service.builder.PojoLineaBuilder;
import service.builder.PojoUtenteBuilder;

public class InizializzaDb {
	private FermataDAO fermataDAO = new FermataDAO();
	private LineaDAO lineaDAO = new LineaDAO();
	private UtenteDAO utenteDAO = new UtenteDAO();
	private MezzoDAO mezzoDAO = new MezzoDAO();
	private OrarioDAO orarioDAO = new OrarioDAO();
	private static final String[] NOMI_FERMATE = {"Brignole", "De Ferrari",
			"Sarzano", "San Giorgio", "Darsena", "Principe", "Dinegro", "Brin"};

	public Object[] inizDb() {
		Object[] dbData = new Object[5];

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
		List<PojoLinea> listaPojoLinee = new ArrayList<>();
		List<Utente> listaUtenti = null;
		List<PojoUtente> listaPojoUtenti = new ArrayList<>();
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

			for (Linea linea : listaLinee) {
				listaPojoLinee.add(new PojoLineaBuilder()
						.setNomeLinea(linea.getNomeLinea())
						.setDirezione(linea.getDirezione())
						.setFermate(linea.getFermate()).costruisci());

			}
			dbData[1] = listaPojoLinee;

			for (Utente utente : listaUtenti) {
				listaPojoUtenti.add(new PojoUtenteBuilder()
						.setNomeUtente(utente.getNomeUtente())
						.setPassword(utente.getPassword())
						.setNome(utente.getNome())
						.setCognome(utente.getCognome())
						.setTelefono(utente.getTelefono())
						.setMail(utente.getMail()).setRuolo(utente.getRuolo())
						.costruisci());
			}
			dbData[2] = listaPojoUtenti;

			dbData[3] = relazioniMezzoFermata(listaMezzi);
			dbData[4] = aggiornaOrari(listaMezzi);

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
							.aggiornaRelazioneFermata(numFermata, "verde"));
				else
					listaPojoFermate.add(FermataService
							.aggiornaRelazioneFermata(numFermata, "blu"));
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
				if (numMezzo <= 3)
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(1,
							numMezzo, "aggiungi"));
				else
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(9,
							numMezzo, "aggiungi"));
			}
		}

		return listaPojoMezzi;
	}

	private List<Orario> aggiornaOrari(List<Mezzo> listaMezzi) {
		List<Orario> listaOrari = new ArrayList<>();
		Integer minuti = 0;

		for (Mezzo mezzo : listaMezzi) {
			LocalTime orarioPrevisto = LocalTime.of(8, minuti);
			minuti = minuti + 10;
			if (mezzo.getNumMezzo() == 3)
				minuti = 0;

			for (String nomeFermata : NOMI_FERMATE) {
				Orario orario = new Orario();

				orario.setMezzo(mezzo);
				orario.setNomeFermata(nomeFermata);
				orario.setOrarioPrevisto(orarioPrevisto);
				orario.setRitardo(LocalTime.of(0, 0));

				orarioDAO.crea(orario);
				listaOrari.add(orario);

				if (!nomeFermata.equals(NOMI_FERMATE[NOMI_FERMATE.length - 1]))
					orarioPrevisto = orarioPrevisto.plusMinutes(5);
				else
					minuti = 0;
			}
		}

		return listaOrari;
	}
}
