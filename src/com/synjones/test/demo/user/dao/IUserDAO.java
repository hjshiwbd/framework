package com.synjones.test.demo.user.dao;

import java.util.List;

import com.synjones.test.bean.DemoUserBean;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:23:53
 * 
 */
public interface IUserDAO
{
	/**
	 * 查询用户列表
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 上午10:19:31
	 * @param user
	 * @return
	 * 
	 */
	public List<DemoUserBean> getList(DemoUserBean user);

	/**
	 * 保存用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 上午10:19:27
	 * @param user
	 * 
	 */
	public void saveUser(DemoUserBean user);

	/**
	 * 修改用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:03:36
	 * @param user
	 * 
	 */
	public void updateUser(DemoUserBean user);
}
