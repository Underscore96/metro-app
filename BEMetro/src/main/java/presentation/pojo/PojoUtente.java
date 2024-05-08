package presentation.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PojoUtente {
	private String nomeUtente;
	private String password;
	private String nome;
	private String cognome;
	private String mail;
	private String telefono;
	private String ruolo;
}
