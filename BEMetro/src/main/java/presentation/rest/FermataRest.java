package presentation.rest;

import org.hibernate.Session;

import db.entity.Fermata;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/fermata")
public class FermataRest {
	Session session = null;
	
	@GET
	@Path("/test")
	public Response test() {
		return Response.ok().build();
	}
	
	@POST
	@Path("/create")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response createConfRule(Fermata fermata) {
		String result = null;
		Response resp = null;

		try {
			result = FermataService.createFermata(fermata);
			resp = Response.ok(result).build();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return resp;
	}
	
	@POST
	@Path("/read")
	@Consumes("Application/json")
	@Produces("Application/json")
	public Response readConfRule(Fermata fermata) {
		Response resp = null;
		Fermata confRuleFound = null;

		try {
			confRuleFound = FermataService.readFermata(fermata);
			resp = Response.ok(confRuleFound).build();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return resp;
	}
}
