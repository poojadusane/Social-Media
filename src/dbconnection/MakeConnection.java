package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeConnection {
	public static Connection getDafaultConnection() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
			System.out.println("\n Driver is connected Successfully");

			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
			return null;
		}
	}

	public static Connection getConnection(String databaseName) {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, "root", "root");
			System.out.println("\n Driver is connected Successfully");

			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
			return null;
		}
	}

}
