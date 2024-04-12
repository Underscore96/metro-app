package presentation.pojo;

import java.time.LocalDate;

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
	private LocalDate orarioPrevisto;
	private LocalDate ritardo;
	private String previsioneMeteo;
	private Linea linea;
}
