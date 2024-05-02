package presentation.pojo;

public class PojoOrarioFE {
	private Integer numMezzo;
	private String orarioPrevisto;
	private String ritardo;

	public Integer getNumMezzo() {
		return numMezzo;
	}

	public void setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
	}

	public String getOrarioPrevisto() {
		return orarioPrevisto;
	}

	public void setOrarioPrevisto(String orarioPrevisto) {
		this.orarioPrevisto = orarioPrevisto;
	}

	public String getRitardo() {
		return ritardo;
	}

	public void setRitardo(String ritardo) {
		this.ritardo = ritardo;
	}

	@Override
	public String toString() {
		return "PojoOrarioFE [numMezzo=" + numMezzo + ", orarioPrevisto="
				+ orarioPrevisto + ", ritardo=" + ritardo + "]";
	}
}
