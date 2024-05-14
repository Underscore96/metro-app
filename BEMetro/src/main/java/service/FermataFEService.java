package service;

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
import presentation.pojo.DatiMezzoFE;
import presentation.pojo.PojoFermataFE;
import presentation.pojo.PojoLinea;
import service.builder.FermataBuilder;
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
		Fermata fer = null;
		List<Mezzo> listaMezzi;
		List<Linea> controlloLinee;
		List<Linea> listaLinee = new ArrayList<>();
		List<PojoLinea> listaPojoLinee = new ArrayList<>();
		String risultato = "FERMATA E LINEE NON AGGIORNATA";
		try {
			if (fermataFE.getNumFermata() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			fer = caricaDati(fermataFE, listaPojoLinee, listaLinee);

			if (fer != null && fer.getNumFermata() != null) {

				if (fer.getIdFermata() != null) {
					fermataDAO.aggiorna(fer);
				} else
					fermataDAO.crea(fer);
			}

			if (!listaPojoLinee.isEmpty()) {
				for (PojoLinea pojoLinea : listaPojoLinee) {
					controlloLinee = lineaDAO
							.leggiDaNomeLinea(pojoLinea.getNomeLinea());
					if (controlloLinee.isEmpty()) {
						LineaService.creaLinea(pojoLinea);
					} else {
						LineaService.aggiornaLinea(pojoLinea);
					}

				}
			}

			if (fer != null && fer.getMezzi() != null) {
				listaMezzi = fer.getMezzi();
				if (!listaMezzi.isEmpty() && listaMezzi.get(0) != null) {
					for (Mezzo mezzo : listaMezzi)
						mezzoDAO.aggiorna(mezzo);
				}
			}

			risultato = "FERMATA E LINEE AGGIORNATA";

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

	public static PojoFermataFE leggiFermataFE(Integer id, Integer numFermata) {
		Fermata fermata = null;
		PojoFermataFE risultato = null;

		try {
			if (id == null || numFermata == null)
				throw new CustomException(
						"Null value encountered in id, or nome_linea, or numero_fermata. id, or  nome_linea, or numero_fermata argument cannot be null. Please check for null references.",
						Response.Status.BAD_REQUEST);

			List<Fermata> listaFermateTrovate = fermataDAO
					.leggiDaNumFermata(numFermata);

			if (listaFermateTrovate.isEmpty())
				throw new CustomException("FERMATA NON PRESENTE NEL DATABASE",
						Response.Status.NOT_FOUND);
			else
				fermata = listaFermateTrovate.get(0);

			List<Linea> listaLinee = fermata.getLinee();
			List<String> listaNomiLinee = new ArrayList<>();
			List<String> listaDestinazioniLinee = new ArrayList<>();

			if (listaLinee.isEmpty()) {
				throw new CustomException(
						"FERMATA NON ASSOCIATA AD UNA LINEA DEL DATABASE",
						Response.Status.NOT_FOUND);
			}

			for (Linea linea : listaLinee) {
				listaNomiLinee.add(linea.getNomeLinea());
				listaDestinazioniLinee.add(linea.getDestinazione());
			}

			risultato = new PojoFermataFEBuilder().setId(id)
					.setNumFermata(numFermata).setNomeFermata(fermata.getNome())
					.setNomiLinee(listaNomiLinee)
					.setDestinazioni(listaDestinazioniLinee)
					.setDirezione(fermata.getDirezione())
					.setOrarioAttuale(fermata.getOrarioAttuale())
					.setPrevisioneMeteo(fermata.getPrevisioneMeteo())
					.setNumMezzi(fermata.getMezzi().size())
					.setDatiMezziFE(generaDatiMezzo(fermata)).costruisci();

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

	private static List<DatiMezzoFE> generaDatiMezzo(Fermata fermata) {
		String destinazione = null;
		List<Orario> listaOrari = null;
		List<DatiMezzoFE> datiMezziFE = new ArrayList<>();
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();

		try {
			for (Mezzo m : listaMezzi) {
				listaOrari = m.getOrari();
				destinazione = m.getDestinazione();
				if (listaOrari != null && !listaOrari.isEmpty()) {
					for (Orario orario : listaOrari) {
						DatiMezzoFE datiMezzoFE = new DatiMezzoFE();
						String presenza = "assente";
						String stato = "";
						List<Mezzo> mezziPresenti = fermata.getMezzi();
						for (Mezzo mezzoPresente : mezziPresenti) {
							if (Objects.equals(mezzoPresente.getNumMezzo(),
									orario.getMezzo().getNumMezzo())) {
								presenza = "presente";
								stato = m.getStato();
							}

						}

						if (Objects.equals(orario.getNumFermata(),
								fermata.getNumFermata())) {

							datiMezzoFE.setIdMezzo(m.getNumMezzo());
							datiMezzoFE.setNumFermata(fermata.getNumFermata());
							datiMezzoFE.setDestinazione(destinazione);
							datiMezzoFE.setOrarioPrevisto(
									orario.getOrarioPrevisto().toString());
							datiMezzoFE
									.setRitardo(orario.getRitardo().toString());
							datiMezzoFE.setStatoMezzo(destinazione);
							datiMezzoFE.setPresenzaMezzo(presenza);
							datiMezzoFE.setStatoMezzo(stato);

							datiMezziFE.add(datiMezzoFE);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datiMezziFE;
	}

	public static String rimuoviFermataFE(PojoFermataFE fermataFE,
			String riferimento) {
		String lineaRiscontro = "LINEA NON CANCELLATA";
		String fermataRiscontro = "FERMATA NON CANCELLATA";
		String risultato;
		List<Linea> listaLinee = new ArrayList<>();
		List<Linea> controlloLinee;
		List<PojoLinea> listaPojoLinee = new ArrayList<>();
		Optional<String> rifOpz = Optional.ofNullable(riferimento);
		String rif = rifOpz.isPresent() ? riferimento : "";
		try {
			if (fermataFE.getNumFermata() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			caricaDati(fermataFE, listaPojoLinee, listaLinee);

			List<Fermata> controlloFermate = fermataDAO
					.leggiDaNumFermata(fermataFE.getNumFermata());

			if (rif.equals("fermata")) {
				if (controlloFermate.isEmpty()
						&& controlloFermate.get(0) != null) {
					fermataRiscontro = "IMPOSSIBILE CANCELLARE FERMATA, DATO NON PRESENTE O GIA' CANCELLATO";
				} else {
					fermataRiscontro = FermataService.cancellaFermata(
							controlloFermate.get(0).getNumFermata());
				}
			}

			for (Linea linea : listaLinee) {
				controlloLinee = lineaDAO
						.leggiDaNomeLinea(linea.getNomeLinea());
				if (rif.equals("linea")) {
					if (controlloLinee.isEmpty()
							&& controlloLinee.get(0) != null) {
						lineaRiscontro = "IMPOSSIBILE CANCELLARE LINEA, DATO NON PRESENTE O GIA' CANCELLATO";
					} else {
						lineaRiscontro = LineaService
								.cancellaLinea(linea.getNomeLinea());
					}
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
		try {
			List<Fermata> listaFermate = fermataDAO.trovaTutteLeFermate();
			Integer idNum = 1;

			for (Fermata fermata : listaFermate) {
				listaFermateFE
						.add(leggiFermataFE(idNum, fermata.getNumFermata()));
				idNum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaFermateFE;
	}

	private static Fermata caricaDati(PojoFermataFE fermataFE,
			List<PojoLinea> listaPojoLinee, List<Linea> listaLinee) {
		List<Linea> listaLineeBuffer = new ArrayList<>();
		List<Mezzo> listaMezzi = new ArrayList<>();
		List<Fermata> listaFermate = new ArrayList<>();
		Fermata fer = null;
		String idFermata = "";
		try {
			if (fermataFE == null || fermataFE.getNumFermata() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			listaFermate = fermataDAO
					.leggiDaNumFermata(fermataFE.getNumFermata());

			if (listaFermate != null && !listaFermate.isEmpty())
				listaFermate.get(0).getLinee();

			for (String nomeLinea : fermataFE.getNomiLinee())
				listaLineeBuffer
						.add(lineaDAO.leggiDaNomeLinea(nomeLinea).get(0));

			for (DatiMezzoFE datiMezzoFE : fermataFE.getDatiMezziFE())
				listaMezzi.add(mezzoDAO
						.leggiDaNumMezzo(datiMezzoFE.getIdMezzo()).get(0));

			fer = new FermataBuilder().setNumFermata(fermataFE.getNumFermata())
					.setNome(fermataFE.getNomeFermata())
					.setDirezione(fermataFE.getDirezione())
					.setOrarioAttuale(fermataFE.getOrarioAttuale())
					.setPrevisioneMeteo(fermataFE.getPrevisioneMeteo())
					.setLinee(listaLineeBuffer).setMezzi(listaMezzi)
					.costruisci();

			if (!listaFermate.isEmpty()) {
				idFermata = listaFermate.get(0).getIdFermata();
				fer.setIdFermata(idFermata);
			}

			for (Linea linea : listaLinee) {
				listaPojoLinee.add(new PojoLineaBuilder()
						.setNomeLinea(linea.getNomeLinea())
						.setDestinazione(linea.getDestinazione())
						.setFermate(linea.getFermate()).costruisci());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fer;
	}
}
