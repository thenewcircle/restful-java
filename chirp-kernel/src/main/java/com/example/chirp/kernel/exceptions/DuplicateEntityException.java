package com.example.chirp.kernel.exceptions;

import com.example.chirp.kernel.User;

/**
 * Exception thrown when attempting to create an entity that already exists.
 */
public class DuplicateEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String entityId;
	
	public DuplicateEntityException(String entityId) {
		super("The record \"" + entityId + "\" already exists.");
		this.entityId = entityId;
	}
	
	public String getEntityId() {
		return entityId;
	}
	
}
