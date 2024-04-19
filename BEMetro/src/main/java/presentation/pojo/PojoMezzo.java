package presentation.pojo;

import java.time.LocalTime;

import db.entity.Fermata;

public class PojoMezzo {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private LocalTime orario;
	private Fermata fermataAttuale;

	public PojoMezzo() {
	}

	public PojoMezzo(Integer numMezzo, Integer numMaxPasseggeri,
			LocalTime orario) {
		this.numMezzo = numMezzo;
		this.numMaxPasseggeri = numMaxPasseggeri;
		this.orario = orario;
	}

	public Integer getNumMezzo() {
		return numMezzo;
	}

	public void setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
	}

	public Integer getNumMaxPasseggeri() {
		return numMaxPasseggeri;
	}

	public void setNumMaxPasseggeri(Integer numMaxPasseggeri) {
		this.numMaxPasseggeri = numMaxPasseggeri;
	}

	public LocalTime getOrario() {
		return orario;
	}

	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}

	public Fermata getFermataAttuale() {
		return fermataAttuale;
	}

	public void setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
	}

	@Override
	public String toString() {
		return "PojoMezzo [numMezzo=" + numMezzo + ", numMaxPasseggeri="
				+ numMaxPasseggeri + ", orario=" + orario + ", fermataAttuale="
				+ fermataAttuale + "]";
	}
}
