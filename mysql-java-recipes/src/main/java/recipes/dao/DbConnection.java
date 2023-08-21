package recipes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import recipes.exception.DbException;

public class DbConnection {

	private static String HOST = "localhost";
	private static String PASSWORD = "recipes";
	private static int PORT = 3306;
	private static String SCHEMA = "recipes";
	private static String USER = "recipes";

	public static Connection getConnection() {
		String url = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER,
				PASSWORD);

		System.out.println("Connecting with url = " + url);

		try {
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Connection to schema '" + SCHEMA + "' is successful!");
			return conn;
		} catch (SQLException e) {
			System.out.println("Unable to get a connection at " + url);
			throw new DbException("Unable to get connection at \" + url");
		}
	}
}
