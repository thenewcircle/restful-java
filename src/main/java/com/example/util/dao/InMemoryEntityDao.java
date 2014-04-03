package com.example.util.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class InMemoryEntityDao<T extends Entity> extends AbstractEntityDao<T> {

	protected final Map<Long, T> entities;
	protected final Object lock;
	private long nextId = 1;

	public InMemoryEntityDao(Class<T> type) {
		super(type);
		entities = new TreeMap<Long, T>();
		lock = entities;
	}

	@Override
	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	@Override
	public Logger getLogger() {
		return this.logger;
	}

	@Override
	public T findExactlyOne(final long entityId) throws NoResultException {
		synchronized (this.lock) {
			final T result = this.findOneOrNull(entityId);
			if (result == null) {
				final String entityName = this.getEntityName();
				final String msg = String.format(
						"Unable to find %s with id=%d", entityName, entityId);
				throw new NoResultException(msg);
			}
			return result;
		}
	}

	@Override
	public T findOneOrNull(final long entityId) {
		synchronized (this.lock) {
			final T result = this.entities.get(entityId);
			return result;
		}
	}

	@Override
	public List<T> findAll() {
		synchronized (this.lock) {
			final Collection<T> values = this.entities.values();
			final List<T> results = new ArrayList<T>(values);
			return results;
		}
	}

	@Override
	public void save(final T entity) throws EntityExistsException {
		synchronized (this.lock) {
			Long id = entity.getId();
			if (id == null) {
				id = this.generateNextId();
				entity.setId(id);
			} else if (this.entities.containsKey(id)) {
				final String entityName = this.getEntityName();
				final String msg = String.format(
						"Entity %s[id=%d] already exists in database.",
						entityName, id);
				throw new EntityExistsException(msg);
			}
			this.entities.put(id, entity);
		}
	}

	@Override
	public T update(final T entity) {
		final Long id = entity.getId();
		synchronized (this.lock) {
			if (id == null)
				throw new IllegalArgumentException("Entity has a null id.");
			if (!this.entities.containsKey(id)) {
				final String entityName = this.getEntityName();
				final String msg = String.format(
						"Entity %s[%d] does not exist in database.",
						entityName, id);
				throw new IllegalArgumentException(msg);
			}
			this.entities.put(id, entity);
			return entity;
		}
	}

	@Override
	public T saveOrUpdate(final T entity) {
		final Long id = entity.getId();
		if (id == null) {
			save(entity);
		} else {
			synchronized (this.lock) {
				this.entities.put(id, entity);
			}
		}
		return entity;
	}

	@Override
	public void delete(final T entity) {
		final Long id = entity.getId();
		synchronized (this.lock) {
			this.entities.remove(id);
		}
	}

	@Override
	public void delete(final long entityId) throws NoResultException {
		synchronized (this.lock) {
			T old = this.entities.remove(entityId);
			if (old == null) {
				final String entityName = this.getEntityName();
				final String msg = String.format(
						"Entity %s[%d] does not exist in database.",
						entityName, entityId);
				throw new EntityNotFoundException(msg);
			}
		}
	}

	@Override
	public boolean deleteIfExists(final long entityId) {
		synchronized (this.lock) {
			T old = this.entities.remove(entityId);
			boolean deleted = (old != null);
			return deleted;
		}
	}

	protected long generateNextId() {
		synchronized (this.lock) {
			while (this.nextId > 0) {
				final long id = this.nextId;
				this.nextId++;
				final boolean exists = this.entities.containsKey(id);
				if (!exists) {
					return id;
				}
			}
			throw new IllegalStateException(
					"Exceeded Long.MAX_VALUE for nextId generation.");
		}
	}
	
	/**
	 * Inject initial data set.
	 */
	public void setInitialTestData(Collection<T> testData) {
		synchronized (this.lock) {
			for (T entity : testData) {
				saveOrUpdate(entity);
			}
		}
	}

}
