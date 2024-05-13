package presentation.rest;

import java.util.List;

import org.hibernate.Session;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoFermataFE;
import presentation.pojo.PojoFermataFESkyTram;
import service.FermataFEService;
import service.FermataFEServiceSkyTram;

@Path("/fermataFE")
public class FermataFERest {
	Session sessione = null;

	@GET
	@Path("/test")
	public Response test() {
		return Response.ok().build();
	}

	@POST
	@Path("/aggiorna")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response aggiornaFermata(PojoFermataFESkyTram fermataFE) {
		Response risposta = null;
		String risultato = null;

		try {
			risultato = FermataFEServiceSkyTram.aggiornaFermataFE(fermataFE);
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	// @DELETE
	// @Path("/rimuovi")
	// @Consumes("Application/json")
	// @Produces("Application/json")
	// public Response rimuoviFermata(
	// @QueryParam("riferimento") String riferimento,
	// PojoFermataFE fermataFE) {
	// Response risposta = null;
	// String risultato = null;
	//
	// try {
	// risultato = FermataFEService.rimuoviFermataFE(fermataFE,
	// riferimento);
	// risposta = Response.ok(risultato).build();
	// } finally {
	// if (sessione != null && sessione.isOpen()) {
	// sessione.close();
	// }
	// }
	// return risposta;
	// }

	@GET
	@Path("/leggi")
	@Produces("Application/json")
	public Response rimuoviFermata(@QueryParam("id") Integer id,
			@QueryParam("numFermata") Integer numFermata) {
		Response risposta = null;
		PojoFermataFE risultato = null;

		try {
			risultato = FermataFEService.leggiFermataFE(id, numFermata);
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@GET
	@Path("/tutte")
	@Produces("Application/json")
	public Response leggiTutteLeFermateFE() {
		Response risposta = null;
		List<PojoFermataFESkyTram> risultato = null;

		try {
			risultato = FermataFEServiceSkyTram.leggiTutteLeFermateFE();
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
