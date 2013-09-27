package tool.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils
{
	private static PreparedStatement ps;
	private static ResultSet rs;

	/**
	 * 查表,获得字段名,和每个字段的类型
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<Map<String, String>> getOracleColList(Connection conn,
	        String tableName)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select a.COLUMN_NAME,b.DATA_TYPE,a.COMMENTS from user_col_comments a, user_tab_columns b where b.TABLE_NAME(+) = a.TABLE_NAME and b.COLUMN_NAME = a.COLUMN_NAME and a.TABLE_NAME = ?";
		System.out.println(sql);
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setString(1, tableName.toUpperCase());
			rs = ps.executeQuery();
			while (rs.next())
			{
				Map<String, String> map = new HashMap<String, String>();
				String name = rs.getString(1);
				String type = rs.getString(2);
				String comment = rs.getString(3);
				comment = comment == null ? "" : comment;
				map.put("name", name);
				map.put("type", type);
				map.put("comment", comment);
				list.add(map);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	/**
	 * 查表,获得字段名,和每个字段的类型
	 * 
	 * @param tableName
	 * @return List[Map[name,type,comment]]
	 */
	public static List<Map<String, String>> getMysqlColList(Connection conn,
	        String tableName)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select t.COLUMN_NAME,t.DATA_TYPE,t.COLUMN_COMMENT from information_schema.`COLUMNS` t where table_name = ?";
//		System.out.println(sql);
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setString(1, tableName.toUpperCase());
			rs = ps.executeQuery();
			while (rs.next())
			{
				Map<String, String> map = new HashMap<String, String>();
				String name = rs.getString(1);
				String type = rs.getString(2);
				String comment = rs.getString(3);
				comment = comment == null ? "" : comment;
				map.put("name", name);
				map.put("type", type);
				map.put("comment", comment);
				list.add(map);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return list;
	}

}
