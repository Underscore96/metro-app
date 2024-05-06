package service.builder;

import java.util.List;

import db.entity.Fermata;
import db.entity.Orario;
import presentation.pojo.PojoMezzo;

public class PojoMezzoBuilder {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private String stato;
	private Fermata fermataAttuale;
	private List<Orario> orari;

	public PojoMezzoBuilder setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
		return this;
	}

	public PojoMezzoBuilder setNumMaxPasseggeri(Integer numMaxPasseggeri) {
		this.numMaxPasseggeri = numMaxPasseggeri;
		return this;
	}

	public PojoMezzoBuilder setStato(String stato) {
		this.stato = stato;
		return this;
	}

	public PojoMezzoBuilder setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
		return this;
	}

	public PojoMezzoBuilder setOrari(List<Orario> orari) {
		this.orari = orari;
		return this;
	}

	public PojoMezzo costruisci() {
		PojoMezzo nuovoMezzo = new PojoMezzo();

		nuovoMezzo.setNumMezzo(numMezzo);
		nuovoMezzo.setNumMaxPasseggeri(numMaxPasseggeri);
		nuovoMezzo.setStato(stato);
		nuovoMezzo.setFermataAttuale(fermataAttuale);
		nuovoMezzo.setOrari(orari);

		return nuovoMezzo;

	}
}
