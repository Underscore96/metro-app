package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import presentation.pojo.PojoOrarioFE;
import presentation.pojo.PojoStatoMezzoFE;
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
		PojoFermata pojoFermata = new PojoFermata();
		List<PojoLinea> listaPojoLinee = new ArrayList<>();
		String risultato = "FERMATA E LINEE NON AGGIORNATA";
		List<Linea> controlloLinee;
		List<Fermata> controlloFermate;
		List<Mezzo> listaMezzi;
		try {
			if (fermataFE.getNumFermata() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			pojoFermata = caricaDati(fermataFE, pojoFermata, listaPojoLinee);

			System.out.println(
					"pojoFermata in aggiornaFermataFE: " + pojoFermata);
			System.out.println(
					"listaPojoLinee in aggiornaFermataFE: " + listaPojoLinee);

			if (!listaPojoLinee.isEmpty()) {
				for (String nomeLinea : fermataFE.getNomiLinee()) {
					controlloLinee = lineaDAO.leggiDaNomeLinea(nomeLinea);
					for (PojoLinea pojoLinea : listaPojoLinee) {
						if (pojoLinea.getNomeLinea().equals(nomeLinea)) {
							if (controlloLinee.isEmpty()) {
								LineaService.creaLinea(pojoLinea);
							} else {
								LineaService.aggiornaLinea(pojoLinea);
							}
							controlloLinee = new ArrayList<>();
						}
					}
				}
			}

			controlloFermate = fermataDAO
					.leggiDaNumFermata(fermataFE.getNumFermata());

			for (String nomeLinea : fermataFE.getNomiLinee()) {
				if (controlloFermate.isEmpty()) {
					FermataService.creaFermata(pojoFermata);
					FermataService.aggiornaRelazioneFermata(
							pojoFermata.getNumFermata(),
							lineaDAO.leggiDaNomeLinea(nomeLinea));
				} else {
					FermataService.aggiornaFermata(pojoFermata);
					FermataService.aggiornaRelazioneFermata(
							pojoFermata.getNumFermata(),
							lineaDAO.leggiDaNomeLinea(nomeLinea));
				}
			}
			listaMezzi = pojoFermata.getMezzi();

			if (!listaMezzi.isEmpty() && listaMezzi.get(0) != null) {
				for (Mezzo mezzo : listaMezzi)
					mezzoDAO.aggiorna(mezzo);
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
		String posizione;

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
			String[] nomiLinee = new String[listaLinee.size()];
			String[] destinazioniLinee = new String[listaLinee.size()];
			List<String> listaNomiLinee = new ArrayList<>();
			List<String> listaDestinazioniLinee = new ArrayList<>();
			if (listaLinee.isEmpty()) {
				throw new CustomException(
						"FERMATA NON ASSOCIATA AD UNA LINEA DEL DATABASE",
						Response.Status.NOT_FOUND);
			} else {
				for (Integer i = 0; i < listaLinee.size(); i++) {
					nomiLinee[i] = listaLinee.get(i).getNomeLinea();
					for (Integer z = 0; z < nomiLinee.length; z++) {
						listaNomiLinee.add(nomiLinee[i]);
					}

					destinazioniLinee[i] = listaLinee.get(i).getDestinazione();
					for (Integer z = 0; z < destinazioniLinee.length; z++) {
						listaDestinazioniLinee.add(destinazioniLinee[i]);
					}
				}
			}

			posizione = fermata.getPosMezzo();

			risultato = new PojoFermataFEBuilder().setId(id)
					.setNumFermata(numFermata).setNomeFermata(fermata.getNome())
					.setNomiLinee(listaNomiLinee)
					.setDestinazioni(listaDestinazioniLinee)
					.setDirezione(fermata.getDirezione())
					.setOrarioAttuale(fermata.getOrarioAttuale())
					.setPrevisioneMeteo(fermata.getPrevisioneMeteo())
					.setPosizioneMezzo(posizione)
					.setNumMezzi(fermata.getMezzi().size())
					.setStatiMezzi(generaStatiMezzi(fermata))
					.setOrariMezzi(stimaTempiPrevisti(fermata)).costruisci();

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

	private static List<PojoStatoMezzoFE> generaStatiMezzi(Fermata fermata) {
		List<PojoStatoMezzoFE> listaStatiMezzi = new ArrayList<>();
		List<Mezzo> listaMezzi;
		listaMezzi = fermata.getMezzi();

		for (Mezzo m : listaMezzi) {
			PojoStatoMezzoFE statoMezzo = new PojoStatoMezzoFE();
			statoMezzo.setIdMezzo(m.getNumMezzo());
			statoMezzo.setStato(m.getStato());

			listaStatiMezzi.add(statoMezzo);
		}
		return listaStatiMezzi;
	}

	private static List<PojoOrarioFE> stimaTempiPrevisti(Fermata fermata) {
		List<Orario> listaOrari = null;
		List<PojoOrarioFE> listaTempiArrivo = new ArrayList<>();
		List<Mezzo> listaMezzi = mezzoDAO.trovaTuttiIMezzi();
		String destinazione = null;
		try {
			for (Mezzo m : listaMezzi) {
				listaOrari = m.getOrari();
				destinazione = m.getDestinazione();
				if (listaOrari != null && !listaOrari.isEmpty()) {

					for (Orario orario : listaOrari) {
						PojoOrarioFE pojoOrarioFE = new PojoOrarioFE();

						if (Objects.equals(orario.getNumFermata(),
								fermata.getNumFermata())) {

							pojoOrarioFE.setIdMezzo(
									orario.getMezzo().getNumMezzo());
							pojoOrarioFE.setDestinazione(destinazione);
							pojoOrarioFE.setOrarioPrevisto(
									orario.getOrarioPrevisto().toString());
							pojoOrarioFE
									.setRitardo(orario.getRitardo().toString());

							listaTempiArrivo.add(pojoOrarioFE);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaTempiArrivo;
	}

	// public static String rimuoviFermataFE(PojoFermataFE fermataFE,
	// String riferimento) {
	// String lineaRiscontro = "LINEA NON CANCELLATA";
	// String fermataRiscontro = "FERMATA NON CANCELLATA";
	// PojoLinea pojolinea = null;
	// PojoFermata pojoFermata = null;
	// String risultato;
	// Object[] pojoDalFE;
	// Optional<String> rifOpz = Optional.ofNullable(riferimento);
	// String rif = rifOpz.isPresent() ? riferimento : "";
	// try {
	// if (fermataFE.getNumFermata() == null
	// || fermataFE.getNomeLinea() == null)
	// throw new CustomException(
	// String.format(
	// ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
	// NUMFERMATA, NOMELINEA),
	// Response.Status.BAD_REQUEST);
	//
	// pojoDalFE = caricaDati(fermataFE);
	// pojoFermata = (PojoFermata) pojoDalFE[0];
	// pojolinea = (PojoLinea) pojoDalFE[1];
	//
	// List<Fermata> controlloFermate = fermataDAO
	// .leggiDaNumFermata(fermataFE.getNumFermata());
	// List<Linea> controlloLinee = lineaDAO
	// .leggiDaNomeLinea(fermataFE.getNomeLinea());
	//
	// if (rif.equals("fermata")) {
	// if (controlloFermate.isEmpty()) {
	// fermataRiscontro = "IMPOSSIBILE CANCELLARE FERMATA, DATO NON PRESENTE O
	// GIA' CANCELLATO";
	// } else {
	// fermataRiscontro = FermataService
	// .cancellaFermata(pojoFermata.getNumFermata());
	// }
	// }
	//
	// if (rif.equals("linea")) {
	// if (controlloLinee.isEmpty()) {
	// lineaRiscontro = "IMPOSSIBILE CANCELLARE LINEA, DATO NON PRESENTE O GIA'
	// CANCELLATO";
	// } else {
	// lineaRiscontro = LineaService
	// .cancellaLinea(pojolinea.getNomeLinea());
	// }
	// }
	//
	// } catch (NullPointerException e) {
	// throw new CustomException(String.format(
	// ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION, NUMFERMATA,
	// NOMELINEA), Response.Status.NOT_FOUND);
	// } catch (IllegalArgumentException e) {
	// throw new CustomException(
	// String.format(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION,
	// FERMATA_BE),
	// Response.Status.BAD_REQUEST);
	// } catch (IndexOutOfBoundsException e) {
	// throw new CustomException(
	// String.format(ErrorMessages.INDEX_OUT_OF_BOUNDS_EXCEPTION),
	// Response.Status.NOT_FOUND);
	// } catch (HibernateException e) {
	// throw new CustomException(e.getMessage(),
	// Response.Status.INTERNAL_SERVER_ERROR);
	// }
	//
	// risultato = lineaRiscontro + " - " + fermataRiscontro;
	// return risultato;
	// }

	public static List<PojoFermataFE> leggiTutteLeFermateFE() {
		List<PojoFermataFE> listaFermateFE = new ArrayList<>();
		List<Fermata> listaFermate = fermataDAO.trovaTutteLeFermate();
		Integer idNum = 1;

		for (Fermata fermata : listaFermate) {
			listaFermateFE.add(leggiFermataFE(idNum, fermata.getNumFermata()));
			idNum++;
		}

		return listaFermateFE;
	}

	private static PojoFermata caricaDati(PojoFermataFE fermataFE,
			PojoFermata pojoFermata, List<PojoLinea> listaPojoLinee) {
		List<Linea> listaLineeFermata = new ArrayList<>();
		List<Mezzo> listaMezzi = new ArrayList<>();
		try {
			if (fermataFE == null || fermataFE.getNumFermata() == null)
				throw new CustomException(
						String.format(
								ErrorMessages.FERMATA_FE_NULL_POINTER_EXCEPTION,
								NUMFERMATA, NOMELINEA),
						Response.Status.BAD_REQUEST);

			for (String nomeLinea : fermataFE.getNomiLinee()) {
				listaLineeFermata
						.add(lineaDAO.leggiDaNomeLinea(nomeLinea).get(0));
			}

			for (PojoStatoMezzoFE pojoStatoMezzoFE : fermataFE
					.getStatiMezzi()) {
				listaMezzi = mezzoDAO
						.leggiDaNumMezzo(pojoStatoMezzoFE.getIdMezzo());
			}

			pojoFermata = new PojoFermataBuilder()
					.setNumFermata(fermataFE.getNumFermata())
					.setNome(fermataFE.getNomeFermata())
					.setDirezione(fermataFE.getDirezione())
					.setOrarioAttuale(fermataFE.getOrarioAttuale())
					.setPrevisioneMeteo(fermataFE.getPrevisioneMeteo())
					.setPosMezzo(fermataFE.getPosizioneMezzo())
					.setLinee(listaLineeFermata).setMezzi(listaMezzi)
					.costruisci();

			for (Linea linea : listaLineeFermata) {
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
		return pojoFermata;
	}
}
