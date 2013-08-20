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
	private String VARCHAR = "VARCHAR";
	private String tableName = "";
	private String beanName = "";

	public static void main(String[] args)
	{
		String tableName = "demo_user";
		String beanName = "DemoUserBean";
		MybatisSqlGenerator templete = new MybatisSqlGenerator(tableName,
		        beanName);
		String sql1 = templete.getMybatisSelect();
		System.out.println(sql1);

		String sql2 = templete.getInsert("#");
		System.out.println(sql2);

		String sql3 = templete.getUpdate("");
		System.out.println(sql3);

		String sql4 = templete.getDelete();
		System.out.println(sql4);
	}

	private List<Map<String, String>> colList;

	public MybatisSqlGenerator(String tableName, String beanName)
	{
		this.tableName = tableName;
		this.beanName = "\"" + beanName + "\"";
		Connection conn = ConnUtils.getConn1();
		this.colList = DBUtils.getColList(conn, tableName);
	}

	/**
	 * ibatis select
	 * 
	 * @param tableName
	 * @return
	 */
	public String getMybatisSelect()
	{
		// <select id="selectList" parameterType="DemoUserBean"
		// resultType="DemoUserBean">
		String sql = "<select id=\"select\" parameterType=" + beanName
		        + " resultType=" + beanName + ">\r\n";
		sql += "select ";

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

			if (type.equals(VARCHAR))
			{
				// 字符型
				// <if test="id!=null and id!=''"> and id = #{id} </if>
				whereAppend += "<if test=\"" + name + " != null and " + name
				        + " != ''\">\r\n" + "and " + name + " = #{" + name
				        + ",jdbcType=" + type + "}\r\n" + "</if>\r\n";
			}
			else
			{
				// <if test="id!=null"> and id = #{id} </if>
				whereAppend += "<if test=\"" + name + "!=null\">\r\n" + "and "
				        + name + " = #{" + name + ",jdbcType=" + type + "}\r\n"
				        + "</if>\r\n";
			}
		}
		sql += " from " + tableName + "\r\n";
		sql += "<where>\r\n" + whereAppend + "</where>\r\n";
		sql += "</select>\r\n";
		return sql;
	}

	/**
	 * 
	 * @param table
	 * @param valuetype
	 *            占位符
	 * @return
	 */
	public String getInsert(String valuePlaceholder)
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

		// <insert id="insert" parameterType="DemoUserBean">
		String result = "<insert id=\"insert\" parameterType=" + beanName
		        + ">\r\n";
		result += "insert into " + tableName + " (" + colStr + ") \r\n";
		result += "values (" + valStr + ")\r\n";
		result += "</insert>\r\n";
		return result;
	}

	/**
	 * mybatis update
	 * 
	 * @param tableName
	 * @param valuePlaceholder
	 * @return
	 */
	public String getUpdate(String valuePlaceholder)
	{
		// <update id="update" parameterType="DemoUserBean">
		String result = "<update id=\"update\" parameterType=" + beanName
		        + ">\r\n";
		result += "update " + tableName + "\r\n";

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
			}
			else
			{
				String s = "<if test=\"" + name + "!=null\">\r\n" + "" + name
				        + " = #{" + name + ",jdbcType=" + type + "},\r\n"
				        + "</if>\r\n";
				colstr += s;
			}

		}
		result += "<set>\r\n" + colstr + "</set>\r\n" + where + "\r\n";
		result += "</update>\r\n";
		return result;
	}

	/**
	 * ibatis delete
	 * 
	 * @param tableName
	 * @return
	 */
	public String getDelete()
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
			type = VARCHAR;// org.apache.ibatis.type.JdbcType.VARCHAR
		}
		if (type.equals("DATE"))
		{
			type = "TIMESTAMP";// org.apache.ibatis.type.JdbcType.TIMESTAMP
		}
		if (type.equals("NUMBER") || type.equals("INT"))
		{
			type = "NUMERIC";// org.apache.ibatis.type.JdbcType.NUMERIC
		}
		return type;
	}
}
