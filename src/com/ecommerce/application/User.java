package com.ecommerce.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.UserServiceException;
import com.ecommerce.interfaces.UserServiceInterface;;

public class User implements UserServiceInterface {
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String address;

	public User() {

	}

	// Constructor to initialize fields
	public User(int id, String firstName, String lastName, String username, String password, String email, String phone,
			String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	@Override
	public void registerUser(User user) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
		ResultSet result = ps.executeQuery();
	}

	@Override
	public void loginUser() throws SQLException {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter First Name: ");
		String firstName = sc.next();
		
		System.out.println("Enter Last Name: ");
		String lastName = sc.next();
	}

}
