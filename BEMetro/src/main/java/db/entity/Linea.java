package db.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@JsonManagedReference
	@JsonIgnore
	private Set<Fermata> fermate;

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
