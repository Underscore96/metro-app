package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import db.dao.UtenteDAO;
import db.entity.Utente;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoUtente;
import service.builder.PojoUtenteBuilder;
import service.builder.UtenteBuilder;

public class UtenteService {
	private static UtenteDAO utenteDAO = new UtenteDAO();
	private static final String NOMEUTENTE = "nomeUtente";

	private UtenteService() {
	}

	public static String creaUtente(PojoUtente utente) {
		Utente utenteDB = null;
		String risultato = "UTENTE NON CREATO";

		try {
			utenteDB = new UtenteBuilder().setNomeUtente(utente.getNomeUtente())
					.setPassword(utente.getPassword()).setNome(utente.getNome())
					.setCognome(utente.getCognome())
					.setTelefono(utente.getTelefono()).setMail(utente.getMail())
					.setRuolo(utente.getRuolo()).costruisci();

			utenteDAO.crea(utenteDB);
			risultato = "UTENTE CREATO";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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

	public static PojoUtente leggiUtente(PojoUtente utente) {
		Utente utenteDB = null;
		PojoUtente risultato = null;
		List<Utente> listaUtentiDB = null;

		try {
			listaUtentiDB = utenteDAO.leggiDaNomeUtente(utente.getNomeUtente());
			utenteDB = listaUtentiDB.get(0);

			if (utenteDB.getNomeUtente() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NOMEUTENTE, NOMEUTENTE),
						Response.Status.NOT_FOUND);

			risultato = new PojoUtenteBuilder()
					.setNomeUtente(utenteDB.getNomeUtente())
					.setPassword(utenteDB.getPassword())
					.setNome(utenteDB.getNome())
					.setCognome(utenteDB.getCognome())
					.setTelefono(utenteDB.getTelefono())
					.setMail(utenteDB.getMail()).setRuolo(utenteDB.getRuolo())
					.costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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

	public static PojoUtente aggiornaUtente(PojoUtente utente) {
		Utente utenteVecchio = null;
		Utente utenteAggiornato = null;
		PojoUtente risultato = null;
		List<Utente> listaUtentiTrovati = null;

		try {
			listaUtentiTrovati = utenteDAO
					.leggiDaNomeUtente(utente.getNomeUtente());
			utenteVecchio = listaUtentiTrovati.get(0);

			if (utente.getNomeUtente() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NOMEUTENTE, NOMEUTENTE),
						Response.Status.NOT_FOUND);

			utenteAggiornato = new UtenteBuilder()
					.setIdUtente(utenteVecchio.getIdUtente())
					.setNomeUtente(utenteVecchio.getNomeUtente())
					.setPassword(utente.getPassword()).setNome(utente.getNome())
					.setCognome(utente.getCognome())
					.setTelefono(utente.getTelefono()).setMail(utente.getMail())
					.setRuolo(utente.getRuolo()).costruisci();

			utenteAggiornato = utenteDAO.aggiorna(utenteAggiornato);

			if (utenteAggiornato.getNomeUtente() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NOMEUTENTE, NOMEUTENTE),
						Response.Status.NOT_FOUND);

			risultato = new PojoUtenteBuilder()
					.setNomeUtente(utenteAggiornato.getNomeUtente())
					.setPassword(utenteAggiornato.getPassword())
					.setNome(utenteAggiornato.getNome())
					.setCognome(utenteAggiornato.getCognome())
					.setTelefono(utenteAggiornato.getTelefono())
					.setMail(utenteAggiornato.getMail())
					.setRuolo(utenteAggiornato.getRuolo()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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

	public static String cancellaUtente(String nomeUtente) {
		List<Utente> listaUtentiDB;
		Utente utenteDB = null;
		String risultato = "UTENTE NON CANCELLATO";

		try {
			listaUtentiDB = utenteDAO.leggiDaNomeUtente(nomeUtente);
			utenteDB = listaUtentiDB.get(0);

			utenteDAO.cancella(utenteDB);
			risultato = "UTENTE CANCELLATO";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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

	public static List<PojoUtente> trovaTuttiIUtenti() {
		PojoUtente utenteSingolo = null;
		List<Utente> listaUtentiDB;
		List<PojoUtente> risultati = new ArrayList<>();

		try {
			listaUtentiDB = utenteDAO.trovaTuttiIUtenti();

			for (Utente utenteDB : listaUtentiDB) {

				if (utenteDB.getNomeUtente() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NOMEUTENTE, NOMEUTENTE),
							Response.Status.NOT_FOUND);

				utenteSingolo = new PojoUtenteBuilder()
						.setNomeUtente(utenteDB.getNomeUtente())
						.setPassword(utenteDB.getPassword())
						.setNome(utenteDB.getNome())
						.setCognome(utenteDB.getCognome())
						.setTelefono(utenteDB.getTelefono())
						.setMail(utenteDB.getMail())
						.setRuolo(utenteDB.getRuolo()).costruisci();

				risultati.add(utenteSingolo);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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

	public static List<PojoUtente> trovaConAttributi(String password,
			String nome, String cognome, String mail, String telefono,
			String ruolo) {
		PojoUtente utenteSingolo = null;
		List<PojoUtente> risultati = new ArrayList<>();
		List<Utente> listaUtentiDB;

		try {
			listaUtentiDB = utenteDAO.trovaConAttributi(password, nome, cognome,
					mail, telefono, ruolo);

			for (Utente utenteDB : listaUtentiDB) {

				if (utenteDB.getNomeUtente() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NOMEUTENTE, NOMEUTENTE),
							Response.Status.NOT_FOUND);

				utenteSingolo = new PojoUtenteBuilder()
						.setNomeUtente(utenteDB.getNomeUtente())
						.setPassword(utenteDB.getPassword())
						.setNome(utenteDB.getNome())
						.setCognome(utenteDB.getCognome())
						.setTelefono(utenteDB.getTelefono())
						.setMail(utenteDB.getMail())
						.setRuolo(utenteDB.getRuolo()).costruisci();

				risultati.add(utenteSingolo);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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

	public static List<PojoUtente> login(String nomeUtente, String password) {
		PojoUtente utenteSingolo = null;
		List<PojoUtente> risultati = new ArrayList<>();
		List<Utente> listaUtentiDB;

		try {
			listaUtentiDB = utenteDAO.login(nomeUtente, password);

			for (Utente utenteDB : listaUtentiDB) {

				if (utenteDB.getNomeUtente() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NOMEUTENTE, NOMEUTENTE),
							Response.Status.NOT_FOUND);

				utenteSingolo = new PojoUtenteBuilder()
						.setNomeUtente(utenteDB.getNomeUtente())
						.setPassword(utenteDB.getPassword())
						.setNome(utenteDB.getNome())
						.setCognome(utenteDB.getCognome())
						.setTelefono(utenteDB.getTelefono())
						.setMail(utenteDB.getMail())
						.setRuolo(utenteDB.getRuolo()).costruisci();

				risultati.add(utenteSingolo);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMEUTENTE, NOMEUTENTE),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMEUTENTE),
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
