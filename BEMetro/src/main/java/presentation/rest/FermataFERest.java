package presentation.rest;

import org.hibernate.Session;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import presentation.pojo.PojoFermataFE;
import service.FermataFEService;

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
	public Response aggiornaFermata(PojoFermataFE fermataFE) {
		Response risposta = null;
		String risultato = null;

		try {
			risultato = FermataFEService.aggiornaFermataFE(fermataFE);
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@DELETE
	@Path("/rimuovi")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response rimuoviFermata(
			@QueryParam("riferimento") String riferimento,
			PojoFermataFE fermataFE) {
		Response risposta = null;
		String risultato = null;

		try {
			risultato = FermataFEService.rimuoviFermataFE(fermataFE,
					riferimento);
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
	public Response rimuoviFermata(@QueryParam("id") Integer id,
			@QueryParam("nome_linea") String nome_linea,
			@QueryParam("numero_fermata") Integer numero_fermata) {
		Response risposta = null;
		PojoFermataFE risultato = null;

		try {
			risultato = FermataFEService.leggiFermataFE(id, nome_linea,
					numero_fermata);
			risposta = Response.ok(risultato).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
