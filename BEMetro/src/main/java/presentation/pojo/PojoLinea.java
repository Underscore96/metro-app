package presentation.pojo;

import java.util.Set;

import db.entity.Fermata;

public class PojoLinea {
	private String nomeLinea;
	private String direzione;
	private Set<Fermata> fermate;

	public PojoLinea() {
	}

	public PojoLinea(String nomeLinea, String direzione, Set<Fermata> fermate) {
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

	public Set<Fermata> getFermate() {
		return fermate;
	}

	public void setFermate(Set<Fermata> fermate) {
		this.fermate = fermate;
	}

	@Override
	public String toString() {
		return "PojoLinea [nomeLinea=" + nomeLinea + ", direzione=" + direzione
				+ ", fermate=" + fermate + "]";
	}
}
