package service.builder;

import java.util.List;

import db.entity.Fermata;
import db.entity.Linea;

public class LineaBuilder {
	private String idLinea;
	private String nomeLinea;
	private Integer numLinea;
	private String destinazione;
	private List<Fermata> fermate;

	public LineaBuilder setIdLinea(String idLinea) {
		this.idLinea = idLinea;
		return this;
	}

	public LineaBuilder setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
		return this;
	}

	public LineaBuilder setNumLinea(Integer numLinea) {
		this.numLinea = numLinea;
		return this;
	}

	public LineaBuilder setDestinazione(String destinazione) {
		this.destinazione = destinazione;
		return this;
	}

	public LineaBuilder setFermate(List<Fermata> fermate) {
		this.fermate = fermate;
		return this;
	}

	public Linea costruisci() {
		Linea nuovaLinea = new Linea();

		nuovaLinea.setIdLinea(idLinea);
		nuovaLinea.setNomeLinea(nomeLinea);
		nuovaLinea.setNumLinea(numLinea);
		nuovaLinea.setDestinazione(destinazione);
		nuovaLinea.setFermate(fermate);

		return nuovaLinea;
	}
}
