package presentation.rest;

import java.util.List;

import org.hibernate.Session;

import db.entity.Corsa;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import service.CorsaService;

@Path("/Corsa")
public class CorsaRest {
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
	public Response creaCorsa(Corsa corsa) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = CorsaService.creaCorsa(corsa);
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
	public Response leggiCorsa(Corsa corsa) {
		Response risposta = null;
		Corsa corsaTrovata = null;

		try {
			corsaTrovata = CorsaService.leggiCorsa(corsa);
			risposta = Response.ok(corsaTrovata).build();
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
	public Response aggiornaCorsa(Corsa corsa) {
		Corsa corsaAggiornato = null;
		Response risposta = null;

		try {
			corsaAggiornato = CorsaService.aggiornaCorsa(corsa);
			risposta = Response.ok(corsaAggiornato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@DELETE
	@Path("/cancella")
	@Produces("application/json")
	public Response cancellaCorsa(@QueryParam("numCorsa") Integer numCorsa) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = CorsaService.cancellaCorsa(numCorsa);
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
	public Response trovaTutteLeCorse() {
		List<Corsa> listaCorse = null;
		Response risposta = null;

		try {
			listaCorse = CorsaService.trovaTutteLeCorse();

			risposta = Response.ok(listaCorse).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
