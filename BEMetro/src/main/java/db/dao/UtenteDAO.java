package db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import db.entity.Utente;
import db.util.HibernateUtil;
import exception.CustomException;
import exception.ErrorMessages;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Response;

public class UtenteDAO {
	Session sessione = null;
	private static final String NOMEUTENTE = "nomeUtente";

	public void crea(Utente utente) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.persist(utente);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NOMEUTENTE),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NOMEUTENTE),
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

	public List<Utente> leggiDaNomeUtente(String nomeUtente) {
		List<Utente> result = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Utente> criteria;
		Root<Utente> root;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Utente.class);
			root = criteria.from(Utente.class);

			criteria.select(root)
					.where(builder.equal(root.get(NOMEUTENTE), nomeUtente));
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

	public Utente aggiorna(Utente utente) {
		Utente risultato = null;
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			risultato = sessione.merge(utente);

			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NOMEUTENTE),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NOMEUTENTE),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
		return risultato;
	}

	public void cancella(Utente utente) {
		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			sessione.remove(utente);
			sessione.getTransaction().commit();

		} catch (PropertyValueException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(String
					.format(ErrorMessages.PROPERTY_VALUE_EXCEPTION, NOMEUTENTE),
					Response.Status.CONFLICT);
		} catch (GenericJDBCException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(
					String.format(ErrorMessages.UNIQUE_CONSTRAINT, NOMEUTENTE),
					Response.Status.CONFLICT);
		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (sessione != null)
				sessione.close();
		}
	}

	public List<Utente> trovaTuttiIUtenti() {
		List<Utente> result = new ArrayList<>();
		CriteriaBuilder builder;
		CriteriaQuery<Utente> criteria;

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			builder = sessione.getCriteriaBuilder();
			criteria = builder.createQuery(Utente.class);
			criteria.from(Utente.class);

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

	public List<Utente> trovaConAttributi(String password, String nome,
			String cognome, String mail, String telefono, String ruolo) {
		List<Utente> risultati = new ArrayList<>();
		CriteriaBuilder criteriaBuilder;
		CriteriaQuery<Utente> criteriaQuery;
		Root<Utente> root;
		List<Predicate> predicates = new ArrayList<>();

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			criteriaBuilder = sessione.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(Utente.class);
			root = criteriaQuery.from(Utente.class);

			if (password != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("password"), password));
			}

			if (cognome != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("cognome"), cognome));
			}

			if (nome != null) {
				predicates.add(criteriaBuilder.equal(root.get("nome"), nome));
			}

			if (mail != null) {
				predicates.add(criteriaBuilder.equal(root.get("mail"), mail));
			}

			if (telefono != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("telefono"), telefono));
			}

			if (ruolo != null) {
				predicates.add(criteriaBuilder.equal(root.get("ruolo"), ruolo));
			}

			criteriaQuery.select(root)
					.where(predicates.toArray(new Predicate[0]));
			Query query = sessione.createQuery(criteriaQuery);

			risultati = castList(Utente.class, query.getResultList());

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
	
	public List<Utente> login(String nomeUtente, String password) {
		List<Utente> risultati = new ArrayList<>();
		CriteriaBuilder criteriaBuilder;
		CriteriaQuery<Utente> criteriaQuery;
		Root<Utente> root;
		List<Predicate> predicates = new ArrayList<>();

		try {
			sessione = HibernateUtil.getSessionFactory().getCurrentSession();
			sessione.beginTransaction();

			criteriaBuilder = sessione.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(Utente.class);
			root = criteriaQuery.from(Utente.class);

			if (nomeUtente != null) {
				predicates.add(
						criteriaBuilder.equal(root.get(NOMEUTENTE), nomeUtente));
			}
			
			if (password != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("password"), password));
			}

			criteriaQuery.select(root)
					.where(predicates.toArray(new Predicate[0]));
			Query query = sessione.createQuery(criteriaQuery);

			risultati = castList(Utente.class, query.getResultList());

		} catch (HibernateException e) {
			sessione.getTransaction().rollback();
			throw new CustomException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			sessione.close();
		}

		return risultati;
	}
}
