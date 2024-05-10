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

public class InizializzaOrariDB {
	private static FermataDAO fermataDAO = new FermataDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static OrarioDAO orarioDAO = new OrarioDAO();
	private static final String[] NOMI_FERMATE = {"Brignole", "De Ferrari",
			"Sarzano", "San Giorgio", "Darsena", "Principe", "Dinegro", "Brin"};
	private static final String[] NOMI_FERMATE_REVERSE = {"Brin", "Dinegro",
			"Principe", "Darsena", "San Giorgio", "Sarzano", "De Ferrari",
			"Brignole"};

	private InizializzaOrariDB() {
	}

	public static List<Orario> generaOrari() {
		Integer minuti = 0;
		Integer contatoreNumOrario = 1;
		List<Orario> listaOrari = new ArrayList<>();
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();

		LocalDate data = LocalDate.now();
		LocalTime ora;
		LocalDateTime dataOra;

		for (Mezzo mezzo : listaMezzi) {

			String destinazioneFermata;
			if (mezzo.getFermataAttuale().getNumFermata() == 1)
				destinazioneFermata = "Ponente";
			else
				destinazioneFermata = "Levante";

			ora = LocalTime.of(8, minuti);
			dataOra = LocalDateTime.of(data, ora);

			if (mezzo.getNumMezzo() == 1 || mezzo.getNumMezzo() == 4)
				minuti = minuti + 15;
			else
				minuti = minuti + 10;

			if (mezzo.getNumMezzo() == 3)
				minuti = 0;

			contatoreNumOrario = creaOrario(contatoreNumOrario, listaOrari,
					data, ora, dataOra, mezzo, destinazioneFermata);
		}
		return listaOrari;
	}

	private static Integer creaOrario(Integer contatoreNumOrario,
			List<Orario> listaOrari, LocalDate data, LocalTime ora,
			LocalDateTime dataOra, Mezzo mezzo, String destinazioneFermata) {
		String[] arrNomi;
		if (destinazioneFermata.equals("Levante"))
			arrNomi = NOMI_FERMATE_REVERSE;
		else
			arrNomi = NOMI_FERMATE;

		for (String nomeFermata : arrNomi) {
			Integer numFermata = trovaNumFermata(destinazioneFermata, nomeFermata);
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
