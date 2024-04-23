package db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	public Utente() {
	}

	public Utente(String idUtente, String nomeUtente, String password,
			String nome, String cognome, String mail, String telefono,
			String ruolo) {
		super();
		this.idUtente = idUtente;
		this.nomeUtente = nomeUtente;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.telefono = telefono;
		this.ruolo = ruolo;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	@Override
	public String toString() {
		return "Utente [idUtente=" + idUtente + ", nomeUtente=" + nomeUtente
				+ ", password=" + password + ", nome=" + nome + ", cognome="
				+ cognome + ", mail=" + mail + ", telefono=" + telefono
				+ ", ruolo=" + ruolo + "]";
	}
}
