package presentation.pojo;

import java.util.List;

import db.entity.Linea;
import db.entity.Mezzo;

public class PojoFermata {
	private Integer numFermata;
	private String nome;
	private String orarioAttuale;
	private String previsioneMeteo;
	private Linea linea;
	private String posMezzo;
	private List<Mezzo> mezzi;

	public PojoFermata() {
	}

	public PojoFermata(Integer numFermata, String nome, String orarioAttuale,
			String previsioneMeteo, String posMezzo) {
		super();
		this.numFermata = numFermata;
		this.nome = nome;
		this.orarioAttuale = orarioAttuale;
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

	public String getOrarioAttuale() {
		return orarioAttuale;
	}

	public void setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
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

	public List<Mezzo> getMezzi() {
		return mezzi;
	}

	public void setMezzi(List<Mezzo> mezzi) {
		this.mezzi = mezzi;
	}

	@Override
	public String toString() {
		return "PojoFermata [numFermata=" + numFermata + ", nome=" + nome
				+ ", orarioAttuale=" + orarioAttuale + ", previsioneMeteo="
				+ previsioneMeteo + ", linea=" + linea + ", posMezzo="
				+ posMezzo + ", mezzi=" + mezzi + "]";
	}
}
