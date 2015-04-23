package com.example.util.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

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

	public Session getHibernateSession() {
		final Object delegate = this.em.getDelegate();
		if (!(delegate instanceof Session)) {
			throw new UnsupportedOperationException(
					"updateWithoutSelect is only implemented for Hibernate-JPA");
		}
		final Session session = (Session) delegate;
		return session;
	}

	public T updateWithoutSelect(final T entity) {
		final Session hibernate = getHibernateSession();
		hibernate.saveOrUpdate(entity);
		return entity;
	}

	public List<T> queryByExample(final T exampleEntity,
			final boolean ignoreCase, final MatchMode matchMode,
			final Integer firstResult, final Integer maxResults,
			final Order sortOrder) {
		Example example = Example.create(exampleEntity);
		if (ignoreCase)
			example.ignoreCase();
		if (matchMode != null)
			example.enableLike(matchMode);
		return queryByExample(example, firstResult, maxResults, sortOrder);
	}

	public List<T> queryByExample(final Example exampleEntity,
			final Integer firstResult, final Integer maxResults,
			final Order sortOrder) {
		final Session hibernate = getHibernateSession();
		Class<T> entityType = this.getEntityClass();
		Criteria criteria = hibernate.createCriteria(entityType);
		criteria.add(exampleEntity);
		if (sortOrder != null)
			criteria.addOrder(sortOrder);
		if (firstResult != null)
			criteria.setFirstResult(firstResult);
		if (firstResult != null)
			criteria.setMaxResults(maxResults);
		@SuppressWarnings("unchecked")
		List<T> results = criteria.list();
		return results;
	}

}
