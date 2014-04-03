package com.example.chirp.model;

/**
 * Exception thrown when attempting to create an entity that already exists.
 */
public class DuplicateEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Class<?> entityType;
	private Object entityId;

	public DuplicateEntityException(Class<?> type, Object id) {
		super(String.format("%s[id=\"%s\"] already exists.", type.getSimpleName(), id));
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
