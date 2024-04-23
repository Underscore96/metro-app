package test_unit_metro_app.crud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		Integer numFermata = fermata.getNumFermata();
		assertThrows(CustomException.class, () -> {
			FermataService.cancellaFermata(numFermata);
		});

		assertThrows(CustomException.class, () -> {
			FermataService.cancellaFermata(null);
		});

		fermata = new PojoFermata();
	}

	@Test
	void When_IdFermataOrNumFermataNullo_Expect_CustomException() {
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
		Fermata expected = new Fermata("30001", 30000, "Brignole", "Piove",
				"presente");

		PojoFermata pojoFermata = new PojoFermata(30000, "Brignole", "Piove",
				"presente");

		Fermata actual = new FermataBuilder().setIdFermata("30001")
				.setNumFermata(pojoFermata.getNumFermata())
				.setNome(pojoFermata.getNome())
				.setPrevisioneMeteo(pojoFermata.getPrevisioneMeteo())
				.costruisci();

		assertThat(actual)
				.extracting("numFermata", "nome", "orarioPrevisto", "ritardo",
						"previsioneMeteo")
				.containsExactly(expected.getNumFermata(), expected.getNome(),
						expected.getPrevisioneMeteo());
	}

	@Test
	void Should_ConvertireAPojoFermata_When_Passo_Fermata() {
		PojoFermata expected = new PojoFermata(30000, "Brignole", "Piove",
				"presente");

		Fermata fermata1 = new Fermata("30000", 30000, "Brignole", "Piove",
				"presente");

		PojoFermata actual = new PojoFermataBuilder()
				.setNumFermata(fermata1.getNumFermata())
				.setNome(fermata1.getNome())
				.setPrevisioneMeteo(fermata1.getPrevisioneMeteo()).costruisci();

		assertThat(actual)
				.extracting("numFermata", "nome", "orarioPrevisto", "ritardo",
						"previsioneMeteo")
				.containsExactly(expected.getNumFermata(), expected.getNome(),
						expected.getPrevisioneMeteo());
	}
}
