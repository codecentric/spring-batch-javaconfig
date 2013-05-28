package de.codecentric.batch.domain;

/**
 * @author Tobias Flohre
 */
public class UnknownGenderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownGenderException() {
		super();
	}

	public UnknownGenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownGenderException(String message) {
		super(message);
	}

	public UnknownGenderException(Throwable cause) {
		super(cause);
	}

	
}
