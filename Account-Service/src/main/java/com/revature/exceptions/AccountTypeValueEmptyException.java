package com.revature.exceptions;

public class AccountTypeValueEmptyException extends RuntimeException {

	private static final long serialVersionUID = -1267385461631494927L;

	public AccountTypeValueEmptyException(String message) {
		super(message);
	}
}
