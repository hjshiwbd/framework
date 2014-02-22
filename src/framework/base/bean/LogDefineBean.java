package framework.base.bean;

public class LogDefineBean extends BaseBean
{
	private String cutpoint;// 主键
	private String code;// 日志编码
	private String name;// 日志名称
	private String modulecode;// 模块编码
	private String modulename;// 模块名称
	private String content;// 日志内容
	private String handler;// 内容处理接口实现类
	private String handlermethod;// 实现类处理方法
	private String handlerparameter;// 处理方法参数
	private String status;// 状态.各位:0可用

	// #######################################################
	// ###################以下不与数据关联#####################
	// #######################################################
	private String isEnable;

	public String getCutpoint()
	{
		return cutpoint;
	}

	public void setCutpoint(String cutpoint)
	{
		this.cutpoint = cutpoint;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getModulecode()
	{
		return modulecode;
	}

	public void setModulecode(String modulecode)
	{
		this.modulecode = modulecode;
	}

	public String getModulename()
	{
		return modulename;
	}

	public void setModulename(String modulename)
	{
		this.modulename = modulename;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getHandler()
	{
		return handler;
	}

	public void setHandler(String handler)
	{
		this.handler = handler;
	}

	public String getHandlermethod()
	{
		return handlermethod;
	}

	public void setHandlermethod(String handlermethod)
	{
		this.handlermethod = handlermethod;
	}

	public String getHandlerparameter()
	{
		return handlerparameter;
	}

	public void setHandlerparameter(String handlerparameter)
	{
		this.handlerparameter = handlerparameter;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * 第一位
	 * 
	 * @return the isEnable
	 */
	public String getIsEnable()
	{
		if (isEnable != null)
		{
			return isEnable;
		}
		if (status != null)
		{
			isEnable = status.substring(0, 1);
			return isEnable;
		}
		return isEnable;
	}

	/**
	 * @param isEnable
	 *            the isEnable to set
	 */
	public void setIsEnable(String isEnable)
	{
		this.isEnable = isEnable;
	}
}