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

	@Column(name = "orarioAttuale", nullable = true, unique = false)
	private String orarioAttuale;

	@Column(name = "previsioneMeteo", length = 40, nullable = true, unique = false)
	private String previsioneMeteo;

	@Column(name = "posMezzo", length = 20, nullable = true, unique = false)
	private String posMezzo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idLinea")
	@JsonBackReference
	private Linea linea;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fermataAttuale")
	@JsonManagedReference
	@JsonIgnore
	private List<Mezzo> mezzi;

	public Fermata() {
	}

	public Fermata(String idFermata, Integer numFermata, String nome,
			String orarioAttuale, String previsioneMeteo, String posMezzo) {
		super();
		this.idFermata = idFermata;
		this.numFermata = numFermata;
		this.nome = nome;
		this.orarioAttuale = orarioAttuale;
		this.previsioneMeteo = previsioneMeteo;
		this.posMezzo = posMezzo;
	}

	public String getIdFermata() {
		return idFermata;
	}

	public void setIdFermata(String idFermata) {
		this.idFermata = idFermata;
	}

	public Integer getNumFermata() {
		return numFermata;
	}

	public void setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrarioAttuale() {
		return orarioAttuale;
	}

	public void setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
	}

	public String getPrevisioneMeteo() {
		return previsioneMeteo;
	}

	public void setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public List<Mezzo> getMezzi() {
		return mezzi;
	}

	public void setMezzi(List<Mezzo> mezzi) {
		this.mezzi = mezzi;
	}

	public String getPosMezzo() {
		return posMezzo;
	}

	public void setPosMezzo(String posMezzo) {
		this.posMezzo = posMezzo;
	}

	@Override
	public String toString() {
		return "Fermata [idFermata=" + idFermata + ", numFermata=" + numFermata
				+ ", nome=" + nome + ", orarioAttuale=" + orarioAttuale
				+ ", previsioneMeteo=" + previsioneMeteo + ", posMezzo="
				+ posMezzo + ", linea=" + linea + ", mezzi=" + mezzi + "]";
	}
}
