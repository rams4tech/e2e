package org.thinkadv.enxita.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static Session session;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session getSession() {
		if (session == null) {
			session = getSessionFactory().openSession();
		}
		return session;
	}

	public static void close() {
		if (session != null) {
			session.close();
			session = null;
			sessionFactory.close();
			sessionFactory = null;
		}
	}

	public static Transaction getTransaction() {
		Transaction transaction = getSession().getTransaction();
		if (transaction == null) {
			transaction = getSession().beginTransaction();
		} else if(!transaction.isActive()) {
			transaction.begin();
		}
		return transaction;
	}
	
	public static void commitTransaction() {
		getTransaction().commit();
	}

}
