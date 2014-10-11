package tool.app;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import tool.utils.ConnUtils;
import tool.utils.DBUtils;
import tool.utils.IOUtils;

/**
 * @author hjin
 * @cratedate 2013-8-6 上午9:56:15
 */
public class Ibatis2SqlGenerator
{
	private final String VARCHAR = "VARCHAR";
	public static final String DB_TYPE_ORACLE = "dbTypeOracle";
	public static final String DB_TYPE_MYSQL = "dbTypeMysql";

	String newline = "\r\n";

	private String comment = "<!-- %s -->" + newline;

	private String tableName = "";
	private String beanPkg = "";// com.synjones.iaweb.article.bean
	private String beanName = "";// ArticleBean
	private String fileFolder = "";
	private String dbType = "";

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
	public Ibatis2SqlGenerator(Connection conn, String databaseName,
	        String tableName, String beanPkg, String beanName,
	        String fileFolder, String dbType)
	{
		this.tableName = tableName;
		this.beanPkg = "\"" + beanPkg + "." + beanName + "\"";
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
		        + mapperName + " class=" + beanName + ">" + newline;
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
	public String getSelect()
	{
		// <select id="selectList" parameterClass="DemoUserBean"
		// resultType="DemoUserBean">
		String sql = String.format(comment, "select")
		        + "<select id=\"select\" parameterClass=" + beanName
		        + " resultClass=" + beanName + ">" + newline;
		sql += "select ";

		String tableAltName = "t";// 表别名
		String whereAppend = "";
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name").toLowerCase();
			String type = map.get("type");
			type = convertType(type);
			String newName = tableAltName + "." + name;// 字段前加别名
			if (i == 0)
			{
				sql += newName;
			}
			else
			{
				sql += ", " + newName;
			}

			whereAppend += "<isNotEmpty property=\"" + name
			        + "\" prepend=\"and\">" + newline + name + " = #" + name
			        + "#" + newline + "</isNotEmpty>" + newline;
		}
		sql += " from " + tableName + " " + tableAltName + newline;
		sql += "where 1=1" + newline + whereAppend;
		sql += "</select>" + newline;
		return sql;
	}

	/**
	 * select t.col1, t.col2 ...
	 * 
	 * @param prefix
	 * @return
	 */
	public String getColWithPrefix(String prefix, String suffix)
	{
		String sql = "select \r\n";
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name").toLowerCase();
			sql += prefix + "." + name + " " + suffix + name + ",";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql += "\r\nfrom " + tableName;
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
					valStr += valuePlaceholder + name + valuePlaceholder;
				}
				else
				{
					colStr += ", " + name;
					valStr += ", " + valuePlaceholder + name + valuePlaceholder;
					;
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

		// <insert id="insert" parameterClass="DemoUserBean">
		String result = String.format(comment, "insert")
		        + "<insert id=\"insert\" parameterClass=" + beanName + ">"
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
		// <update id="update" parameterClass="DemoUserBean">
		String result = String.format(comment, "update")
		        + "<update id=\"update\" parameterClass=" + beanName + ">"
		        + newline;
		result += "update " + tableName + " set" + newline;

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
				where = " where " + name + " = " + valuePlaceholder + name
				        + valuePlaceholder;
			}
			else
			{
				String s = "<isNotNull property=\"" + name
				        + "\" prepend=\",\">" + newline + name + " = #" + name
				        + "#" + newline + "</isNotNull>" + newline;
				colstr += s;
			}

		}
		result += colstr + where + "" + newline;
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
		type = convertType(type);
		String result = String.format(comment, "delete")
		        + "<delete id=\"delete\" parameterClass=" + beanName + ">"
		        + newline;
		result += "delete from " + tableName + " where " + name + " = #" + name
		        + "#" + newline;
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
		String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\" >"
		        + newline;
		// 文件内容
		StringBuffer all = new StringBuffer(xmlHead);

		// 包名
		all.append("<sqlMap namespace="
		        + beanName.toLowerCase().replace("bean", "") + ">" + newline);

		// 生成sql
		String valuePlaceholder = "#";
		all.append(newline);
		all.append(getTypeAlias());
		all.append(newline);
		all.append(getResultMap());
		all.append(newline);
		all.append(getSelect());
		all.append("" + newline);
		all.append(getInsert(valuePlaceholder));
		all.append(newline);
		all.append(getUpdate(valuePlaceholder));
		all.append(newline);
		all.append(getDelete());
		all.append(newline);
		all.append("</sqlMap>");

		System.out.println(all);

		if (isCreateFile)
		{
			// 写文件
			// sqlmap-article.ibatis.xml
			String fileName = "sqlmap-"
			        + beanName.replace("\"", "").toLowerCase()
			                .replace("bean", "") + ".ibatis.xml";
			IOUtils.createFile(fileFolder, fileName, all.toString());
		}
	}

	/**
	 * typeAlias的xml
	 * 
	 * @return
	 */
	private Object getTypeAlias()
	{
		// <typeAlias alias="articleBean"
		// type="com.synjones.iaweb.article.bean.ArticleBean" />
		String xml = "<typeAlias alias=" + beanName + " type=" + beanPkg
		        + " />" + newline;
		return xml;
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
