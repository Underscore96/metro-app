package test_unit_metro_app.crud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.entity.Linea;
import db.util.HibernateUtil;
import jakarta.persistence.EntityExistsException;

@DisplayName("LINEA DAO")
class LineaDAOTest {
	private final Linea linea = new Linea("30000", "30000", 1, "Brignole");

	@Test
	void when_ArgumentoNullo_Expect_IllegalArgumentException() {
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
	void when_CreoLineaGiaPresente_Expect_EntityExistsException() {

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
