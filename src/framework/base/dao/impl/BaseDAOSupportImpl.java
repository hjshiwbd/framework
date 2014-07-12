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

	@Override
	public <T> T selectOne(String mybatisId, Object param)
	{
		return getSqlSession().selectOne(mybatisId, param);
	}

	@Override
	public <T> List<T> selectList(String mybatisId, Object param)
	{
		long l1 = System.currentTimeMillis();
		List<T> list = getSqlSession().selectList(mybatisId, param);
		long l2 = System.currentTimeMillis();
		if (logger.isDebugEnabled())
		{
			logger.debug("selectList:" + (l2 - l1));
		}
		return list;
	}

	@Override
	public int insert(String mybatisId, Object param)
	{
		return getSqlSession().insert(mybatisId, param);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends BaseMapper> T getMapper(Class<? extends BaseMapper> cls)
	{
		return (T) getSqlSession().getMapper(cls);
	}

	@Override
	public int update(String mybatisId, Object param)
	{
		return getSqlSession().update(mybatisId, param);
	}

	@Override
	public int delete(String mybatisId, Object param)
	{
		return getSqlSession().delete(mybatisId, param);
	}

	@Override
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

	@Override
	public <E> Pager<E> selectPage(String mybatisId, Object param,
	        Pager<E> pager)
	{
		if (pager == null)
		{
			// pager为空,表示查全表
			List<E> list = getSqlSession().selectList(mybatisId, param);

			// pageList中包含查询结果
			pager = new Pager<E>();
			pager.setPageList(list);
			return pager;
		}
		else
		{
			// 构建PageBounds查询对象
			PageBounds pageBounds = new PageBounds(pager.getCurtPage(),
			        pager.getCountPerPage(), Order.formString(
			                pager.getOrderby(), pager.getOrderbyFormat()),
			        pager.isContainsTotalCount());

			// 查列表
			long l1 = System.currentTimeMillis();
			List<E> list = getSqlSession().selectList(mybatisId, param,
			        pageBounds);
			long l2 = System.currentTimeMillis();
			if (logger.isDebugEnabled())
			{
				logger.debug("select1:" + (l2 - l1));
			}
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
}
