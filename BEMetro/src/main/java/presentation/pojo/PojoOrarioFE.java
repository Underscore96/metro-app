package presentation.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PojoOrarioFE {
	private Integer numMezzo;
	private String orarioPrevisto;
	private String ritardo;
}
