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
import presentation.pojo.DatiMezzoFE;
import presentation.pojo.PojoFermataFESkyTram;
import service.FermataFEServiceSkyTram;

public class ClasseMainSkyTram {
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static SimulazioneDAO simulazioneDAO = new SimulazioneDAO();
	private static Random random = new Random();

	private ClasseMainSkyTram() {
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

		try {
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
			List<PojoFermataFESkyTram> elencoFermate = FermataFEServiceSkyTram
					.leggiTutteLeFermateFE();

			for (PojoFermataFESkyTram fermataFE : elencoFermate) {
				calcolaRitardo(fermataFE);
				previsioneMeteo(fermataFE);
				registraPosizioneMezzi(mezziPerFermata);
				aggiornaOraAttuale(fermataFE);

				FermataFEServiceSkyTram.aggiornaFermataFE(fermataFE);
			}

			GestorePosizioneMezziSkyTram.aggPosizioneMezzi(mezziPerFermata, numCicli);

			if (Boolean.TRUE.equals(sim.getStatoEsecuzione())) {
				sim.setNumCicli(numCicli + 1);
				simulazioneDAO.aggiornaSimulazione(sim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void calcolaRitardo(PojoFermataFESkyTram fermata) {
		OrarioDAO orarioDAO = new OrarioDAO();

		List<DatiMezzoFE> elencoDatiMezzi = fermata.getDatiMezziFE();

		for (Integer i = 0; i < elencoDatiMezzi.size(); i++) {

			DatiMezzoFE datiMezzoFE = elencoDatiMezzi.get(i);

			List<Orario> listaOrariDaAggiornare = mezzoDAO
					.leggiDaNumMezzo(datiMezzoFE.getIdMezzo()).get(0)
					.getOrari();

			Integer ritardoRandom = 0;
			Integer randomValue = random.nextInt(11);
			if (randomValue > 7) {
				ritardoRandom = randomValue <= 9 ? 2 : -1;
			}
			LocalDateTime ritardoAggiornato = LocalDateTime
					.parse(datiMezzoFE.getRitardo()).plusMinutes(ritardoRandom);

			for (Orario orario : listaOrariDaAggiornare) {
				if (Objects.equals(orario.getNumFermata(),
						fermata.getNumFermata())) {
					orario.setRitardo(ritardoAggiornato);
					orarioDAO.aggiorna(orario);
				}
			}

			datiMezzoFE.setOrarioPrevisto(ritardoAggiornato.toString());
			elencoDatiMezzi.set(i, datiMezzoFE);
		}
		fermata.setDatiMezziFE(elencoDatiMezzi);
	}

	private static void previsioneMeteo(PojoFermataFESkyTram fermataFE) {
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
			Map<Integer, Fermata> mezziPerFermata) {
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();
		for (Mezzo m : listaMezzi) {
			mezziPerFermata.put(m.getNumMezzo(), m.getFermataAttuale());
		}
	}

	private static void aggiornaOraAttuale(PojoFermataFESkyTram fermataFE) {
		String stringaOrarioAttuale = fermataFE.getOrarioAttuale();
		LocalDateTime orarioAttuale = LocalDateTime.parse(stringaOrarioAttuale);
		fermataFE.setOrarioAttuale(orarioAttuale.plusMinutes(5).toString());
	}
}