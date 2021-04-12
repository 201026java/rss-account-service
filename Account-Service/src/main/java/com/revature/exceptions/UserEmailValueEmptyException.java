package com.revature.exceptions;

public class UserEmailValueEmptyException extends RuntimeException {

	private static final long serialVersionUID = 5884912525241574269L;

	public UserEmailValueEmptyException(String message) {
		super(message);
	}
}
