package db.entity;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Fermata")
public class Fermata {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFermata", nullable = false, unique = true, length = 20)
	private String idFermata;

	@Column(name = "numFermata", length = 10, nullable = false, unique = true)
	private Integer numFermata;

	@Column(name = "nome", length = 40, nullable = true, unique = false)
	private String nome;

	@Column(name = "orarioPrevisto", nullable = true, unique = false)
	private LocalTime orarioPrevisto;

	@Column(name = "ritardo", nullable = true, unique = false)
	private LocalTime ritardo;

	@Column(name = "previsioneMeteo", length = 40, nullable = true, unique = false)
	private String previsioneMeteo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idLinea")
	@JsonIgnore
	@JsonBackReference
	private Linea linea;

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

	public LocalTime getOrarioPrevisto() {
		return orarioPrevisto;
	}

	public void setOrarioPrevisto(LocalTime orarioPrevisto) {
		this.orarioPrevisto = orarioPrevisto;
	}

	public LocalTime getRitardo() {
		return ritardo;
	}

	public void setRitardo(LocalTime ritardo) {
		this.ritardo = ritardo;
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

	@Override
	public String toString() {
		return "Fermata [idFermata=" + idFermata + ", numFermata=" + numFermata
				+ ", nome=" + nome + ", orarioPrevisto=" + orarioPrevisto
				+ ", ritardo=" + ritardo + ", previsioneMeteo="
				+ previsioneMeteo + ", linea=" + linea + "]";
	}
}
