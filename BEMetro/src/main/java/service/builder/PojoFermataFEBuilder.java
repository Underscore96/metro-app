package service.builder;

import java.util.List;

import presentation.pojo.PojoFermataFE;
import presentation.pojo.PojoOrarioFE;
import presentation.pojo.PojoStatoMezzoFE;

public class PojoFermataFEBuilder {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private String nomeLinea;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private String posizioneMezzo;
	private Integer numMezzi;
	private List<PojoStatoMezzoFE> statiMezzi;
	private List<PojoOrarioFE> orariMezzi;

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

	public PojoFermataFEBuilder setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
		return this;
	}

	public PojoFermataFEBuilder setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public PojoFermataFEBuilder setPosizioneMezzo(String posizioneMezzo) {
		this.posizioneMezzo = posizioneMezzo;
		return this;
	}

	public PojoFermataFEBuilder setNumMezzi(Integer numMezzi) {
		this.numMezzi = numMezzi;
		return this;
	}

	public PojoFermataFEBuilder setStatiMezzi(
			List<PojoStatoMezzoFE> statiMezzi) {
		this.statiMezzi = statiMezzi;
		return this;
	}

	public PojoFermataFEBuilder setOrariMezzi(List<PojoOrarioFE> orariMezzi) {
		this.orariMezzi = orariMezzi;
		return this;
	}

	public PojoFermataFE costruisci() {
		PojoFermataFE nuovoPojoFermataFE = new PojoFermataFE();

		nuovoPojoFermataFE.setId(id);
		nuovoPojoFermataFE.setNumFermata(numFermata);
		nuovoPojoFermataFE.setNomeFermata(nomeFermata);
		nuovoPojoFermataFE.setNomeLinea(nomeLinea);
		nuovoPojoFermataFE.setDirezione(direzione);
		nuovoPojoFermataFE.setOrarioAttuale(orarioAttuale);
		nuovoPojoFermataFE.setPrevisioneMeteo(previsioneMeteo);
		nuovoPojoFermataFE.setOrariMezzi(orariMezzi);
		nuovoPojoFermataFE.setNumMezzi(numMezzi);
		nuovoPojoFermataFE.setStatiMezzi(statiMezzi);
		nuovoPojoFermataFE.setPosizioneMezzo(posizioneMezzo);

		return nuovoPojoFermataFE;
	}
}
