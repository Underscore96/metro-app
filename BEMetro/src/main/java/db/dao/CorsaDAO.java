package db.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import db.entity.Corsa;
import db.util.HibernateUtil;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Response;

public class CorsaDAO {
	Session sessione = null;
	private static final String NUMCORSA = "numCorsa";

	public void crea(Corsa corsa) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.persist(corsa);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NUMCORSA),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NUMCORSA),
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

	public List<Corsa> leggiDaNumCorsa(Integer numCorsa) {
		List<Corsa> risultati = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Corsa> criteria;
		Root<Corsa> root;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Corsa.class);
			root = criteria.from(Corsa.class);

			criteria.select(root)
					.where(builder.equal(root.get(NUMCORSA), numCorsa));
			risultati = sessione.createQuery(criteria).getResultList();

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

	public Corsa aggiorna(Corsa corsa) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			corsa = sessione.merge(corsa);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NUMCORSA),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NUMCORSA),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return corsa;
	}

	public void cancella(Corsa corsa) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.remove(corsa);

			sessione.getTransaction().commit();

		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
	}

	public List<Corsa> trovaTutteLeCorse() {
		List<Corsa> risultati = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Corsa> criteria;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Corsa.class);
			criteria.from(Corsa.class);

			risultati = sessione.createQuery(criteria).getResultList();

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
}
