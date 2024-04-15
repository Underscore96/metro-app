package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import db.entity.Fermata;
import db.util.HibernateUtil;
import jakarta.persistence.EntityExistsException;

class FermataDAOTest {
	private LocalTime orarioPrevisto = LocalTime.of(8, 24);
	private LocalTime ritardo = LocalTime.of(8, 34);
	private final Fermata fermata = new Fermata("30000", 30000, "Brignole",
			orarioPrevisto, ritardo, "Piove", null);

	@Test
	void when_ArgumentNull_Expect_IllegalArgumentException() {
		Session sessione = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		sessione.beginTransaction();
		fermata.setIdFermata(null);
		fermata.setNumFermata(null);
		assertThrows(IllegalArgumentException.class, () -> {
			sessione.persist(null);
		});
		sessione.getTransaction().rollback();
	}

	@Test
	void when_PersistEntityAlreadyPresent_Expect_EntityExistsException() {

		Session sessione = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		sessione.getSessionFactory().getCurrentSession();
		sessione.beginTransaction();
		assertThrows(EntityExistsException.class, () -> {
			sessione.persist(fermata);
		});
		sessione.getTransaction().rollback();
	}
}
