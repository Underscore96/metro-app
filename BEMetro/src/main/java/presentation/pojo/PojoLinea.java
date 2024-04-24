package presentation.pojo;

import java.util.List;

import db.entity.Fermata;

public class PojoLinea {
	private String nomeLinea;
	private String direzione;
	private List<Fermata> fermate;

	public PojoLinea() {
	}

	public PojoLinea(String nomeLinea, String direzione) {
		super();
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
	}

	public PojoLinea(String nomeLinea, String direzione,
			List<Fermata> fermate) {
		super();
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
		this.fermate = fermate;
	}

	public String getNomeLinea() {
		return nomeLinea;
	}

	public void setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public List<Fermata> getFermate() {
		return fermate;
	}

	public void setFermate(List<Fermata> fermate) {
		this.fermate = fermate;
	}

	@Override
	public String toString() {
		return "PojoLinea [nomeLinea=" + nomeLinea + ", direzione=" + direzione
				+ "]";
	}
}
