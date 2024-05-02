package presentation.rest;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import db.dao.SimulazioneDAO;
import db.entity.Simulazione;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import simulatore.ClasseMain;

@Path("/simulazione")
public class SimulazioneRest {
	static SimulazioneDAO simulazioneDAO = new SimulazioneDAO();
	private static ScheduledExecutorService scheduler = null;
	private Semaphore semaphore = new Semaphore(1);
	private static Boolean running = null;
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
	public Response creaSimulazione(Simulazione simulazione) {
		Response risposta = null;

		try {
			simulazioneDAO.creaSimulazione(simulazione);
			risposta = Response.ok(simulazione).build();

		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}

	@GET
	@Path("/tutte")
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public Response leggiSimulazioni() {
		List<Simulazione> listaSim = simulazioneDAO.trovaTutteLeSimulazioni();
		return Response.ok(listaSim).build();
	}

	@GET
	@Path("/inizia")
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public String startTask(@QueryParam("id") String id) {
		Simulazione sim = null;
		List<Simulazione> listaSimulazioni = simulazioneDAO
				.leggiDaIdSimulazione(id);

		if (!listaSimulazioni.isEmpty()) {
			sim = listaSimulazioni.get(0);
			running = sim.getStatoEsecuzione();
		}

		try {
			semaphore.acquire();

			// controllo lo stato di persistenza di running
			if (sim != null) {
				if (Boolean.TRUE.equals(sim.getStatoEsecuzione())) {
					return "La simulazione è già in esecuzione.";
				} else {
					sim.setStatoEsecuzione(true);
					simulazioneDAO.aggiornaSimulazione(sim);
					running = sim.getStatoEsecuzione();
				}
			} else {
				Simulazione simCreata = new Simulazione();
				simCreata.setStatoEsecuzione(true);
				simulazioneDAO.creaSimulazione(simCreata);
				running = simCreata.getStatoEsecuzione();
			}

			// se l'applicativo non è già attivo viene eseguito l'applicativo
			if (scheduler == null || scheduler.isShutdown())
				scheduler = Executors.newScheduledThreadPool(1);

			Runnable task = new Runnable() {
				@Override
				public void run() {
					if (Boolean.FALSE.equals(running)) {
						scheduler.shutdown();
						return;
					}
					ClasseMain.updateData("1");
				}
			};

			scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
			return "Simulazione inizializzata correttamente.";

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return "Errore durante l'avvio della simulazione: "
					+ e.getMessage();

		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
	}

	@GET
	@Path("/interrompi")
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public String stopTask(@QueryParam("id") String id) {
		Simulazione sim = null;
		List<Simulazione> listaSimulazioni = simulazioneDAO
				.leggiDaIdSimulazione(id);

		if (!listaSimulazioni.isEmpty()) {
			sim = listaSimulazioni.get(0);
			running = sim.getStatoEsecuzione();
		}

		try {
			semaphore.acquire();
			if (sim != null && running) {
				sim.setStatoEsecuzione(false);
				simulazioneDAO.aggiornaSimulazione(sim);
				running = sim.getStatoEsecuzione();

				ScheduledExecutorService currentScheduler = scheduler;
				if (currentScheduler != null && !currentScheduler.isShutdown())
					scheduler.shutdownNow();

				return "Simulazione interrotta.";

			} else {
				return "La simulazione è già interrotta o non è mai stata eseguita.";
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return "Errore durante l'interruzione della simulazione: "
					+ e.getMessage();

		} finally {
			semaphore.release();
		}
	}

	@PUT
	@Path("/aggiorna")
	@Consumes("application/json")
	@Produces("application/json")
	public Response aggiornaLinea(Simulazione simulazione) {
		Simulazione simulazioneAggiornata = null;
		Response risposta = null;

		try {
			simulazioneAggiornata = simulazioneDAO
					.aggiornaSimulazione(simulazione);
			risposta = Response.ok(simulazioneAggiornata).build();
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
	public Response cancellaLinea(Simulazione simulazione) {
		Response risposta = null;

		try {
			simulazioneDAO.cancellaSimulazione(simulazione);
			risposta = Response.ok().build();
		} finally {
			if (sessione != null && sessione.isOpen()) {
				sessione.close();
			}
		}
		return risposta;
	}
}
