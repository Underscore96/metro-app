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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@EqualsAndHashCode(of = { "idFermata" })
@Entity
@Table(name = "Fermate")
public class Fermata {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFermata", nullable = false, unique = true, length = 20)
	private String idFermata;

	@Column(name = "numFermata", length = 10, nullable = false, unique = true)
	private Integer numFermata;

	@Column(name = "nome", length = 40, nullable = true, unique = false)
	private String nome;

	@Column(name = "direzione", length = 40, nullable = true, unique = false)
	private String direzione;

	@Column(name = "orarioAttuale", nullable = true, unique = false)
	private String orarioAttuale;

	@Column(name = "previsioneMeteo", length = 40, nullable = true, unique = false)
	private String previsioneMeteo;

	@Column(name = "posMezzo", length = 20, nullable = true, unique = false)
	private String posMezzo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Fermate_Linee", joinColumns = @JoinColumn(name = "idFermata"), inverseJoinColumns = @JoinColumn(name = "idLinea"))
	@JsonIgnore
	@ToString.Exclude
	private List<Linea> linee;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fermataAttuale")
	@JsonManagedReference
	@JsonIgnore
	@ToString.Exclude
	private List<Mezzo> mezzi;

	public Fermata(String idFermata, Integer numFermata, String nome, String direzione, String orarioAttuale,
			String previsioneMeteo, String posMezzo) {
		super();
		this.idFermata = idFermata;
		this.numFermata = numFermata;
		this.nome = nome;
		this.direzione = direzione;
		this.orarioAttuale = orarioAttuale;
		this.previsioneMeteo = previsioneMeteo;
		this.posMezzo = posMezzo;
	}
}
