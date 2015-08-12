package org.tadeusantos.routeopt.services.exceptions;

/**
 * Exception raised when a someone is trying to execute an operation over an missing point.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class PointNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6035773662070468747L;

	public PointNotFoundException() {
	}

	public PointNotFoundException(String message) {
		super(message);
	}

	public PointNotFoundException(Throwable cause) {
		super(cause);
	}

	public PointNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
