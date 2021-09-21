package data;

import java.sql.*;

public class DBConnect {
	Connection conn = null;

	public static Connection conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/dacnpm", "root", "Nghia2705");
			return con;
		} catch (ClassNotFoundException | SQLException ex) {
			System.err.println("DBConnect : " + ex.getMessage());
			return null;
		}
	}
}
