package chirp.model;

/**
 * Exception thrown when the requested entity does not exist.
 */
public class NoSuchEntityException extends RuntimeException {
	private static final long serialVersionUID = -9077947327027173050L;

	public NoSuchEntityException(String message) {
		super(message);
	}
}
