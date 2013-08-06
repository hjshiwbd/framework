package tool.app;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import tool.utils.ConnUtils;
import tool.utils.DBUtils;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:56:15
 * 
 */
public class MybatisSqlGenerator
{
	public static void main(String[] args)
	{
		String tablename = "demo_user";
		MybatisSqlGenerator templete = new MybatisSqlGenerator(tablename);
		String sql1 = templete.getMybatisSelect(tablename);
		System.out.println(sql1);

		String sql2 = templete.getInsert(tablename, "#");
		System.out.println(sql2);

		String sql3 = templete.getUpdate(tablename, "");
		System.out.println(sql3);

		String sql4 = templete.getDelete(tablename);
		System.out.println(sql4);
	}

	private List<Map<String, String>> colList;

	public MybatisSqlGenerator(String tableName)
	{
		Connection conn = ConnUtils.getSSCConn();
		this.colList = DBUtils.getColList(conn, tableName);
	}

	/**
	 * ibatis select
	 * 
	 * @param tableName
	 * @return
	 */
	public String getMybatisSelect(String tableName)
	{
		String sql = "select ";

		String whereAppend = "";
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name").toLowerCase();
			String type = map.get("type");
			type = convertOracleType(type);
			if (i == 0)
			{
				sql += name;
			}
			else
			{
				sql += ", " + name;
			}

			/**
			 * <if test="id!=''"> and id = #{id} </if>
			 */
			whereAppend += "<if test=\"" + name + "!=null\">\r\n" + "and "
			        + name + " = #{" + name + ",jdbcType=" + type + "}\r\n"
			        + "</if>\r\n";
		}
		sql += " from " + tableName;
		sql += " where 1 = 1\r\n" + whereAppend;
		return sql;
	}

	/**
	 * 
	 * @param table
	 * @param valuetype
	 *            占位符
	 * @return
	 */
	public String getInsert(String tableName, String valuePlaceholder)
	{
		String colStr = "";
		String valStr = "";

		if (valuePlaceholder.equals("#"))
		{
			// ibatis
			for (int i = 0; i < colList.size(); i++)
			{
				Map<String, String> map = colList.get(i);
				// 字段名
				String name = map.get("name").toLowerCase();
				// 数据类型
				String type = map.get("type");
				type = convertOracleType(type);

				if (i == 0)
				{
					colStr += name;
					valStr += valuePlaceholder + "{" + name + ",jdbcType="
					        + type + "}";
				}
				else
				{
					colStr += ", " + name;
					valStr += ", " + valuePlaceholder + "{" + name
					        + ",jdbcType=" + type + "}";
				}
			}
		}
		else
		{
			//
			for (int i = 0; i < colList.size(); i++)
			{
				Map<String, String> map = colList.get(i);
				String name = map.get("name").toLowerCase();
				if (i == 0)
				{
					colStr += name;
					valStr += valuePlaceholder;
				}
				else
				{
					colStr += ", " + name;
					valStr += ", " + valuePlaceholder;
				}
			}
		}

		String result = "insert into " + tableName + " (" + colStr
		        + ") \r\nvalues (" + valStr + ")\r\n";
		return result;
	}

	/**
	 * mybatis update
	 * 
	 * @param tableName
	 * @param valuePlaceholder
	 * @return
	 */
	public String getUpdate(String tableName, String valuePlaceholder)
	{
		String result = "update " + tableName + " set ";

		String where = "";
		String colstr = "";
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name").toLowerCase();
			String type = map.get("type");
			type = convertOracleType(type);
			if (i == 0)
			{
				where = " where " + name + " = #{" + name + "}";
				colstr += name + " = #{" + name + "}\r\n";
			}
			else
			{
				// String s = "<isNotNull property=\"" + name
				// + "\" prepend=\",\">\r\n" + name + " = #" + name
				// + "#\r\n</isNotNull>\r\n";
				String s = "<if test=\"" + name + "!=null\">\r\n" + "," + name
				        + " = #{" + name + ",jdbcType=" + type + "}\r\n"
				        + "</if>\r\n";
				colstr += s;
			}
		}
		result += colstr + where + "\r\n";
		return result;
	}

	/**
	 * ibatis delete
	 * 
	 * @param tableName
	 * @return
	 */
	public String getDelete(String tableName)
	{
		Map<String, String> map = colList.get(0);
		String name = map.get("name").toLowerCase();
		String result = "delete from " + tableName + " where " + name + " = #"
		        + name + "#";
		return result;
	}

	/**
	 * oracle数据库类型名称到java类型名称的转换
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 上午10:15:14
	 * @param type
	 * @return
	 * 
	 */
	public String convertOracleType(String type)
	{
		if (type.equals("VARCHAR2"))
		{
			type = "VARCHAR";// java.sql.Types.VARCHAR
		}
		if (type.equals("DATE"))
		{
			type = "TIMESTAMP";// Types.TIMESTAMP
		}
		if (type.equals("NUMBER"))
		{
			type = "NUMERIC";// Types.NUMERIC
		}
		return type;
	}
}
