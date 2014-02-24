package tool.app;

import tool.utils.ConnUtils;

import java.sql.Connection;

public class CreateFile
{
    public static void main(String[] args) throws Exception
    {
        // bean,mapper.xml
        m1();

        // struct
        m2();
    }

    public static void m1()
    {
        String tableName = "p_word";
        String beanFileName = "WordBean";
        String packageName = "com.jap.bean";
        String folderPath = "F:/workspaces/eclipse_jee_20140222/jap/src/com/jap/bean";
        CreateJavaBeanFile c = new CreateJavaBeanFile(getConn(), tableName,
                beanFileName, packageName, folderPath, CreateJavaBeanFile.DB_TYPE_MYSQL);
        c.createBeanFile(true);

        String mybatisFolder = "F:/workspaces/eclipse_jee_20140222/jap/src/config/mybatis";
        packageName = "com.jap.mapper.";
        MybatisSqlGenerator mybatis = new MybatisSqlGenerator(getConn(),
                packageName, tableName, beanFileName, mybatisFolder,
                MybatisSqlGenerator.DB_TYPE_MYSQL);
        mybatis.createMapperFile(true);
    }


    public static void m2() throws Exception
    {
        String templateDir = "F:/!job/github/framework";
        String pkgModule = "com.jap";
        String pkgMapper = "com.jap.mapper";
        String module = "dict";
        String author = "hjin";
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
