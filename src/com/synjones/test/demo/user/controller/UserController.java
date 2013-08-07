package com.synjones.test.demo.user.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synjones.test.bean.DemoUserBean;
import com.synjones.test.demo.user.service.IUserService;

import framework.base.annotation.LogInfo;
import framework.base.common.Pager;
import framework.base.controller.BaseController;
import framework.base.utils.JavaBeanUtil;

/**
 * 用户类请求控制器
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午9:21:47
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController
{

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	/**
	 * 用户列表
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:43:15
	 * @return
	 * 
	 */
	@RequestMapping("/list")
	@LogInfo("aaa")
	public String list()
	{
		DemoUserBean search = new DemoUserBean();
		// search.setId(1);
		// search.setName("admin");

		Pager<DemoUserBean> pager = new Pager<DemoUserBean>();
		pager.setCountPerPage(2);
		
		userService.getPageList(search, pager);
		logger.debug("userlist:" + JavaBeanUtil.toString(pager.getPageList()));
		request.setAttribute("userList", pager.getPageList());
		return "demoUser/user_list";
	}

	/**
	 * 用户新增初始化
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:43:22
	 * @return
	 * 
	 */
	@RequestMapping("/add_user")
	public String toAddUser()
	{
		return "demoUser/add_user";
	}

	/**
	 * 用户新增
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:43:29
	 * @param user
	 * @return
	 * 
	 */
	@RequestMapping("/do_add_user")
	public String doAddUser(DemoUserBean user)
	{
		logger.info("to add user:" + JavaBeanUtil.toString(user));

		userService.saveUser(user);
		logger.info("add user successfully");
		return "redirect:/user/list";
	}

	/**
	 * 用户编辑初始化
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:43:41
	 * @param userid
	 * @return
	 * 
	 */
	@RequestMapping("/edit/{userid}")
	public String editUser(@PathVariable String userid)
	{
		DemoUserBean user = new DemoUserBean();
		user.setId(Integer.parseInt(userid));
		user = userService.getUser(user);
		request.setAttribute("user", user);

		return "demoUser/user_edit";
	}

	/**
	 * 用户编辑
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:43:48
	 * @param user
	 * @return
	 * 
	 */
	@RequestMapping("/do_edit")
	public String doEditUser(DemoUserBean user)
	{
		logger.info("do edit user:" + JavaBeanUtil.toString(user));
		userService.updateUser(user);
		logger.info("update user successfully");

		return "redirect:/user/edit/" + user.getId();
	}

	@Override
	public void setLogger()
	{
		logger = LoggerFactory.getLogger(this.getClass());
	}
}
