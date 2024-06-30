package com.ecommerce.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce.database.DatabaseConnection;

public class Product {
	private int id;
	private String name;
	private String description;
	private double price;
	private int quantity;

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

	public Product(int id, String name, String description, double price, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	public static void getInputProduct() throws SQLException {
		Scanner scanner = new Scanner(System.in);

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
		
		
		getProduct(name,description,price,quantity);

	}

	public static void getProduct(String name, String description, double price, int quantity) throws SQLException {


		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("insert into products(PRODUCT_NAME,DESCRIPTION,PRICE,QUANTITY)values(?,?,?,?)");
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

}
