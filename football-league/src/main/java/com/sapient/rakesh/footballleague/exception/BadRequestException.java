package com.sapient.rakesh.footballleague.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String ex) {
		super(ex);
	}

	public BadRequestException() {
	}
}
