package com.ecommerce.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			FileInputStream fis = new FileInputStream(
					"C:\\Users\\TEJAS\\java_development_main\\vc_code\\miniProject\\src\\com\\ecommerce\\database\\config.properties");
			Properties properties = new Properties();
			properties.load(fis);

			String dbUrl = properties.getProperty("dbUrl");
			String dbUser = properties.getProperty("dbUserName");
			String dbPassword = properties.getProperty("dbPassword");

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// return Connection
		return connection;
	}
}
