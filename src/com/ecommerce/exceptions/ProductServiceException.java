package com.ecommerce.exceptions;

public class ProductServiceException extends RuntimeException {
	private static final long serialVersionUID = 4982820243876365632L;

	public ProductServiceException(String message) {
		super(message);
	}
}
