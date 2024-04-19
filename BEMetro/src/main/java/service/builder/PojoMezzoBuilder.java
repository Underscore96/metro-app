package service.builder;

import java.time.LocalTime;

import db.entity.Fermata;
import presentation.pojo.PojoMezzo;

public class PojoMezzoBuilder {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private LocalTime orario;
	private Fermata fermataAttuale;

	public PojoMezzoBuilder setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
		return this;
	}

	public PojoMezzoBuilder setNumMaxPasseggeri(Integer numMaxPasseggeri) {
		this.numMaxPasseggeri = numMaxPasseggeri;
		return this;
	}

	public PojoMezzoBuilder setOrario(LocalTime orario) {
		this.orario = orario;
		return this;
	}

	public PojoMezzoBuilder setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
		return this;
	}

	public PojoMezzo costruisci() {
		PojoMezzo nuovoMezzo = new PojoMezzo();

		nuovoMezzo.setNumMezzo(numMezzo);
		nuovoMezzo.setNumMaxPasseggeri(numMaxPasseggeri);
		nuovoMezzo.setOrario(orario);
		nuovoMezzo.setFermataAttuale(fermataAttuale);

		return nuovoMezzo;

	}
}
