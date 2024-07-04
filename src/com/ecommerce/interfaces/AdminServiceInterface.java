package com.ecommerce.interfaces;

import java.sql.SQLException;

import com.ecommerce.exceptions.UserServiceException;

public interface AdminServiceInterface {
	public void viewRegisteredUsers() throws SQLException, UserServiceException;

	public void viewUserHistory(String username) throws SQLException, UserServiceException;

	public double calculateBill() throws SQLException;

	public void displayBill();
}
