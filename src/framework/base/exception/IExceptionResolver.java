package framework.base.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 基于spring exception处理的扩展
 * @author hjin
 *
 */
public interface IExceptionResolver extends HandlerExceptionResolver
{
	public String getExceptionClass();
	public void setExceptionClass(String exceptionClass);
	public void setViewName(String viewName);
}
