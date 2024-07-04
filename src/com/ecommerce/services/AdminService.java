package com.ecommerce.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.UserServiceException;
import com.ecommerce.interfaces.AdminServiceInterface;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.User;

public class AdminService implements AdminServiceInterface {

	@Override
	public void viewRegisteredUsers() throws SQLException, UserServiceException {
		Connection con = null;
		PreparedStatement userPs = null;
		ResultSet userRs = null;

		try {
			con = DatabaseConnection.getConnection();
			userPs = con.prepareStatement("SELECT * FROM users");
			userRs = userPs.executeQuery();

			if (userRs.next() == false)
				throw new UserServiceException("");

			while (userRs.next()) {
				System.out.println(new User(userRs.getInt("id"), userRs.getString("username"),
						userRs.getString("password"), userRs.getString("role"), userRs.getString("firstName"),
						userRs.getString("lastName"), userRs.getString("email"), userRs.getString("phone"),
						userRs.getString("address"), userRs.getBoolean("isloggedin"), userRs.getBoolean("isactive")));
			}
		} catch (SQLException | UserServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void viewUserHistory(String username) throws SQLException, UserServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement orderPs = null;
		PreparedStatement orderDetailPs = null;
		ResultSet rs = null;
		ResultSet orderRs = null;
		ResultSet orderResult = null;

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT id FROM users WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();

			int userId = -1;
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			orderPs = con.prepareStatement("SELECT * FROM orders WHERE user_id = ?");
			orderPs.setInt(1, userId);
			orderRs = orderPs.executeQuery();

			while (orderRs.next()) {
				System.out.println(new Order(orderRs.getInt("id"), orderRs.getInt("user_id"),
						orderRs.getDouble("total_amount"), orderRs.getTimestamp("date")));

				int orderId = orderRs.getInt("order_id");
				orderDetailPs = con.prepareStatement("SELECT * FROM orderDetails WHERE order_id = ?");
				orderDetailPs.setInt(1, orderId);
				orderResult = orderDetailPs.executeQuery();

				while (orderResult.next()) {
					System.out
							.println(new OrderDetails(orderResult.getInt("order_id"), orderResult.getInt("product_id"),
									orderResult.getInt("quantity"), orderResult.getDouble("price")));
				}
			}

		} catch (SQLException | UserServiceException e) {
			System.err.println(e.getMessage());
		}

	}

}
