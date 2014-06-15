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
//		createStructure();
	}

	public static void createFile()
	{
		String dbtype = MybatisSqlGenerator.DB_TYPE_ORACLE;
		String databaseName = "photo";// 仅用于mysql的colList查询
		String tableName = "t_image";
		String beanFileName = "ImageBean";
		String packageName = "cn.edu.whut.photo.bean";
		String folderPath = "F:/svn/mine/weiyun/hjin/project/photo/code/photo/src/cn/edu/whut/photo/bean";

		CreateJavaBeanFile c = new CreateJavaBeanFile(getConn(dbtype),
		        databaseName, tableName, beanFileName, packageName, folderPath,
		        CreateJavaBeanFile.DB_TYPE_ORACLE);
		c.createBeanFile(true);

		String mybatisFolder = "F:/svn/mine/weiyun/hjin/project/photo/code/photo/src/config/mybatis";
		packageName = "cn.edu.whut.photo.mapper.";
		MybatisSqlGenerator mybatis = new MybatisSqlGenerator(getConn(dbtype),
		        packageName, databaseName, tableName, beanFileName,
		        mybatisFolder, MybatisSqlGenerator.DB_TYPE_ORACLE);
		mybatis.createMapperFile(true);
	}

	public static Connection getConn(String dbtype)
	{
		return dbtype.equals(MybatisSqlGenerator.DB_TYPE_ORACLE) ? getOracleConn()
		        : getMysqlConn();
	}

	public static void createStructure() throws Exception
	{
		String templateDir = "F:/github/framework";
		String pkgModule = "cn.edu.whut.photo";
		String pkgMapper = "cn.edu.whut.photo.mapper";
		String module = "image";
		String author = "hjin";
		CreateStructFolders struct = new CreateStructFolders(templateDir,
		        pkgModule, pkgMapper, module, author);
		struct.execute();

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
		String ip = "202.114.93.215";
		String port = "1521";
		String dbname = "orcl";
		String username = "photo";
		String password = "photo";
		return ConnUtils.getOracleConn(ip, port, dbname, username, password);
	}
}
