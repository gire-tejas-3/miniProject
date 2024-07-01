package com.ecommerce.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


import com.ecommerce.database.DatabaseConnection;



public class AddProductTest {
	
	public static void main(String[] args) {
		try {
			getInputProduct();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
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

}
