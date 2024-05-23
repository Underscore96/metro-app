package db.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"idLinea"})
@Entity
@Table(name = "Linee")
public class Linea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLinea", nullable = false, unique = true, length = 20)
	private String idLinea;

	@Column(name = "nomeLinea", length = 10, nullable = false, unique = true)
	private String nomeLinea;

	@Column(name = "numLinea", length = 10, nullable = true, unique = false)
	private Integer numLinea;

	@Column(name = "destinazione", length = 20, nullable = true, unique = false)
	private String destinazione;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "linee")
	@ToString.Exclude
	@JsonIgnore
	private List<Fermata> fermate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idCorsa")
	@JsonManagedReference
	@JsonIgnore
	@ToString.Exclude
	private List<Corsa> corse;

	public Linea(String idLinea, String nomeLinea, Integer numLinea,
			String destinazione) {
		super();
		this.idLinea = idLinea;
		this.nomeLinea = nomeLinea;
		this.numLinea = numLinea;
		this.destinazione = destinazione;
	}
}
