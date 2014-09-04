import java.sql.*;
import javax.swing.*;

public class sqliteConnection {
	
	Connection conn = null;
	
	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:AlphaDB.sqlite");
			System.out.println("Connection Successful");
			return conn;
		} catch (Exception e) {
			System.out.println("Connection Error");
			return null;
		}
	}

}
