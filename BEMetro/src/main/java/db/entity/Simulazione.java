package db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Simulazione")
public class Simulazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSimulazione", nullable = false, unique = true, length = 20)
	private String idSimulazione;

	@Column(name = "statoEsecuzione", nullable = false, unique = false)
	private Boolean statoEsecuzione;

	@Column(name = "numCicli", nullable = true, unique = false)
	private Integer numCicli;
}
