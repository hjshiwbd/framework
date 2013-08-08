package demo.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.bean.DemoUserBean;
import demo.user.dao.IUserDAO;
import demo.user.service.IUserService;

import framework.base.common.Pager;
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
	public List<DemoUserBean> getPageList(DemoUserBean user)
	{
		return userDAO.selectList(user);
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
		userDAO.insert(user);
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
		return userDAO.selectOne(user);
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
		userDAO.update(user);
	}

	public Pager<DemoUserBean> getPageList(DemoUserBean user,
	        Pager<DemoUserBean> pager)
	{
		return userDAO.selectPage(user, pager);
	}

}
