package com.ecommerce.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.UserServiceException;
import com.ecommerce.interfaces.UserServiceInterface;
import com.ecommerce.model.User;

public class UserService implements UserServiceInterface {

	private boolean userExists(Connection con, String username) throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=?");
		ps.setString(1, username);
		ResultSet result = ps.executeQuery();
		return result.next();
	}

	private boolean userValidate(String username, String password) throws UserServiceException {
		if (!username.matches("^[a-zA-Z0-9]{4,12}$")) {
			throw new UserServiceException(
					"Invalid username format. Username should consist of alphabets and digits only, "
							+ "with length between 4 to 12 characters.");
		}

		if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{6,16}$")) {
			throw new UserServiceException(
					"Invalid password format. Password should contain at least one digit, one lowercase letter, one uppercase letter, one special character, no spaces, "
							+ "and it must be 6-16 characters long.");
		}
		return true;
	}

	// 1. User Registration
	@Override
	public void registerUser(User user) throws SQLException, UserServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseConnection.getConnection();
			if (userExists(con, user.getUsername())) {
				throw new UserServiceException("User Already Present!");
			}

			if (userValidate(user.getUsername(), user.getPassword())) {
				System.out.println(ps);
				ps = con.prepareStatement(
						"INSERT INTO users (firstname, lastname, username, password, email, phone, address, role, isloggedin, isactive) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, user.getFirstName());
				ps.setString(2, user.getLastName());
				ps.setString(3, user.getUsername());
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getEmail());
				ps.setString(6, user.getPhone());
				ps.setString(7, user.getAddress());
				ps.setString(8, user.getRole());
				ps.setBoolean(9, user.isLoggedIn());
				ps.setBoolean(10, user.isActive());

				int rowsInserted = ps.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("A new user was inserted successfully!");
				} else {
					throw new UserServiceException("Failed to insert the user.");
				}
			}
		} catch (UserServiceException | SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (ps != null) {
				ps.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	// 2. User Login
	@Override
	public User loginUser(String username, String password) throws SQLException, UserServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		PreparedStatement updatePs = null;

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			result = ps.executeQuery();

			if (result.next()) {
				int userId = result.getInt("id");
//	            System.out.println("User ID: " + userId);
	            
				updatePs = con.prepareStatement("UPDATE users SET isloggedin=?, isactive=? WHERE username=?");
				updatePs.setBoolean(1, true);
				updatePs.setBoolean(2, true);
				updatePs.setString(3, result.getString("username"));
				updatePs.executeUpdate();
				System.out.println("Login successful! Welcome, " + result.getString("firstName"));
				System.out.println();
				return new User(userId, result.getString("firstName"), result.getString("lastName"),
						result.getString("username"), result.getString("password"), result.getString("email"),
						result.getString("phone"), result.getString("address"), result.getString("role"),
						result.getBoolean("isloggedin"), result.getBoolean("isactive"));
			} else {
				throw new UserServiceException("Invalid username or password.");
			}
		} catch (UserServiceException | SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (result != null)
				result.close();
			if (updatePs != null)
				updatePs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		}
		return null;
	}
}
