package framework.base.log.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import framework.base.bean.LogDefineBean;
import framework.base.log.handler.ILogHandler;
import framework.base.utils.JavaBeanUtil;

/**
 * @author hjin
 * @cratedate 2013-9-5 下午3:39:27
 * 
 */
@Component
public class CommonLogHandler implements ILogHandler
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * framework.base.log.handler.ILogHandler#getLogContent(framework.base.log
	 * .handler.ILogHandler, framework.base.bean.LogDefineBean,
	 * javax.servlet.http.HttpServletRequest)
	 */
	public String getLogContent(ILogHandler target, LogDefineBean logDefine,
	        HttpServletRequest request)
	{
		String content = logDefine.getContent();
		if (logger.isDebugEnabled())
		{
			logger.debug("content in templete:" + content);
		}

		// 正则解析content,匹配{*}格式.执行request.getParameter(*),替換content中內容
		Pattern pattern = Pattern.compile("\\{[\\S&&[^\\{]&&[^\\}]]*\\}");
		// Pattern pattern = Pattern.compile("\\{[\\S]*\\}");
		Matcher m = pattern.matcher(content);

		// 匹配结果
		List<String> parameterKeys = new ArrayList<String>();
		while (m.find())
		{
			String key = m.group();
			// {name} -> name
			key = key.substring(1, key.length() - 1);
			if (!parameterKeys.contains(key))
			{
				parameterKeys.add(key);
			}

			logger.debug("matchced key:" + key);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("parameterkeys:"
			        + JavaBeanUtil.toString(parameterKeys));
		}

		// 替换content中内容
		for (int i = 0; i < parameterKeys.size(); i++)
		{
			String key = parameterKeys.get(i);

			// 执行request.getParameter(*)
			String value = request.getParameter(key);

			if (logger.isDebugEnabled())
			{
				logger.debug("key:" + key + ",value:" + value);
			}

			if (value != null)
			{
				content = content.replace("{" + key + "}", value);
			}
			else
			{
				content = content.replace("{" + key + "}", "[no value for "
				        + key + "]");
			}
		}

		return content;
	}

}
