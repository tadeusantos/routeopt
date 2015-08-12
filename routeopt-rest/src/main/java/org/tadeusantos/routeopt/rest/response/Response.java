package org.tadeusantos.routeopt.rest.response;

/**
 * Shared API Response
 * 
 * A common JSON response will have Status, Message and Result payload.
 * 
 * @author Tadeu Santos
 *
 */
public class Response {
	
	private ResponseStatus status;
	private String message;
	private Object result;

	public Response() {
	}
	
	public Response(ResponseStatus status, String message, Object result) {
		super();
		this.status = status;
		this.message = message;
		this.result = result;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
