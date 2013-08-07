package com.synjones.test.demo.user.service;

import com.synjones.test.bean.DemoUserBean;

import framework.base.common.Pager;
import framework.base.service.IBaseService;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:26:50
 * 
 */
public interface IUserService extends IBaseService
{
	/**
	 * 列表查询
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午1:06:02
	 * @param user
	 * @return
	 * 
	 */
	public Pager<DemoUserBean> getPageList(DemoUserBean user,Pager<DemoUserBean> pager);

	/**
	 * 查询单个用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午3:12:49
	 * @param user
	 * @return
	 * 
	 */
	public DemoUserBean getUser(DemoUserBean user);

	/**
	 * 保存用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午1:06:29
	 * @param user
	 * 
	 */
	public void saveUser(DemoUserBean user);

	/**
	 * 修改用户
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:06:18
	 * @param user
	 * 
	 */
	public void updateUser(DemoUserBean user);
}
