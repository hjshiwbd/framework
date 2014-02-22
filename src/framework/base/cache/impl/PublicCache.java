package framework.base.cache.impl;

import framework.base.cache.Cache;

/**
 * 系统公共缓存管理,单例模式
 * 
 * @author hjin
 * 
 */
public class PublicCache implements Cache
{

	private static int Max_Size = 200; // cache的最大值

	private static PublicCache instance = new PublicCache(); // PublicCache的实例，单例摸式

	private Cache _cache; // cache类

	private PublicCache()
	{
		_cache = new CacheImpl(Max_Size);
	}

	public static PublicCache getInstance()
	{
		return instance;
	}

	public synchronized void cache(Object cacheKey, Object obj)
	{
		_cache.cache(cacheKey, obj);
	}

	public Object getCachedData(Object cacheKey)
	{
		return _cache.getCachedData(cacheKey);
	}

	public long getCachedSize()
	{
		return _cache.getCachedSize();
	}

	public synchronized Object remove(Object cacheKey)
	{
		return _cache.remove(cacheKey);
	}

	public synchronized void clear()
	{
		_cache.clear();
	}

	public boolean contains(Object cacheKey)
	{
		return _cache.contains(cacheKey);
	}

}
