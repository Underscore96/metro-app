package test_unit_metro_app.crud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Utente;
import exception.CustomException;
import presentation.pojo.PojoUtente;
import service.UtenteService;
import service.builder.PojoUtenteBuilder;
import service.builder.UtenteBuilder;

@DisplayName("UTENTE SERVICE")
class UtenteServiceTest {
	private static PojoUtente utente = new PojoUtente();
	private static Utente ut = new Utente();

	@Test
	void When_CreaUtenteNullo_Expect_CustomException() {
		utente.setNomeUtente(null);

		assertThrows(CustomException.class, () -> {
			UtenteService.creaUtente(utente);
		});

		assertThrows(CustomException.class, () -> {
			UtenteService.creaUtente(null);
		});

		utente = new PojoUtente();
	}

	@Test
	void When_LeggiUtenteNullo_Expect_CustomException() {
		utente.setNomeUtente(null);

		assertThrows(CustomException.class, () -> {
			UtenteService.leggiUtente(utente);
		});

		assertThrows(CustomException.class, () -> {
			UtenteService.leggiUtente(null);
		});

		utente = new PojoUtente();
	}

	@Test
	void When_AggiornaUtenteNullo_Expect_CustomException() {
		utente.setNomeUtente(null);

		assertThrows(CustomException.class, () -> {
			UtenteService.aggiornaUtente(utente);
		});

		assertThrows(CustomException.class, () -> {
			UtenteService.aggiornaUtente(null);
		});

		utente = new PojoUtente();
	}

	@Test
	void When_CancellaUtenteNullo_Expect_CustomException() {
		utente.setNomeUtente(null);
		String nomeUtente = utente.getNomeUtente();

		assertThrows(CustomException.class, () -> {
			UtenteService.cancellaUtente(nomeUtente);
		});

		assertThrows(CustomException.class, () -> {
			UtenteService.cancellaUtente(null);
		});

		utente = new PojoUtente();
	}

	@Test
	void when_IdUtenteOrNomeUtenteNullo_Expect_CustomException() {
		ut.setIdUtente(null);
		ut.setNomeUtente(null);
		String valore1 = ut.getIdUtente();
		String valore2 = ut.getNomeUtente();

		assertThrows(NullPointerException.class, () -> {
			valore1.concat("a");
		});

		assertThrows(NullPointerException.class, () -> {
			valore2.concat("a");
		});

		ut = new Utente();
	}

	@SuppressWarnings("null")
	@Test
	void When_ListaUtentiNullaOrVuota_Expect_returnaNullo() {
		List<Utente> listaNulla = null;
		List<Utente> listaVuota = new ArrayList<>();
		List<Utente> membroNullo = new ArrayList<>();
		membroNullo.add(null);
		ut = membroNullo.get(0);

		assertThrows(NullPointerException.class, () -> {
			listaNulla.add(null);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			listaVuota.get(0);
		});
		assertThrows(NullPointerException.class, () -> {
			ut.setTelefono(null);
		});

		ut = new Utente();
	}

	@Test
	void Should_ConvertireAUtente_When_PassoPojoUtente() {
		Utente expected = new Utente("1000", "Fra123", "12345", "Francesco",
				"Mestre", "f.mestre@gmail.com", "348455445", "amministratore");

		PojoUtente pojoUtente = new PojoUtente("Fra123", "12345", "Francesco",
				"Mestre", "f.mestre@gmail.com", "348455445", "amministratore");

		Utente actual = new UtenteBuilder().setIdUtente("1000")
				.setNomeUtente(pojoUtente.getNomeUtente())
				.setPassword(pojoUtente.getPassword())
				.setNome(pojoUtente.getNome())
				.setCognome(pojoUtente.getCognome())
				.setTelefono(pojoUtente.getTelefono())
				.setMail(pojoUtente.getMail()).setRuolo(pojoUtente.getRuolo())
				.costruisci();

		assertThat(actual)
				.extracting("nomeUtente", "password", "nome", "cognome",
						"telefono", "mail", "ruolo")
				.containsExactly(expected.getNomeUtente(),
						expected.getPassword(), expected.getNome(),
						expected.getCognome(), expected.getTelefono(),
						expected.getMail(), expected.getRuolo());
	}

	@Test
	void Should_ConvertireAPojoUtente_When_PassoUtente() {
		PojoUtente expected = new PojoUtente("Fra123", "12345", "Francesco",
				"Mestre", "f.mestre@gmail.com", "348455445", "amministratore");

		Utente pojoUtente = new Utente("1000", "Fra123", "12345", "Francesco",
				"Mestre", "f.mestre@gmail.com", "348455445", "amministratore");

		PojoUtente actual = new PojoUtenteBuilder()
				.setNomeUtente(pojoUtente.getNomeUtente())
				.setPassword(pojoUtente.getPassword())
				.setNome(pojoUtente.getNome())
				.setCognome(pojoUtente.getCognome())
				.setTelefono(pojoUtente.getTelefono())
				.setMail(pojoUtente.getMail()).setRuolo(pojoUtente.getRuolo())
				.costruisci();

		assertThat(actual)
				.extracting("nomeUtente", "password", "nome", "cognome",
						"telefono", "mail", "ruolo")
				.containsExactly(expected.getNomeUtente(),
						expected.getPassword(), expected.getNome(),
						expected.getCognome(), expected.getTelefono(),
						expected.getMail(), expected.getRuolo());
	}
}
