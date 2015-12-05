package org.devolunteers.cfg2016.backend.exceptions;

public class JSONExceptionResponse {
	private int statusCode;
	private String message;

	public JSONExceptionResponse() {
		super();
	}

	public JSONExceptionResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
