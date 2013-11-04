package tool.app;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import tool.app.MybatisSqlGenerator;
import tool.utils.ConnUtils;
import tool.utils.DBUtils;
import tool.utils.IOUtils;

/**
 * 根据数据库字段创建javabean文件
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午8:56:56
 * 
 */
public class CreateJavaBeanFile
{
	private static Connection conn;
	private static List<Map<String, String>> colList;

	private String beanFileName;
	private String packageName;
	private String beanFileFolder;

	public static void main(String[] args)
	{
		
	}

	public CreateJavaBeanFile(String tableName, String beanFileName,
	        String packageName, String folderPath)
	{
		this.beanFileName = beanFileName;
		this.packageName = packageName;
		this.beanFileFolder = folderPath;

		// 先查表,获得字段名,和每个字段的类型,注释
		getConn();
		// colList = DBUtils.getMysqlColList(conn, tableName);
		colList = DBUtils.getOracleColList(conn, tableName);
	}

	/**
	 * 根据数据库表的结构,在指定包名下创建javabean文件
	 * 
	 * @author hjin
	 * @cratedate 2013-9-9 上午10:28:35
	 * 
	 */
	public void createBeanFile(boolean isCreateFile)
	{
		// 用于输出的文本
		StringBuffer out = new StringBuffer("package ");
		out.append(packageName).append(";\r\n\r\n")
		        .append("import framework.base.bean.BaseBean;")
		        .append("\r\n\r\n");
		// 类名
		out.append("public class " + beanFileName + " extends BaseBean")
		        .append(" {\r\n");

		// 属性名输出文本
		StringBuffer fieldOut = new StringBuffer();
		// 用字段名来生成javabean文件
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name");
			name = name.toLowerCase();

			String type = map.get("type");
			type = getType(type);

			String comment = map.get("comment");

			String format = "\tprivate %s %s;// %s\r\n";
			format = String.format(format, type, name, comment);
			fieldOut.append(format);
		}

		// java文件内容拼接完成
		out.append(fieldOut).append("\r\n").append("}");
		System.out.println(out);

		if (isCreateFile)
		{
			IOUtils.createFile(beanFileFolder, beanFileName + ".java",
			        out.toString());
		}
	}

	public static String getType(String dataType)
	{
		String type = "";
		if (dataType.equalsIgnoreCase("int"))
		{
			type = "Integer";
		}
		if (dataType.equalsIgnoreCase("NUMBER"))
		{
			type = "Integer";
		} else if (dataType.equalsIgnoreCase("VARCHAR2"))
		{
			type = "String";
		} else if (dataType.equalsIgnoreCase("DATE"))
		{
			type = "java.util.Date";
		} else
		{
			type = "String";
		}
		return type;
	}

	/**
	 * jdbc数据库连接
	 */
	public static void getConn()
	{
		// String ip = "59.69.67.101";
		// String port = "1521";
		// String sid = "whut";
		// String username = "ssc2";
		// String password = "ssc2";
		// conn = ConnUtils.getOracleConn(ip, port, sid, username, password);
		// conn = ConnUtils.getConn1();
		conn = ConnUtils.getSSCPMConn();

	}
}
