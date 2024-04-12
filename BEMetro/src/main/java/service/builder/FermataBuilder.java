package service.builder;

import java.time.LocalTime;

import db.entity.Fermata;
import db.entity.Linea;

public class FermataBuilder {
	private String idFermata;
	private Integer numFermata;
	private String nome;
	private LocalTime orarioPrevisto;
	private LocalTime ritardo;
	private String previsioneMeteo;
	private Linea linea;

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

	public FermataBuilder setOrarioPrevisto(LocalTime orarioPrevisto) {
		this.orarioPrevisto = orarioPrevisto;
		return this;
	}

	public FermataBuilder setRitardo(LocalTime ritardo) {
		this.ritardo = ritardo;
		return this;
	}

	public FermataBuilder setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public FermataBuilder setLinea(Linea linea) {
		this.linea = linea;
		return this;
	}

	public Fermata costruisci() {
		Fermata nuovaFermata = new Fermata();

		nuovaFermata.setIdFermata(idFermata);
		nuovaFermata.setNumFermata(numFermata);
		nuovaFermata.setNome(nome);
		nuovaFermata.setOrarioPrevisto(orarioPrevisto);
		nuovaFermata.setRitardo(ritardo);
		nuovaFermata.setPrevisioneMeteo(previsioneMeteo);
		nuovaFermata.setLinea(linea);

		return nuovaFermata;
	}
}
