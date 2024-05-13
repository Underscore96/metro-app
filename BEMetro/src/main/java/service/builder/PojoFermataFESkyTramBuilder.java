package service.builder;

import java.util.List;

import presentation.pojo.DatiMezzoFE;
import presentation.pojo.PojoFermataFESkyTram;

public class PojoFermataFESkyTramBuilder {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private List<String> nomiLinee;
	private List<String> destinazioni;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private Integer numMezzi;
	private List<DatiMezzoFE> datiMezziFE;

	public PojoFermataFESkyTramBuilder setId(Integer id) {
		this.id = id;
		return this;
	}

	public PojoFermataFESkyTramBuilder setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
		return this;
	}

	public PojoFermataFESkyTramBuilder setNomeFermata(String nomeFermata) {
		this.nomeFermata = nomeFermata;
		return this;
	}

	public PojoFermataFESkyTramBuilder setNomiLinee(List<String> nomiLinee) {
		this.nomiLinee = nomiLinee;
		return this;
	}

	public PojoFermataFESkyTramBuilder setDestinazioni(
			List<String> destinazioni) {
		this.destinazioni = destinazioni;
		return this;
	}

	public PojoFermataFESkyTramBuilder setDirezione(String direzione) {
		this.direzione = direzione;
		return this;
	}

	public PojoFermataFESkyTramBuilder setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
		return this;
	}

	public PojoFermataFESkyTramBuilder setPrevisioneMeteo(
			String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public PojoFermataFESkyTramBuilder setNumMezzi(Integer numMezzi) {
		this.numMezzi = numMezzi;
		return this;
	}

	public PojoFermataFESkyTramBuilder setDatiMezziFE(
			List<DatiMezzoFE> datiMezziFE) {
		this.datiMezziFE = datiMezziFE;
		return this;
	}

	public PojoFermataFESkyTram costruisci() {
		PojoFermataFESkyTram nuovoPojoFermataFE = new PojoFermataFESkyTram();

		nuovoPojoFermataFE.setId(id);
		nuovoPojoFermataFE.setNumFermata(numFermata);
		nuovoPojoFermataFE.setNomeFermata(nomeFermata);
		nuovoPojoFermataFE.setNomiLinee(nomiLinee);
		nuovoPojoFermataFE.setDestinazioni(destinazioni);
		nuovoPojoFermataFE.setDirezione(direzione);
		nuovoPojoFermataFE.setOrarioAttuale(orarioAttuale);
		nuovoPojoFermataFE.setPrevisioneMeteo(previsioneMeteo);
		nuovoPojoFermataFE.setNumMezzi(numMezzi);
		nuovoPojoFermataFE.setDatiMezziFE(datiMezziFE);

		return nuovoPojoFermataFE;
	}
}
