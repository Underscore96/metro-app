package simulatore;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import db.dao.CorsaDAO;
import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.entity.Corsa;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Orario;

public class GestoreCorse {
	private static FermataDAO fermataDAO = new FermataDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static CorsaDAO corsaDAO = new CorsaDAO();
	private static LineaDAO lineaDAO = new LineaDAO();

	private GestoreCorse() {
	}

	public static Corsa generaCorsa(Integer numMezzo, String nomeLinea) {
		Mezzo mezzo;
		List<Corsa> listaCorse;
		Linea linea;
		Corsa corsa = new Corsa();
		
		try {
			linea = lineaDAO.leggiDaNomeLinea(nomeLinea).get(0);
			mezzo = mezzoDAO.leggiDaNumMezzo(numMezzo).get(0);
			listaCorse = corsaDAO.trovaTutteLeCorse();

			corsa.setNumCorsa(listaCorse.size() + 1);
			caricaOrari(mezzo, corsa);
			corsa.setMezzo(mezzo);
			corsa.setLinea(linea);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return corsa;
	}

	private static Corsa caricaOrari(Mezzo mezzo, Corsa corsa) {
		List<Orario> listaOrari;
		String orarioAttuale = "";
		LocalTime orarioPrevisto;
		LocalTime orarioRitardo;
		Fermata fermata = fermataDAO
				.leggiDaNumFermata(mezzo.getFermataAttuale().getNumFermata())
				.get(0);

		listaOrari = mezzo.getOrari();
		orarioAttuale = fermata.getOrarioAttuale();
		corsa.setOrarioFineCorsa(LocalDateTime.parse(orarioAttuale));
		for (Orario o : listaOrari) {
			if (Objects.equals(o.getNumFermata(), fermata.getNumFermata())) {
				orarioPrevisto = o.getOrarioPrevisto().toLocalTime();
				orarioRitardo = o.getRitardo().toLocalTime();

				corsa.setRitardoMedio(
						orarioPrevisto.plusMinutes(orarioRitardo.getMinute()));
			}
		}
		return corsa;
	}
}
