package service;

import java.util.List;

import org.hibernate.HibernateException;

import db.dao.CorsaDAO;
import db.entity.Corsa;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.ws.rs.core.Response;

public class CorsaService {
	private static CorsaDAO corsaDAO = new CorsaDAO();
	private static final String NUMCORSA = "numCorsa";

	private CorsaService() {
	}

	public static String creaCorsa(Corsa corsa) {
		String risultato = "CORSA NON CREATA";

		try {
			corsaDAO.crea(corsa);
			risultato = "CORSA CREATA";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMCORSA, NUMCORSA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMCORSA),
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

	public static Corsa leggiCorsa(Corsa corsa) {
		List<Corsa> listaCorsaDB = null;
		Corsa corsaDB = null;

		try {
			if (corsa.getNumCorsa() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NUMCORSA, NUMCORSA),
						Response.Status.NOT_FOUND);

			listaCorsaDB = corsaDAO.leggiDaNumCorsa(corsa.getNumCorsa());

			corsaDB = listaCorsaDB.get(0);

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMCORSA, NUMCORSA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMCORSA),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		return corsaDB;
	}

	public static Corsa aggiornaCorsa(Corsa corsa) {
		Corsa corsaVecchia = null;
		Corsa corsaAggiornata = null;
		List<Corsa> listaCorseTrovate = null;

		try {
			listaCorseTrovate = corsaDAO.leggiDaNumCorsa(corsa.getNumCorsa());

			corsaVecchia = listaCorseTrovate.get(0);
			corsa.setIdCorsa(corsaVecchia.getIdCorsa());

			corsaAggiornata = corsaDAO.aggiorna(corsa);

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMCORSA, NUMCORSA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMCORSA),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		return corsaAggiornata;
	}

	public static String cancellaCorsa(Integer numCorsa) {
		List<Corsa> listaCorseDB;
		Corsa corsaTrovata = null;
		String risultato = "CORSA NON CANCELLATA";

		try {
			listaCorseDB = corsaDAO.leggiDaNumCorsa(numCorsa);
			corsaTrovata = listaCorseDB.get(0);

			corsaDAO.cancella(corsaTrovata);

			risultato = "CORSA CANCELLATA";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMCORSA, NUMCORSA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMCORSA),
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

	public static List<Corsa> trovaTutteLeCorse() {
		List<Corsa> risultati = null;
		try {
			risultati = corsaDAO.trovaTutteLeCorse();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NUMCORSA, NUMCORSA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(String
					.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION, NUMCORSA),
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
}
