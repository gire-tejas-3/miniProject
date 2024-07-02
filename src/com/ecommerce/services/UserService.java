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
			ps.close();
			con.close();
		}
	}
	// 2. User Login
	@Override
	public void loginUser(String username, String password) throws SQLException, UserServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			result = ps.executeQuery();

			if (result.next()) {
				PreparedStatement updatePs = con.prepareStatement("UPDATE users SET isloggedin=?, isactive=?");
				updatePs.setBoolean(1, true);
				updatePs.setBoolean(2, true);
				System.out.println("Login successful! Welcome, " + result.getString("firstName"));
				updatePs.close();
			} else {
				throw new UserServiceException("Invalid username or password.");
			}
		} catch (UserServiceException | SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			result.close();
			ps.close();
			con.close();
		}

	}

//	// Temporary
//	public static void main(String[] args) throws SQLException {
//		UserServiceInterface userService = new UserService();
//		Scanner sc = new Scanner(System.in);
////		System.out.println("Enter First Name: ");
////		String firstName = sc.next();
////
////		System.out.println("Enter Last Name: ");
////		String lastName = sc.next();
////
////		System.out.println("Enter Username: ");
////		String username = sc.next();
////
////		System.out.println("Enter Password: ");
////		String password = sc.next();
////
////		System.out.println("Enter Email: ");
////		String email = sc.next();
////
////		System.out.println("Enter Phone: ");
////		String phone = sc.next();
////
////		System.out.println("Enter Address: ");
////		String address = sc.next();
////
////		System.out.println("Enter Role(admin/user/guest): ");
////		String role = sc.next();
////
////		User user = new User(firstName, lastName, username, password, email, phone, address, role);
////		userService.registerUser(user);
//
//		System.out.println("\nNow, let's try logging in.");
//		System.out.println("Enter Username: ");
//		String username1 = sc.next();
//
//		System.out.println("Enter Password: ");
//		String password1 = sc.next();
//		userService.loginUser(username1, password1);
//	}
//
}
