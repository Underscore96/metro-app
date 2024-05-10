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
		String nomeLinea = linea.getNomeLinea();
		assertThrows(CustomException.class, () -> {
			LineaService.cancellaLinea(nomeLinea);
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

		lin = new Linea();
	}

	@SuppressWarnings("null")
	@Test
	void When_ListaFermateNullaOrVuota_Expect_returnaNullo() {
		List<Linea> listaNulla = null;
		List<Linea> listaVuota = new ArrayList<>();
		List<Linea> membroNullo = new ArrayList<>();
		membroNullo.add(null);
		lin = membroNullo.get(0);

		assertThrows(NullPointerException.class, () -> {
			listaNulla.add(null);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			listaVuota.get(0);
		});
		assertThrows(NullPointerException.class, () -> {
			lin.setDestinazione(null);
		});

		lin = new Linea();
	}

	@Test
	void Should_ConvertireALinea_When_PassoPojoFermata() {
		Linea expected = new Linea("30001", "verde", "Brignole");

		PojoLinea pojoLinea = new PojoLinea("verde", "Brignole");

		Linea actual = new LineaBuilder().setIdLinea("30001")
				.setNomeLinea(pojoLinea.getNomeLinea())
				.setDestinazione(pojoLinea.getDestinazione()).costruisci();

		assertThat(actual).extracting("idLinea", "nomeLinea", "destinazione")
				.containsExactly(expected.getIdLinea(), expected.getNomeLinea(),
						expected.getDestinazione());
	}

	@Test
	void Should_ConvertireAPojoLinea_When_PassoFermata() {
		PojoLinea expected = new PojoLinea("verde", "Brignole");

		Linea Linea = new Linea("30001", "verde", "Brignole");

		PojoLinea actual = new PojoLineaBuilder()
				.setNomeLinea(Linea.getNomeLinea())
				.setDestinazione(Linea.getDestinazione()).costruisci();

		assertThat(actual).extracting("nomeLinea", "destinazione").containsExactly(
				expected.getNomeLinea(), expected.getDestinazione());
	}
}
