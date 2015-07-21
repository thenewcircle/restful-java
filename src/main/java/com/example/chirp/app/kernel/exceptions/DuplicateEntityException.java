package com.example.chirp.app.kernel.exceptions;

/**
 * Exception thrown when attempting to create an entity that already exists.
 */
public class DuplicateEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateEntityException() {
	}
}
