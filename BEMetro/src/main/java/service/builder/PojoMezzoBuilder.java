package service.builder;

import java.util.Set;

import db.entity.Fermata;
import db.entity.Orario;
import presentation.pojo.PojoMezzo;

public class PojoMezzoBuilder {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private Fermata fermataAttuale;
	private Set<Orario> orari;

	public PojoMezzoBuilder setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
		return this;
	}

	public PojoMezzoBuilder setNumMaxPasseggeri(Integer numMaxPasseggeri) {
		this.numMaxPasseggeri = numMaxPasseggeri;
		return this;
	}

	public PojoMezzoBuilder setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
		return this;
	}
	
	public PojoMezzoBuilder setOrari(Set<Orario> orari) {
		this.orari = orari;
		return this;
	}

	public PojoMezzo costruisci() {
		PojoMezzo nuovoMezzo = new PojoMezzo();

		nuovoMezzo.setNumMezzo(numMezzo);
		nuovoMezzo.setNumMaxPasseggeri(numMaxPasseggeri);
		nuovoMezzo.setFermataAttuale(fermataAttuale);
		nuovoMezzo.setOrari(orari);

		return nuovoMezzo;

	}
}
