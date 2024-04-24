package service.builder;

import java.time.LocalTime;
import java.util.List;

import presentation.pojo.PojoFermataFE;

public class PojoFermataFEBuilder {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private String nomeLinea;
	private String direzione;
	private String previsioneMeteo;
	private String posizioneMezzo;
	private List<LocalTime> tempiArrivo;
	private List<LocalTime> ritardiStimato;
	private Integer numeroMezzi;
	private List<Integer> numIdMezzi;

	public PojoFermataFEBuilder setId(Integer id) {
		this.id = id;
		return this;
	}

	public PojoFermataFEBuilder setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
		return this;
	}

	public PojoFermataFEBuilder setNomeFermata(String nomeFermata) {
		this.nomeFermata = nomeFermata;
		return this;
	}

	public PojoFermataFEBuilder setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
		return this;
	}

	public PojoFermataFEBuilder setDirezione(String direzione) {
		this.direzione = direzione;
		return this;
	}

	public PojoFermataFEBuilder setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public PojoFermataFEBuilder setTempiArrivo(List<LocalTime> tempiArrivo) {
		this.tempiArrivo = tempiArrivo;
		return this;
	}

	public PojoFermataFEBuilder setRitardiStimato(
			List<LocalTime> ritardiStimato) {
		this.ritardiStimato = ritardiStimato;
		return this;
	}

	public PojoFermataFEBuilder setPosizioneMezzo(String posizioneMezzo) {
		this.posizioneMezzo = posizioneMezzo;
		return this;
	}

	public PojoFermataFEBuilder setNumeroMezzi(Integer numeroMezzi) {
		this.numeroMezzi = numeroMezzi;
		return this;
	}

	public PojoFermataFEBuilder setNumIdMezzi(List<Integer> numIdMezzi) {
		this.numIdMezzi = numIdMezzi;
		return this;
	}

	public PojoFermataFE costruisci() {
		PojoFermataFE nuovoPojoFermataFE = new PojoFermataFE();

		nuovoPojoFermataFE.setId(id);
		nuovoPojoFermataFE.setNumFermata(numFermata);
		nuovoPojoFermataFE.setNomeFermata(nomeFermata);
		nuovoPojoFermataFE.setNomeLinea(nomeLinea);
		nuovoPojoFermataFE.setDirezione(direzione);
		nuovoPojoFermataFE.setPrevisioneMeteo(previsioneMeteo);
		nuovoPojoFermataFE.setTempiArrivo(tempiArrivo);
		nuovoPojoFermataFE.setRitardiStimato(ritardiStimato);
		nuovoPojoFermataFE.setPosizioneMezzo(posizioneMezzo);
		nuovoPojoFermataFE.setNumeroMezzi(numeroMezzi);
		nuovoPojoFermataFE.setNumIdMezzi(numIdMezzi);

		return nuovoPojoFermataFE;
	}
}
