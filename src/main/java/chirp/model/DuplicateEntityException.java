package chirp.model;

/**
 * Exception thrown when attempting to create an entity that already exists.
 */
public class DuplicateEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DuplicateEntityException(String message) {
		super(message);
	}
}
