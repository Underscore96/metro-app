package service.builder;

import presentation.pojo.PojoUtente;

public class PojoUtenteBuilder {
	private String nomeUtente;
	private String password;
	private String nome;
	private String cognome;
	private String mail;
	private String telefono;
	private String ruolo;

	public PojoUtenteBuilder setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
		return this;
	}

	public PojoUtenteBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public PojoUtenteBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public PojoUtenteBuilder setCognome(String cognome) {
		this.cognome = cognome;
		return this;
	}

	public PojoUtenteBuilder setMail(String mail) {
		this.mail = mail;
		return this;
	}

	public PojoUtenteBuilder setTelefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	public PojoUtenteBuilder setRuolo(String ruolo) {
		this.ruolo = ruolo;
		return this;
	}

	public PojoUtente costruisci() {
		PojoUtente newUtente = new PojoUtente();

		newUtente.setNomeUtente(nomeUtente);
		newUtente.setPassword(password);
		newUtente.setNome(nome);
		newUtente.setCognome(cognome);
		newUtente.setMail(mail);
		newUtente.setTelefono(telefono);
		newUtente.setRuolo(ruolo);

		return newUtente;
	}
}
