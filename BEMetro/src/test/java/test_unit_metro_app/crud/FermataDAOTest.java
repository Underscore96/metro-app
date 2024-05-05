package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Fermata;
import db.util.HibernateUtil;
import jakarta.persistence.EntityExistsException;

@DisplayName("FERMATA DAO")
class FermataDAOTest {
	private final Fermata fermata = new Fermata("30000", 30000, "Brignole",
			"2024-05-05T08:10", "Piove", "presente");

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
