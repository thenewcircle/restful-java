package com.example.util.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;

public class JpaEntityDao<T extends Entity> extends AbstractEntityDao<T>
		implements EntityDao<T> {

	@PersistenceContext
	protected EntityManager em;

	public JpaEntityDao(Class<T> type) {
		this(type, null, null);
	}

	public JpaEntityDao(Class<T> type, EntityManager em) {
		this(type, em, null);
	}

	public JpaEntityDao(Class<T> type, Logger logger) {
		this(type, null, logger);
	}

	public JpaEntityDao(Class<T> type, EntityManager em, Logger logger) {
		super(type, logger);
		this.em = em;
	}

	public EntityType<T> getEntityType() {
		final EntityType<T> model = this.em.getMetamodel().entity(
				this.entityClass);
		return model;
	}

	@Override
	public String getEntityName() {
		final EntityType<T> model = this.getEntityType();
		final String name = model.getName();
		return name;
	}

	public T getReference(final long entityId) {
		final T result = this.em.getReference(this.entityClass, entityId);
		return result;
	}

	@Override
	public T findExactlyOne(final long entityId) throws NoResultException {
		final T result = this.findOneOrNull(entityId);
		if (result == null) {
			final String entityName = this.getEntityName();
			final String msg = String.format("Unable to find %s with id=%d",
					entityName, entityId);
			throw new NoResultException(msg);
		}
		return result;
	}

	public T findExactlyOne(final String query, Object... queryParams)
			throws NoResultException, NonUniqueResultException {
		final TypedQuery<T> q = this.em.createQuery(query, this.entityClass);
		for (int i = 0; i < queryParams.length; i++) {
			q.setParameter(i + 1, queryParams[i]);
		}
		final T result = q.getSingleResult();
		return result;
	}

	public T findExactlyOne(CriteriaQuery<T> query) throws NoResultException,
			NonUniqueResultException {
		final TypedQuery<T> q = this.em.createQuery(query);
		final T result = q.getSingleResult();
		return result;
	}

	@Override
	public T findOneOrNull(final long entityId) {
		final T result = this.em.find(this.entityClass, entityId);
		return result;
	}

	public T findOneOrNull(final String query, Object... queryParams)
			throws NonUniqueResultException {
		try {
			final T result = this.findExactlyOne(query, queryParams);
			return result;
		} catch (final NoResultException e) {
			return null;
		}
	}

	public T findOneOrNull(CriteriaQuery<T> query) {
		try {
			final T result = this.findExactlyOne(query);
			return result;
		} catch (final NoResultException e) {
			return null;
		}
	}

	public List<T> findList(final String query, Object... queryParams) {
		final TypedQuery<T> q = this.em.createQuery(query, this.entityClass);
		for (int i = 0; i < queryParams.length; i++) {
			q.setParameter(i + 1, queryParams[i]);
		}
		final List<T> result = q.getResultList();
		return result;
	}

	public List<T> findList(CriteriaQuery<T> query) {
		final TypedQuery<T> q = this.em.createQuery(query);
		final List<T> result = q.getResultList();
		return result;
	}

	@Override
	public List<T> findAll() {
		final CriteriaQuery<T> criteriaQuery = this.em.getCriteriaBuilder()
				.createQuery(this.entityClass);
		criteriaQuery.from(this.entityClass);
		final TypedQuery<T> query = this.em.createQuery(criteriaQuery);
		final List<T> results = query.getResultList();
		return results;
	}

	public CriteriaQuery<T> newCriteriaQuery() {
		final CriteriaQuery<T> criteriaQuery = this.em.getCriteriaBuilder()
				.createQuery(this.entityClass);
		return criteriaQuery;
	}

	@Override
	public void save(final T entity) throws EntityExistsException {
		this.em.persist(entity);
	}

	@Override
	public T update(final T entity) {
		final T result = this.em.merge(entity);
		return result;
	}

	@Override
	public T saveOrUpdate(final T entity) {
		final Object id = entity.getId();
		if (id == null) {
			this.save(entity);
			return entity;
		} else {
			return this.update(entity);
		}
	}

	@Override
	public void delete(final T entity) {
		this.em.remove(entity);
	}

	@Override
	public void delete(final long entityId) throws EntityNotFoundException {
		final T entity = this.em.getReference(this.entityClass, entityId);
		this.em.remove(entity);
	}

	@Override
	public boolean deleteIfExists(final long entityId) throws EntityNotFoundException {
		try {
			final T entity = this.em.getReference(this.entityClass, entityId);
			this.em.remove(entity);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

}
