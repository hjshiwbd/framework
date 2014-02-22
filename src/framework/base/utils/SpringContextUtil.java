package framework.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean.单例模式
 * 
 * @author hjin
 * @cratedate 2013-9-5 下午2:09:29
 * 
 */
@Component
public class SpringContextUtil implements ApplicationContextAware
{
	private static Logger logger = LoggerFactory
	        .getLogger(SpringContextUtil.class);

	private static SpringContextUtil springBeanGetter = null;

	private static ApplicationContext applicationContext;

	private SpringContextUtil()
	{
	}

	public static SpringContextUtil getInstance()
	{
		if (springBeanGetter == null)
		{
			synchronized (SpringContextUtil.class)
			{
				if (springBeanGetter == null)
				{
					springBeanGetter = new SpringContextUtil();
				}
			}
		}
		return springBeanGetter;
	}

	/**
	 * 根据id查询context,得到spring bean
	 * 
	 * @author hjin
	 * @cratedate 2013-9-5 下午2:36:59
	 * @param beanid
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanid)
	{
		if (applicationContext != null)
		{
			return (T) applicationContext.getBean(beanid);
		}
		else
		{
			logger.info("applicationContext is null");
			return null;
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
	        throws BeansException
	{
		SpringContextUtil.applicationContext = applicationContext;
	}

}
