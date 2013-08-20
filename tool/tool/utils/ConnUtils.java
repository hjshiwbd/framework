package tool.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class ConnUtils
{
	private static Connection conn;

	public static Connection getOracleConn(String ip, String port, String sid,
	        String username, String password)
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return conn;
	}

	public static Connection getMysqlConn(String ip, String port,
	        String dbname, String username, String password)
	{
		try
		{
			Class.forName(Driver.class.getName());
			// jdbc:mysql://localhost:3306/iispace?characterEncoding=utf-8
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname
			        + "?characterEncoding=utf-8";
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * jdbc数据库连接
	 */
	public static Connection getSSCConn()
	{
		String ip = "59.69.67.101";
		String port = "1521";
		String sid = "whut";
		String username = "ssc2";
		String password = "ssc2";
		return getOracleConn(ip, port, sid, username, password);
	}

	public static Connection getConn1()
	{
		String ip = "localhost";
		String port = "3306";
		String sid = "iispace";
		String username = "root";
		String password = "root";
		return getMysqlConn(ip, port, sid, username, password);
	}

}
