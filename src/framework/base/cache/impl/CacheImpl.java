package framework.base.cache.impl;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import framework.base.cache.Cache;

/**
 * 缓存实现类
 * 
 * @author hjin
 * 
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class CacheImpl implements Cache
{

	// 对缓存的对象数进行限制，只能在构造SimpCache对象时设定，不能改变
	private int Max_Size = 200;

	// 存在同样的key值,value对象是否替换的标记
	private boolean SameKeyReplaceValue = true;

	private Vector keys;

	private ConcurrentHashMap map;

	public CacheImpl()
	{
		map = new ConcurrentHashMap(Max_Size);
		keys = new Vector(Max_Size);
	}

	public CacheImpl(int maxSize)
	{
		Max_Size = maxSize;
		map = new ConcurrentHashMap(Max_Size);
		keys = new Vector(Max_Size);
	}

	/**
	 * 返回指定key的cache (non-Javadoc)
	 * 
	 * @param cacheKey
	 * @return
	 */
	public Object getCachedData(Object cacheKey)
	{
		return map.get(cacheKey);
	}

	/**
	 * 添加cache,如果添加的key已经在cache中存在,则用新值覆盖老值
	 * 
	 * @param cacheKey
	 * @param obj
	 */
	public synchronized void cache(Object cacheKey, Object obj)
	{
		/**
		 * 检查是否已有同样cacheKey的对象存在 如果有存在，而替换的标记被标为假的话，就不替换
		 */
		if ((!SameKeyReplaceValue) && (map.containsKey(cacheKey)))
		{
			return;
		}

		// 如果缓存的对象数已达到最大限制，就删除掉一个缓存对象
		if (keys.size() >= Max_Size)
		{
			removeOne();
		}
		if (!map.containsKey(cacheKey))
		{
			keys.add(cacheKey);
		}
		map.put(cacheKey, obj);
	}

	/**
	 * 删除掉缓存的第一个对象x
	 */
	private void removeOne()
	{
		map.remove(keys.remove(0));
	}

	/**
	 * 得到缓存的对象个数
	 * 
	 * @return
	 */
	public long getCachedSize()
	{
		return map.size();
	}

	/**
	 * 移除key对应的缓存对象
	 * 
	 * @param cacheKey
	 * @return
	 */
	public Object remove(Object cacheKey)
	{
		keys.removeElement(cacheKey);
		return map.remove(cacheKey);
	}

	/**
	 * 清空缓存
	 * 
	 * @return
	 */
	public void clear()
	{
		keys.clear();
		map.clear();
	}

	/**
	 * 查询cacheKey是否存在在缓存中
	 * 
	 * @param cacheKey
	 * @return
	 */
	public boolean contains(Object cacheKey)
	{
		return map.containsKey(cacheKey);
	}
}
