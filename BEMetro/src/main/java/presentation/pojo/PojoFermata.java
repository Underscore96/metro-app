package presentation.pojo;

import java.util.Set;

import db.entity.Linea;
import db.entity.Mezzo;

public class PojoFermata {
	private Integer numFermata;
	private String nome;
	private String previsioneMeteo;
	private Linea linea;
	private String posMezzo;
	private Set<Mezzo> mezzi;

	public PojoFermata() {
	}

	public PojoFermata(Integer numFermata, String nome, String previsioneMeteo,
			String posMezzo) {
		super();
		this.numFermata = numFermata;
		this.nome = nome;
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
				+ ", previsioneMeteo=" + previsioneMeteo + ", linea=" + linea
				+ ", posMezzo=" + posMezzo + ", mezzi=" + mezzi + "]";
	}

}
