package framework.base.log.handler;

import javax.servlet.http.HttpServletRequest;

import framework.base.bean.LogDefineBean;

/**
 * 
 * @author hjin
 * @cratedate 2013-9-4 上午10:49:01
 * 
 */
public interface ILogHandler
{
	/**
	 * 获得日志内容
	 * 
	 * @author hjin
	 * @cratedate 2013-9-5 下午4:20:48
	 * @param target 实现类
	 * @param logDefine 日志定义
	 * @param request HttpServletRequest
	 * @return
	 * 
	 */
	public String getLogContent(ILogHandler target, LogDefineBean logDefine,
	        HttpServletRequest request);
}
