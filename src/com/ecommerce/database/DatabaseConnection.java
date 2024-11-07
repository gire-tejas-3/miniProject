package com.ecommerce.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	private static Connection connection = null;

	public static Connection getConnection() {
		InputStream fis;
		try {
			fis = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties");
			Properties properties = new Properties();
			properties.load(fis);

			String dbUrl = properties.getProperty("dbUrl");
			String dbUser = properties.getProperty("dbUserName");
			String dbPassword = properties.getProperty("dbPassword");

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		return connection;
	}

}
