package com.example.util.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Session;

public class HibernateJpaEntityDao<T extends Entity> extends JpaEntityDao<T> {

	public HibernateJpaEntityDao(Class<T> type) {
		super(type);
	}

	public HibernateJpaEntityDao(Class<T> type, EntityManager em) {
		super(type, em);
	}

	public HibernateJpaEntityDao(Class<T> type, Logger logger) {
		super(type, logger);
	}

	public HibernateJpaEntityDao(Class<T> type, EntityManager em, Logger logger) {
		super(type, em, logger);
	}

	public T updateWithoutSelect(final T entity) {
		final Object delegate = this.em.getDelegate();
		if (!(delegate instanceof Session)) {
			throw new UnsupportedOperationException(
					"updateWithoutSelect is only implemented for Hibernate-JPA");
		}
		final Session session = (Session) delegate;
		session.saveOrUpdate(entity);
		return entity;
	}

}
