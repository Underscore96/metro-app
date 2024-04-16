package presentation.rest;

import java.util.List;

import org.hibernate.Session;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoUtente;
import service.UtenteService;

@Path("/login")
public class UtenteRest {
	Session sessione = null;

	@GET
	@Path("/test")
	public Response test() {
		return Response.ok().build();
	}

	@POST
	@Path("/crea")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response creaUtente(PojoUtente utente) {
		String risultato = null;
		Response risposta = null;
		System.out.println("utenteREST: " + utente);

		try {
			risultato = UtenteService.creaUtente(utente);
			risposta = Response.ok(risultato).build();

		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@POST
	@Path("/leggi")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response leggiFermata(PojoUtente utente) {
		Response risposta = null;
		PojoUtente utenteTrovato = null;

		try {
			utenteTrovato = UtenteService.leggiUtente(utente);
			risposta = Response.ok(utenteTrovato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@PUT
	@Path("/aggiorna")
	@Consumes("application/json")
	@Produces("application/json")
	public Response aggiornaUtente(PojoUtente utente) {
		PojoUtente utenteAggiornata = null;
		Response risposta = null;

		try {
			utenteAggiornata = UtenteService.aggiornaUtente(utente);
			risposta = Response.ok(utenteAggiornata).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@DELETE
	@Path("/cancella")
	@Consumes("application/json")
	@Produces("application/json")
	public Response cancellaUtente(PojoUtente utente) {
		String risultato = null;
		Response risposta = null;
		System.out.println("utenteREST: " + utente);

		try {
			risultato = UtenteService.cancellaUtente(utente);
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@GET
	@Path("tutte")
	@Produces("application/Json")
	public Response trovaTuttiIUtenti() {
		List<PojoUtente> listaUtenti = null;
		Response risposta = null;

		try {
			listaUtenti = UtenteService.trovaTuttiIUtenti();

			risposta = Response.ok(listaUtenti).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@GET
	@Path("attributi")
	@Produces("application/Json")
	public Response trovaConAttributi(@QueryParam("password") String password,
			@QueryParam("nome") String nome,
			@QueryParam("cognome") String cognome,
			@QueryParam("mail") String mail,
			@QueryParam("telefono") String telefono,
			@QueryParam("ruolo") String ruolo) {
		List<PojoUtente> listaUtenti = null;
		Response risultati = null;

		try {
			listaUtenti = UtenteService.trovaConAttributi(password, nome,
					cognome, mail, telefono, ruolo);

			risultati = Response.ok(listaUtenti).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risultati;
	}

	@GET
	@Produces("application/Json")
	public Response login(@QueryParam("nomeUtente") String nomeUtente,
			@QueryParam("password") String password) {
		List<PojoUtente> listaUtenti = null;
		Response risultati = null;

		try {
			listaUtenti = UtenteService.login(nomeUtente, password);

			risultati = Response.ok(listaUtenti).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risultati;
	}
}
