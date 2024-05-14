package db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import db.entity.Fermata;
import db.util.HibernateUtil;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Response;

public class FermataDAO {
	Session sessione = null;
	private static final String NUMFERMATA = "numFermata";

	public void crea(Fermata fermata) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.persist(fermata);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NUMFERMATA),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NUMFERMATA),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null) {
				sessione.close();
			}
		}
	}

	public List<Fermata> leggiDaNumFermata(Integer numFermata) {
		List<Fermata> risultati = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Fermata> criteria;
		Root<Fermata> root;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Fermata.class);
			root = criteria.from(Fermata.class);

			criteria.select(root)
					.where(builder.equal(root.get(NUMFERMATA), numFermata));
			risultati = sessione.createQuery(criteria).getResultList();

			for (Fermata fermata : risultati) {
				Hibernate.initialize(fermata.getLinee());
			}

		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return risultati;
	}

	public Fermata aggiorna(Fermata fermata) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			Hibernate.initialize(fermata.getLinee());
			fermata = sessione.merge(fermata);

			sessione.getTransaction().commit();
		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NUMFERMATA),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NUMFERMATA),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return fermata;
	}

	public void cancella(Fermata fermata) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.remove(fermata);

			sessione.getTransaction().commit();

		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
	}

	public List<Fermata> trovaTutteLeFermate() {
		List<Fermata> risultati = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Fermata> criteria;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Fermata.class);
			criteria.from(Fermata.class);

			risultati = sessione.createQuery(criteria).getResultList();

			for (Fermata fermata : risultati) {
				Hibernate.initialize(fermata.getLinee());
			}

		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return risultati;
	}

	public List<Fermata> trovaConAttributi(String nome) {
		List<Fermata> risultati = new ArrayList<>();
		CriteriaBuilder criteriaBuilder;
		CriteriaQuery<Fermata> criteriaQuery;
		Root<Fermata> root;
		List<Predicate> predicates = new ArrayList<>();

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			criteriaBuilder = sessione.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(Fermata.class);
			root = criteriaQuery.from(Fermata.class);

			if (nome != null) {
				predicates.add(criteriaBuilder.equal(root.get("nome"), nome));
			}

			criteriaQuery.select(root)
					.where(predicates.toArray(new Predicate[0]));
			Query query = sessione.createQuery(criteriaQuery);

			risultati = castList(Fermata.class, query.getResultList());

			for (Fermata fermata : risultati) {
				Hibernate.initialize(fermata.getLinee());
			}

		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			sessione.close();
		}

		return risultati;
	}

	public static <T> List<T> castList(Class<? extends T> classType,
			Collection<?> myCollection) {
		List<T> r = new ArrayList<>(myCollection.size());
		for (Object member : myCollection)
			r.add(classType.cast(member));
		return r;
	}
}
