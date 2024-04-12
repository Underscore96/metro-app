package presentation.pojo;

import java.util.Set;

import db.entity.Fermata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"nomeLinea"})
public class PojoLinea {
	private String nomeLinea;
	private String direzione;
	private Set<Fermata> fermate;
}
