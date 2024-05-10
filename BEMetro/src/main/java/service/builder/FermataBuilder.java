package service.builder;

import java.util.List;

import db.entity.Fermata;
import db.entity.Linea;
import db.entity.Mezzo;

public class FermataBuilder {
	private String idFermata;
	private Integer numFermata;
	private String nome;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private String posMezzo;
	private List<Linea> linee;
	private List<Mezzo> mezzi;

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

	public FermataBuilder setDirezione(String direzione) {
		this.direzione = direzione;
		return this;
	}

	public FermataBuilder setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
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

	public FermataBuilder setLinee(List<Linea> linee) {
		this.linee = linee;
		return this;
	}

	public FermataBuilder setMezzi(List<Mezzo> mezzi) {
		this.mezzi = mezzi;
		return this;
	}

	public Fermata costruisci() {
		Fermata nuovaFermata = new Fermata();

		nuovaFermata.setIdFermata(idFermata);
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
