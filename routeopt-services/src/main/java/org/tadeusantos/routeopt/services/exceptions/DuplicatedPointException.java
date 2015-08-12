package org.tadeusantos.routeopt.services.exceptions;

/**
 * Exception raised when a someone is trying to create a duplicated point document.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class DuplicatedPointException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2613584844854905498L;

	public DuplicatedPointException() {
	}

	public DuplicatedPointException(String message) {
		super(message);
	}

	public DuplicatedPointException(Throwable cause) {
		super(cause);
	}

	public DuplicatedPointException(String message, Throwable cause) {
		super(message, cause);
	}
}
