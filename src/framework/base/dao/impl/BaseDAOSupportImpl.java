package framework.base.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import framework.base.common.Pager;
import framework.base.dao.IBaseDAOSupport;
import framework.base.mapper.BaseMapper;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-5 下午5:34:48
 * 
 */
public class BaseDAOSupportImpl extends SqlSessionDaoSupport implements
		IBaseDAOSupport
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

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
	public <T> T selectOne(String mybatisId, Object param)
	{
		return getSqlSession().selectOne(mybatisId, param);
	}

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
	public <T> List<T> selectList(String mybatisId, Object param)
	{
		return getSqlSession().selectList(mybatisId, param);
	}

	/**
	 * 插入对象
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 上午10:17:47
	 * @param param
	 * @return
	 * 
	 */
	public int insert(String mybatisId, Object param)
	{
		return getSqlSession().insert(mybatisId, param);
	}

	/**
	 * 构建mapper
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:50:38
	 * @param cls
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseMapper> T getMapper(Class<? extends BaseMapper> cls)
	{
		return (T) getSqlSession().getMapper(cls);
	}

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
	public int update(String mybatisId, Object param)
	{
		return getSqlSession().update(mybatisId, param);
	}

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
	public int delete(String mybatisId, Object param)
	{
		return getSqlSession().delete(mybatisId, param);
	}

	/**
	 * 查询行数
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:55:59
	 * @param sql
	 * @return
	 * 
	 */
	public int selectCount(String table, String where, Object... param)
	{
		String sql = "select count(1) from " + table + where;
		if (logger.isDebugEnabled())
		{
			logger.debug("select count sql:" + sql);
			logger.debug("select count param:" + Arrays.toString(param));
		}
		return jdbcTemplate.queryForObject(sql, Integer.class, param);
	}

	/**
	 * 分页查询
	 * 
	 * @author hjin
	 * @cratedate 2013-8-7 上午11:46:27
	 * @param mybatisId
	 * @param param
	 * @param pager
	 *            必须初始化curtPage,countPerPage
	 * @return
	 * 
	 */
	public <E> Pager<E> selectPage(String mybatisId, Object param,
			Pager<E> pager)
	{
		// 构建PageBounds查询对象
		PageBounds pageBounds = new PageBounds(pager.getCurtPage(),
				pager.getCountPerPage(), Order.formString(pager.getOrderby()),
				pager.isContainsTotalCount());

		// 查列表
		List<E> list = getSqlSession().selectList(mybatisId, param, pageBounds);
		pager.setPageList(list);

		// 查总行数
		if (pager.isContainsTotalCount())
		{
			// 允许查询总行数
			PageList<E> l = (PageList<E>) list;
			pager.setTotal(l.getPaginator().getTotalCount());
		}

		return pager;
	}
}
