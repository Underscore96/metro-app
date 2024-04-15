package presentation.pojo;

import java.util.Set;

import db.entity.Fermata;

public class PojoLinea {
	private String nomeLinea;
	private String direzione;
	private Set<Fermata> fermate;

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
				+ "]";
	}
}
