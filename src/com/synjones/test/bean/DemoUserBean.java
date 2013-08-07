package com.synjones.test.bean;

/**
 * 示例用户对象
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午8:55:20
 * 
 */
public class DemoUserBean
{
	private Integer id;// 主键
	private String name;// 用户名
	private String nickname;// 昵称
	private String password;// 密码
	private java.util.Date birthday;// 生日
	private String gender;// 性别
	private String headpic;// 头像
	private java.util.Date registdate;// 注册日期
	private java.util.Date lastlogindate;// 最近登录日期
	private String status;// 状态.0否1是

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public java.util.Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(java.util.Date birthday)
	{
		this.birthday = birthday;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getHeadpic()
	{
		return headpic;
	}

	public void setHeadpic(String headpic)
	{
		this.headpic = headpic;
	}

	public java.util.Date getRegistdate()
	{
		return registdate;
	}

	public void setRegistdate(java.util.Date registdate)
	{
		this.registdate = registdate;
	}

	public java.util.Date getLastlogindate()
	{
		return lastlogindate;
	}

	public void setLastlogindate(java.util.Date lastlogindate)
	{
		this.lastlogindate = lastlogindate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}