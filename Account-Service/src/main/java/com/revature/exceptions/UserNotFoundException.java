package com.revature.exceptions;

public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -587891821695844145L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
