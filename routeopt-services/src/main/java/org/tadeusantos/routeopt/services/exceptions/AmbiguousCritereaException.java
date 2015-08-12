package org.tadeusantos.routeopt.services.exceptions;

/**
 * Exception raised when a find method return more than the expect number of MongoDB documents.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class AmbiguousCritereaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 249100146954870730L;

	public AmbiguousCritereaException() {
	}

	public AmbiguousCritereaException(String name) {
		super(name);
	}

	public AmbiguousCritereaException(Throwable cause) {
		super(cause);
	}

	public AmbiguousCritereaException(String name, Throwable cause) {
		super(name, cause);
	}

}
