package framework.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.base.cache.Cache;
import framework.base.cache.impl.PublicCache;

/**
 * 缓存工具类
 * 
 * @author hjin
 * @cratedate 2013-8-27 下午3:23:34
 * 
 */
public class PublicCacheUtil
{
	private static Logger logger = LoggerFactory
	        .getLogger(PublicCacheUtil.class);
	private static Cache cache = PublicCache.getInstance();

	/**
	 * 获取String型缓存
	 * 
	 * @author hjin
	 * @cratedate 2013-8-29 下午3:36:31
	 * @param key
	 *            cacheKey
	 * @return
	 * 
	 */
	public static String getString(String key)
	{
		Object o = cache.getCachedData(key);
		if (o instanceof String)
		{
			return o.toString();
		}
		else
		{
			logger.info("no string cache:" + key);
			return null;
		}
	}

}
