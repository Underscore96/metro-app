package db.entity;

import java.util.List;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Linee")
public class Linea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLinea", nullable = false, unique = true, length = 20)
	private String idLinea;

	@Column(name = "nomeLinea", length = 10, nullable = false, unique = true)
	private String nomeLinea;

	@Column(name = "direzione", length = 40, nullable = true, unique = false)
	private String direzione;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "linea", cascade = CascadeType.ALL)
	@JsonManagedReference
	@JsonIgnore
	private List<Fermata> fermate;

	public Linea(String idLinea, String nomeLinea, String direzione) {
		super();
		this.idLinea = idLinea;
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
	}
}
