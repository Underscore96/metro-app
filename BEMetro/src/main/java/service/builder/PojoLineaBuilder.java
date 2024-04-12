package service.builder;

import java.util.Set;

import db.entity.Fermata;
import presentation.pojo.PojoLinea;

public class PojoLineaBuilder {
	private String nomeLinea;
	private String direzione;
	private Set<Fermata> fermate;

	public PojoLineaBuilder setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
		return this;
	}

	public PojoLineaBuilder setDirezione(String direzione) {
		this.direzione = direzione;
		return this;
	}

	public PojoLineaBuilder setFermate(Set<Fermata> fermate) {
		this.fermate = fermate;
		return this;
	}

	public PojoLinea costruisci() {
		PojoLinea nuovaLinea = new PojoLinea();

		nuovaLinea.setNomeLinea(nomeLinea);
		nuovaLinea.setDirezione(direzione);
		nuovaLinea.setFermate(fermate);

		return nuovaLinea;
	}
}
