package com.ecommerce.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.model.Product;

public interface GuestServiceInterface {
	public List<Product> getAllProducts() throws SQLException, ProductServiceException;
}
