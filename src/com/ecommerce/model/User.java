package com.ecommerce.model;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String address;
	private String role;
	private boolean isLoggedIn;
	private boolean isActive;

	// Constructors
	public User() {
		super();
		this.isLoggedIn = false;
		this.isActive = true;
	}

	public User(String firstName, String lastName, String username, String password, String email, String phone,
			String address, String role) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public User(String firstName, String lastName, String username, String password, String email, String phone,
			String address, String role, boolean isLoggedIn, boolean isActive) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.isLoggedIn = isLoggedIn;
		this.isActive = isActive;
	}

	public User(String username, String firstName, String lastName, String email, String phone, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	// Getter and Setter Methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", role=" + role + ", isLoggedIn=" + isLoggedIn + ", isActive=" + isActive + "]";
	}
}
