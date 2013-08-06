package com.synjones.test.demo.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synjones.test.bean.DemoUserBean;
import com.synjones.test.demo.user.dao.IUserDAO;
import com.synjones.test.demo.user.service.IUserService;

import framework.base.exception.BaseException;
import framework.base.service.impl.BaseServiceImpl;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:28:21
 * 
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService

{
	@Autowired
	private IUserDAO userDAO;

	/**
	 * 列表查询
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午1:06:02
	 * @param user
	 * @return
	 * 
	 */
	public List<DemoUserBean> getList(DemoUserBean user)
	{
		return userDAO.getList(user);
	}

	/**
	 * 保存用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午1:06:29
	 * @param user
	 * 
	 */
	public void saveUser(DemoUserBean user)
	{
		userDAO.saveUser(user);
	}

	/**
	 * 查询单个用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午3:12:49
	 * @param user
	 * @return
	 * 
	 */
	public DemoUserBean getUser(DemoUserBean user)
	{
		List<DemoUserBean> users = getList(user);
		if (users != null)
		{
			if (users.size() == 1)
			{
				return users.get(0);
			}
			else
			{
				throw new BaseException(
				        "more than 1 row result found, expected one");
			}
		}
		else
		{
			throw new BaseException("no result found, expected one");
		}
	}

	/**
	 * 修改用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:06:18
	 * @param user
	 * 
	 */
	public void updateUser(DemoUserBean user)
	{
		userDAO.updateUser(user);
	}

}
