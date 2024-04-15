package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import db.dao.LineaDAO;
import db.entity.Fermata;
import db.entity.Linea;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoFermata;
import presentation.pojo.PojoLinea;
import service.builder.LineaBuilder;
import service.builder.PojoFermataBuilder;
import service.builder.PojoLineaBuilder;

public class LineaService {
	private static LineaDAO lineaDAO = new LineaDAO();
	private static final String NOMELINEA = "nomeLinea";

	private LineaService() {
	}

	public static String creaLinea(PojoLinea linea) {
		Linea dbLinea = null;
		String risultato = "LINEA NON CREATA";

		try {
			dbLinea = new LineaBuilder().setNomeLinea(linea.getNomeLinea())
					.setDirezione(linea.getDirezione())
					.setFermate(linea.getFermate()).costruisci();

			lineaDAO.crea(dbLinea);
			risultato = "LINEA CREATE";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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

	public static PojoLinea leggiLinea(PojoLinea linea) {
		Linea lineaDB = null;
		PojoLinea risultato = null;
		List<Linea> lineeDB = null;

		try {
			lineeDB = lineaDAO.leggiDaNomeLinea(linea.getNomeLinea());
			lineaDB = lineeDB.get(0);

			if (lineaDB.getNomeLinea() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NOMELINEA, NOMELINEA),
						Response.Status.NOT_FOUND);

			System.out.println("SET FERMATE lineaDB: " + lineaDB.getFermate());

			risultato = new PojoLineaBuilder()
					.setNomeLinea(lineaDB.getNomeLinea())
					.setDirezione(lineaDB.getDirezione())
					.setFermate(lineaDB.getFermate()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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

	public static PojoLinea aggiornaLinea(PojoLinea linea) {
		Linea lineaVecchia = null;
		Linea lineaAggiornata = null;
		PojoLinea risultato = null;
		List<Linea> listaLineeTrovate = null;

		try {
			listaLineeTrovate = lineaDAO.leggiDaNomeLinea(linea.getNomeLinea());
			lineaVecchia = listaLineeTrovate.get(0);

			if (linea.getNomeLinea() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NOMELINEA, NOMELINEA),
						Response.Status.NOT_FOUND);

			lineaAggiornata = new LineaBuilder()
					.setIdLinea(lineaVecchia.getIdLinea())
					.setNomeLinea(lineaVecchia.getNomeLinea())
					.setDirezione(linea.getDirezione())
					.setFermate(linea.getFermate()).costruisci();

			lineaAggiornata = lineaDAO.aggiorna(lineaAggiornata);

			if (lineaAggiornata.getNomeLinea() == null)
				throw new CustomException(
						String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
								NOMELINEA, NOMELINEA),
						Response.Status.NOT_FOUND);

			risultato = new PojoLineaBuilder()
					.setNomeLinea(lineaAggiornata.getNomeLinea())
					.setDirezione(lineaAggiornata.getDirezione())
					.setFermate(lineaAggiornata.getFermate()).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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

	public static String cancellaLinea(PojoLinea linea) {
		List<Linea> listaLineeDB;
		Linea LineaDB = null;
		String risultato = "LINEA NON CANCELLATA";

		try {
			listaLineeDB = lineaDAO.leggiDaNomeLinea(linea.getNomeLinea());
			LineaDB = listaLineeDB.get(0);

			lineaDAO.cancella(LineaDB);
			risultato = "LINEA CANCELLATA";

		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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

	public static List<PojoLinea> trovaTutteLeLinee() {
		PojoLinea lineaSingola = null;
		List<Linea> listaLineeDB;
		List<PojoLinea> risultati = new ArrayList<>();

		try {
			listaLineeDB = lineaDAO.trovaTutteLeLinee();

			for (Linea lineaDB : listaLineeDB) {

				if (lineaDB.getNomeLinea() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NOMELINEA, NOMELINEA),
							Response.Status.NOT_FOUND);

				lineaSingola = new PojoLineaBuilder()
						.setNomeLinea(lineaDB.getNomeLinea())
						.setDirezione(lineaDB.getDirezione())
						.setFermate(lineaDB.getFermate()).costruisci();

				risultati.add(lineaSingola);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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

	public static List<PojoLinea> trovaConAttributi(String direzione) {
		PojoLinea lineaSingola = null;
		List<PojoLinea> risultati = new ArrayList<>();
		List<Linea> listaLineeDB;

		try {
			listaLineeDB = lineaDAO.trovaConAttributi(direzione);

			for (Linea lineaDB : listaLineeDB) {

				if (lineaDB.getNomeLinea() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NOMELINEA, NOMELINEA),
							Response.Status.NOT_FOUND);

				lineaSingola = new PojoLineaBuilder()
						.setNomeLinea(lineaDB.getNomeLinea())
						.setDirezione(lineaDB.getDirezione())
						.setFermate(lineaDB.getFermate()).costruisci();

				risultati.add(lineaSingola);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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

	public static List<PojoFermata> leggiLineaConFermate(Linea linea) {
		PojoFermata pojoFermata = null;
		List<Fermata> listaFermateDB = null;
		List<PojoFermata> risultato = new ArrayList<>();

		try {
			listaFermateDB = lineaDAO
					.leggiLineaConFermate(linea.getNomeLinea());

			for (Fermata fermataDB : listaFermateDB) {

				if (fermataDB.getNumFermata() == null)
					throw new CustomException(
							String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
									NOMELINEA, NOMELINEA),
							Response.Status.NOT_FOUND);

				System.out.println("SET FERMATE: " + fermataDB);

				pojoFermata = new PojoFermataBuilder()
						.setNumFermata(fermataDB.getNumFermata())
						.setNome(fermataDB.getNome())
						.setOrarioPrevisto(fermataDB.getOrarioPrevisto())
						.setRitardo(fermataDB.getRitardo())
						.setPrevisioneMeteo(fermataDB.getPrevisioneMeteo())
						.setLinea(fermataDB.getLinea()).costruisci();

				risultato.add(pojoFermata);
			}
		} catch (NullPointerException e) {
			throw new CustomException(
					String.format(ErrorMessages.NULL_POINTER_EXCEPTION,
							NOMELINEA, NOMELINEA),
					Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							NOMELINEA),
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
