package service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
		List<PojoLinea> listaPojoLinee = new ArrayList<>();
		List<PojoUtente> listaPojoUtenti = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Fermata> listaFermate = estraiFermateJSON(mapper);
		List<Linea> listaLinee = estraiLineeJSON(mapper);
		List<Utente> listaUtenti = estraiUtentiJSON(mapper);
		List<Mezzo> listaMezzi = estraiMezziJSON(mapper);

		try {
			aggiornaDBFermate(listaFermate);
			aggiornaDBLinee(listaLinee);
			aggiornaDBUtenti(listaUtenti);
			aggiornaDBMezzi(listaMezzi);

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
			dbData[4] = generaOrari();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dbData;
	}

	private List<Fermata> estraiFermateJSON(ObjectMapper mapper) {
		List<Fermata> listaFermate = null;

		try {
			InputStream fermataInputStream = getClass().getClassLoader()
					.getResourceAsStream("fermata.json");

			listaFermate = mapper.readValue(fermataInputStream,
					new TypeReference<List<Fermata>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaFermate;
	}

	private List<Linea> estraiLineeJSON(ObjectMapper mapper) {
		List<Linea> listaLinee = null;

		try {
			InputStream lineaInputStream = getClass().getClassLoader()
					.getResourceAsStream("linea.json");

			listaLinee = mapper.readValue(lineaInputStream,
					new TypeReference<List<Linea>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaLinee;
	}

	private List<Utente> estraiUtentiJSON(ObjectMapper mapper) {
		List<Utente> listaUtenti = null;

		try {
			InputStream utenteInputStream = getClass().getClassLoader()
					.getResourceAsStream("utente.json");

			listaUtenti = mapper.readValue(utenteInputStream,
					new TypeReference<List<Utente>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaUtenti;
	}

	private List<Mezzo> estraiMezziJSON(ObjectMapper mapper) {
		List<Mezzo> listaMezzi = null;

		try {
			InputStream mezzoInputStream = getClass().getClassLoader()
					.getResourceAsStream("mezzo.json");

			listaMezzi = mapper.readValue(mezzoInputStream,
					new TypeReference<List<Mezzo>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaMezzi;
	}

	private void aggiornaDBFermate(List<Fermata> listaFermate) {

		for (Fermata fermata : listaFermate) {

			LocalDate dataAttuale = LocalDate.now();
			LocalTime orarioIniziale = LocalTime.of(8, 0);
			LocalDateTime dataTime = LocalDateTime.of(dataAttuale,
					orarioIniziale);

			if (fermataDAO.leggiDaNumFermata(fermata.getNumFermata())
					.isEmpty()) {
				fermata.setOrarioAttuale(dataTime.toString());
				fermataDAO.crea(fermata);
			} else {
				FermataService.cancellaFermata(fermata.getNumFermata());
				fermata.setOrarioAttuale(dataTime.toString());
				fermataDAO.crea(fermata);
			}
		}
	}

	private void aggiornaDBLinee(List<Linea> listaLinee) {
		for (Linea linea : listaLinee) {
			if (lineaDAO.leggiDaNomeLinea(linea.getNomeLinea()).isEmpty())
				lineaDAO.crea(linea);
			else {
				LineaService.cancellaLinea(linea.getNomeLinea());
				lineaDAO.crea(linea);
			}
		}
	}

	private void aggiornaDBUtenti(List<Utente> listaUtenti) {
		for (Utente utente : listaUtenti) {
			if (utenteDAO.leggiDaNomeUtente(utente.getNomeUtente()).isEmpty())
				utenteDAO.crea(utente);
			else {
				UtenteService.cancellaUtente(utente.getNomeUtente());
				utenteDAO.crea(utente);
			}
		}
	}

	private void aggiornaDBMezzi(List<Mezzo> listaMezzi) {
		for (Mezzo mezzo : listaMezzi) {
			if (mezzoDAO.leggiDaNumMezzo(mezzo.getNumMezzo()).isEmpty())
				mezzoDAO.crea(mezzo);
			else {
				MezzoService.cancellaMezzo(mezzo.getNumMezzo());
				mezzoDAO.crea(mezzo);
			}
		}
	}

	private List<PojoFermata> relazioniFermateLinee(List<Fermata> listaFermate,
			List<Linea> listalinee) {
		List<PojoFermata> listaPojoFermate = new ArrayList<>();
		String[] listaNomiFermate = {"Brignole", "De Ferrari", "Sarzano",
				"San Giorgio", "Darsena", "Principe", "Dinegro", "Brin"};
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

	private List<Orario> generaOrari() {
		Integer minuti = 0;
		Integer contatoreNumOrario = 1;
		List<Orario> listaOrari = new ArrayList<>();
		List<Integer> minList = new ArrayList<>();
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();

		LocalDate data = LocalDate.now();
		LocalTime ora;
		LocalDateTime dataOra;

		for (Mezzo mezzo : listaMezzi) {

			String direzioneMezzo = mezzo.getFermataAttuale().getLinea()
					.getDirezione();
			ora = LocalTime.of(8, minuti);
			dataOra = LocalDateTime.of(data, ora);

			minuti = minuti + 5;
			minList.add(minuti);
			minList.add(mezzo.getNumMezzo());

			if (mezzo.getNumMezzo() == 3)
				minuti = 0;

			contatoreNumOrario = creaOrario(contatoreNumOrario, listaOrari,
					data, ora, dataOra, mezzo, direzioneMezzo);
		}
		return listaOrari;
	}

	private Integer trovaNumFermata(String direzioneMezzo, String nomeFermata) {
		Integer numFermata = null;
		List<Fermata> fermateTrovate = fermataDAO
				.trovaConAttributi(nomeFermata);

		for (Fermata fermata : fermateTrovate) {
			if (direzioneMezzo.equals(fermata.getLinea().getDirezione()))
				numFermata = fermata.getNumFermata();
		}

		return numFermata;
	}

	private Integer creaOrario(Integer contatoreNumOrario,
			List<Orario> listaOrari, LocalDate data, LocalTime ora,
			LocalDateTime dataOra, Mezzo mezzo, String direzioneMezzo) {
		for (String nomeFermata : NOMI_FERMATE) {
			Integer numFermata = trovaNumFermata(direzioneMezzo, nomeFermata);
			Orario orario = new Orario();

			orario.setNumOrario(contatoreNumOrario);
			orario.setMezzo(mezzo);
			orario.setNumFermata(numFermata);
			orario.setOrarioPrevisto(dataOra);
			orario.setRitardo(dataOra);

			List<Orario> orarioDaAggiornare = orarioDAO
					.leggiDaNumOrario(contatoreNumOrario);

			if (orarioDaAggiornare == null || orarioDaAggiornare.isEmpty()) {
				orarioDAO.crea(orario);
			} else {
				Orario o = orarioDaAggiornare.get(0);
				orarioDAO.cancella(o);
				orarioDAO.crea(orario);
			}

			listaOrari.add(orario);

			if (!nomeFermata.equals(NOMI_FERMATE[NOMI_FERMATE.length - 1])) {
				ora = ora.plusMinutes(5);
				dataOra = LocalDateTime.of(data, ora);
			}

			contatoreNumOrario++;
		}
		return contatoreNumOrario;
	}
}
