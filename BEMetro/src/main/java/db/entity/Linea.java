package db.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Linea")
public class Linea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLinea", nullable = false, unique = true, length = 20)
	private String idLinea;

	@Column(name = "nomeLinea", length = 10, nullable = false, unique = true)
	private String nomeLinea;

	@Column(name = "direzione", length = 40, nullable = true, unique = false)
	private String direzione;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "idFermata", cascade = CascadeType.ALL)
	private Set<Fermata> fermate;

	public Linea() {
	}

	public Linea(String idLinea, String nomeLinea, String direzione) {
		super();
		this.idLinea = idLinea;
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
	}

	public Linea(String idLinea, String nomeLinea, String direzione,
			Set<Fermata> fermate) {
		super();
		this.idLinea = idLinea;
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
		this.fermate = fermate;
	}

	public String getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(String idLinea) {
		this.idLinea = idLinea;
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
		return "Linea [idLinea=" + idLinea + ", nomeLinea=" + nomeLinea
				+ ", direzione=" + direzione + "]";
	}
}
