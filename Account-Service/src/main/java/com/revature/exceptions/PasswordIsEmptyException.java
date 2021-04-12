package com.revature.exceptions;

public class PasswordIsEmptyException extends RuntimeException {

	private static final long serialVersionUID = -7555236886052701784L;

	public PasswordIsEmptyException(String message) {
		super(message);
	}

}
