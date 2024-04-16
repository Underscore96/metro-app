package presentation.rest;

import java.util.List;

import org.hibernate.Session;

import db.entity.Linea;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoFermata;
import presentation.pojo.PojoLinea;
import service.LineaService;

@Path("/linea")
public class LineaRest {
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
	public Response creaLinea(PojoLinea linea) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = LineaService.creaLinea(linea);
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
	public Response leggiLinea(PojoLinea linea) {
		Response risposta = null;
		PojoLinea lineaTrovata = null;

		try {
			lineaTrovata = LineaService.leggiLinea(linea);
			risposta = Response.ok(lineaTrovata).build();
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
	public Response aggiornaLinea(PojoLinea linea) {
		PojoLinea lineaAggiornata = null;
		Response risposta = null;

		try {
			lineaAggiornata = LineaService.aggiornaLinea(linea);
			risposta = Response.ok(lineaAggiornata).build();
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
	public Response cancellaLinea(PojoLinea linea) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = LineaService.cancellaLinea(linea);
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
	public Response trovaTutteLeLinee() {
		List<PojoLinea> listaLinee = null;
		Response risposta = null;

		try {
			listaLinee = LineaService.trovaTutteLeLinee();

			risposta = Response.ok(listaLinee).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@POST
	@Path("attributi")
	@Produces("application/Json")
	public Response trovaConAttributi(
			@QueryParam("direzione") String direzione) {
		List<PojoLinea> listaLinee = null;
		Response risultati = null;

		try {
			listaLinee = LineaService.trovaConAttributi(direzione);

			risultati = Response.ok(listaLinee).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risultati;
	}

	@GET
	@Path("/leggilinea")
	@Produces("Application/json")
	public Response leggiLinea(@QueryParam("nomeLinea") String nomeLinea) {
		Response risposta = null;
		List<PojoFermata> listaFermate = null;

		try {
			listaFermate = LineaService.leggiLineaConFermate(nomeLinea);
			risposta = Response.ok(listaFermate).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
