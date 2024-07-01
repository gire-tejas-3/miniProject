package com.ecommerce.interfaces;

import java.sql.SQLException;

import com.ecommerce.application.User;
import com.ecommerce.exceptions.UserServiceException;

public interface UserServiceInterface {
	public void registerUser(User user) throws SQLException, UserServiceException;
	public void loginUser() throws SQLException,UserServiceException;
}
