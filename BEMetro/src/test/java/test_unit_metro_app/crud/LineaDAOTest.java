package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import db.entity.Linea;
import db.util.HibernateUtil;
import jakarta.persistence.EntityExistsException;

class LineaDAOTest {
	private final Linea linea = new Linea("30000", "30000", "Brignole");

	@Test
	void when_ArgumentNull_Expect_IllegalArgumentException() {
		Session sessione = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		sessione.beginTransaction();
		linea.setIdLinea("30000");
		linea.setNomeLinea("30000");
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
			sessione.persist(linea);
		});
		sessione.getTransaction().rollback();
	}
}
