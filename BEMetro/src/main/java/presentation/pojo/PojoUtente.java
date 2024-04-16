package presentation.pojo;

public class PojoUtente {
	private String nomeUtente;
	private String password;
	private String nome;
	private String cognome;
	private String mail;
	private String telefono;
	private String ruolo;

	public PojoUtente() {
		super();
	}

	public PojoUtente(String nomeUtente, String password, String nome,
			String cognome, String mail, String telefono, String ruolo) {
		super();
		this.nomeUtente = nomeUtente;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.telefono = telefono;
		this.ruolo = ruolo;
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

	public void setPassword(String password) {
		this.password = password;
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
		return "PojoUtente [nomeUtente=" + nomeUtente + ", password=" + password
				+ ", nome=" + nome + ", cognome=" + cognome + ", mail=" + mail
				+ ", telefono=" + telefono + ", ruolo=" + ruolo + "]";
	}
}
