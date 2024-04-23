package db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Simulazione")
public class Simulazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSimulazione", nullable = false, unique = true, length = 20)
	private String idSimulazione;

	@Column(name = "statoEsecuzione", nullable = false, unique = false)
	private Boolean statoEsecuzione;

	public String getIdSimulazione() {
		return idSimulazione;
	}

	public void setIdSimulazione(String idSimulazione) {
		this.idSimulazione = idSimulazione;
	}

	public Boolean getStatoEsecuzione() {
		return statoEsecuzione;
	}

	public void setStatoEsecuzione(Boolean statoEsecuzione) {
		this.statoEsecuzione = statoEsecuzione;
	}

	@Override
	public String toString() {
		return "Simulazione [idSimulazione=" + idSimulazione
				+ ", statoEsecuzione=" + statoEsecuzione + "]";
	}
}
