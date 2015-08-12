package org.tadeusantos.routeopt.services.exceptions;


/**
 * Exception raised when a someone is trying to create a duplicated subroute document.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class DuplicatedSubrouteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2613584844854905498L;

	public DuplicatedSubrouteException() {
	}

	public DuplicatedSubrouteException(String message) {
		super(message);
	}

	public DuplicatedSubrouteException(Throwable cause) {
		super(cause);
	}

	public DuplicatedSubrouteException(String message, Throwable cause) {
		super(message, cause);
	}
}
