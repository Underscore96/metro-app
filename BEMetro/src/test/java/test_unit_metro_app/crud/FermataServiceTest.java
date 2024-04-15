package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Fermata;
import exception.CustomException;
import presentation.pojo.PojoFermata;
import service.FermataService;
import service.builder.FermataBuilder;
import service.builder.PojoFermataBuilder;

@DisplayName("FERMATA SERVICE")
class FermataServiceTest {
	private static PojoFermata fermata = new PojoFermata();
	private static Fermata fer = new Fermata();

	@Test
	void When_CreaFermataNulla_Expect_CustomException() {
		fermata.setNumFermata(null);

		assertThrows(CustomException.class, () -> {
			FermataService.creaFermata(fermata);
		});

		assertThrows(CustomException.class, () -> {
			FermataService.creaFermata(null);
		});

		fermata = new PojoFermata();
	}

	@Test
	void When_LeggiFermataNulla_Expect_CustomException() {
		fermata.setNumFermata(null);

		assertThrows(CustomException.class, () -> {
			FermataService.leggiFermata(fermata);
		});

		assertThrows(CustomException.class, () -> {
			FermataService.leggiFermata(null);
		});

		fermata = new PojoFermata();
	}

	@Test
	void When_AggiornaFermataNulla_Expect_CustomException() {
		fermata.setNumFermata(null);

		assertThrows(CustomException.class, () -> {
			FermataService.aggiornaFermata(fermata);
		});

		assertThrows(CustomException.class, () -> {
			FermataService.aggiornaFermata(null);
		});

		fermata = new PojoFermata();
	}

	@Test
	void When_CancellaFermataNulla_Expect_CustomException() {
		fermata.setNumFermata(null);

		assertThrows(CustomException.class, () -> {
			FermataService.cancellaFermata(fermata);
		});

		assertThrows(CustomException.class, () -> {
			FermataService.cancellaFermata(null);
		});

		fermata = new PojoFermata();
	}

	@Test
	void When_IdFermataONumFermataNullo_Expect_CustomException() {
		fer.setIdFermata(null);
		fer.setNumFermata(null);
		String valore1 = fer.getIdFermata();
		Integer valore2 = fer.getNumFermata();

		assertThrows(NullPointerException.class, () -> {
			valore1.concat("a");
		});
		assertThrows(NullPointerException.class, () -> {
			valore2.floatValue();
		});
	}

	@SuppressWarnings("null")
	@Test
	void When_ListaFermateNullaOrVuota_Expect_returnaNullo() {
		List<Fermata> nullList = null;
		List<Fermata> emptyList = new ArrayList<>();
		List<Fermata> nullMember = new ArrayList<>();
		nullMember.add(null);
		fer = nullMember.get(0);

		assertThrows(NullPointerException.class, () -> {
			nullList.add(null);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			emptyList.get(0);
		});
		assertThrows(NullPointerException.class, () -> {
			fer.setNome(null);
		});

		fer = new Fermata();
	}

	@Test
	void Should_ConvertireAFermata_When_Passo_PojoFermata() {
		PojoFermata pojoFermata = new PojoFermata(30000, "Brignole",
				LocalTime.of(8, 24), LocalTime.of(8, 34), "Piove", null);

		Fermata expected = new Fermata("30001", 30000, "Brignole",
				LocalTime.of(8, 24), LocalTime.of(8, 34), "Piove", null);

		Fermata actual = new FermataBuilder().setIdFermata("30001")
				.setNumFermata(pojoFermata.getNumFermata())
				.setNome(pojoFermata.getNome())
				.setOrarioPrevisto(pojoFermata.getOrarioPrevisto())
				.setRitardo(pojoFermata.getRitardo())
				.setPrevisioneMeteo(pojoFermata.getPrevisioneMeteo())
				.setLinea(pojoFermata.getLinea()).costruisci();

		assertEquals(expected, actual);
	}

	@Test
	void Should_ConvertireAPojoFermata_When_Passo_Fermata() {
		Fermata fermata1 = new Fermata("30000", 30000, "Brignole",
				LocalTime.of(8, 24), LocalTime.of(8, 34), "Piove", null);

		PojoFermata expected = new PojoFermata(30000, "Brignole",
				LocalTime.of(8, 24), LocalTime.of(8, 34), "Piove", null);

		PojoFermata actual = new PojoFermataBuilder()
				.setNumFermata(fermata1.getNumFermata())
				.setNome(fermata1.getNome())
				.setOrarioPrevisto(fermata1.getOrarioPrevisto())
				.setRitardo(fermata1.getRitardo())
				.setPrevisioneMeteo(fermata1.getPrevisioneMeteo())
				.setLinea(fermata1.getLinea()).costruisci();

		assertEquals(expected, actual);
	}
}
