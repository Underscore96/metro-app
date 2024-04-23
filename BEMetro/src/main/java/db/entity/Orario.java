package db.entity;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "Orari")
public class Orario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFermata", nullable = false, unique = true, length = 20)
	private String idOrario;

	@Column(name = "nomeFermata", length = 20, nullable = true, unique = false)
	private String nomeFermata;

	@Column(name = "orarioPrevisto", nullable = true, unique = false)
	private LocalTime orarioPrevisto;

	@Column(name = "ritardo", nullable = true, unique = false)
	private LocalTime ritardo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idMezzo")
	@JsonBackReference
	private Mezzo mezzo;

	public Orario() {
	}

	public Orario(String idOrario, String nomeFermata, LocalTime orarioPrevisto,
			LocalTime ritardo) {
		super();
		this.idOrario = idOrario;
		this.nomeFermata = nomeFermata;
		this.orarioPrevisto = orarioPrevisto;
		this.ritardo = ritardo;
	}

	public String getIdOrario() {
		return idOrario;
	}

	public void setIdOrario(String idOrario) {
		this.idOrario = idOrario;
	}

	public String getNomeFermata() {
		return nomeFermata;
	}

	public void setNomeFermata(String nomeFermata) {
		this.nomeFermata = nomeFermata;
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

	public Mezzo getMezzo() {
		return mezzo;
	}

	public void setMezzo(Mezzo mezzo) {
		this.mezzo = mezzo;
	}

	@Override
	public String toString() {
		return "Orario [idOrario=" + idOrario + ", nomeFermata=" + nomeFermata
				+ ", orarioPrevisto=" + orarioPrevisto + ", ritardo=" + ritardo
				+ ", mezzo=" + mezzo + "]";
	}
}
