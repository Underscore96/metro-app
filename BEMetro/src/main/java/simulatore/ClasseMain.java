package simulatore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import db.dao.FermataDAO;
import db.dao.MezzoDAO;
import db.dao.OrarioDAO;
import db.dao.SimulazioneDAO;
import db.entity.Fermata;
import db.entity.Mezzo;
import db.entity.Orario;
import db.entity.Simulazione;
import presentation.pojo.PojoFermataFE;
import presentation.pojo.PojoOrarioFE;
import service.FermataFEService;
import service.MezzoService;

public class ClasseMain {
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static SimulazioneDAO simulazioneDAO = new SimulazioneDAO();
	private static FermataDAO fermataDAO = new FermataDAO();
	private static Random random = new Random();
	private static final String ASS = "assente";
	private static final String PRES = "presente";

	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors
				.newScheduledThreadPool(1);

		Runnable task = new Runnable() {
			@Override
			public void run() {
				updateData("1");
			}
		};

		scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
	}

	public static void updateData(String idSimulazione) {
		Simulazione sim = null;
		Integer numCicli = 1;
		List<Simulazione> listSim = simulazioneDAO
				.leggiDaIdSimulazione(idSimulazione);

		if (listSim != null && !listSim.isEmpty()) {
			sim = listSim.get(0);
			if (sim.getNumCicli() != null)
				numCicli = sim.getNumCicli();
			else
				sim.setNumCicli(1);
			simulazioneDAO.aggiornaSimulazione(sim);
			numCicli = sim.getNumCicli();
		}

		Map<Integer, Fermata> mezziPerFermata = new HashMap<>();
		List<PojoFermataFE> elencoFermate = FermataFEService
				.leggiTutteLeFermateFE();

		for (PojoFermataFE fermataFE : elencoFermate) {

			aggiornaOrariFermata(fermataFE);
			fermataFE.setPrevisioneMeteo(previsioneMeteo());

			registraPosizioneMezzi(mezziPerFermata, fermataFE);
			FermataFEService.aggiornaFermataFE(fermataFE);
		}
		aggPosizioneMezzi(mezziPerFermata, numCicli);
		if (sim != null) {
			sim.setNumCicli(numCicli + 1);
			simulazioneDAO.aggiornaSimulazione(sim);
		}
	}

	private static void aggPosizioneMezzi(Map<Integer, Fermata> mezziPerFermata,
			Integer numCicli) {
		Set<Integer> setNumMezzi = mezziPerFermata.keySet();

		if (numCicli == 1) {
			Map<Integer, Fermata> mezziPerFermata1 = new HashMap<>();
			Set<Integer> setNumMezzi1 = new HashSet<>();

			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			Fermata fer9 = fermataDAO.leggiDaNumFermata(9).get(0);

			mezziPerFermata1.put(1, fer1);
			mezziPerFermata1.put(4, fer9);

			setNumMezzi1.add(1);
			setNumMezzi1.add(4);

			aggiornaRelazioni(mezziPerFermata1, setNumMezzi1);
		}
		if (numCicli == 2) {
			Map<Integer, Fermata> mezziPerFermata2 = new HashMap<>();
			Set<Integer> setNumMezzi2 = new HashSet<>();

			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			Fermata fer2 = fermataDAO.leggiDaNumFermata(2).get(0);
			Fermata fer9 = fermataDAO.leggiDaNumFermata(9).get(0);
			Fermata fer10 = fermataDAO.leggiDaNumFermata(10).get(0);

			mezziPerFermata2.put(2, fer1);
			mezziPerFermata2.put(1, fer2);
			mezziPerFermata2.put(5, fer9);
			mezziPerFermata2.put(4, fer10);

			setNumMezzi2.add(2);
			setNumMezzi2.add(1);
			setNumMezzi2.add(5);
			setNumMezzi2.add(4);

			aggiornaRelazioni(mezziPerFermata2, setNumMezzi2);
		}
		if (numCicli == 3) {
			Map<Integer, Fermata> mezziPerFermata3 = new HashMap<>();
			Set<Integer> setNumMezzi3 = new HashSet<>();

			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			Fermata fer2 = fermataDAO.leggiDaNumFermata(2).get(0);
			Fermata fer3 = fermataDAO.leggiDaNumFermata(3).get(0);
			Fermata fer9 = fermataDAO.leggiDaNumFermata(9).get(0);
			Fermata fer10 = fermataDAO.leggiDaNumFermata(10).get(0);
			Fermata fer11 = fermataDAO.leggiDaNumFermata(11).get(0);

			mezziPerFermata3.put(3, fer1);
			mezziPerFermata3.put(2, fer2);
			mezziPerFermata3.put(1, fer3);
			mezziPerFermata3.put(6, fer9);
			mezziPerFermata3.put(5, fer10);
			mezziPerFermata3.put(4, fer11);

			setNumMezzi3.add(3);
			setNumMezzi3.add(2);
			setNumMezzi3.add(1);
			setNumMezzi3.add(6);
			setNumMezzi3.add(5);
			setNumMezzi3.add(4);

			aggiornaRelazioni(mezziPerFermata3, setNumMezzi3);
		} else {
			aggiornaRelazioni(mezziPerFermata, setNumMezzi);
		}
	}

	private static void aggiornaRelazioni(Map<Integer, Fermata> mezziPerFermata,
			Set<Integer> setNumMezzi) {
		Fermata fer;
		for (Integer numMezzo : setNumMezzi) {
			fer = mezziPerFermata.get(numMezzo);

			if (fer.getNumFermata() == 16) {
				MezzoService.aggiornaRelazioneMezzo(16, numMezzo, "rimuovi");
				MezzoService.aggiornaRelazioneMezzo(1, numMezzo, "");
			} else {
				MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata(),
						numMezzo, "rimuovi");
				MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata() + 1,
						numMezzo, "");
			}
		}
	}

	private static void registraPosizioneMezzi(
			Map<Integer, Fermata> mezziPerFermata, PojoFermataFE fermataFE) {
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();
		for (Mezzo m : listaMezzi) {
			mezziPerFermata.put(m.getNumMezzo(), m.getFermataAttuale());
		}

		if (fermataFE.getPosizioneMezzo().equals(PRES)) {
			fermataFE.setPosizioneMezzo(ASS);
		}
	}

	private static void aggiornaOrariFermata(PojoFermataFE fermata) {
		OrarioDAO orarioDAO = new OrarioDAO();

		List<PojoOrarioFE> elencoOrariFermata = fermata.getOrariMezzi();

		for (Integer i = 0; i < elencoOrariFermata.size(); i++) {

			PojoOrarioFE orarioMezzo = elencoOrariFermata.get(i);

			List<Orario> listaOrariDaAggiornare = mezzoDAO
					.leggiDaNumMezzo(orarioMezzo.getNumMezzo()).get(0)
					.getOrari();

			Integer ritardoRandom = 0;
			Integer randomValue = random.nextInt(11);
			if (randomValue > 7) {
				ritardoRandom = randomValue <= 9 ? 2 : -1;
			}
			LocalDateTime ritardoAggiornato = LocalDateTime
					.parse(orarioMezzo.getRitardo()).plusMinutes(ritardoRandom);

			for (Orario orario : listaOrariDaAggiornare) {
				if (Objects.equals(orario.getNumFermata(),
						fermata.getNumFermata())) {
					orario.setRitardo(ritardoAggiornato);
					orarioDAO.aggiorna(orario);
				}
			}

			orarioMezzo.setOrarioPrevisto(ritardoAggiornato.toString());
			elencoOrariFermata.set(i, orarioMezzo);
		}
		fermata.setOrariMezzi(elencoOrariFermata);

	}

	private static String previsioneMeteo() {
		Integer meteo = random.nextInt(3);

		switch (meteo) {
			case 1 :
				return "pioggia";
			case 2 :
				return "sole";
			case 3 :
				return "nuvolo";
			default :
				return "sole";
		}
	}
}