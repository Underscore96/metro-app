package presentation.pojo;

import java.util.Set;

import db.entity.Fermata;
import db.entity.Orario;

public class PojoMezzo {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private Set<Orario> orari;
	private Fermata fermataAttuale;

	public PojoMezzo() {
	}

	public PojoMezzo(Integer numMezzo, Integer numMaxPasseggeri,
			Set<Orario> orari) {
		this.numMezzo = numMezzo;
		this.numMaxPasseggeri = numMaxPasseggeri;
		this.orari = orari;
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

	public Set<Orario> getOrari() {
		return orari;
	}

	public void setOrari(Set<Orario> orari) {
		this.orari = orari;
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
				+ numMaxPasseggeri + ", orari=" + orari + ", fermataAttuale="
				+ fermataAttuale + "]";
	}
}
