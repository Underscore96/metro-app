package test_unit_metro_app.crud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Linea;
import exception.CustomException;
import presentation.pojo.PojoLinea;
import service.LineaService;
import service.builder.LineaBuilder;
import service.builder.PojoLineaBuilder;

@DisplayName("LINEA SERVICE")
class LineaServiceTest {
	private static PojoLinea linea = new PojoLinea();
	private static Linea lin = new Linea();

	@Test
	void When_CreaLineaNulla_Expect_CustomException() {
		linea.setNomeLinea(null);

		assertThrows(CustomException.class, () -> {
			LineaService.creaLinea(linea);
		});

		assertThrows(CustomException.class, () -> {
			LineaService.creaLinea(null);
		});

		linea = new PojoLinea();
	}

	@Test
	void When_LeggiLineaNulla_Expect_CustomException() {
		linea.setNomeLinea(null);

		assertThrows(CustomException.class, () -> {
			LineaService.leggiLinea(linea);
		});

		assertThrows(CustomException.class, () -> {
			LineaService.leggiLinea(null);
		});

		linea = new PojoLinea();
	}

	@Test
	void When_LeggiLineaConFermataNulla_Expect_CustomException() {
		linea.setNomeLinea(null);

		assertThrows(CustomException.class, () -> {
			LineaService.leggiLineaConFermate(null);
		});

		assertThrows(CustomException.class, () -> {
			LineaService.leggiLineaConFermate(null);
		});

		linea = new PojoLinea();
	}

	@Test
	void When_AggiornaLineaNulla_Expect_CustomException() {
		linea.setNomeLinea(null);

		assertThrows(CustomException.class, () -> {
			LineaService.aggiornaLinea(linea);
		});

		assertThrows(CustomException.class, () -> {
			LineaService.aggiornaLinea(null);
		});

		linea = new PojoLinea();
	}

	@Test
	void When_CancellaLineaNulla_Expect_CustomException() {
		linea.setNomeLinea(null);

		assertThrows(CustomException.class, () -> {
			LineaService.cancellaLinea(linea);
		});

		assertThrows(CustomException.class, () -> {
			LineaService.cancellaLinea(null);
		});

		linea = new PojoLinea();
	}

	@Test
	void When_IdLineaOrNomeLineaNullo_Expect_CustomException() {
		lin.setIdLinea(null);
		lin.setNomeLinea(null);
		String valore1 = lin.getIdLinea();
		String valore2 = lin.getNomeLinea();

		assertThrows(NullPointerException.class, () -> {
			valore1.concat("a");
		});
		assertThrows(NullPointerException.class, () -> {
			valore2.concat("a");
		});
	}

	@SuppressWarnings("null")
	@Test
	void When_ListaFermateNullaOrVuota_Expect_returnaNullo() {
		List<Linea> nullList = null;
		List<Linea> emptyList = new ArrayList<>();
		List<Linea> nullMember = new ArrayList<>();
		nullMember.add(null);
		lin = nullMember.get(0);

		assertThrows(NullPointerException.class, () -> {
			nullList.add(null);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			emptyList.get(0);
		});
		assertThrows(NullPointerException.class, () -> {
			lin.setDirezione(null);
		});

		lin = new Linea();
	}

	@Test
	void Should_convertireALinea_When_Passo_PojoFermata() {
		Linea expected = new Linea("30001", "verde", "Brignole");

		PojoLinea pojoLinea = new PojoLinea("verde", "Brignole");

		Linea actual = new LineaBuilder().setIdLinea("30001")
				.setNomeLinea(pojoLinea.getNomeLinea())
				.setDirezione(pojoLinea.getDirezione()).costruisci();

		assertThat(actual).extracting("idLinea", "nomeLinea", "direzione")
				.containsExactly(expected.getIdLinea(), expected.getNomeLinea(),
						expected.getDirezione());
	}

	@Test
	void Should_convertireAPojoLinea_When_Passo_Fermata() {
		PojoLinea expected = new PojoLinea("verde", "Brignole");

		Linea Linea = new Linea("30001", "verde", "Brignole");

		PojoLinea actual = new PojoLineaBuilder()
				.setNomeLinea(Linea.getNomeLinea())
				.setDirezione(Linea.getDirezione()).costruisci();

		assertThat(actual).extracting("nomeLinea", "direzione").containsExactly(
				expected.getNomeLinea(), expected.getDirezione());
	}
}
