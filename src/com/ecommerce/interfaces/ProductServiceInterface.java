package com.ecommerce.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.model.Product;

public interface ProductServiceInterface {
	public List<Product> getAllProductsSorted() throws SQLException, ProductServiceException;

	public void addProduct(Product product) throws SQLException, ProductServiceException;

	public int checkProductQuantity(int productId) throws SQLException, ProductServiceException;

	Product getProductById(int productId) throws SQLException, ProductServiceException;
}
