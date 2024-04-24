package service.builder;

import java.util.List;

import db.entity.Linea;
import db.entity.Mezzo;
import presentation.pojo.PojoFermata;

public class PojoFermataBuilder {
	private Integer numFermata;
	private String nome;
	private String previsioneMeteo;
	private String posMezzo;
	private Linea linea;
	private List<Mezzo> mezzi;

	public PojoFermataBuilder setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
		return this;
	}

	public PojoFermataBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public PojoFermataBuilder setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public PojoFermataBuilder setPosMezzo(String posMezzo) {
		this.posMezzo = posMezzo;
		return this;
	}

	public PojoFermataBuilder setLinea(Linea linea) {
		this.linea = linea;
		return this;
	}

	public PojoFermataBuilder setMezzi(List<Mezzo> mezzi) {
		this.mezzi = mezzi;
		return this;
	}

	public PojoFermata costruisci() {
		PojoFermata nuovaFermata = new PojoFermata();

		nuovaFermata.setNumFermata(numFermata);
		nuovaFermata.setNome(nome);
		nuovaFermata.setPrevisioneMeteo(previsioneMeteo);
		nuovaFermata.setPosMezzo(posMezzo);
		nuovaFermata.setLinea(linea);
		nuovaFermata.setMezzi(mezzi);

		return nuovaFermata;
	}
}
