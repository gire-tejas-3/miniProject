package com.ecommerce.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Statement;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.interfaces.CartServiceInterface;
import com.ecommerce.model.*;
import com.ecommerce.services.*;

public class CartServices implements CartServiceInterface {
	private List<OrderDetails> cart = new ArrayList<OrderDetails>();
	ProductService productService = new ProductService();

	// 4. Buy Product
	@Override
	public void addToCart(Product product, int quantity) {
		try {
			cart.add(new OrderDetails(0, product.getId(), quantity, quantity * product.getPrice()));
			System.out.println("Item added to cart!");
		} catch (Exception e) {
			System.out.println("Failed to add item to cart: " + e.getMessage());
		}
	}

	// 5. View Cart
	@Override
	public void viewCart() {
		for (OrderDetails item : cart) {
			System.out.println(item);
		}
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
			con.setAutoCommit(false);

			// insert into order table
			orderPs = con.prepareStatement("INSERT INTO orders (user_id, total_amount) VALUES(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
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
				orderDetailPs.setInt(2, item.getProductId());
				orderDetailPs.setInt(3, item.getQuantity());
				orderDetailPs.setDouble(4, item.getPrice());
				orderDetailPs.executeUpdate();
				
				// Reduce product quantity in Product table
				ProductService productService = new ProductService();
	            int currentQuantity = productService.checkProductQuantity(item.getProductId());
	            int newQuantity = currentQuantity - item.getQuantity();
	            if (newQuantity < 0) {
	                throw new ProductServiceException("Not enough quantity available for product ID " + item.getProductId());
	            }

	            PreparedStatement updateProductPs = con.prepareStatement(
	                    "UPDATE products SET quantity = ? WHERE pid = ?");
	            updateProductPs.setInt(1, newQuantity);
	            updateProductPs.setInt(2, item.getProductId());
	            updateProductPs.executeUpdate();
	            updateProductPs.close();
			}
			con.commit();
			cart.clear();
			return new Order(orderId, user.getId(), totalAmount, new Date());

		} catch (SQLException | ProductServiceException e) {
			if (con != null) {
				con.rollback();
			}
			System.err.println(e.getMessage());
		} finally {
			if (orderResult != null) {
				orderResult.close();
			}
			if (orderDetailPs != null) {
				orderDetailPs.close();
			}
			if (orderPs != null) {
				orderPs.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return null;
	}
}
