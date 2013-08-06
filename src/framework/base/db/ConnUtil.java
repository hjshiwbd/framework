package framework.base.db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 获取数据库连接
 * 
 * @author hjin
 * @cratedate 2013-7-30 上午10:21:14
 * 
 */
public class ConnUtil
{
	public SqlSession getConn()
	{
		String resource = "framework/config/mybatis/mybatis-config.xml";
		try
		{
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder()
			        .build(inputStream);
			SqlSession session = factory.openSession();
			return session;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
