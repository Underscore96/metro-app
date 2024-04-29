package presentation.pojo;

import java.time.LocalDateTime;

public class PojoOrarioFE {
	private Integer numMezzo;
	private LocalDateTime orarioPrevisto;
	private LocalDateTime ritardo;

	public Integer getNumMezzo() {
		return numMezzo;
	}

	public void setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
	}

	public LocalDateTime getOrarioPrevisto() {
		return orarioPrevisto;
	}

	public void setOrarioPrevisto(LocalDateTime orarioPrevisto) {
		this.orarioPrevisto = orarioPrevisto;
	}

	public LocalDateTime getRitardo() {
		return ritardo;
	}

	public void setRitardo(LocalDateTime ritardo) {
		this.ritardo = ritardo;
	}

	@Override
	public String toString() {
		return "PojoOrarioFE [numMezzo=" + numMezzo + ", orarioPrevisto="
				+ orarioPrevisto + ", ritardo=" + ritardo + "]";
	}
}
