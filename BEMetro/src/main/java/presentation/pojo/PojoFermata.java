package presentation.pojo;

import java.time.LocalTime;

import db.entity.Linea;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "numFermata" })
public class PojoFermata {
	private Integer numFermata;
	private String nome;
	private LocalTime orarioPrevisto;
	private LocalTime ritardo;
	private String previsioneMeteo;
	private Linea linea;
}
