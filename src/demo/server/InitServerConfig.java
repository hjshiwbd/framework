package demo.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import framework.base.cache.impl.PublicCache;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-20 上午9:40:23
 * 
 */
public class InitServerConfig extends HttpServlet
{
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		String serverPath = config.getServletContext().getRealPath("");
		PublicCache cache = PublicCache.getInstance();
		cache.cache("serverPath", serverPath);
	}
}
