package com.example.util.dao;

import java.util.logging.Logger;

public abstract class AbstractEntityDao<T extends Entity> implements
		EntityDao<T> {

	protected final Class<T> entityClass;
	protected final Logger logger;

	public AbstractEntityDao(Class<T> type) {
		this(type, null);
	}

	public AbstractEntityDao(Class<T> type, Logger logger) {
		this.entityClass = type;
		if (logger != null) {
			this.logger = logger;
		} else {
			final Class<?> clazz = this.getClass();
			this.logger = Logger.getLogger(clazz.getName());
		}
	}

	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	public String getEntityName() {
		final String name = this.entityClass.getSimpleName();
		return name;
	}

	public Logger getLogger() {
		return this.logger;
	}

}
