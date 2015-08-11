package com.example.chirp.app.kernel.exceptions;

/**
 * Exception thrown when attempting to load an entity that does not exist.
 */
public class NoSuchEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchEntityException() {
	}
}
