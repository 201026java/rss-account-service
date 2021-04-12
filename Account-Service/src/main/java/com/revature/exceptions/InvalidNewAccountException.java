package com.revature.exceptions;

public class InvalidNewAccountException extends RuntimeException {

	private static final long serialVersionUID = -5730795792864660991L;

	public InvalidNewAccountException(String message) {
		super(message);
	}
}
