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
import presentation.pojo.PojoFermata;
import service.FermataService;

@Path("/fermata")
public class FermataRest {
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
	public Response creaFermata(PojoFermata fermata) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = FermataService.creaFermata(fermata);
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
	public Response leggiFermata(PojoFermata fermata) {
		Response risposta = null;
		PojoFermata fermataTrovata = null;

		try {
			fermataTrovata = FermataService.leggiFermata(fermata);
			risposta = Response.ok(fermataTrovata).build();
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
	public Response aggiornaFermata(PojoFermata fermata) {
		PojoFermata fermataAggiornata = null;
		Response risposta = null;

		try {
			fermataAggiornata = FermataService.aggiornaFermata(fermata);
			risposta = Response.ok(fermataAggiornata).build();
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
	public Response cancellaFermata(
			@QueryParam("numFermata") Integer numFermata) {
		String risultato = null;
		Response risposta = null;

		try {
			risultato = FermataService.cancellaFermata(numFermata);
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
	public Response trovaTutteLeFermate() {
		List<PojoFermata> listaFermate = null;
		Response risposta = null;

		try {
			listaFermate = FermataService.trovaTutteLeFermate();

			risposta = Response.ok(listaFermate).build();
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
	public Response trovaConAttributi(@QueryParam("nome") String nome,
			@QueryParam("previsioneMeteo") String previsioneMeteo) {
		List<PojoFermata> listaFermate = null;
		Response risultati = null;

		try {
			listaFermate = FermataService.trovaConAttributi(nome,
					previsioneMeteo);

			risultati = Response.ok(listaFermate).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risultati;
	}

	@PUT
	@Path("/rinomina")
	@Consumes("application/json")
	@Produces("application/json")
	public Response rinominaFermata(
			@QueryParam("numFermata") Integer numFermata,
			@QueryParam("nomeFermata") String nomeFermata) {
		PojoFermata fermataAggiornata = null;
		Response risposta = null;

		try {
			fermataAggiornata = FermataService.rinominaFermata(numFermata,
					nomeFermata);
			risposta = Response.ok(fermataAggiornata).build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
