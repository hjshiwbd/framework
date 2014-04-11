package framework.base.log.handler.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.base.bean.LogDefineBean;
import framework.base.log.handler.ILogHandler;
import framework.base.utils.ReflectUtil;

/**
 * 日志处理通用实现类
 * 
 * @author hjin
 * @cratedate 2013-9-4 下午4:13:10
 * 
 */
public abstract class LogHandlerSupport implements ILogHandler
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	protected ILogHandler target;
	protected LogDefineBean logDefine;
	protected HttpServletRequest request;

	@Override
	public String getLogContent(ILogHandler target, LogDefineBean logDefine,
	        HttpServletRequest request)
	{
		// 初始化公共参数
		initParameter(target, logDefine, request);

		// 设置当前实现类的方法参数
		setParams();

		// 反射执行方法,得到日志内容
		String content = (String) ReflectUtil.invokeMethod(target,
		        logDefine.getHandlermethod());
		if (logger.isDebugEnabled())
		{
			logger.debug("log content:" + content);
		}
		return content;
	}

	private void initParameter(ILogHandler target, LogDefineBean logDefine,
	        HttpServletRequest request)
	{
		this.target = target;
		this.logDefine = logDefine;
		this.request = request;
	}

	/**
	 * 设置全局参数
	 * 
	 * @author hjin
	 * @cratedate 2013-9-6 上午9:34:08
	 * 
	 */
	protected void setParams()
	{
		String parameter = logDefine.getHandlerparameter();
		String[] params = parameter.split(",");
		for (String property : params)
		{
			// 配置的参数
			property = property.replace("{", "").replace("}", "");
			if (logger.isDebugEnabled())
            {
				logger.debug("parameter:" + property);
            }
			String value = request.getParameter(property);

			// 根据配置从request中取值
			Object o = getRequestValue(property);
			String clazz1 = o.getClass().getName();
			if (logger.isDebugEnabled())
			{
				logger.debug("request value:" + o + " ,type:" + clazz1);
			}

			// 参数对应的set方法
			Method setMethod = ReflectUtil.findGetAndSetMethod(
			        target.getClass(), property)[1];
			String clazz2 = setMethod.getParameterTypes()[0].getName();
			if (logger.isDebugEnabled())
			{
				logger.debug("setMethod paramType:" + clazz2);
			}

			// request中的值与set方法参数类型一致
			if (clazz1.equals(clazz2))
			{
				ReflectUtil.invokeSetMethod(target, property, value);
			}
		}
	}

	/**
	 * 从request中取值.优先request.getParameter(),其次request.getAttribute()
	 * 
	 * @author hjin
	 * @cratedate 2013-9-6 上午8:51:23
	 * @return
	 * 
	 */
	private Object getRequestValue(String property)
	{
		// 执行request.getParameter
		Object value = request.getParameter(property);
		if (value == null)
		{
			value = request.getAttribute(property);
		}

		return value;
	}
}
