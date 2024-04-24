package presentation.pojo;

import java.util.List;

import db.entity.Fermata;
import db.entity.Orario;

public class PojoMezzo {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private List<Orario> orari;
	private Fermata fermataAttuale;

	public PojoMezzo() {
	}

	public PojoMezzo(Integer numMezzo, Integer numMaxPasseggeri,
			List<Orario> orari) {
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

	public List<Orario> getOrari() {
		return orari;
	}

	public void setOrari(List<Orario> orari) {
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
