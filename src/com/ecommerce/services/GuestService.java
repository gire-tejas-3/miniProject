package com.ecommerce.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.interfaces.GuestServiceInterface;
import com.ecommerce.model.Product;

public class GuestService implements GuestServiceInterface {
	@Override
	public List<Product> getAllProducts() throws SQLException, ProductServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		List<Product> products = new ArrayList<Product>();
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM products WHERE ");
			result = ps.executeQuery();
			if (result.next() == false) {
				throw new ProductServiceException("No Product Found!");
			}

			while (result.next()) {
				products.add(new Product(result.getString("product_name"), result.getString("description"),
						result.getDouble("price"), result.getInt("quantity	")));
			}

		} catch (SQLException | ProductServiceException e) {
			System.err.println(e.getMessage());
		} finally {
			result.close();
			ps.close();
			con.close();
		}
		return products;
	}

}
