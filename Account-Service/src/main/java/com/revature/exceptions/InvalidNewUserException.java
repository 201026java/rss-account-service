package com.revature.exceptions;

public class InvalidNewUserException extends RuntimeException {

	private static final long serialVersionUID = -572081354914520485L;
	
	public InvalidNewUserException(String message) {
		super(message);
	}

}
