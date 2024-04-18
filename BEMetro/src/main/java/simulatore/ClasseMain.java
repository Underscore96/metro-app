package simulatore;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import presentation.pojo.PojoFermata;
import service.FermataService;

public class ClasseMain {
	private static Random random = new Random();

	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors
				.newScheduledThreadPool(1);

		Runnable task = new Runnable() {
			@Override
			public void run() {
				updateData();
			}
		};

		scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
	}
	private static void updateData() {
		List<PojoFermata> elencoFermate = FermataService.trovaTutteLeFermate();

		LocalTime orarioPrevisto = generaOrarioRandom();
		Long minutiRitardo = random.nextLong(30);
		for (PojoFermata fermata : elencoFermate) {
			orarioPrevisto = orarioPrevisto.plusMinutes(5);
			fermata.setOrarioPrevisto(orarioPrevisto);
			fermata.setRitardo(orarioPrevisto.plusMinutes(minutiRitardo));
			fermata.setPrevisioneMeteo(previsioneMeteo());

			FermataService.aggiornaFermata(fermata);
		}
	}

	private static LocalTime generaOrarioRandom() {
		int ore = random.nextInt(24);
		int minuti = random.nextInt(60);
		LocalTime orarioRandom = LocalTime.of(ore, minuti);
		return orarioRandom;
	}

	private static String previsioneMeteo() {
		Integer meteo = random.nextInt(3);

		switch (meteo) {
			case 1 :
				return "pioggia";
			case 2 :
				return "sole";
			case 3 :
				return "nuvolo";
			default :
				return "sole";
		}
	}
}
