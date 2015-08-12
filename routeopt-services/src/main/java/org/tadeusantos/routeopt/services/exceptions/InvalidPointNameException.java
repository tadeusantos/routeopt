package org.tadeusantos.routeopt.services.exceptions;


/**
 * Exception raised when the name of a point is not right.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class InvalidPointNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895059842737408308L;

	public InvalidPointNameException() {
	}

	public InvalidPointNameException(String message) {
		super(message);
	}

	public InvalidPointNameException(Throwable cause) {
		super(cause);
	}

	public InvalidPointNameException(String message, Throwable cause) {
		super(message, cause);
	}


}
