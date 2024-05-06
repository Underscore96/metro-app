package simulatore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

public class ClasseMain {
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static SimulazioneDAO simulazioneDAO = new SimulazioneDAO();
	private static Random random = new Random();
	private static final String ASS = "assente";
	private static final String PRES = "presente";

	private ClasseMain() {
	}

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
		Simulazione sim = new Simulazione();
		Integer numCicli = 1;
		List<Simulazione> listSim = simulazioneDAO
				.leggiDaIdSimulazione(idSimulazione);

		if (listSim != null && !listSim.isEmpty()) {
			sim = listSim.get(0);

			if (sim.getNumCicli() != null) {
				numCicli = sim.getNumCicli();

			}
		} else {
			sim.setNumCicli(1);
			sim.setStatoEsecuzione(false);
			simulazioneDAO.aggiornaSimulazione(sim);
			numCicli = sim.getNumCicli();
		}

		Map<Integer, Fermata> mezziPerFermata = new HashMap<>();
		List<PojoFermataFE> elencoFermate = FermataFEService
				.leggiTutteLeFermateFE();

		for (PojoFermataFE fermataFE : elencoFermate) {

			calcolaRitardo(fermataFE);
			previsioneMeteo(fermataFE);
			registraPosizioneMezzi(mezziPerFermata, fermataFE);
			aggiornaOraAttuale(fermataFE);

			FermataFEService.aggiornaFermataFE(fermataFE);
		}
		GestorePosizioneMezzi.aggPosizioneMezzi(mezziPerFermata, numCicli);
		if (Boolean.TRUE.equals(sim.getStatoEsecuzione())) {
			sim.setNumCicli(numCicli + 1);
			simulazioneDAO.aggiornaSimulazione(sim);
		}
	}

	private static void calcolaRitardo(PojoFermataFE fermata) {
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

	private static void previsioneMeteo(PojoFermataFE fermataFE) {
		Integer randomGenerator = random.nextInt(3);

		switch (randomGenerator) {
			case 1 :
				fermataFE.setPrevisioneMeteo("pioggia");
				break;
			case 2 :
				fermataFE.setPrevisioneMeteo("sole");
				break;
			case 3 :
				fermataFE.setPrevisioneMeteo("nuvolo");
				break;
			default :
				fermataFE.setPrevisioneMeteo("sole");
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

	private static void aggiornaOraAttuale(PojoFermataFE fermataFE) {
		String stringaOrarioAttuale = fermataFE.getOrarioAttuale();
		LocalDateTime orarioAttuale = LocalDateTime.parse(stringaOrarioAttuale);
		fermataFE.setOrarioAttuale(orarioAttuale.plusMinutes(5).toString());
	}
}