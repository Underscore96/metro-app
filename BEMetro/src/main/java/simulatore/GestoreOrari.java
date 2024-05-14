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

	private GestoreOrari() {
	}

	private static OrarioDAO orarioDAO = new OrarioDAO();
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static LineaDAO lineaDAO = new LineaDAO();

	public static void generaOrari(Integer numMezzo, String nomeLinea) {
		Linea linea;
		Integer index = 0;
		Mezzo mezzo = null;
		Integer numFermata = null;
		List<Fermata> fermateTrovate = null;
		List<Orario> listaOrari = new ArrayList<>();

		try {
			mezzo = mezzoDAO.leggiDaNumMezzo(numMezzo).get(0);

			linea = lineaDAO.leggiDaNomeLinea(nomeLinea).get(0);

			if (linea == null || mezzo == null) {
				throw new CustomException(
						"ERRORE NELL'AGGIORNAMENTO DEGLI ORARI",
						Response.Status.NOT_FOUND);
			}

			fermateTrovate = linea.getFermate();

			for (Orario orario : mezzo.getOrari()) {
				numFermata = fermateTrovate.get(index).getNumFermata();
				orario.setNumFermata(numFermata);
				orario.setOrarioPrevisto(
						orario.getOrarioPrevisto().plusMinutes(80));
				orario.setRitardo(orario.getRitardo().plusMinutes(80));

				orarioDAO.aggiorna(orario);
				listaOrari.add(orario);

				index++;
			}
			mezzo.setOrari(listaOrari);
			mezzoDAO.aggiorna(mezzo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
