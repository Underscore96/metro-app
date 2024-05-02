package simulatore;

import java.time.LocalDateTime;
import java.util.HashMap;
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

	@SuppressWarnings("unchecked")
	private static void aggPosizioneMezzi(Map<Integer, Fermata> mezziPerFermata,
			Integer numCicli) {
		Fermata fer;
		Set<Integer> setNumMezzi = mezziPerFermata.keySet();
		if (numCicli < 2) {
			Map<Integer, Fermata> mezziPerFermata1 = new HashMap<>();
			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			mezziPerFermata1 = (Map<Integer, Fermata>) mezziPerFermata1.put(1,
					fer1);
			aggiornaRelazioni(mezziPerFermata1, setNumMezzi);
		}
		if (numCicli >= 2 && numCicli < 3) {
			Map<Integer, Fermata> mezziPerFermata2 = new HashMap<>();
			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			mezziPerFermata2 = (Map<Integer, Fermata>) mezziPerFermata2.put(1,
					fer1);
			aggiornaRelazioni(mezziPerFermata2, setNumMezzi);
		}
		aggiornaRelazioni(mezziPerFermata, setNumMezzi);
	}

	private static void aggiornaRelazioni(Map<Integer, Fermata> mezziPerFermata,
			Set<Integer> setNumMezzi) {
		Fermata fer;
		for (Integer numMezzo : setNumMezzi) {
			fer = mezziPerFermata.get(numMezzo);

			if (fer.getNumFermata() == 8) {
				MezzoService.aggiornaRelazioneMezzo(8, numMezzo, "rimuovi");
				MezzoService.aggiornaRelazioneMezzo(1, numMezzo, "");
			} else if (fer.getNumFermata() == 16) {
				MezzoService.aggiornaRelazioneMezzo(16, numMezzo, "rimuovi");
				MezzoService.aggiornaRelazioneMezzo(9, numMezzo, "");
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