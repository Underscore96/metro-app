package service.builder;

import db.entity.Utente;

public class UtenteBuilder {
	private String idUtente;
	private String nomeUtente;
	private String password;
	private String nome;
	private String cognome;
	private String mail;
	private String telefono;
	private String ruolo;

	public UtenteBuilder setIdUtente(String idUtente) {
		this.idUtente = idUtente;
		return this;
	}

	public UtenteBuilder setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
		return this;
	}

	public UtenteBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public UtenteBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public UtenteBuilder setCognome(String cognome) {
		this.cognome = cognome;
		return this;
	}

	public UtenteBuilder setMail(String mail) {
		this.mail = mail;
		return this;
	}

	public UtenteBuilder setTelefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	public UtenteBuilder setRuolo(String ruolo) {
		this.ruolo = ruolo;
		return this;
	}

	public Utente costruisci() {
		Utente newUtente = new Utente();

		newUtente.setIdUtente(idUtente);
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
