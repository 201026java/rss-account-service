package com.revature.exceptions;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7499856651910714628L;

	public AccountNotFoundException(String message) {
		super(message);
	}

}
