package chirp.model;

/**
 * Exception thrown when attempting to create an entity that already exists.
 */
public class DuplicateEntityException extends RuntimeException {
	
	public DuplicateEntityException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;
}
