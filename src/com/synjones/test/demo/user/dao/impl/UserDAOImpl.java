package com.synjones.test.demo.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synjones.test.demo.user.dao.IUserDAO;
import com.synjones.test.mapper.DemoUserMapper;

import framework.base.common.Pager;
import framework.base.dao.IBaseDAOSupport;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:24:17
 * 
 */
@Repository
@SuppressWarnings("unused")
public class UserDAOImpl implements IUserDAO
{
	@Autowired
	private IBaseDAOSupport daoSupport;

    private DemoUserMapper getMapper()
	{
		return daoSupport.getMapper(DemoUserMapper.class);
	}

	private String getMapperPrefix()
	{
		return DemoUserMapper.class.getName() + ".";
	}

	public int insert(Object param)
	{
		return daoSupport.insert(getMapperPrefix() + "insert", param);
	}

	public int delete(Object param)
	{
		return daoSupport.delete(getMapperPrefix() + "delete", param);
	}

	public int update(Object param)
	{
		return daoSupport.update(getMapperPrefix() + "update", param);
	}

	public <E> List<E> selectList(Object param)
	{
		return daoSupport.selectList(getMapperPrefix() + "selectList", param);
	}

	public <T> T selectOne(Object param)
	{
		return daoSupport.selectOne(getMapperPrefix() + "selectList", param);
	}

	public <E> Pager<E> selectPage(Object param, Pager<E> pager)
	{
		return daoSupport.selectPage(getMapperPrefix() + "selectList", param, pager);
	}

}
