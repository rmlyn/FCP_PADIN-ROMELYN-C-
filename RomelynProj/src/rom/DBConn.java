package rom;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	private static final String URL = "jdbc:mysql://localhost:3306/romelyndb";
	private static final String USER = "root";
	private static final String PSW = "";
	
	public static Connection getConn() throws Exception {
		return DriverManager.getConnection(URL, USER, PSW);
	}
}
