package com.ecommerce.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce.database.DatabaseConnection;

public class Product {
	private int id;
	private String name;
	private String description;
	private double price;
	private int quantity;

	// Constructors
	public Product() {

	}

	public Product(String name, String description, double price, int quantity) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public static void getInputProduct() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter how many product you want to add....");
		int n = scanner.nextInt();
		for (int i = 1; i <= n; i++) {
			System.out.println("Please enter Product name>>");
			String name = scanner.next();
			System.out.println("Product name is>> " + name);
			System.out.println("Please enter product description>>");
			String description = scanner.next();
			System.out.println("Product description>> " + description);
			System.out.println("Please enter price>>");
			double price = scanner.nextDouble();
			System.out.println("Price>> " + price);
			System.out.println("Please enter  quantity>>");
			int quantity = scanner.nextInt();
			System.out.println("Product quantity>> " + quantity);

			addProduct(name, description, price, quantity);
		}

	}

	public static void addProduct(String name, String description, double price, int quantity) throws SQLException {

		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("insert into products(PRODUCT_NAME,DESCRIPTION,PRICE,QUANTITY)values(?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setDouble(3, price);
			ps.setInt(4, quantity);

			int a = ps.executeUpdate();
			System.out.println("Inserted data successfully..." + a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ps.close();
		}

	}

	PreparedStatement ps = null;

	ResultSet rs = null;

	public void getProduct() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		try {
			ps = conn.prepareStatement("select * from products order by PRODUCT_NAME");
			rs = ps.executeQuery();
			while (rs.next()) {

				System.out.println("Product name>>" + rs.getString("PRODUCT_NAME"));
				System.out.println("Description>> " + rs.getString("DESCRIPTION"));
				System.out.println("Price>> " + rs.getDouble("PRICE"));
				System.out.println("Quantity>> " + rs.getInt("QUANTITY"));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			ps.close();
			rs.close();
		}
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", quantity=" + quantity + "]";
	}
}
