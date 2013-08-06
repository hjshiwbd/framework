package com.synjones.test.demo.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synjones.test.bean.DemoUserBean;
import com.synjones.test.demo.user.dao.IUserDAO;
import com.synjones.test.mapper.DemoUserMapper;

import framework.base.dao.IBaseDAO;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:24:17
 * 
 */
@Repository
public class UserDAOImpl implements IUserDAO
{
	@Autowired
	private IBaseDAO baseDAO;

	public DemoUserMapper getMapper()
	{
		return baseDAO.getMapper(DemoUserMapper.class);
	}

	public List<DemoUserBean> getList(DemoUserBean user)
	{
		// return baseDAO.selectList(
		// "com.synjones.test.mapper.DemoUserMapper.selectList", user);
		return getMapper().selectList(user);
	}

	public void saveUser(DemoUserBean user)
	{
		// baseDAO.insert("com.synjones.test.mapper.DemoUserMapper.insert",
		// user);
		getMapper().insert(user);
	}

	public void updateUser(DemoUserBean user)
	{
		getMapper().update(user);
	}
}
