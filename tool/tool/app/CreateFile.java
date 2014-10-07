package tool.app;

import tool.utils.ConnUtils;

import java.sql.Connection;

public class CreateFile
{
	public static void main(String[] args) throws Exception
	{
		// bean,mapper.xml
		createFile();

		// struct
		// createStructure();

		// cols
		// createCols();
	}

	public static void createFile()
	{
		String dbtype = MybatisSqlGenerator.DB_TYPE_ORACLE;
		String databaseName = "monitor";// 仅用于mysql的colList查询
		String tableName = "t_sys_interface";
		String beanFileName = "SysInterfaceBean";
		String packageName = "com.synjones.push.bean";
		String folderPath = "F:/svn/synjones/192.168.1.243/synjones/wuhan/trunk/push/code/push/src/main/java/com/synjones/push/bean";

		CreateJavaBeanFile c = new CreateJavaBeanFile(getConn(dbtype),
		        databaseName, tableName, beanFileName, packageName, folderPath,
		        CreateJavaBeanFile.DB_TYPE_ORACLE);
		c.createBeanFile(true);

		String mybatisFolder = "F:/svn/synjones/192.168.1.243/synjones/wuhan/trunk/push/code/push/src/main/resources/config/mybatis";
		packageName = "com.synjones.push.mapper.";
		MybatisSqlGenerator mybatis = new MybatisSqlGenerator(getConn(dbtype),
		        packageName, databaseName, tableName, beanFileName,
		        mybatisFolder, MybatisSqlGenerator.DB_TYPE_ORACLE);
		mybatis.createMapperFile(true);
	}

	public static void createStructure() throws Exception
	{
		String templateDir = "F:/github/framework";
		String pkgModule = "com.synjones.push";
		String pkgMapper = "com.synjones.push.mapper";
		String module = "wljf";
		String author = "huangjin";
		CreateStructFolders struct = new CreateStructFolders(templateDir,
		        pkgModule, pkgMapper, module, author);
		struct.execute();
	}

	public static void createCols() throws Exception
	{
		String dbtype = MybatisSqlGenerator.DB_TYPE_ORACLE;
		String databaseName = "photo";// 仅用于mysql的colList查询
		String tableName = "t_log_token";
		String beanFileName = "ImageBean";
		String packageName = "cn.edu.whut.photo.bean";
		String mybatisFolder = "F:/svn/mine/weiyun/hjin/project/photo/code/photo/src/config/mybatis";
		packageName = "cn.edu.whut.photo.mapper.";
		MybatisSqlGenerator mybatis = new MybatisSqlGenerator(getConn(dbtype),
		        packageName, databaseName, tableName, beanFileName,
		        mybatisFolder, MybatisSqlGenerator.DB_TYPE_ORACLE);
		System.out.println(mybatis.getColWithPrefix("t", ""));
	}

	public static Connection getConn(String dbtype)
	{
		return dbtype.equals(MybatisSqlGenerator.DB_TYPE_ORACLE) ? getOracleConn()
		        : getMysqlConn();
	}

	/**
	 * jdbc数据库连接
	 */
	public static Connection getMysqlConn()
	{
		String ip = "localhost";
		String port = "3306";
		String dbname = "jap";
		String username = "root";
		String password = "root";
		return ConnUtils.getMysqlConn(ip, port, dbname, username, password);
	}

	/**
	 * jdbc数据库连接
	 */
	public static Connection getOracleConn()
	{
		String ip = "202.114.93.220";
		String port = "1521";
		String dbname = "orcl";
		String username = "push";
		String password = "push";
		return ConnUtils.getOracleConn(ip, port, dbname, username, password);
	}
}
