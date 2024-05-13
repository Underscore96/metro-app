package presentation.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatiMezzoFE {
	private Integer idMezzo;
	private Integer numFermata;
	private String destinazione;
	private String orarioPrevisto;
	private String ritardo;
	private String statoMezzo;
	private String presenzaMezzo;
}
