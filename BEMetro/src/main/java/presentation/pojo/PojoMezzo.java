package presentation.pojo;

import java.util.List;

import db.entity.Fermata;
import db.entity.Orario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PojoMezzo {
	private Integer numMezzo;
	private Integer numMaxPasseggeri;
	private String stato;
	private Fermata fermataAttuale;
	private List<Orario> orari;

	public PojoMezzo(Integer numMezzo, Integer numMaxPasseggeri,
			List<Orario> orari) {
		this.numMezzo = numMezzo;
		this.numMaxPasseggeri = numMaxPasseggeri;
		this.orari = orari;
	}
}
