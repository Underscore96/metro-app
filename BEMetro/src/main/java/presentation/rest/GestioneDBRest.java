package presentation.rest;

import org.hibernate.Session;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import service.InizializzaDb;

@Path("/gestioneDB")
public class GestioneDBRest {
	Session sessione = null;

	@GET
	@Path("/test")
	public Response test() {
		return Response.ok().build();
	}

	@GET
	@Path("/inizializza")
	@Produces("application/json")
	public Response dbInitFromJson() {
		Response risposta = null;
		Object[] risultato;
		try {
			InizializzaDb inizializzatore = new InizializzaDb();
			risultato = inizializzatore.inizDb();

			risposta = Response.ok(risultato).build();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return risposta;
	}
}
