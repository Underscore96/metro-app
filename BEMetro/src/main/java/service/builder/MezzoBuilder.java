package service.builder;

import java.time.LocalTime;

import db.entity.Fermata;
import db.entity.Mezzo;

public class MezzoBuilder {
	private String idMezzo;
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private LocalTime orario;
	private Fermata fermataAttuale;

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

	public MezzoBuilder setOrario(LocalTime orario) {
		this.orario = orario;
		return this;
	}

	public MezzoBuilder setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
		return this;
	}

	public Mezzo costruisci() {
		Mezzo nuovoMezzo = new Mezzo();

		nuovoMezzo.setIdMezzo(idMezzo);
		nuovoMezzo.setNumMezzo(numMezzo);
		nuovoMezzo.setNumMaxPasseggeri(numMaxPasseggeri);
		nuovoMezzo.setOrario(orario);
		nuovoMezzo.setFermataAttuale(fermataAttuale);

		return nuovoMezzo;

	}
}
