package com.ecommerce.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.interfaces.ProductServiceInterface;
import com.ecommerce.model.Product;

public class ProductService implements ProductServiceInterface {

	// 3. User view Product item as Sorted Order
	@Override
	public List<Product> getAllProductsSorted() throws SQLException, ProductServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		List<Product> products = new ArrayList<Product>();
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM products WHERE role=? ORDER BY ? ASC");
			ps.setString(1, "user");
			ps.setString(2, "pid");
			result = ps.executeQuery();
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

	// 7. Add product item
	@Override
	public void addProduct(Product product) throws SQLException, ProductServiceException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement(
					"INSERT INTO products (produuct_name, description, price, quantity) VALUES (?, ?, ?, ?)");
			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getPrice());
			ps.setInt(3, product.getQuantity());

			int result = ps.executeUpdate();

		} catch (SQLException | ProductServiceException e) {
			System.err.println(e.getMessage());
		} finally {
			ps.close();
			con.close();
		}
	}

}
