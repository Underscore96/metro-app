package service.inizializzadb;

import java.util.ArrayList;
import java.util.List;

import db.dao.CorsaDAO;
import db.dao.FermataDAO;
import db.dao.MezzoDAO;
import db.entity.Corsa;
import db.entity.Fermata;
import db.entity.Mezzo;

public class GestoreCorse {
	private static CorsaDAO corsaDAO = new CorsaDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static FermataDAO fermataDAO = new FermataDAO();
	private GestoreCorse() {
	}

	public static List<Corsa> aggiornaCorse() {
		Integer index = 1;
		Fermata fer;
		Corsa corsa;
		List<Fermata> listaFermate;
		List<Corsa> listaCorseAggiornata = new ArrayList<>();
		try {
			List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();

			for (Mezzo mezzo : listaMezzi) {
				fer = mezzo.getFermataAttuale();
				listaFermate = fermataDAO
						.leggiDaNumFermata(fer.getNumFermata());
				for (Integer i = 0; i < 3; i++) {
					corsa = corsaDAO.leggiDaNumCorsa(index).get(0);

					corsa.setMezzo(mezzo);
					corsa.setLinea(listaFermate.get(0).getLinee().get(0));

					corsaDAO.aggiorna(corsa);
					listaCorseAggiornata.add(corsa);
					index++;	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCorseAggiornata;
	}
}