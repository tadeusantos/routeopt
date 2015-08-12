package org.tadeusantos.routeopt.services.exceptions;


/**
 * Exception raised when a someone is trying to execute an operation over an missing route.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class SubrouteNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6035773662070468747L;

	public SubrouteNotFoundException() {
	}

	public SubrouteNotFoundException(String message) {
		super(message);
	}

	public SubrouteNotFoundException(Throwable cause) {
		super(cause);
	}

	public SubrouteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
