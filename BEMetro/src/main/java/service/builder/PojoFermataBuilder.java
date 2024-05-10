package service.builder;

import java.util.List;

import db.entity.Linea;
import db.entity.Mezzo;
import presentation.pojo.PojoFermata;

public class PojoFermataBuilder {
	private Integer numFermata;
	private String nome;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private String posMezzo;
	private List<Linea> linee;
	private List<Mezzo> mezzi;

	public PojoFermataBuilder setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
		return this;
	}

	public PojoFermataBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public PojoFermataBuilder setDirezione(String direzione) {
		this.direzione = direzione;
		return this;
	}

	public PojoFermataBuilder setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
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

	public PojoFermataBuilder setLinee(List<Linea> linee) {
		this.linee = linee;
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
		nuovaFermata.setDirezione(direzione);
		nuovaFermata.setOrarioAttuale(orarioAttuale);
		nuovaFermata.setPrevisioneMeteo(previsioneMeteo);
		nuovaFermata.setPosMezzo(posMezzo);
		nuovaFermata.setLinee(linee);
		nuovaFermata.setMezzi(mezzi);

		return nuovaFermata;
	}
}
