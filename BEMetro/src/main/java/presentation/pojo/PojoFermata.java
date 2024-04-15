package presentation.pojo;

import java.time.LocalTime;

import db.entity.Linea;

public class PojoFermata {
	private Integer numFermata;
	private String nome;
	private LocalTime orarioPrevisto;
	private LocalTime ritardo;
	private String previsioneMeteo;
	private Linea linea;

	public Integer getNumFermata() {
		return numFermata;
	}

	public void setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalTime getOrarioPrevisto() {
		return orarioPrevisto;
	}

	public void setOrarioPrevisto(LocalTime orarioPrevisto) {
		this.orarioPrevisto = orarioPrevisto;
	}

	public LocalTime getRitardo() {
		return ritardo;
	}

	public void setRitardo(LocalTime ritardo) {
		this.ritardo = ritardo;
	}

	public String getPrevisioneMeteo() {
		return previsioneMeteo;
	}

	public void setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	@Override
	public String toString() {
		return "PojoFermata [numFermata=" + numFermata + ", nome=" + nome
				+ ", orarioPrevisto=" + orarioPrevisto + ", ritardo=" + ritardo
				+ ", previsioneMeteo=" + previsioneMeteo + ", linea=" + linea
				+ "]";
	}

}
