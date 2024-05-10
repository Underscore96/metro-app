package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import db.dao.FermataDAO;
import db.dao.MezzoDAO;
import db.entity.Fermata;
import db.entity.Mezzo;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoMezzo;
import service.builder.MezzoBuilder;
import service.builder.PojoMezzoBuilder;

public class MezzoService {
	private static MezzoDAO mezzoDAO = new MezzoDAO();
	private static FermataDAO fermataDAO = new FermataDAO();
	private static final String NUMMEZZO = "numMezzo";

	private MezzoService() {
	}

	public static String creaMezzo(PojoMezzo mezzo) {
		Mezzo mezzoDB = null;
		String risultato = "MEZZO NON CREATO";

		try {
			mezzoDB = new MezzoBuilder().setNumMezzo(mezzo.getNumMezzo())
					.setNumMaxPasseggeri(mezzo.getNumMaxPasseggeri())
					.setOrari(mezzo.getOrari()).setStato(mezzo.getStato())
					.setDestinazione(mezzo.getDestinazione())
					.setFermataAttuale(mezzo.getFermataAttuale()).costruisci();

			mezzoDAO.crea(mezzoDB);
			risultato = "MEZZO CREATO";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMMEZZO, NUMMEZZO),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMMEZZO),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return risultato;
	}

	public static PojoMezzo leggiMezzo(PojoMezzo mezzo) {
		Mezzo mezzoDB = null;
		PojoMezzo risultato = null;
		List<Mezzo> listaMezziDB = null;

		try {
			listaMezziDB = mezzoDAO.leggiDaNumMezzo(mezzo.getNumMezzo());
			mezzoDB = listaMezziDB.get(0);

			if (mezzoDB.getNumMezzo() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMMEZZO, NUMMEZZO),
						Response.Status.NOT_FOUND);

			risultato = new PojoMezzoBuilder()
					.setNumMezzo(mezzoDB.getNumMezzo())
					.setNumMaxPasseggeri(mezzoDB.getNumMaxPasseggeri())
					.setOrari(mezzoDB.getOrari()).setStato(mezzoDB.getStato())
					.setDestinazione(mezzoDB.getDestinazione())
					.setFermataAttuale(mezzoDB.getFermataAttuale())
					.costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMMEZZO, NUMMEZZO),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMMEZZO),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return risultato;
	}

	public static PojoMezzo aggiornaMezzo(PojoMezzo mezzo) {
		Mezzo mezzoVecchio = null;
		Mezzo mezzoAggiornato = null;
		PojoMezzo risultato = null;
		List<Mezzo> listaMezziTrovati = null;

		try {
			listaMezziTrovati = mezzoDAO.leggiDaNumMezzo(mezzo.getNumMezzo());
			mezzoVecchio = listaMezziTrovati.get(0);

			if (mezzoVecchio.getNumMezzo() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMMEZZO, NUMMEZZO),
						Response.Status.NOT_FOUND);

			mezzoAggiornato = new MezzoBuilder()
					.setIdMezzo(mezzoVecchio.getIdMezzo())
					.setNumMezzo(mezzoVecchio.getNumMezzo())
					.setNumMaxPasseggeri(mezzo.getNumMaxPasseggeri())
					.setOrari(mezzo.getOrari()).setStato(mezzo.getStato())
					.setDestinazione(mezzo.getDestinazione())
					.setFermataAttuale(mezzo.getFermataAttuale()).costruisci();

			mezzoAggiornato = mezzoDAO.aggiorna(mezzoAggiornato);

			if (mezzoAggiornato.getNumMezzo() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMMEZZO, NUMMEZZO),
						Response.Status.NOT_FOUND);

			risultato = new PojoMezzoBuilder()
					.setNumMezzo(mezzoAggiornato.getNumMezzo())
					.setNumMaxPasseggeri(mezzoAggiornato.getNumMaxPasseggeri())
					.setOrari(mezzoAggiornato.getOrari())
					.setStato(mezzoAggiornato.getStato())
					.setDestinazione(mezzoAggiornato.getDestinazione())
					.setFermataAttuale(mezzoAggiornato.getFermataAttuale())
					.costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMMEZZO, NUMMEZZO),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMMEZZO),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return risultato;
	}

	public static String cancellaMezzo(Integer numMezzo) {
		List<Mezzo> listamezziDB;
		Mezzo mezzoTrovato = null;
		String risultato = "MEZZO NON CANCELLATO";

		try {
			listamezziDB = mezzoDAO.leggiDaNumMezzo(numMezzo);
			mezzoTrovato = listamezziDB.get(0);

			mezzoDAO.cancella(mezzoTrovato);
			risultato = "MEZZO CANCELLATO";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMMEZZO, NUMMEZZO),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMMEZZO),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return risultato;
	}

	public static List<PojoMezzo> trovaTuttiIMezzi() {
		PojoMezzo mezzoSingolo = null;
		List<Mezzo> listaMezziDB;
		List<PojoMezzo> risultati = new ArrayList<>();

		try {
			listaMezziDB = mezzoDAO.trovaTuttiIMezzi();

			for (Mezzo mezzoDB : listaMezziDB) {

				if (mezzoDB.getNumMezzo() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NUMMEZZO, NUMMEZZO),
							Response.Status.NOT_FOUND);

				mezzoSingolo = new PojoMezzoBuilder()
						.setNumMezzo(mezzoDB.getNumMezzo())
						.setNumMaxPasseggeri(mezzoDB.getNumMaxPasseggeri())
						.setOrari(mezzoDB.getOrari())
						.setStato(mezzoDB.getStato())
						.setDestinazione(mezzoDB.getDestinazione())
						.setFermataAttuale(mezzoDB.getFermataAttuale())
						.costruisci();

				risultati.add(mezzoSingolo);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMMEZZO, NUMMEZZO),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMMEZZO),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return risultati;
	}

	public static PojoMezzo aggiornaRelazioneMezzo(Integer numFermata,
			Integer numMezzo, String comando) {
		PojoMezzo risultato = null;
		Mezzo mezzoDB;
		List<Mezzo> listaMezziDB;
		Fermata fermataDB;
		List<Fermata> listaFermateDB;

		listaMezziDB = mezzoDAO.leggiDaNumMezzo(numMezzo);
		mezzoDB = listaMezziDB.get(0);

		listaFermateDB = fermataDAO.leggiDaNumFermata(numFermata);
		fermataDB = listaFermateDB.get(0);

		try {
			if (comando.equals("rimuovi")) {
				mezzoDB.setFermataAttuale(null);
				mezzoDB = mezzoDAO.aggiorna(mezzoDB);

			} else {
				mezzoDB.setFermataAttuale(fermataDB);
				mezzoDB = mezzoDAO.aggiorna(mezzoDB);
			}

			if (mezzoDB.getNumMezzo() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMMEZZO, NUMMEZZO),
						Response.Status.NOT_FOUND);

			risultato = new PojoMezzoBuilder()
					.setNumMezzo(mezzoDB.getNumMezzo())
					.setNumMaxPasseggeri(mezzoDB.getNumMaxPasseggeri())
					.setOrari(mezzoDB.getOrari()).setStato(mezzoDB.getStato())
					.setDestinazione(mezzoDB.getDestinazione())
					.setFermataAttuale(mezzoDB.getFermataAttuale())
					.costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMMEZZO, NUMMEZZO),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMMEZZO),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return risultato;
	}
}
