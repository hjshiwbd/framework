package tool.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.utils.ConnUtils;

/**
 * 根据数据库字段创建javabean文件
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午8:56:56
 * 
 */
public class GenerateJavaBeanFile
{
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;

	public static void main(String[] args)
	{
		// 数据库表名
		String tableName = "demo_user";
		// 文件名
		String beanFileName = "DemoUserBean";
		// 创建文件包名
		// String packageName = "com.synjones.iaweb.user.bean";
		String packageName = "demo.bean";
		createBeanFile(tableName, beanFileName, packageName);
	}

	/**
	 * 根据数据库表的结构,在指定包名下创建javabean文件
	 * 
	 * @param tableName
	 *            表名
	 * @param packageName
	 *            包名
	 */
	public static void createBeanFile(String tableName, String beanFileName,
	        String packageName)
	{
		// 用于输出的文本
		StringBuffer out = new StringBuffer("package ");
		out.append(packageName).append(";\r\n\r\n");
		// 类名
		out.append("public class " + beanFileName).append(" {\r\n");

		// 先查表,获得字段名,和每个字段的类型
		List<Map<String, String>> colList = getColList(tableName);
		// 属性名输出文本
		StringBuffer fieldOut = new StringBuffer();
		// 用字段名来生成javabean文件
		for (int i = 0; i < colList.size(); i++)
		{
			Map<String, String> map = colList.get(i);
			String name = map.get("name");
			name = name.toLowerCase();
			String type = map.get("type");
			if (type.equalsIgnoreCase("NUMBER"))
			{
				type = "Integer";
			}
			else if (type.equalsIgnoreCase("VARCHAR2"))
			{
				type = "String";
			}
			else if (type.equalsIgnoreCase("DATE"))
			{
				type = "java.util.Date";
			}
			else
			{
				type = "String";
			}
			String comment = map.get("comment");

			String format = "\tprivate %s %s;// %s\r\n";
			format = String.format(format, type, name, comment);
			fieldOut.append(format);
		}

		// java文件内容拼接完成
		out.append(fieldOut).append("\r\n").append("}");
		System.out.println(out);

		// 输出文件,路径
		File outFile = new File("");
		// 项目的物理路径
		String prjPath = outFile.getAbsolutePath() + "/src/";
		String packagePath = packageName.replaceAll("\\.", "/");
		// 生成输出文件
		outFile = new File(prjPath + packagePath + "/" + beanFileName + ".java");
		// 如果路径不存在,就新建
		if (!outFile.getParentFile().exists())
		{
			outFile.getParentFile().mkdirs();
		}
		System.out.println(outFile.getAbsolutePath());

		// 文件流写入
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(outFile);
			bw = new BufferedWriter(fw);
			bw.write(out.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				bw.close();
				fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查表,获得字段名,和每个字段的类型
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<Map<String, String>> getColList(String tableName)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select a.COLUMN_NAME,b.DATA_TYPE,a.COMMENTS from user_col_comments a, user_tab_columns b where b.TABLE_NAME(+) = a.TABLE_NAME and b.COLUMN_NAME = a.COLUMN_NAME and a.TABLE_NAME = ?";
		getConn();
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
	 * jdbc数据库连接
	 */
	public static void getConn()
	{
		String ip = "59.69.67.101";
		String port = "1521";
		String sid = "whut";
		String username = "ssc2";
		String password = "ssc2";
		conn = ConnUtils.getConn(ip, port, sid, username, password);
	}
}
