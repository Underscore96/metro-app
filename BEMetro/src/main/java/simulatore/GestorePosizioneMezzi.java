package simulatore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import db.dao.FermataDAO;
import db.entity.Fermata;
import service.MezzoService;

public class GestorePosizioneMezzi {

	private static FermataDAO fermataDAO = new FermataDAO();
	private static final String RIM = "rimuovi";

	private GestorePosizioneMezzi() {
	}

	public static void aggPosizioneMezzi(Map<Integer, Fermata> mezziPerFermata,
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

			aggiornaRelazioniIniziali(mezziPerFermata1, setNumMezzi1);
			return;
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

			aggiornaRelazioniIniziali(mezziPerFermata2, setNumMezzi2);
			return;
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

			aggiornaRelazioniIniziali(mezziPerFermata3, setNumMezzi3);
		} else {
			aggiornaRelazioni(mezziPerFermata, setNumMezzi);
		}
	}

	private static void aggiornaRelazioniIniziali(
			Map<Integer, Fermata> mezziPerFermata, Set<Integer> setNumMezzi) {

		for (Integer numMezzo : setNumMezzi) {
			Fermata fer = mezziPerFermata.get(numMezzo);

			MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata(), numMezzo,
					RIM);

			MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata() + 1,
					numMezzo, "");
		}
	}

	private static void aggiornaRelazioni(Map<Integer, Fermata> mezziPerFermata,
			Set<Integer> setNumMezzi) {
		Fermata fer;
		for (Integer numMezzo : setNumMezzi) {
			fer = mezziPerFermata.get(numMezzo);

			if (fer.getNumFermata() == 8) {
				MezzoService.aggiornaRelazioneMezzo(8, numMezzo, RIM);
				MezzoService.aggiornaRelazioneMezzo(9, numMezzo, "");
				GestoreOrari.generaOrari(numMezzo, "Brignole");
			}
			if (fer.getNumFermata() == 16) {
				MezzoService.aggiornaRelazioneMezzo(16, numMezzo, RIM);
				MezzoService.aggiornaRelazioneMezzo(1, numMezzo, "");
				GestoreOrari.generaOrari(numMezzo, "Brin");
			} else {
				MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata(),
						numMezzo, RIM);
				MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata() + 1,
						numMezzo, "");
			}
		}
	}
}
