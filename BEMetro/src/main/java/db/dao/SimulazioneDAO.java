package db.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import db.entity.Simulazione;
import db.util.HibernateUtil;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Response;

public class SimulazioneDAO {
	Session sessione = null;
	private static final String ID_SIMULAZIONE = "idSimulazione";

	public void creaSimulazione(Simulazione simulazione) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.persist(simulazione);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION,
							ID_SIMULAZIONE),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.UNIQUE_CONSTRAINT, ID_SIMULAZIONE),
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

	public List<Simulazione> leggiDaIdSimulazione(String idSimulazione) {
		List<Simulazione> result = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Simulazione> criteria;
		Root<Simulazione> root;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Simulazione.class);
			root = criteria.from(Simulazione.class);

			criteria.select(root).where(
					builder.equal(root.get(ID_SIMULAZIONE), idSimulazione));
			result = sessione.createQuery(criteria).getResultList();

		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return result;
	}

	public Simulazione aggiornaSimulazione(Simulazione simulazione) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			simulazione = sessione.merge(simulazione);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION,
							ID_SIMULAZIONE),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.UNIQUE_CONSTRAINT, ID_SIMULAZIONE),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return simulazione;
	}

	public void cancellaSimulazione(Simulazione simulazione) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.remove(simulazione);

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

	public List<Simulazione> trovaTutteLeSimulazioni() {
		List<Simulazione> result = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Simulazione> criteria;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Simulazione.class);
			criteria.from(Simulazione.class);

			result = sessione.createQuery(criteria).getResultList();
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return result;
	}
}
