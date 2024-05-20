package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.entity.Fermata;
import db.entity.Linea;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoFermata;
import service.builder.FermataBuilder;
import service.builder.PojoFermataBuilder;

public class FermataService {
	private static FermataDAO fermataDAO = new FermataDAO();
	private static LineaDAO lineaDAO = new LineaDAO();
	private static final String NUMFERMATA = "numFermata";

	private FermataService() {
	}

	public static String creaFermata(PojoFermata fermata) {
		Fermata dbFermata = null;
		String risultato = "FERMATA NON CREATA";

		try {
			dbFermata = new FermataBuilder()
					.setNumFermata(fermata.getNumFermata())
					.setNome(fermata.getNome())
					.setDirezione(fermata.getDirezione())
					.setOrarioAttuale(fermata.getOrarioAttuale())
					.setPosMezzo(fermata.getPosMezzo())
					.setPrevisioneMeteo(fermata.getPrevisioneMeteo())
					.setLinee(fermata.getLinee()).setMezzi(fermata.getMezzi())
					.costruisci();

			fermataDAO.crea(dbFermata);
			risultato = "FERMATA CREATA";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static PojoFermata leggiFermata(PojoFermata fermata) {
		Fermata fermataDB = null;
		PojoFermata risultato = null;
		List<Fermata> fermateDB = null;

		try {
			fermateDB = fermataDAO.leggiDaNumFermata(fermata.getNumFermata());
			fermataDB = fermateDB.get(0);

			if (fermataDB.getNumFermata() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMFERMATA, NUMFERMATA),
						Response.Status.NOT_FOUND);

			risultato = new PojoFermataBuilder()
					.setNumFermata(fermataDB.getNumFermata())
					.setNome(fermataDB.getNome())
					.setDirezione(fermataDB.getDirezione())
					.setOrarioAttuale(fermataDB.getOrarioAttuale())
					.setPrevisioneMeteo(fermataDB.getPrevisioneMeteo())
					.setPosMezzo(fermataDB.getPosMezzo())
					.setLinee(fermataDB.getLinee())
					.setMezzi(fermataDB.getMezzi()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static PojoFermata aggiornaFermata(PojoFermata fermata) {
		Fermata fermataVecchia = null;
		Fermata fermataAggiornata = null;
		PojoFermata risultato = null;
		List<Fermata> listaFermateTrovate = null;

		try {
			listaFermateTrovate = fermataDAO
					.leggiDaNumFermata(fermata.getNumFermata());
			fermataVecchia = listaFermateTrovate.get(0);

			if (fermata.getNumFermata() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMFERMATA, NUMFERMATA),
						Response.Status.NOT_FOUND);

			fermataAggiornata = new FermataBuilder()
					.setIdFermata(fermataVecchia.getIdFermata())
					.setNumFermata(fermataVecchia.getNumFermata())
					.setNome(fermata.getNome())
					.setDirezione(fermata.getDirezione())
					.setOrarioAttuale(fermata.getOrarioAttuale())
					.setPrevisioneMeteo(fermata.getPrevisioneMeteo())
					.setPosMezzo(fermata.getPosMezzo())
					.setLinee(fermata.getLinee()).setMezzi(fermata.getMezzi())
					.costruisci();

			fermataAggiornata = fermataDAO.aggiorna(fermataAggiornata);

			if (fermataAggiornata.getNumFermata() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMFERMATA, NUMFERMATA),
						Response.Status.NOT_FOUND);

			risultato = new PojoFermataBuilder()
					.setNumFermata(fermataAggiornata.getNumFermata())
					.setNome(fermataAggiornata.getNome())
					.setDirezione(fermataAggiornata.getDirezione())
					.setOrarioAttuale(fermataAggiornata.getOrarioAttuale())
					.setPrevisioneMeteo(fermataAggiornata.getPrevisioneMeteo())
					.setPosMezzo(fermataAggiornata.getPosMezzo())
					.setLinee(fermataAggiornata.getLinee())
					.setMezzi(fermataAggiornata.getMezzi()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static String cancellaFermata(Integer numFermata) {
		List<Fermata> fermateDB;
		Fermata fermataTrovata = null;
		String risultato = "FERMATA NON CANCELLATA";

		try {
			fermateDB = fermataDAO.leggiDaNumFermata(numFermata);
			fermataTrovata = fermateDB.get(0);

			fermataDAO.cancella(fermataTrovata);
			risultato = "FERMATA CANCELLATA";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static List<PojoFermata> trovaTutteLeFermate() {
		PojoFermata fermataSingola = null;
		List<Fermata> listaFermateDB;
		List<PojoFermata> risultati = new ArrayList<>();

		try {
			listaFermateDB = fermataDAO.trovaTutteLeFermate();

			for (Fermata fermataDB : listaFermateDB) {

				if (fermataDB.getNumFermata() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NUMFERMATA, NUMFERMATA),
							Response.Status.NOT_FOUND);

				fermataSingola = new PojoFermataBuilder()
						.setNumFermata(fermataDB.getNumFermata())
						.setNome(fermataDB.getNome())
						.setDirezione(fermataDB.getDirezione())
						.setOrarioAttuale(fermataDB.getOrarioAttuale())
						.setPrevisioneMeteo(fermataDB.getPrevisioneMeteo())
						.setPosMezzo(fermataDB.getPosMezzo())
						.setLinee(fermataDB.getLinee())
						.setMezzi(fermataDB.getMezzi()).costruisci();

				risultati.add(fermataSingola);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static List<PojoFermata> trovaConAttributi(String nome) {
		PojoFermata fermataSingola = null;
		List<PojoFermata> risultati = new ArrayList<>();
		List<Fermata> listaFermateDB;

		try {
			listaFermateDB = fermataDAO.trovaConAttributi(nome);

			for (Fermata fermataDB : listaFermateDB) {

				if (fermataDB.getNumFermata() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NUMFERMATA, NUMFERMATA),
							Response.Status.NOT_FOUND);

				fermataSingola = new PojoFermataBuilder()
						.setNumFermata(fermataDB.getNumFermata())
						.setNome(fermataDB.getNome())
						.setDirezione(fermataDB.getDirezione())
						.setOrarioAttuale(fermataDB.getOrarioAttuale())
						.setPrevisioneMeteo(fermataDB.getPrevisioneMeteo())
						.setPosMezzo(fermataDB.getPosMezzo())
						.setLinee(fermataDB.getLinee())
						.setMezzi(fermataDB.getMezzi()).costruisci();

				risultati.add(fermataSingola);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static PojoFermata aggiornaRelazioneFermata(Integer numFermata,
			List<String> listaNomeLinee) {
		PojoFermata risultato = null;
		Fermata fermataDB;
		List<Fermata> listaFermateDB;
		List<Linea> listaLinee = new ArrayList<>();

		try {
			listaFermateDB = fermataDAO.leggiDaNumFermata(numFermata);
			fermataDB = listaFermateDB.get(0);

			for (String nomeLinea : listaNomeLinee) {
				listaLinee.add(lineaDAO.leggiDaNomeLinea(nomeLinea).get(0));
			}

			fermataDB.setLinee(listaLinee);

			fermataDB = fermataDAO.aggiorna(fermataDB);

			if (fermataDB.getNumFermata() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMFERMATA, NUMFERMATA),
						Response.Status.NOT_FOUND);

			risultato = new PojoFermataBuilder()
					.setNumFermata(fermataDB.getNumFermata())
					.setNome(fermataDB.getNome())
					.setDirezione(fermataDB.getDirezione())
					.setOrarioAttuale(fermataDB.getOrarioAttuale())
					.setPrevisioneMeteo(fermataDB.getPrevisioneMeteo())
					.setPosMezzo(fermataDB.getPosMezzo())
					.setLinee(fermataDB.getLinee())
					.setMezzi(fermataDB.getMezzi()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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

	public static PojoFermata rinominaFermata(Integer numFermata,
			String nomeFermata) {
		Fermata fermataVecchia = null;
		Fermata fermataAggiornata = null;
		PojoFermata risultato = null;
		List<Fermata> listaFermateTrovate = null;

		try {
			listaFermateTrovate = fermataDAO.leggiDaNumFermata(numFermata);
			fermataVecchia = listaFermateTrovate.get(0);

			if (numFermata == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMFERMATA, NUMFERMATA),
						Response.Status.NOT_FOUND);

			fermataAggiornata = new FermataBuilder()
					.setIdFermata(fermataVecchia.getIdFermata())
					.setNumFermata(numFermata).setNome(nomeFermata)
					.setDirezione(fermataVecchia.getDirezione())
					.setOrarioAttuale(fermataVecchia.getOrarioAttuale())
					.setPrevisioneMeteo(fermataVecchia.getPrevisioneMeteo())
					.setPosMezzo(fermataVecchia.getPosMezzo())
					.setLinee(fermataVecchia.getLinee())
					.setMezzi(fermataVecchia.getMezzi()).costruisci();

			fermataAggiornata = fermataDAO.aggiorna(fermataAggiornata);

			if (fermataAggiornata.getNumFermata() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMFERMATA, NUMFERMATA),
						Response.Status.NOT_FOUND);

			risultato = new PojoFermataBuilder()
					.setNumFermata(fermataAggiornata.getNumFermata())
					.setNome(fermataAggiornata.getNome())
					.setDirezione(fermataAggiornata.getDirezione())
					.setOrarioAttuale(fermataAggiornata.getOrarioAttuale())
					.setPrevisioneMeteo(fermataAggiornata.getPrevisioneMeteo())
					.setPosMezzo(fermataAggiornata.getPosMezzo())
					.setLinee(fermataAggiornata.getLinee())
					.setMezzi(fermataAggiornata.getMezzi()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMFERMATA, NUMFERMATA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NUMFERMATA),
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
