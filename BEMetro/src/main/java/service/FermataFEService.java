package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;

import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.entity.Fermata;
import db.entity.Linea;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoFermata;
import presentation.pojo.PojoFermataFE;
import presentation.pojo.PojoLinea;
import service.builder.PojoFermataBuilder;
import service.builder.PojoFermataFEBuilder;
import service.builder.PojoLineaBuilder;

public class FermataFEService {
	private static FermataDAO fermataDAO = new FermataDAO();
	private static LineaDAO lineaDAO = new LineaDAO();
	private static final String NUMFERMATA = "numFermata";
	private static final String NOMELINEA = "nomeLinea";
	private static final String FERMATA_BE = "FermataBE";

	private FermataFEService() {
	}

	public static String aggiornaFermataFE(PojoFermataFE fermataFE) {
		PojoLinea pojolinea = null;
		PojoFermata pojoFermata = null;
		String risultato = "FERMATA E LINEA NON AGGIORNATA";
		Object[] pojoDalFE;
		List<Linea> controlloLinee;
		List<Fermata> controlloFermate;
		try {
			if (fermataFE.getNumero_fermata() == null
					|| fermataFE.getNome_linea() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			pojoDalFE = caricaDati(fermataFE);
			pojoFermata = (PojoFermata) pojoDalFE[0];
			pojolinea = (PojoLinea) pojoDalFE[1];

			controlloLinee = lineaDAO
					.leggiDaNomeLinea(fermataFE.getNome_linea());

			if (controlloLinee.isEmpty()) {
				LineaService.creaLinea(pojolinea);
			} else {
				LineaService.aggiornaLinea(pojolinea);
			}

			controlloFermate = fermataDAO
					.leggiDaNumFermata(fermataFE.getNumero_fermata());

			if (controlloFermate.isEmpty()) {
				FermataService.creaFermata(pojoFermata);
				FermataService.aggiornaRelazioneFermata(
						pojoFermata.getNumFermata(), pojolinea.getNomeLinea());
			} else {
				FermataService.aggiornaRelazioneFermata(
						pojoFermata.getNumFermata(), pojolinea.getNomeLinea());
			}

			risultato = "FERMATA E LINEA AGGIORNATA";

		} catch (NullPointerException e) {
			throw new CustomException(String.format(
					ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION, NUMFERMATA,
					NOMELINEA), Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							FERMATA_BE),
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

	public static PojoFermataFE leggiFermataFE(Integer id, String nome_linea,
			Integer numero_fermata) {
		Linea linea = null;
		Fermata fermata = null;
		PojoFermataFE risultato = null;
		String posizione;
		List<Integer> listaNumFermata = new ArrayList<>();

		try {
			if (id == null || nome_linea == null || numero_fermata == null)
				throw new CustomException(
						"Null value encountered in id, or nome_linea, or numero_fermata. id, or  nome_linea, or numero_fermata argument cannot be null. Please check for null references.",
						Response.Status.BAD_REQUEST);

			List<Fermata> controlloFermate = fermataDAO
					.leggiDaNumFermata(numero_fermata);
			List<Linea> controlloLinee = lineaDAO.leggiDaNomeLinea(nome_linea);

			if (controlloFermate.isEmpty())
				throw new CustomException("FERMATA NON PRESENTE NEL DATABASE",
						Response.Status.NOT_FOUND);
			else
				fermata = controlloFermate.get(0);

			if (controlloLinee.isEmpty()) {
				throw new CustomException("LINEA NON PRESENTE NEL DATABASE",
						Response.Status.NOT_FOUND);
			} else {
				linea = controlloLinee.get(0);
			}

			fermata.getMezzi().forEach(m -> listaNumFermata
					.add(m.getFermataAttuale().getNumFermata()));

			if (!listaNumFermata.isEmpty()) {
				posizione = "presente";
			} else {
				posizione = "assente";
			}

			risultato = new PojoFermataFEBuilder().setId(id)
					.setNumero_fermata(numero_fermata)
					.setNome_fermata(fermata.getNome())
					.setNome_Linea(nome_linea)
					.setDirezione(linea.getDirezione())
					.setPrevisione_meteo(fermata.getPrevisioneMeteo())
					.setPosizione_mezzo(posizione).costruisci();

		} catch (NullPointerException e) {
			throw new CustomException(String.format(
					ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION, NUMFERMATA,
					NOMELINEA), Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							FERMATA_BE),
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

	public static String rimuoviFermataFE(PojoFermataFE fermataFE,
			String riferimento) {
		String lineaRiscontro = "LINEA NON CANCELLATA";
		String fermataRiscontro = "FERMATA NON CANCELLATA";
		PojoLinea pojolinea = null;
		PojoFermata pojoFermata = null;
		String risultato;
		Object[] pojoDalFE;
		Optional<String> rifOpz = Optional.ofNullable(riferimento);
		String rif = rifOpz.isPresent() ? riferimento : "";
		try {
			if (fermataFE.getNumero_fermata() == null
					|| fermataFE.getNome_linea() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			pojoDalFE = caricaDati(fermataFE);
			pojoFermata = (PojoFermata) pojoDalFE[0];
			pojolinea = (PojoLinea) pojoDalFE[1];

			List<Fermata> controlloFermate = fermataDAO
					.leggiDaNumFermata(fermataFE.getNumero_fermata());
			List<Linea> controlloLinee = lineaDAO
					.leggiDaNomeLinea(fermataFE.getNome_linea());

			if (rif.equals("fermata")) {
				if (controlloFermate.isEmpty()) {
					fermataRiscontro = "IMPOSSIBILE CANCELLARE FERMATA, DATO NON PRESENTE O GIA' CANCELLATO";
				} else {
					fermataRiscontro = FermataService
							.cancellaFermata(pojoFermata.getNumFermata());
				}
			}

			if (rif.equals("linea")) {
				if (controlloLinee.isEmpty()) {
					lineaRiscontro = "IMPOSSIBILE CANCELLARE LINEA, DATO NON PRESENTE O GIA' CANCELLATO";
				} else {
					lineaRiscontro = LineaService
							.cancellaLinea(pojolinea.getNomeLinea());
				}
			}

		} catch (NullPointerException e) {
			throw new CustomException(String.format(
					ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION, NUMFERMATA,
					NOMELINEA), Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							FERMATA_BE),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		risultato = lineaRiscontro + " - " + fermataRiscontro;
		return risultato;
	}

	public static List<PojoFermataFE> leggiTutteLeFermateFE() {
		List<PojoFermataFE> listaFermateFE = new ArrayList<>();
		List<Fermata> listaFermate = fermataDAO.trovaTutteLeFermate();
		Integer idNum = 1;

		for (Fermata fermata : listaFermate) {
			listaFermateFE.add(
					leggiFermataFE(idNum, fermata.getLinea().getNomeLinea(),
							fermata.getNumFermata()));
			idNum++;
		}
		return listaFermateFE;
	}

	private static Object[] caricaDati(PojoFermataFE fermataFE) {
		Object[] pojoArray = new Object[2];
		PojoFermata fermata;
		PojoLinea linea;
		try {
			if (fermataFE.getNumero_fermata() == null
					|| fermataFE.getNome_linea() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			fermata = new PojoFermataBuilder()
					.setNumFermata(fermataFE.getNumero_fermata())
					.setNome(fermataFE.getNome_fermata())
					.setPrevisioneMeteo(fermataFE.getPrevisione_meteo())
					.setLinea(null).setMezzi(null).costruisci();

			linea = new PojoLineaBuilder()
					.setNomeLinea(fermataFE.getNome_linea())
					.setDirezione(fermataFE.getDirezione()).setFermate(null)
					.costruisci();

			pojoArray[0] = fermata;
			pojoArray[1] = linea;

		} catch (NullPointerException e) {
			throw new CustomException(String.format(
					ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION, NUMFERMATA,
					NOMELINEA), Response.Status.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new CustomException(
					String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
							FERMATA_BE),
					Response.Status.BAD_REQUEST);
		} catch (IndexOutOfBoundsException e) {
			throw new CustomException(
					String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
					Response.Status.NOT_FOUND);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return pojoArray;
	}

}
