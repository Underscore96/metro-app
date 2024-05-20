package db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"idUtente"})
@Entity
@Table(name = "Utenti")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUtente", nullable = false, unique = true, length = 20)
	private String idUtente;

	@Column(name = "nomeUtente", length = 20, nullable = false, unique = true)
	private String nomeUtente;

	@Column(name = "password", length = 20, nullable = false, unique = true)
	private String password;

	@Column(name = "nome", length = 20, nullable = true, unique = false)
	private String nome;

	@Column(name = "cognome", length = 20, nullable = true, unique = false)
	private String cognome;

	@Column(name = "mail", length = 40, nullable = true, unique = false)
	private String mail;

	@Column(name = "telefono", length = 20, nullable = true, unique = false)
	private String telefono;

	@Column(name = "ruolo", length = 20, nullable = true, unique = false)
	private String ruolo;
}
