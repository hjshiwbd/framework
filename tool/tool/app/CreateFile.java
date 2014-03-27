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
		createStructure();
	}

	public static void createFile()
	{
		String databaseName = "demo";
		String tableName = "p_test";
		String beanFileName = "TestBean";
		String packageName = "com.jap.bean";
		String folderPath = "F:/workspaces/eclipse_jee_20140222/jap/src/com/jap/bean";
		CreateJavaBeanFile c = new CreateJavaBeanFile(getConn(), databaseName,
		        tableName, beanFileName, packageName, folderPath,
		        CreateJavaBeanFile.DB_TYPE_MYSQL);
		c.createBeanFile(true);

		String mybatisFolder = "F:/workspaces/eclipse_jee_20140222/jap/src/config/mybatis";
		packageName = "com.jap.mapper.";
		MybatisSqlGenerator mybatis = new MybatisSqlGenerator(getConn(),
		        packageName, databaseName, tableName, beanFileName,
		        mybatisFolder, MybatisSqlGenerator.DB_TYPE_MYSQL);
		mybatis.createMapperFile(true);
	}

	public static void createStructure() throws Exception
	{
		String templateDir = "F:/github/framework";
		String pkgModule = "com.jap";
		String pkgMapper = "com.jap.mapper";
		String module = "test";
		String author = "wy";
		CreateStructFolders struct = new CreateStructFolders(templateDir,
		        pkgModule, pkgMapper, module, author);
		struct.execute();

	}

	/**
	 * jdbc数据库连接
	 */
	public static Connection getConn()
	{
		String ip = "localhost";
		String port = "3306";
		String dbname = "jap";
		String username = "root";
		String password = "root";
		return ConnUtils.getMysqlConn(ip, port, dbname, username, password);
	}
}
