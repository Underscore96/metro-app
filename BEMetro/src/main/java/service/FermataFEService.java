package service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.HibernateException;

import db.dao.FermataDAO;
import db.dao.LineaDAO;
import db.dao.MezzoDAO;
import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;
import db.entity.Orario;
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
	private static MezzoDAO mezzoDAO = new MezzoDAO();
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
		List<Mezzo> listaMezzi = new ArrayList<>();
		List<LocalTime> listaRitardiStimati = new ArrayList<>();

		try {
			if (id == null || nome_linea == null || numero_fermata == null)
				throw new CustomException(
						"Null value encountered in id, or nome_linea, or numero_fermata. id, or  nome_linea, or numero_fermata argument cannot be null. Please check for null references.",
						Response.Status.BAD_REQUEST);

			List<Fermata> listaFermateTrovate = fermataDAO
					.leggiDaNumFermata(numero_fermata);
			List<Linea> listaLineeTrovate = lineaDAO
					.leggiDaNomeLinea(nome_linea);

			if (listaFermateTrovate.isEmpty())
				throw new CustomException("FERMATA NON PRESENTE NEL DATABASE",
						Response.Status.NOT_FOUND);
			else
				fermata = listaFermateTrovate.get(0);

			if (listaLineeTrovate.isEmpty()) {
				throw new CustomException("LINEA NON PRESENTE NEL DATABASE",
						Response.Status.NOT_FOUND);
			} else {
				linea = listaLineeTrovate.get(0);
			}

			posizione = presenzaMezzo(fermata);

			risultato = new PojoFermataFEBuilder().setId(id)
					.setNumero_fermata(numero_fermata)
					.setNome_fermata(fermata.getNome())
					.setNome_Linea(nome_linea)
					.setDirezione(linea.getDirezione())
					.setPrevisione_meteo(fermata.getPrevisioneMeteo())
					.setPosizione_mezzo(posizione)
					.setTempi_arrivo(stimaTempiPrevisti(fermata))
					.setRitardi_stimato(listaRitardiStimati)
					.setNumero_mezzi(fermata.getMezzi().size())
					.setListaMezzi(listaMezzi).costruisci(); // da sistemare

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

	private static List<LocalTime> stimaTempiPrevisti(Fermata fermata) {
		List<Orario> listaOrari = null;
		List<LocalTime> listaTempiArrivo = new ArrayList<>();
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();
		String daStampare = "";
		String daStampare2 = "";
		List<String> listaDaStampare = new ArrayList<>();
		try {
			for (Mezzo m : listaMezzi) {
				daStampare = "mezzo: " + m.getNumMezzo()
						+ " - DIR fermata - mezzo: "
						+ fermata.getLinea().getDirezione() + "\\"
						+ m.getFermataAttuale().getLinea().getDirezione()
						+ " - COMP: "
						+ m.getFermataAttuale().getLinea().getDirezione()
								.equals(fermata.getLinea().getDirezione())
						+ "\n";
				listaDaStampare.add(daStampare);

				if (m.getFermataAttuale().getLinea().getDirezione()
						.equals(fermata.getLinea().getDirezione()))
					listaOrari = m.getOrari();

				if (listaOrari != null && !listaOrari.isEmpty()) {
					for (Orario orario : listaOrari) {
						listaDaStampare.add(daStampare2);
						if (Objects.equals(orario.getNumFermata(),
								fermata.getNumFermata()))
							listaTempiArrivo.add(orario.getOrarioPrevisto());

						daStampare2 = "orario: " + orario
								+ " - listaTempiArrivo: " + listaTempiArrivo
								+ " - fermata: " + fermata.getNome() + " - "
								+ fermata.getNumFermata() + "\n";
						// listaRitardiStimati.add(orario.getRitardo());
					}
				}
				listaOrari = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(listaDaStampare);
		return listaTempiArrivo;
	}

	private static String presenzaMezzo(Fermata fermata) {
		String posizione;
		List<Integer> listaNumFermata = new ArrayList<>();
		fermata.getMezzi().forEach(m -> listaNumFermata
				.add(m.getFermataAttuale().getNumFermata()));

		if (!listaNumFermata.isEmpty()) {
			posizione = "presente";
		} else {
			posizione = "assente";
		}
		return posizione;
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
					.setPosMezzo(fermataFE.getPosizione_mezzo()).setLinea(null)
					.setMezzi(null).costruisci();

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
