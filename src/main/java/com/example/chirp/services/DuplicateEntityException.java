package com.example.chirp.services;

import com.example.util.BaseRuntimeException;

/**
 * Exception thrown when attempting to create an entity that already exists.
 */
public class DuplicateEntityException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;
	private Class<?> entityType;
	private Object entityId;

	public DuplicateEntityException(Class<?> type, Object id) {
		super("%s[id=\"%s\"] already exists.", type.getSimpleName(), id);
		this.entityType = type;
		this.entityId = id;
	}

	public Class<?> getEntityType() {
		return entityType;
	}

	public Object getEntityId() {
		return entityId;
	}
}
