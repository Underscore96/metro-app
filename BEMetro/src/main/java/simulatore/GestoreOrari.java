package simulatore;

import java.util.ArrayList;
import java.util.List;

import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.dao.OrarioDAO;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Orario;
import exception.CustomException;
import jakarta.ws.rs.core.Response;

public class GestoreOrari {
	private static OrarioDAO orarioDAO = new OrarioDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static LineaDAO lineaDAO = new LineaDAO();

	public static void generaOrari(Integer numMezzo, String direzione) {
		List<Mezzo> listaMezzi = mezzoDAO.leggiDaNumMezzo(numMezzo);
		List<Orario> listaOrari = new ArrayList<>();
		Mezzo mezzo = null;
		Integer numFermata = null;
		List<Fermata> fermateTrovate = null;
		Integer index = 0;
		Linea linea;
		if (listaMezzi != null && !listaMezzi.isEmpty()) {
			mezzo = listaMezzi.get(0);
		}

		List<Linea> lineeTrovate = lineaDAO.trovaConAttributi(direzione);

		if (lineeTrovate != null && !lineeTrovate.isEmpty()) {
			linea = lineeTrovate.get(0);
			fermateTrovate = linea.getFermate();
		}

		if (mezzo != null && mezzo.getOrari() != null) {
			for (Orario orario : mezzo.getOrari()) {
				orario.setOrarioPrevisto(
						orario.getOrarioPrevisto().plusMinutes(40));
				orario.setRitardo(orario.getRitardo().plusMinutes(40));

				if (fermateTrovate != null)
					numFermata = fermateTrovate.get(index).getNumFermata();
				else
					throw new CustomException(
							"ERRORE NELL'AGGIORNAMENTO DEGLI ORARI",
							Response.Status.NOT_FOUND);
				orario.setNumFermata(numFermata);
				orarioDAO.aggiorna(orario);
				listaOrari.add(orario);
				index++;
			}
			mezzo.setOrari(listaOrari);
			mezzoDAO.aggiorna(mezzo);
		}
	}
}
