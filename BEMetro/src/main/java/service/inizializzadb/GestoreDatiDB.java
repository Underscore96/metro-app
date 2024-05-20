package service.inizializzadb;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import db.dao.CorsaDAO;
import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.dao.UtenteDAO;
import db.entity.Corsa;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Utente;
import presentation.pojo.PojoFermata;
import presentation.pojo.PojoLinea;
import presentation.pojo.PojoMezzo;
import presentation.pojo.PojoUtente;
import service.CorsaService;
import service.FermataService;
import service.LineaService;
import service.MezzoService;
import service.UtenteService;
import service.builder.PojoLineaBuilder;
import service.builder.PojoUtenteBuilder;

public class GestoreDatiDB {
	private FermataDAO fermataDAO = new FermataDAO();
	private LineaDAO lineaDAO = new LineaDAO();
	private UtenteDAO utenteDAO = new UtenteDAO();
	private MezzoDAO mezzoDAO = new MezzoDAO();
	private CorsaDAO corsaDAO = new CorsaDAO();
	private static final String AGG = "aggiungi";

	public Object[] inizDb() {
		Object[] dbData = new Object[6];
		List<PojoLinea> listaPojoLinee = new ArrayList<>();
		List<PojoUtente> listaPojoUtenti = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Fermata> listaFermate = estraiFermateJSON(mapper);
		List<Linea> listaLinee = estraiLineeJSON(mapper);
		List<Utente> listaUtenti = estraiUtentiJSON(mapper);
		List<Mezzo> listaMezzi = estraiMezziJSON(mapper);
		List<Corsa> listaCorse = estraiCorseJSON(mapper);

		try {
			aggiornaDBFermate(listaFermate);
			aggiornaDBLinee(listaLinee);
			aggiornaDBUtenti(listaUtenti);
			aggiornaDBMezzi(listaMezzi);
			aggiornaDBCorse(listaCorse);

			dbData[0] = relazioniFermateLinee(listaFermate);

			for (Linea linea : listaLinee) {
				listaPojoLinee.add(new PojoLineaBuilder()
						.setNomeLinea(linea.getNomeLinea())
						.setDestinazione(linea.getDestinazione())
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
			dbData[4] = InizializzaOrariDB.generaOrari();
			dbData[5] = GestoreCorse.aggiornaCorse();
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

	private List<Corsa> estraiCorseJSON(ObjectMapper mapper) {
		List<Corsa> listaCorse = null;

		try {
			InputStream corsaInputStream = getClass().getClassLoader()
					.getResourceAsStream("corsa.json");

			listaCorse = mapper.readValue(corsaInputStream,
					new TypeReference<List<Corsa>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCorse;
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

	private void aggiornaDBCorse(List<Corsa> listaCorse) {
		for (Corsa corsa : listaCorse) {
			if (corsaDAO.leggiDaNumCorsa(corsa.getNumCorsa()).isEmpty())
				corsaDAO.crea(corsa);
			else {
				CorsaService.cancellaCorsa(corsa.getNumCorsa());
				corsaDAO.crea(corsa);
			}
		}
	}

	private List<PojoFermata> relazioniFermateLinee(
			List<Fermata> listaFermate) {
		List<PojoFermata> listaPojoFermate = new ArrayList<>();
		List<String> listaNomiLinee = new ArrayList<>();
		Integer numFermata;

		if (listaFermate != null) {
			for (Fermata fermata : listaFermate) {
				numFermata = fermata.getNumFermata();

				if (numFermata >= 1 && numFermata <= 7) {
					listaNomiLinee.add("verde");
					listaPojoFermate.add(
							FermataService.aggiornaRelazioneFermata(numFermata,
									listaNomiLinee));
					listaNomiLinee.clear();

				} else if (numFermata == 8) {
					listaNomiLinee.add("verde");
					listaNomiLinee.add("gialla");
					listaPojoFermate.add(
							FermataService.aggiornaRelazioneFermata(numFermata,
									listaNomiLinee));
					listaNomiLinee.clear();

				} else if (numFermata > 8 && numFermata <= 15) {
					listaNomiLinee.add("gialla");
					listaPojoFermate.add(
							FermataService.aggiornaRelazioneFermata(numFermata,
									listaNomiLinee));
					listaNomiLinee.clear();

				} else if (numFermata >= 16 && numFermata < 23) {
					listaNomiLinee.add("rossa");
					listaPojoFermate.add(
							FermataService.aggiornaRelazioneFermata(numFermata,
									listaNomiLinee));
					listaNomiLinee.clear();

				} else if (numFermata == 23) {
					listaNomiLinee.add("blu");
					listaNomiLinee.add("rossa");
					listaPojoFermate.add(
							FermataService.aggiornaRelazioneFermata(numFermata,
									listaNomiLinee));
					listaNomiLinee.clear();

				} else {
					listaNomiLinee.add("blu");
					listaPojoFermate.add(
							FermataService.aggiornaRelazioneFermata(numFermata,
									listaNomiLinee));
					listaNomiLinee.clear();
				}
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
				if (numMezzo >= 1 && numMezzo <= 3)
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(1,
							numMezzo, AGG));
				else if (numMezzo >= 4 && numMezzo <= 6)
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(8,
							numMezzo, AGG));
				else if (numMezzo >= 7 && numMezzo <= 9)
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(16,
							numMezzo, AGG));
				else if (numMezzo >= 10 && numMezzo <= 12)
					listaPojoMezzi.add(MezzoService.aggiornaRelazioneMezzo(23,
							numMezzo, AGG));
			}
		}

		return listaPojoMezzi;
	}
}
