package framework.base.cache;

/**
 * 缓存接口
 * 
 * @author hjin
 * 
 */
public interface Cache {

	/**
	 * 缓存对象
	 * 
	 * @param cacheKey
	 * @param obj
	 */
	public void cache(Object cacheKey, Object obj);

	/**
	 * 返回指定的cache
	 * 
	 * @param cacheKey
	 *            缓存
	 * @return
	 */
	public Object getCachedData(Object cacheKey);

	/**
	 * 返回cache的大小
	 * 
	 * @return
	 */
	public long getCachedSize();

	/**
	 * 查询cacheKey是否存在在缓存中
	 * 
	 * @param cacheKey
	 * @return
	 */
	public boolean contains(Object cacheKey);

	/**
	 * 删除指定的cache
	 * 
	 * @param cacheKey
	 * @return
	 */
	public Object remove(Object cacheKey);

	/**
	 * 清空缓存
	 * 
	 * @return
	 */
	public void clear();

}
