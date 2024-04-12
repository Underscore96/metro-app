package presentation.pojo;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PojoFermataFE {
	private Integer id;
	private Integer numero_fermata;
	private String nome_fermata;
	private String nome_linea;
	private String direzione;
	private String previsione_meteo;
	private LocalTime tempo_arrivo;
	private LocalTime ritardo_stimato;
}