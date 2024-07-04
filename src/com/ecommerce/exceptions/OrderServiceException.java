package com.ecommerce.exceptions;

public class OrderServiceException extends RuntimeException {
	private static final long serialVersionUID = 4982820243876365632L;

	public OrderServiceException(String message) {
		super(message);
	}
}
