package com.example.chirp.services;

import com.example.util.BaseRuntimeException;

/**
 * Exception thrown when the requested entity does not exist.
 */
public class NoSuchEntityException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;
	private final Class<?> entityType;
	private final String entityTypeName;
	private final Object entityId;

	public NoSuchEntityException(String entityType, Object entityId) {
		super("%s[id=\"%s\"] not found.", entityType, entityId);
		this.entityType = null;
		this.entityTypeName = entityType;
		this.entityId = entityId;
	}

	public NoSuchEntityException(Class<?> entityType, Object entityId) {
		super(String.format("%s[id=\"%s\"] not found.",
				entityType.getSimpleName(), entityId));
		this.entityType = entityType;
		this.entityTypeName = entityType.getSimpleName();
		this.entityId = entityId;
	}

	public Class<?> getEntityType() {
		return entityType;
	}

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public Object getEntityId() {
		return entityId;
	}

}
