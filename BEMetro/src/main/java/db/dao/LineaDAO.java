package db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import db.entity.Fermata;
import db.entity.Linea;
import db.util.HibernateUtil;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Response;

public class LineaDAO {
	Session sessione = null;
	private static final String NOMELINEA = "nomeLinea";

	public void crea(Linea linea) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.persist(linea);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NOMELINEA),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NOMELINEA),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null) {
				sessione.close();
			}
		}
	}

	public List<Linea> leggiDaNomeLinea(String nomeLinea) {
		List<Linea> result = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Linea> criteria;
		Root<Linea> root;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Linea.class);
			root = criteria.from(Linea.class);

			criteria.select(root)
					.where(builder.equal(root.get(NOMELINEA), nomeLinea));
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

	public Linea aggiorna(Linea linea) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			linea = sessione.merge(linea);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NOMELINEA),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NOMELINEA),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return linea;
	}

	public void cancella(Linea linea) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.remove(linea);
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

	public List<Linea> trovaTutteLeLinee() {
		List<Linea> result = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Linea> criteria;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Linea.class);
			criteria.from(Linea.class);

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

	public List<Linea> trovaConAttributi(String direzione) {
		List<Linea> risultati = new ArrayList<>();
		CriteriaBuilder criteriaBuilder;
		CriteriaQuery<Linea> criteriaQuery;
		Root<Linea> root;
		List<Predicate> predicates = new ArrayList<>();

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			criteriaBuilder = sessione.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(Linea.class);
			root = criteriaQuery.from(Linea.class);

			if (direzione != null) {
				predicates.add(criteriaBuilder.equal(root.get("direzione"),
						direzione));
			}

			criteriaQuery.select(root)
					.where(predicates.toArray(new Predicate[0]));
			Query query = sessione.createQuery(criteriaQuery);

			risultati = castList(Linea.class, query.getResultList());

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

	public List<Fermata> leggiLineaConFermate(String nomeLinea) {
//		List<Fermata> result = new ArrayList<>();
//		CriteriaBuilder builder;
//		CriteriaQuery<Fermata> criteria;
//		Root<Fermata> root;
		List<Fermata> result = new ArrayList<>();

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			String sqlQuery = "SELECT f.* FROM Fermata f JOIN Linea l ON f.idLinea = l.idLinea WHERE l.nomeLinea = :nomeLinea";
	        result = sessione.createNativeQuery(sqlQuery, Fermata.class)
	                        .setParameter("nomeLinea", nomeLinea)
	                        .getResultList();
			
//			builder = sessione.getCriteriaBuilder();
//			criteria = builder.createQuery(Fermata.class);
//			root = criteria.from(Fermata.class);
//
//			Join<Fermata, Linea> lineaJoin = root.join("linee", JoinType.INNER);
//
//			criteria.select(root).where(
//					builder.equal(lineaJoin.get("NOMELINEA"), nomeLinea));
			
//			result = sessione.createQuery(criteria).getResultList();
			System.out.println("STAMPO LINEA: " + result);
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
