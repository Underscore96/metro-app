package db.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "mezzi")
public class Mezzo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMezzo", nullable = false, unique = true, length = 20)
	private String idMezzo;

	@Column(name = "numMezzo", length = 10, nullable = false, unique = true)
	private Integer numMezzo;

	@Column(name = "numMaxPasseggeri", length = 10, nullable = true, unique = false)
	private Integer numMaxPasseggeri;

	@Column(name = "stato", length = 20, nullable = true, unique = false)
	private String stato;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idFermata")
	@JsonBackReference
	private Fermata fermataAttuale;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mezzo")
	@JsonManagedReference
	@JsonIgnore
	private List<Orario> orari;

	public Mezzo(String idMezzo, Integer numMezzo, Integer numMaxPasseggeri) {
		super();
		this.idMezzo = idMezzo;
		this.numMezzo = numMezzo;
		this.numMaxPasseggeri = numMaxPasseggeri;
	}
}
