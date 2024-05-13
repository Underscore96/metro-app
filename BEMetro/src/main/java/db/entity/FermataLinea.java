package db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@EqualsAndHashCode(of = {"idFermataLinea"})
@Entity
@Table(name = "Fermate_Lineee")
public class FermataLinea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFermataLinea")
	private Long idFermataLinea;

	@ManyToOne
	@JoinColumn(name = "idFermata")
	private Fermata fermata;

	@ManyToOne
	@JoinColumn(name = "idLinea")
	private Linea linea;
}