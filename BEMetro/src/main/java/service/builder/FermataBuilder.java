package service.builder;

import java.util.Set;

import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;

public class FermataBuilder {
	private String idFermata;
	private Integer numFermata;
	private String nome;
	private String previsioneMeteo;
	private String posMezzo;
	private Linea linea;
	private Set<Mezzo> mezzi;

	public FermataBuilder setIdFermata(String idFermata) {
		this.idFermata = idFermata;
		return this;
	}

	public FermataBuilder setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
		return this;
	}

	public FermataBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public FermataBuilder setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public FermataBuilder setPosMezzo(String posMezzo) {
		this.posMezzo = posMezzo;
		return this;
	}

	public FermataBuilder setLinea(Linea linea) {
		this.linea = linea;
		return this;
	}

	public FermataBuilder setMezzi(Set<Mezzo> mezzi) {
		this.mezzi = mezzi;
		return this;
	}

	public Fermata costruisci() {
		Fermata nuovaFermata = new Fermata();

		nuovaFermata.setIdFermata(idFermata);
		nuovaFermata.setNumFermata(numFermata);
		nuovaFermata.setNome(nome);
		nuovaFermata.setPrevisioneMeteo(previsioneMeteo);
		nuovaFermata.setPosMezzo(posMezzo);
		nuovaFermata.setLinea(linea);
		nuovaFermata.setMezzi(mezzi);

		return nuovaFermata;
	}
}
