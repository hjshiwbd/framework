package framework.base.dao;

import java.util.List;

import framework.base.common.Pager;
import framework.base.mapper.BaseMapper;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-5 下午5:32:28
 * 
 */
public interface IBaseDAOSupport
{
	/**
	 * 构建mapper
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:50:38
	 * @param cls
	 * @return
	 * 
	 */
	public <T extends BaseMapper> T getMapper(Class<? extends BaseMapper> cls);

	/**
	 * 查询单个对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-5 下午5:38:25
	 * @param mybatisId
	 * @param param
	 * @return
	 * 
	 */
	public <T> T selectOne(String mybatisId, Object param);

	/**
	 * 查询列表
	 * 
	 * @author hjin
	 * @cratedate 2013-8-5 下午5:38:30
	 * @param mybatisId
	 * @param param
	 * @return
	 * 
	 */
	public <T> List<T> selectList(String mybatisId, Object param);

	/**
	 * 分页查询.当pager=null时执行selectList
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 上午11:46:27
	 * @param mybatisId
	 * @param param
	 * @param pager
	 *            非null时必须初始化curtPage,countPerPage;为null则执行selectList方法.
	 * @return
	 * 
	 */
	public <E> Pager<E> selectPage(String mybatisId, Object param, Pager<E> pager);

	/**
	 * 插入对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 上午10:17:47
	 * @param param
	 * @return
	 * 
	 */
	public int insert(String mybatisId, Object param);

	/**
	 * 修改对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:51:26
	 * @param mybatisId
	 * @param param
	 * @return
	 * 
	 */
	public int update(String mybatisId, Object param);

	/**
	 * 删除
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:51:46
	 * @param mybatisId
	 * @param param
	 * @return
	 * 
	 */
	public int delete(String mybatisId, Object param);

	/**
	 * 查询行数
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:55:59
	 * @param sql
	 * @return
	 * 
	 */
	public int selectCount(String table,String where, Object... param);
}
