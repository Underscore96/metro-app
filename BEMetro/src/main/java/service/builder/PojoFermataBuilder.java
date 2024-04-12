package service.builder;

import java.time.LocalDate;

import db.entity.Linea;
import presentation.pojo.PojoFermata;

public class PojoFermataBuilder {
	private Integer numFermata;
	private String nome;
	private LocalDate orarioPrevisto;
	private LocalDate ritardo;
	private String previsioneMeteo;
	private Linea linea;

	public PojoFermataBuilder setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
		return this;
	}

	public PojoFermataBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public PojoFermataBuilder setOrarioPrevisto(LocalDate orarioPrevisto) {
		this.orarioPrevisto = orarioPrevisto;
		return this;
	}

	public PojoFermataBuilder setRitardo(LocalDate ritardo) {
		this.ritardo = ritardo;
		return this;
	}

	public PojoFermataBuilder setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
		return this;
	}

	public PojoFermataBuilder setLinea(Linea linea) {
		this.linea = linea;
		return this;
	}

	public PojoFermata costruisci() {
		PojoFermata nuovaFermata = new PojoFermata();

		nuovaFermata.setNumFermata(numFermata);
		nuovaFermata.setNome(nome);
		nuovaFermata.setOrarioPrevisto(orarioPrevisto);
		nuovaFermata.setRitardo(ritardo);
		nuovaFermata.setPrevisioneMeteo(previsioneMeteo);
		nuovaFermata.setLinea(linea);

		return nuovaFermata;
	}
}
