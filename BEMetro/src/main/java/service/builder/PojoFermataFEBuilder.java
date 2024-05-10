package service.builder;

import java.util.List;

import presentation.pojo.PojoFermataFE;
import presentation.pojo.PojoOrarioFE;
import presentation.pojo.PojoStatoMezzoFE;

public class PojoFermataFEBuilder {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private List<String> nomiLinee;
	private List<String> destinazioni;
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

	public PojoFermataFEBuilder setNomiLinee(List<String> nomiLinee) {
		this.nomiLinee = nomiLinee;
		return this;
	}

	public PojoFermataFEBuilder setDestinazioni(List<String> destinazioni) {
		this.destinazioni = destinazioni;
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
		nuovoPojoFermataFE.setNomiLinee(nomiLinee);
		nuovoPojoFermataFE.setDestinazioni(destinazioni);
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
