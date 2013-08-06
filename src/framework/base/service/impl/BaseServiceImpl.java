package framework.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import framework.base.service.IBaseService;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 下午1:22:17
 * 
 */
public class BaseServiceImpl implements IBaseService, InitializingBean
{
	protected Logger logger;

	public void afterPropertiesSet() throws Exception
	{
		logger = LoggerFactory.getLogger(getClass());
	}

}
