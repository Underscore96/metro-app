package service.builder;

import java.util.List;

import db.entity.Fermata;
import db.entity.Mezzo;
import db.entity.Orario;

public class MezzoBuilder {
	private String idMezzo;
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private String stato;
	private Fermata fermataAttuale;
	private List<Orario> orari;

	public MezzoBuilder setIdMezzo(String idMezzo) {
		this.idMezzo = idMezzo;
		return this;
	}

	public MezzoBuilder setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
		return this;
	}

	public MezzoBuilder setNumMaxPasseggeri(Integer numMaxPasseggeri) {
		this.numMaxPasseggeri = numMaxPasseggeri;
		return this;
	}

	public MezzoBuilder setStato(String stato) {
		this.stato = stato;
		return this;
	}

	public MezzoBuilder setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
		return this;
	}

	public MezzoBuilder setOrari(List<Orario> orari) {
		this.orari = orari;
		return this;
	}

	public Mezzo costruisci() {
		Mezzo nuovoMezzo = new Mezzo();

		nuovoMezzo.setIdMezzo(idMezzo);
		nuovoMezzo.setNumMezzo(numMezzo);
		nuovoMezzo.setNumMaxPasseggeri(numMaxPasseggeri);
		nuovoMezzo.setStato(stato);
		nuovoMezzo.setFermataAttuale(fermataAttuale);
		nuovoMezzo.setOrari(orari);

		return nuovoMezzo;

	}
}
