package presentation.pojo;

import java.util.List;

import db.entity.Linea;
import db.entity.Mezzo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PojoFermata {
	private Integer numFermata;
	private String nome;
	private String orarioAttuale;
	private String previsioneMeteo;
	private Linea linea;
	private String posMezzo;
	private List<Mezzo> mezzi;

	public PojoFermata(Integer numFermata, String nome, String orarioAttuale,
			String previsioneMeteo, String posMezzo) {
		super();
		this.numFermata = numFermata;
		this.nome = nome;
		this.orarioAttuale = orarioAttuale;
		this.previsioneMeteo = previsioneMeteo;
		this.posMezzo = posMezzo;
	}
}
