package presentation.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PojoFermataFE {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private List<String> nomiLinee;
	private List<String> destinazioni;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private Integer numMezzi;
	private List<DatiMezzoFE> datiMezziFE;
}