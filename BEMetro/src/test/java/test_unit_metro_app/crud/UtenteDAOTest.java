package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Utente;
import db.util.HibernateUtil;
import jakarta.persistence.EntityExistsException;

@DisplayName("UTENTE DAO")
class UtenteDAOTest {
	private final Utente utente = new Utente("3001", "nomeUtente", "password",
			"nome", "cognome", "telefono", "mail", "ruolo");

	@Test
	void when_ArgumentoNullo_Expect_IllegalArgumentException() {
		Session sessione = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		sessione.beginTransaction();
		utente.setIdUtente("30000");
		utente.setNomeUtente("30000");
		assertThrows(IllegalArgumentException.class, () -> {
			sessione.persist(null);
		});
		sessione.getTransaction().rollback();
	}

	@Test
	void when_CreoUtenteGiaPresente_Expect_EntityExistsException() {

		Session sessione = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		sessione.getSessionFactory().getCurrentSession();
		sessione.beginTransaction();
		assertThrows(EntityExistsException.class, () -> {
			sessione.persist(utente);
		});
		sessione.getTransaction().rollback();
	}
}
