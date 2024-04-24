package service.builder;

import java.util.List;

import db.entity.Fermata;
import db.entity.Linea;

public class LineaBuilder {
	private String idLinea;
	private String nomeLinea;
	private String direzione;
	private List<Fermata> fermate;

	public LineaBuilder setIdLinea(String idLinea) {
		this.idLinea = idLinea;
		return this;
	}

	public LineaBuilder setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
		return this;
	}

	public LineaBuilder setDirezione(String direzione) {
		this.direzione = direzione;
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
		nuovaLinea.setDirezione(direzione);
		nuovaLinea.setFermate(fermate);

		return nuovaLinea;
	}
}
