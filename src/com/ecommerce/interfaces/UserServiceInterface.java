package com.ecommerce.interfaces;

import java.sql.SQLException;

import com.ecommerce.exceptions.UserServiceException;
import com.ecommerce.model.User;

public interface UserServiceInterface {
	public void registerUser(User user) throws SQLException, UserServiceException;

	public User loginUser(String username, String password) throws SQLException, UserServiceException;
}
