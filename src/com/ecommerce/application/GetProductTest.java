package com.ecommerce.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.database.DatabaseConnection;

 
public class GetProductTest {

	
	public static void main(String[] args) {
		GetProductTest test = new GetProductTest();
		
		try {
			test.getProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		PreparedStatement ps = null;
		ResultSet rs = null;
		public  void getProduct() throws SQLException {
			Connection conn = DatabaseConnection.getConnection();
			try {
				ps= conn.prepareStatement("select * from products order by PID");
				rs = ps.executeQuery();
				while(rs.next()) {
				
				System.out.println("Product name>>" + rs.getString("PRODUCT_NAME"));
				System.out.println("Description>> "+rs.getString("DESCRIPTION"));
				System.out.println("Price>> "+rs.getDouble("PRICE"));
				System.out.println("Quantity>> "+rs.getInt("QUANTITY"));
				
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}finally {
				ps.close();
				rs.close();
			}
	}

}
