package simulatore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import db.dao.FermataDAO;
import db.dao.MezzoDAO;
import db.entity.Fermata;
import db.entity.Mezzo;
import service.MezzoService;

public class GestorePosizioneMezziSkyTram {

	private static FermataDAO fermataDAO = new FermataDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static final String STA = "in stazione";
	private static final String ARR = "in arrivo";
	private static final String RIM = "rimuovi";

	private GestorePosizioneMezziSkyTram() {
	}

	public static void aggPosizioneMezzi(Map<Integer, Fermata> mezziPerFermata,
			Integer numCicli) {
		Set<Integer> setNumMezzi = mezziPerFermata.keySet();
		if (numCicli <= 2) {
			Map<Integer, Fermata> mezziPerFermata1 = new HashMap<>();
			Set<Integer> setNumMezzi1 = new HashSet<>();

			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);

			Fermata fer8 = fermataDAO.leggiDaNumFermata(8).get(0);

			Fermata fer16 = fermataDAO.leggiDaNumFermata(16).get(0);

			Fermata fer23 = fermataDAO.leggiDaNumFermata(23).get(0);

			mezziPerFermata1.put(1, fer1);

			mezziPerFermata1.put(4, fer8);

			mezziPerFermata1.put(7, fer16);

			mezziPerFermata1.put(10, fer23);

			setNumMezzi1.add(1);

			setNumMezzi1.add(4);

			setNumMezzi1.add(7);

			setNumMezzi1.add(10);

			aggiornaStatiIniziali(mezziPerFermata1, setNumMezzi1);
			return;
		}
		if (numCicli <= 4) {
			Map<Integer, Fermata> mezziPerFermata2 = new HashMap<>();
			Set<Integer> setNumMezzi2 = new HashSet<>();

			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			Fermata fer2 = fermataDAO.leggiDaNumFermata(2).get(0);

			Fermata fer8 = fermataDAO.leggiDaNumFermata(8).get(0);
			Fermata fer9 = fermataDAO.leggiDaNumFermata(9).get(0);

			Fermata fer16 = fermataDAO.leggiDaNumFermata(16).get(0);
			Fermata fer17 = fermataDAO.leggiDaNumFermata(17).get(0);

			Fermata fer23 = fermataDAO.leggiDaNumFermata(23).get(0);
			Fermata fer24 = fermataDAO.leggiDaNumFermata(24).get(0);

			mezziPerFermata2.put(2, fer1);
			mezziPerFermata2.put(1, fer2);

			mezziPerFermata2.put(5, fer8);
			mezziPerFermata2.put(4, fer9);

			mezziPerFermata2.put(8, fer16);
			mezziPerFermata2.put(7, fer17);

			mezziPerFermata2.put(11, fer23);
			mezziPerFermata2.put(10, fer24);

			setNumMezzi2.add(2);
			setNumMezzi2.add(1);

			setNumMezzi2.add(5);
			setNumMezzi2.add(4);

			setNumMezzi2.add(8);
			setNumMezzi2.add(7);

			setNumMezzi2.add(11);
			setNumMezzi2.add(10);

			aggiornaStatiIniziali(mezziPerFermata2, setNumMezzi2);
			return;
		}
		if (numCicli <= 6) {
			Map<Integer, Fermata> mezziPerFermata3 = new HashMap<>();
			Set<Integer> setNumMezzi3 = new HashSet<>();

			Fermata fer1 = fermataDAO.leggiDaNumFermata(1).get(0);
			Fermata fer2 = fermataDAO.leggiDaNumFermata(2).get(0);
			Fermata fer3 = fermataDAO.leggiDaNumFermata(3).get(0);

			Fermata fer8 = fermataDAO.leggiDaNumFermata(8).get(0);
			Fermata fer9 = fermataDAO.leggiDaNumFermata(9).get(0);
			Fermata fer10 = fermataDAO.leggiDaNumFermata(10).get(0);

			Fermata fer16 = fermataDAO.leggiDaNumFermata(16).get(0);
			Fermata fer17 = fermataDAO.leggiDaNumFermata(17).get(0);
			Fermata fer18 = fermataDAO.leggiDaNumFermata(18).get(0);

			System.out
					.println("FERMATA23: " + fermataDAO.leggiDaNumFermata(23));
			System.out
					.println("FERMATA24: " + fermataDAO.leggiDaNumFermata(24));
			System.out
					.println("FERMATA25: " + fermataDAO.leggiDaNumFermata(25));
			Fermata fer23 = fermataDAO.leggiDaNumFermata(23).get(0);
			Fermata fer24 = fermataDAO.leggiDaNumFermata(24).get(0);
			Fermata fer25 = fermataDAO.leggiDaNumFermata(25).get(0);

			mezziPerFermata3.put(3, fer1);
			mezziPerFermata3.put(2, fer2);
			mezziPerFermata3.put(1, fer3);

			mezziPerFermata3.put(6, fer8);
			mezziPerFermata3.put(5, fer9);
			mezziPerFermata3.put(4, fer10);

			mezziPerFermata3.put(9, fer16);
			mezziPerFermata3.put(8, fer17);
			mezziPerFermata3.put(7, fer18);

			mezziPerFermata3.put(12, fer23);
			mezziPerFermata3.put(11, fer24);
			mezziPerFermata3.put(10, fer25);

			setNumMezzi3.add(3);
			setNumMezzi3.add(2);
			setNumMezzi3.add(1);

			setNumMezzi3.add(6);
			setNumMezzi3.add(5);
			setNumMezzi3.add(4);

			setNumMezzi3.add(9);
			setNumMezzi3.add(8);
			setNumMezzi3.add(7);

			setNumMezzi3.add(12);
			setNumMezzi3.add(11);
			setNumMezzi3.add(10);

			aggiornaStatiIniziali(mezziPerFermata3, setNumMezzi3);
		} else {
			aggiornaStati(mezziPerFermata, setNumMezzi);
		}
	}

	private static void aggiornaStatiIniziali(
			Map<Integer, Fermata> mezziPerFermata, Set<Integer> setNumMezzi) {
		Fermata fer;
		List<Mezzo> listaMezzi;
		Mezzo m;
		String stato;
		System.out.println("SET NUM MEZZI : " + setNumMezzi);
		System.out.println("MEZZI PER FERMATA : " + mezziPerFermata);
		for (Integer numMezzo : setNumMezzi) {
			fer = mezziPerFermata.get(numMezzo);
			listaMezzi = mezzoDAO.leggiDaNumMezzo(numMezzo);

			if (listaMezzi != null && !listaMezzi.isEmpty()) {
				m = listaMezzi.get(0);
				stato = m.getStato();
				if (stato.equals(ARR)) {
					m.setStato(STA);
					mezzoDAO.aggiorna(m);
				} else if (stato.equals(STA)) {
					m.setStato(ARR);
					mezzoDAO.aggiorna(m);
					MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata(),
							numMezzo, RIM);
					MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata() + 1,
							numMezzo, "");
				}
			}
		}
	}

	private static void aggiornaStati(Map<Integer, Fermata> mezziPerFermata,
			Set<Integer> setNumMezzi) {
		Fermata fer;
		List<Mezzo> listaMezzi;
		Mezzo m;
		String stato;
		for (Integer numMezzo : setNumMezzi) {
			fer = mezziPerFermata.get(numMezzo);
			listaMezzi = mezzoDAO.leggiDaNumMezzo(numMezzo);

			if (listaMezzi != null && !listaMezzi.isEmpty()) {
				m = listaMezzi.get(0);
				stato = m.getStato();
				if (stato.equals(ARR)) {
					m.setStato(STA);
					mezzoDAO.aggiorna(m);
				} else if (stato.equals(STA)) {
					m.setStato(ARR);
					mezzoDAO.aggiorna(m);
					aggiornaRelazioni(fer, numMezzo, m.getDestinazione());
				}
			}
		}
	}
	private static void aggiornaRelazioni(Fermata fer, Integer numMezzo,
			String destinazione) {
		if (fer.getNumFermata() == 8 && destinazione.equals("Brignole")) {
			MezzoService.aggiornaRelazioneMezzo(8, numMezzo, RIM);
			MezzoService.aggiornaRelazioneMezzo(23, numMezzo, "");
			GestoreOrari.generaOrari(numMezzo, "blu");
		} else if (fer.getNumFermata() == 30) {
			MezzoService.aggiornaRelazioneMezzo(30, numMezzo, RIM);
			MezzoService.aggiornaRelazioneMezzo(1, numMezzo, "");
			GestoreOrari.generaOrari(numMezzo, "verde");
		} else if (fer.getNumFermata() == 15) {
			MezzoService.aggiornaRelazioneMezzo(15, numMezzo, RIM);
			MezzoService.aggiornaRelazioneMezzo(16, numMezzo, "");
			GestoreOrari.generaOrari(numMezzo, "rossa");
		} else if (fer.getNumFermata() == 23
				&& destinazione.equals("Brignole")) {
			MezzoService.aggiornaRelazioneMezzo(23, numMezzo, RIM);
			MezzoService.aggiornaRelazioneMezzo(8, numMezzo, "");
			GestoreOrari.generaOrari(numMezzo, "gialla");
		} else {
			MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata(), numMezzo,
					RIM);
			MezzoService.aggiornaRelazioneMezzo(fer.getNumFermata() + 1,
					numMezzo, "");
		}
	}
}