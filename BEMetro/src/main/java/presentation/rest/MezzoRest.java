package presentation.rest;

import java.util.List;

import org.hibernate.Session;

import db.entity.TestBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoMezzo;
import service.MezzoService;

@Path("/mezzo")
public class MezzoRest {
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
	public Response creaMezzo(PojoMezzo mezzo) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = MezzoService.creaMezzo(mezzo);
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
	public Response leggiMezzo(PojoMezzo mezzo) {
		Response risposta = null;
		PojoMezzo mezzoTrovato = null;

		try {
			mezzoTrovato = MezzoService.leggiMezzo(mezzo);
			risposta = Response.ok(mezzoTrovato).build();
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
	public Response aggiornaMezzo(PojoMezzo mezzo) {
		PojoMezzo mezzoAggiornato = null;
		Response risposta = null;

		try {
			mezzoAggiornato = MezzoService.aggiornaMezzo(mezzo);
			risposta = Response.ok(mezzoAggiornato).build();
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
	public Response cancellaMezzo(@QueryParam("numMezzo") Integer numMezzo) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = MezzoService.cancellaMezzo(numMezzo);
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@GET
	@Path("tutti")
	@Produces("application/Json")
	public Response trovaTuttiIMezzi() {
		List<PojoMezzo> listaMezzi = null;
		Response risposta = null;

		try {
			listaMezzi = MezzoService.trovaTuttiIMezzi();

			risposta = Response.ok(listaMezzi).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
	@POST
	@Path("testesempio")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response creaTestEsempioService(TestBuilder test) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = MezzoService.creaTestBuilderService(test);
			risposta = Response.ok(risultato).build();

		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
