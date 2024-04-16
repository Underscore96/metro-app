package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Fermata;
import db.util.HibernateUtil;
import jakarta.persistence.EntityExistsException;

@DisplayName("FERMATA DAO")
class FermataDAOTest {
	private LocalTime orarioPrevisto = LocalTime.of(8, 24);
	private LocalTime ritardo = LocalTime.of(8, 34);
	private final Fermata fermata = new Fermata("30000", 30000, "Brignole",
			orarioPrevisto, ritardo, "Piove");

	@Test
	void when_ArgumentoNullo_Expect_IllegalArgumentException() {
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
	void when_CreoFermataGiaPresente_Expect_EntityExistsException() {

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
