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
	private String nomeLinea;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private String posizioneMezzo;
	private Integer numMezzi;
	private List<PojoStatoMezzoFE> statiMezzi;
	private List<PojoOrarioFE> orariMezzi;

	public PojoFermataFE(Integer id, Integer numFermata, String nomeFermata,
			String nomeLinea, String direzione, String orarioAttuale,
			String previsioneMeteo, Integer numMezzi,
			List<PojoStatoMezzoFE> statiMezzi, String posizioneMezzo) {
		super();
		this.id = id;
		this.numFermata = numFermata;
		this.nomeFermata = nomeFermata;
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
		this.orarioAttuale = orarioAttuale;
		this.previsioneMeteo = previsioneMeteo;
		this.numMezzi = numMezzi;
		this.statiMezzi = statiMezzi;
		this.posizioneMezzo = posizioneMezzo;
	}
}