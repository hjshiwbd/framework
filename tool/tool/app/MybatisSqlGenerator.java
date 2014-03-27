package tool.app;

import org.apache.commons.lang3.StringUtils;

import tool.utils.ConnUtils;
import tool.utils.DBUtils;
import tool.utils.IOUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author hjin
 * @cratedate 2013-8-6 上午9:56:15
 */
public class MybatisSqlGenerator
{
	private final String VARCHAR = "VARCHAR";
	public static final String DB_TYPE_ORACLE = "dbTypeOracle";
	public static final String DB_TYPE_MYSQL = "dbTypeMysql";

	String newline = "\r\n";

	private String pkg;
	private String comment = "<!-- %s -->" + newline;

	private String tableName = "";
	private String beanName = "";
	private String fileFolder = "";
	private String dbType = "";

	public static void main(String[] args)
	{

	}

	/**
	 * List[Map[name,type,comment]]
	 */
	private List<Map<String, String>> colList;

	/**
	 * 构造器
	 * 
	 * @param tableName
	 *            数据库表名
	 * @param beanName
	 *            javabean名
	 * @param fileFolder
	 *            xml生成物理地址
	 */
	public MybatisSqlGenerator(Connection conn, String pkg,
	        String databaseName, String tableName, String beanName,
	        String fileFolder, String dbType)
	{
		this.pkg = pkg;
		this.tableName = tableName;
		this.beanName = "\"" + beanName + "\"";
		this.fileFolder = fileFolder;
		this.dbType = dbType;

		if (dbType.equals(DB_TYPE_MYSQL))
		{
			colList = DBUtils.getMysqlColList(conn, databaseName, tableName);
		}
		else if (dbType.equals(DB_TYPE_ORACLE))
		{
			colList = DBUtils.getOracleColList(conn, tableName);
		}
	}

	/**
	 * 生成resultMap映射
	 * 
	 * @return
	 * @author hjin
	 * @cratedate 2013-9-9 下午1:36:45
	 */
	public String getResultMap()
	{
		// <resultMap type="LogDefineBean" id="LogDefineMap">
		String mapperName = beanName.replace("Bean", "Map");
		String s1 = String.format(comment, "resultMap") + "<resultMap id="
		        + mapperName + " type=" + beanName + ">" + newline;
		StringBuffer buffer = new StringBuffer(s1);
		for (Map<String, String> colmap : colList)
		{
			// <result property="cutpoint" column="cutpoint" />
			String name = colmap.get("name").toLowerCase();
			String s2 = "<result property=\"" + name + "\" column=\"" + name
			        + "\" />" + newline;
			buffer.append(s2);
		}
		buffer.append("</resultMap>").append(newline);

		return buffer.toString();
	}

