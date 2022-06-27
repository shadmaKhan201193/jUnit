package com.itl.exceptions;

public class NGException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String error;

	//private Throwable t;

	public NGException() {
	}

	public NGException(String err) {
		this.error = err;
	}

	public NGException(String err, Throwable t) {
		super(t);
		this.error = err;
		//this.t = t;
	}

	public String getError() {
		return error;
	}

}
