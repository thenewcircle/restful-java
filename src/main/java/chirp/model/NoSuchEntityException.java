package chirp.model;

/**
 * Exception thrown when attempting to reference an entity that does not exist.
 */
public class NoSuchEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchEntityException(String message) {
		super(message);
	}
}
