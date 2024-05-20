package service.builder;

import java.util.List;

import db.entity.Fermata;
import presentation.pojo.PojoLinea;

public class PojoLineaBuilder {
	private String nomeLinea;
	private Integer numLinea;
	private String destinazione;
	private List<Fermata> fermate;

	public PojoLineaBuilder setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
		return this;
	}

	public PojoLineaBuilder setNumLinea(Integer numLinea) {
		this.numLinea = numLinea;
		return this;
	}

	public PojoLineaBuilder setDestinazione(String destinazione) {
		this.destinazione = destinazione;
		return this;
	}

	public PojoLineaBuilder setFermate(List<Fermata> fermate) {
		this.fermate = fermate;
		return this;
	}

	public PojoLinea costruisci() {
		PojoLinea nuovaLinea = new PojoLinea();

		nuovaLinea.setNomeLinea(nomeLinea);
		nuovaLinea.setNumLinea(numLinea);
		nuovaLinea.setDestinazione(destinazione);
		nuovaLinea.setFermate(fermate);

		return nuovaLinea;
	}
}