	/**
	 * ibatis select
	 * 
	 * @return
	 */
	public String getMybatisSelect()
	{
		// <select id="selectList" parameterType="DemoUserBean"
		// resultType="DemoUserBean">
		String sql = String.format(comment, "select")
		        + "<select id=\"select\" parameterType=" + beanName
		        + " resultType=" + beanName + ">" + newline;
		sql += "select ";

		String whereAppend = "";
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name").toLowerCase();
			String type = map.get("type");
			type = convertType(type);
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
				        + " != ''\">" + newline + "and " + name + " = #{"
				        + name + ",jdbcType=" + type + "}" + newline + "</if>"
				        + newline;
			}
			else
			{
				// <if test="id!=null"> and id = #{id} </if>
				whereAppend += "<if test=\"" + name + "!=null\">" + newline
				        + "and " + name + " = #{" + name + ",jdbcType=" + type
				        + "}" + newline + "</if>" + newline;
			}
		}
		sql += " from " + tableName + newline;
		sql += "<where>" + newline + whereAppend + "</where>" + newline;
		sql += "</select>" + newline;
		return sql;
	}

	/**
	 * @param valuePlaceholder
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
				type = convertType(type);

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
		String result = String.format(comment, "insert")
		        + "<insert id=\"insert\" parameterType=" + beanName + ">"
		        + newline;
		result += "insert into " + tableName + " (" + colStr + ") " + newline;
		result += "values (" + valStr + ")" + newline;
		result += "</insert>" + newline;
		return result;
	}

	/**
	 * mybatis update
	 * 
	 * @param valuePlaceholder
	 * @return
	 */
	public String getUpdate(String valuePlaceholder)
	{
		// <update id="update" parameterType="DemoUserBean">
		String result = String.format(comment, "update")
		        + "<update id=\"update\" parameterType=" + beanName + ">"
		        + newline;
		result += "update " + tableName + newline;

		String where = "";
		String colstr = "";
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name").toLowerCase();
			String type = map.get("type");
			type = convertType(type);
			if (i == 0)
			{
				where = " where " + name + " = #{" + name + "}";
			}
			else
			{
				String s = "<if test=\"" + name + "!=null\">" + newline + ""
				        + name + " = #{" + name + ",jdbcType=" + type + "},"
				        + newline + "</if>" + newline;
				colstr += s;
			}

		}
		result += "<set>" + newline + colstr + "</set>" + newline + where + ""
		        + newline;
		result += "</update>" + newline;
		return result;
	}

	/**
	 * ibatis delete
	 * 
	 * @return
	 */
	public String getDelete()
	{
		Map<String, String> map = colList.get(0);
		String name = map.get("name").toLowerCase();
		String type = map.get("type").toLowerCase();
		String result = String.format(comment, "delete")
		        + "<delete id=\"delete\" parameterType=" + beanName + ">"
		        + newline;
		result += "delete from " + tableName + " where " + name + " = #{"
		        + name + ",jdbcType=" + type + "}" + newline;
		result += "</delete>";
		return result;
	}

	/**
	 * 格式化列名,加入前缀
	 * 
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public String formatColumn(String prefix, String suffix)
	{
		String result = ",";
		for (Map<String, String> map : colList)
		{
			String name = map.get("name").toLowerCase();

			String _suffix;
			if (!StringUtils.isBlank(suffix))
			{
				_suffix = " " + suffix + name;
			}
			else
			{
				_suffix = "";
			}

			result += prefix + name + _suffix + ", ";
		}
		result = result.substring(1);
		System.out.println(result);
		return result;
	}

	/**
	 * create mybatis mapper file
	 * 
	 * @return
	 * @author hjin
	 * @cratedate 2013-9-5 上午9:42:42
	 */
	public void createMapperFile(boolean isCreateFile)
	{
		// xml头
		String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n<!DOCTYPE mapper\r\n  "
		        + "PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" "
		        + "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" + newline;
		// 文件内容
		StringBuffer all = new StringBuffer(xmlHead);

		// 包名
		String beanFullName = pkg
		        + beanName.replace("\"", "").replace("Bean", "");
		all.append("<mapper namespace=\"" + beanFullName + "\">" + newline);

		// 生成sql
		String valuePlaceholder = "#";
		all.append(newline);
		all.append(getResultMap());
		all.append(newline);
		all.append(getMybatisSelect());
		all.append("" + newline);
		all.append(getInsert(valuePlaceholder));
		all.append(newline);
		all.append(getUpdate(valuePlaceholder));
		all.append(newline);
		all.append(getDelete());
		all.append(newline);
		all.append("</mapper>");

		System.out.println(all);

		if (isCreateFile)
		{
			// 写文件
			String fileName = "mapper-"
			        + beanName.replace("\"", "").toLowerCase()
			                .replace("bean", "") + ".xml";
			IOUtils.createFile(fileFolder, fileName, all.toString());
		}
	}

	/**
	 * oracle数据库类型名称到java类型名称的转换
	 * 
	 * @param type
	 * @return
	 * @author hjin
	 * @cratedate 2013-8-6 上午10:15:14
	 */
	public String convertType(String type)
	{
		type = type.toUpperCase();

		if (DB_TYPE_ORACLE.equals(dbType))
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
		}
		else if (DB_TYPE_MYSQL.equals(dbType))
		{
			if (type.equals("VARCHAR"))
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
		}
		return type;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public Connection getConn()
	{
		String ip = "localhost";
		String port = "3306";
		String dbname = "jap";
		String username = "root";
		String password = "root";
		return ConnUtils.getMysqlConn(ip, port, dbname, username, password);

	}
}
