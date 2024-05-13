package service.inizializzadb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import db.dao.FermataDAO;
import db.dao.MezzoDAO;
import db.dao.OrarioDAO;
import db.entity.Fermata;
import db.entity.Mezzo;
import db.entity.Orario;

public class InizializzaOrariDBSkyTram {
	private static FermataDAO fermataDAO = new FermataDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static OrarioDAO orarioDAO = new OrarioDAO();
	private static final String[] NOMI_FERMATE_DIR_BRIGNOLE_LEV = {"Brin",
			"Dinegro", "Principe", "Darsena", "San Giorgio", "Sarzano",
			"De Ferrari", "Brignole"};
	private static final String[] NOMI_FERMATE_DIR_MOLASSANA_LEV = {"Brignole",
			"Marassi", "Parenzo", "Staglieno", "Adriatico", "Bligny",
			"San Gottardo", "Molassana"};
	private static final String[] NOMI_FERMATE_DIR_BRIGNOLE_PON = {"Molassana",
			"San Gottardo", "Bligny", "Adriatico", "Staglieno", "Parenzo",
			"Marassi", "Brignole"};
	private static final String[] NOMI_FERMATE_DIR_BRIN_PON = {"Brignole",
			"De Ferrari", "Sarzano", "San Giorgio", "Darsena", "Principe",
			"Dinegro", "Brin"};
	private static final String LEV = "Levante";
	private static final String PON = "Ponente";

	private InizializzaOrariDBSkyTram() {
	}

	public static List<Orario> generaOrari() {
		Integer ore = 8;
		Integer minuti = 0;
		Integer contatoreNumOrario = 1;
		List<Orario> listaOrari = new ArrayList<>();
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();

		LocalDate data = LocalDate.now();
		LocalTime ora;
		LocalDateTime dataOra;

		for (Mezzo mezzo : listaMezzi) {

			String destinazioneFermata;
			destinazioneFermata = assegnaDestinazione(mezzo);

			if (minuti > 59) {
				minuti = minuti - 19;
				ore++;
			}
			ora = LocalTime.of(ore, minuti);
			dataOra = LocalDateTime.of(data, ora);

			if (mezzo.getNumMezzo() == 1 || mezzo.getNumMezzo() == 4
					|| mezzo.getNumMezzo() == 7 || mezzo.getNumMezzo() == 10)
				minuti = minuti + 15;
			else
				minuti = minuti + 10;

			if (mezzo.getNumMezzo() == 3 || mezzo.getNumMezzo() == 6
					|| mezzo.getNumMezzo() == 9) {
				minuti = 0;
				ore = 8;
			}

			contatoreNumOrario = creaOrario(contatoreNumOrario, listaOrari,
					data, ora, dataOra, mezzo, destinazioneFermata);
		}
		return listaOrari;
	}

	private static String assegnaDestinazione(Mezzo mezzo) {
		String destinazioneFermata;
		if (mezzo.getFermataAttuale().getNumFermata() == 1)
			destinazioneFermata = LEV;
		else if (mezzo.getFermataAttuale().getNumFermata() == 8) {
			if (mezzo.getNumMezzo() > 3 && mezzo.getNumMezzo() <= 6) {
				destinazioneFermata = LEV;
			} else {
				destinazioneFermata = PON;
			}
		} else {
			destinazioneFermata = PON;
		}
		return destinazioneFermata;
	}

	private static Integer creaOrario(Integer contatoreNumOrario,
			List<Orario> listaOrari, LocalDate data, LocalTime ora,
			LocalDateTime dataOra, Mezzo mezzo, String destinazioneFermata) {
		String[] arrNomi;
		
		if (destinazioneFermata.equals(LEV)) {
			if (mezzo.getDestinazione().equals("Brignole")) {
				arrNomi = NOMI_FERMATE_DIR_BRIGNOLE_LEV;
			} else {
				arrNomi = NOMI_FERMATE_DIR_MOLASSANA_LEV;
			}
		} else {
			if (mezzo.getDestinazione().equals("Brignole")) {
				arrNomi = NOMI_FERMATE_DIR_BRIGNOLE_PON;
			} else {
				arrNomi = NOMI_FERMATE_DIR_BRIN_PON;
			}
		}
		
		for (String nomeFermata : arrNomi) {
			Integer numFermata = trovaNumFermata(destinazioneFermata,
					nomeFermata);
			Orario orario = new Orario();

			orario.setNumOrario(contatoreNumOrario);
			orario.setNumFermata(numFermata);
			orario.setOrarioPrevisto(dataOra);
			orario.setRitardo(dataOra);
			orario.setMezzo(mezzo);

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

			if (!nomeFermata.equals(arrNomi[arrNomi.length - 1])) {
				ora = ora.plusMinutes(10);
				dataOra = LocalDateTime.of(data, ora);
			}

			contatoreNumOrario++;
		}
		return contatoreNumOrario;
	}

	private static Integer trovaNumFermata(String direzioneMezzo,
			String nomeFermata) {
		Integer numFermata = null;
		List<Fermata> fermateTrovate = fermataDAO
				.trovaConAttributi(nomeFermata);

		for (Fermata fermata : fermateTrovate) {
			if (direzioneMezzo.equals(fermata.getDirezione()))
				numFermata = fermata.getNumFermata();
		}

		return numFermata;
	}
}
