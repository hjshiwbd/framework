package framework.base.dao;

import java.util.List;

import framework.base.common.Pager;

/**
 * 基础的dao方法:增删改查分页
 * 
 * @author hjin
 * @cratedate 2013-8-7 下午2:27:00
 * 
 */
public interface IBaseDAO
{
	/**
	 * 保存对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 下午2:45:09
	 * @param param
	 * @return
	 * 
	 */
	public int insert(Object param);

	/**
	 * 删除对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 下午2:45:18
	 * @param param
	 * @return
	 * 
	 */
	public int delete(Object param);

	/**
	 * 修改对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 下午2:45:40
	 * @param param
	 * @return
	 * 
	 */
	public int update(Object param);

	/**
	 * 列表查询
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 下午2:45:36
	 * @param param
	 * @return
	 * 
	 */
	public <E> List<E> selectList(Object param);

	/**
	 * 单对象查询
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 下午2:45:28
	 * @param param
	 * @return
	 * 
	 */
	public <T> T selectOne(Object param);

	/**
	 * 分页查询
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 下午2:45:24
	 * @param param
	 * @param pager
	 * @return
	 * 
	 */
	public <E> Pager<E> selectPage(Object param, Pager<E> pager);
}
