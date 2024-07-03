package com.ecommerce.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.interfaces.CartServiceInterface;
import com.ecommerce.model.*;

public class CartServices implements CartServiceInterface {
	private List<OrderDetails> cart = new ArrayList<OrderDetails>();
	private static final double TAX_RATE = 9d / 100d; // 9% tax

	// 4. Buy Product
	@Override
	public void addToCart(Product product, int quantity) {
		cart.add(new OrderDetails(0, product.getId(), quantity, quantity * product.getPrice()));
		System.out.println("Item Added to Cart!");
	}

	// 5. View Cart
	@Override
	public void viewCart() {
		for (OrderDetails item : cart) {
			System.out.println(item);
		}
	}

	//8. Calculate Bill
	@Override
	public double calculateBill() {
		double totalAmount = 0;
		for (OrderDetails item : cart) {
			totalAmount += item.getPrice();
		}
		return totalAmount;
	}

	
	// 9. Display amount to End User
	@Override
	public void displayBill() {
		double totalAmount = calculateBill();
		double taxAmount = totalAmount * TAX_RATE;
		double finalAmount = totalAmount + taxAmount;
		System.out.println();
		System.out.println("***************************");
		System.out.println("Amount: " + totalAmount);
		System.out.println("Tax: " + taxAmount);
		System.out.println("Total Amount: " + finalAmount);
		System.out.println("***************************");
		System.out.println();
	}

	// 6. Purchase the item
	@Override
	public Order checkout(User user) throws SQLException {
		Connection con = null;
		PreparedStatement orderPs = null;
		PreparedStatement orderDetailPs = null;
		ResultSet orderResult = null;

		double totalAmount = 0;
		for (OrderDetails item : cart) {
			totalAmount += item.getPrice();
		}

		try {
			con = DatabaseConnection.getConnection();
			// insert into order table
			orderPs = con.prepareStatement("INSERT INTO orders (user_id, total_amount) VALUES(?, ?)");
			orderPs.setInt(1, user.getId());
			orderPs.setDouble(2, totalAmount);
			orderPs.executeUpdate();

			// generated order id
			orderResult = orderPs.getGeneratedKeys();

			int orderId = -1;
			if (orderResult.next()) {
				orderId = orderResult.getInt(1);
			} else {
				throw new SQLException("Failed to retrieve order ID");
			}

			for (OrderDetails item : cart) {
				item.setOrderId(orderId);
				orderDetailPs = con.prepareStatement(
						"INSERT INTO orderDetails (order_id, product_id, quantity, price) VALUES (?,?,?,?)");
				orderDetailPs.setInt(1, item.getOrderId());
				orderDetailPs.setInt(1, item.getProductId());
				orderDetailPs.setInt(1, item.getQuantity());
				orderDetailPs.setDouble(1, item.getPrice());
				orderDetailPs.executeUpdate();
			}
			cart.clear();
			return new Order(orderId, user.getId(), totalAmount, new Date());

		} catch (SQLException | ProductServiceException e) {
			System.err.println(e.getMessage());
		} finally {
			orderResult.close();
			orderDetailPs.close();
			orderPs.close();
			con.close();
		}
		return null;
	}
}
