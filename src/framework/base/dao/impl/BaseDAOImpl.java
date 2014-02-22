package framework.base.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import framework.base.common.Pager;
import framework.base.dao.IBaseDAO;
import framework.base.dao.IBaseDAOSupport;

/**
 * @author hjin
 * @cratedate 2013-9-14 下午6:28:53
 * 
 */
@Repository
public abstract class BaseDAOImpl implements IBaseDAO
{
	@Autowired
	private IBaseDAOSupport baseDAOSupport;

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#insert(java.lang.Object)
	 */
	@Override
	public int insert(Object param)
	{
		return baseDAOSupport.insert(getNamespace() + "insert", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#delete(java.lang.Object)
	 */
	@Override
	public int delete(Object param)
	{
		return baseDAOSupport.delete(getNamespace() + "delete", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#update(java.lang.Object)
	 */
	@Override
	public int update(Object param)
	{
		return baseDAOSupport.update(getNamespace() + "update", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#selectList(java.lang.Object)
	 */
	@Override
	public <E> List<E> selectList(Object param)
	{
		return baseDAOSupport.selectList(getNamespace() + "select", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#selectOne(java.lang.Object)
	 */
	@Override
	public <T> T selectOne(Object param)
	{
		return baseDAOSupport.selectOne(getNamespace() + "select", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#selectPage(java.lang.Object,
	 * framework.base.common.Pager)
	 */
	@Override
	public <E> Pager<E> selectPage(Object param, Pager<E> pager)
	{
		return baseDAOSupport.selectPage(getNamespace() + "select", param,
				pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.base.dao.IBaseDAO#selectCount()
	 */
	@Override
	public int selectCount(String table, String where, Object... param)
	{
		return baseDAOSupport.selectCount(table, where, param);
	}

}
