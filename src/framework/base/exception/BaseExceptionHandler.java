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
import framework.base.common.BaseConstants.CommonPageParam;
import framework.base.utils.PublicCacheUtil;

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
		ModelAndView model = new ModelAndView();

		// 处理异常内容
		String msg = ex.getMessage();
		model.addObject(CommonPageParam.PROCESS_RESULT, "0");// 页面访问结果为出错
		model.addObject("errorType", "error");// 页面访问结果的内容类型,如业务成功/业务失败/未登录等
		if (msg != null && msg.startsWith("{"))
		{
			// json格式
			try
			{
				Map msgObj = (Map) JSONUtil.deserialize(msg);
				// 错误信息
				model.addObject(BaseConstants.CommonPageParam.SHOW_MSG,
				        ((Map) msgObj)
				                .get(BaseConstants.CommonPageParam.SHOW_MSG));
				// 出错后跳转地址
				model.addObject(
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
			model.addObject(BaseConstants.CommonPageParam.SHOW_MSG, msg);
			model.addObject(BaseConstants.CommonPageParam.PAGE_CONTINUE_URL,
			        "/");
		}

		// 控制台日志输出
		ex.printStackTrace();

		// 文件日志输出
		StackTraceElement[] stackTraceElement = ex.getStackTrace();
		StringBuffer errorInfo = new StringBuffer("errorStack:\r\n" + ">>>\t"
		        + ex.getClass().getName() + " " + ex.getMessage() + "\r\n");
		for (int i = 0; i < stackTraceElement.length; i++)
		{
			StackTraceElement sElement = stackTraceElement[i];
			errorInfo.append(">>>\t" + sElement.toString() + "\r\n");
		}
		logger.error(errorInfo.toString());

		model.setViewName(PublicCacheUtil
		        .getString(CommonPageParam.GLOBAL_PAGE_RESULT));
		return model;
	}

}
