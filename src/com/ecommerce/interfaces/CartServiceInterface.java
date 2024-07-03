package com.ecommerce.interfaces;

import java.sql.SQLException;

import com.ecommerce.model.*;

public interface CartServiceInterface {
	public void addToCart(Product product, int quantity);

	public void viewCart();

	public Order checkout(User user) throws SQLException;

	public double calculateBill();

	public void displayBill();
}
