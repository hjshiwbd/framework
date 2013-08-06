package framework.base.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.jsonplugin.JSONUtil;

import framework.base.common.BaseConstants;

/**
 * 通用异常处理
 * 
 * @author hjin
 * @cratedate 2013-8-6 下午2:20:00
 * 
 */
public class BaseExceptionHandler implements HandlerExceptionResolver
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("rawtypes")
	public ModelAndView resolveException(HttpServletRequest request,
	        HttpServletResponse response, Object handler, Exception ex)
	{
		// 控制台输出
		ex.printStackTrace();

		// 处理异常内容
		String msg = ex.getMessage();
		if (msg.startsWith("{"))
		{
			// json格式
			try
			{
				Map msgObj = (Map) JSONUtil.deserialize(msg);
				// 错误信息
				request.setAttribute(BaseConstants.CommonPageParam.SHOW_MSG,
				        ((Map) msgObj)
				                .get(BaseConstants.CommonPageParam.SHOW_MSG));
				// 出错后跳转地址
				request.setAttribute(
				        BaseConstants.CommonPageParam.PAGE_CONTINUE_URL,
				        ((Map) msgObj)
				                .get(BaseConstants.CommonPageParam.PAGE_CONTINUE_URL));
			}
			catch (Exception jsonEx)
			{
				jsonEx.printStackTrace();
			}
		}
		else
		{
			// 一般字符串
			request.setAttribute(BaseConstants.CommonPageParam.SHOW_MSG, msg);
		}

		// 打印异常日志
		StackTraceElement[] stackTraceElement = ex.getStackTrace();
		StringBuffer errorInfo = new StringBuffer("errorStack:\r\n"
		        + ex.getClass().getName() + " " + ex.getMessage() + "\r\n");
		for (int i = 0; i < stackTraceElement.length; i++)
		{
			StackTraceElement sElement = stackTraceElement[i];
			errorInfo.append("\t" + sElement.toString() + "\r\n");
		}
		logger.error(errorInfo.toString());

		return new ModelAndView("common/common_result");
	}

}
