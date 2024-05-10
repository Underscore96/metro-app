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
	private String destinazione;
	private List<Fermata> fermate;

	public PojoLinea(String nomeLinea, String destinazione) {
		super();
		this.nomeLinea = nomeLinea;
		this.destinazione = destinazione;
	}
}
