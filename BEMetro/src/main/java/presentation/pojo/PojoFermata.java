package presentation.pojo;

import java.time.LocalTime;
import java.util.Set;

import db.entity.Linea;
import db.entity.Mezzo;

public class PojoFermata {
	private Integer numFermata;
	private String nome;
	private LocalTime orarioPrevisto;
	private LocalTime ritardo;
	private String previsioneMeteo;
	private Linea linea;
	private String posMezzo;
	private Set<Mezzo> mezzi;

	public PojoFermata() {
	}

	public PojoFermata(Integer numFermata, String nome,
			LocalTime orarioPrevisto, LocalTime ritardo, String previsioneMeteo,
			String posMezzo) {
		super();
		this.numFermata = numFermata;
		this.nome = nome;
		this.orarioPrevisto = orarioPrevisto;
		this.ritardo = ritardo;
		this.previsioneMeteo = previsioneMeteo;
		this.posMezzo = posMezzo;
	}

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

	public String getPosMezzo() {
		return posMezzo;
	}

	public void setPosMezzo(String posMezzo) {
		this.posMezzo = posMezzo;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Set<Mezzo> getMezzi() {
		return mezzi;
	}

	public void setMezzi(Set<Mezzo> mezzi) {
		this.mezzi = mezzi;
	}

	@Override
	public String toString() {
		return "PojoFermata [numFermata=" + numFermata + ", nome=" + nome
				+ ", orarioPrevisto=" + orarioPrevisto + ", ritardo=" + ritardo
				+ ", previsioneMeteo=" + previsioneMeteo + ", posMezzo="
				+ posMezzo + "]";
	}

}
