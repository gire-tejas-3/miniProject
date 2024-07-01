package com.ecommerce.exceptions;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = -6545916590450619821L;

	public UserServiceException(String message) {
		super(message);
	}
}
