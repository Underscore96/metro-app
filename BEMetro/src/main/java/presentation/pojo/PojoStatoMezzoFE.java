package presentation.pojo;

public class PojoStatoMezzoFE {
	private Integer idMezzo;
	private String stato;

	public Integer getIdMezzo() {
		return idMezzo;
	}

	public void setIdMezzo(Integer numMezzo) {
		this.idMezzo = numMezzo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "PojoStatoMezzo [numMezzo=" + idMezzo + ", stato=" + stato + "]";
	}
}
