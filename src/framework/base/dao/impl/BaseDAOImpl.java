package framework.base.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import framework.base.dao.IBaseDAO;
import framework.base.mapper.BaseMapper;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-5 下午5:34:48
 * 
 */
@Repository
public class BaseDAOImpl extends SqlSessionDaoSupport implements IBaseDAO
{
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
	public int selectCount(String sql, Object... param)
	{
		return jdbcTemplate.queryForObject(sql, Integer.class, param);
	}
}
