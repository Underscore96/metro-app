package db.entity;

import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"idCorsa"})
@Entity
@Table(name = "Corse")
public class Corsa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCorsa", nullable = false, unique = true, length = 20)
	private String idCorsa;

	@Column(name = "numCorsa", length = 10, nullable = false, unique = true)
	private Integer numCorsa;

	@Column(name = "orarioFineCorsa", nullable = true, unique = false)
	private LocalDateTime orarioFineCorsa;

	@Column(name = "ritardoMedio", nullable = true, unique = false)
	private LocalTime ritardoMedio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idMezzo")
	private Mezzo mezzo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nomeLinea")
	@JsonBackReference
	private Linea linea;

	public Corsa(String idCorsa, Integer numCorsa,
			LocalDateTime orarioFineCorsa, LocalTime ritardoMedio) {
		super();
		this.idCorsa = idCorsa;
		this.numCorsa = numCorsa;
		this.orarioFineCorsa = orarioFineCorsa;
		this.ritardoMedio = ritardoMedio;
	}
}
