package tool.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
	 * @return List[Map],map包含key:name字段名,type字段类型
	 */
	public static List<Map<String, String>> getColList(Connection conn,
	        String tableName)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select * from " + tableName + " where 1 = 2";
		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++)
			{
				Map<String, String> map = new HashMap<String, String>();
				String name = data.getColumnName(i);
				String type = data.getColumnTypeName(i);
				map.put("name", name);
				map.put("type", type);
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
