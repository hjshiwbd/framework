package tool.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtils {
	private static Connection conn;

	public static Connection getConn(String ip, String port, String sid,
			String username, String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * jdbc数据库连接
	 */
	public static Connection getSSCConn() {
		String ip = "59.69.67.101";
		String port = "1521";
		String sid = "whut";
		String username = "ssc2";
		String password = "ssc2";
		return getConn(ip, port, sid, username, password);
	}
}
