package db.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private HibernateUtil() {
	}

	private static final Logger LOG = LogManager.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		SessionFactory sessionFactory = null;
		Configuration conf;
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			conf = new Configuration().configure();
			sessionFactory = conf.buildSessionFactory();
			return sessionFactory;
		} catch (Exception e) {
			LOG.error("SessionFactory creation failed.", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
