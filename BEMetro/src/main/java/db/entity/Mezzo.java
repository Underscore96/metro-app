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

	@Column(name = "orario", nullable = true, unique = false)
	private LocalTime orario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idFermata")
	@JsonBackReference
	private Fermata fermataAttuale;

	public Mezzo() {
	}

	public Mezzo(String idMezzo, Integer numMezzo, Integer numMaxPasseggeri,
			LocalTime orario) {
		super();
		this.idMezzo = idMezzo;
		this.numMezzo = numMezzo;
		this.numMaxPasseggeri = numMaxPasseggeri;
		this.orario = orario;
	}

	public String getIdMezzo() {
		return idMezzo;
	}

	public void setIdMezzo(String idMezzo) {
		this.idMezzo = idMezzo;
	}

	public Integer getNumMezzo() {
		return numMezzo;
	}

	public void setNumMezzo(Integer numMezzo) {
		this.numMezzo = numMezzo;
	}

	public Integer getNumMaxPasseggeri() {
		return numMaxPasseggeri;
	}

	public void setNumMaxPasseggeri(Integer numMaxPasseggeri) {
		this.numMaxPasseggeri = numMaxPasseggeri;
	}

	public LocalTime getOrario() {
		return orario;
	}

	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}

	public Fermata getFermataAttuale() {
		return fermataAttuale;
	}

	public void setFermataAttuale(Fermata fermataAttuale) {
		this.fermataAttuale = fermataAttuale;
	}

	@Override
	public String toString() {
		return "Mezzo [idMezzo=" + idMezzo + ", numMezzo=" + numMezzo
				+ ", numMaxPasseggeri=" + numMaxPasseggeri + ", orario="
				+ orario + ", fermataAttuale=" + fermataAttuale + "]";
	}
}
