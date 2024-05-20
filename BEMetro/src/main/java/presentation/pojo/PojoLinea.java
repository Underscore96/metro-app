package presentation.pojo;

import java.util.List;

import db.entity.Fermata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PojoLinea {
	private String nomeLinea;
	private Integer numLinea;
	private String destinazione;
	private List<Fermata> fermate;

	public PojoLinea(String nomeLinea, Integer numLinea, String destinazione) {
		super();
		this.nomeLinea = nomeLinea;
		this.numLinea = numLinea;
		this.destinazione = destinazione;
	}
}
