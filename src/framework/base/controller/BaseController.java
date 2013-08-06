package framework.base.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import framework.base.common.BaseConstants;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-5 上午11:01:11
 * 
 */
@Controller
public abstract class BaseController implements Serializable
{
	/**
	 * @description
	 */
	private static final long serialVersionUID = -3740647827551448127L;
	/**
	 * request
	 */
	protected HttpServletRequest request;
	/**
	 * response
	 */
	protected HttpServletResponse response;
	/**
	 * session
	 */
	protected HttpSession session;
	/**
	 * 日志对象
	 */
	protected Logger logger;
	/**
	 * model对象
	 */
	protected ModelAndView model = new ModelAndView();

	/**
	 * 初始化参数
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:28:29
	 * @param request
	 * @param response
	 * 
	 */
	@ModelAttribute
	public void setReqRespSession(HttpServletRequest request,
			HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	/**
	 * 初始化日志对象,在子类中进行实现,以子类的class进行初始化
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午4:28:53
	 * 
	 */
	@ModelAttribute
	public abstract void setLogger();

	public Logger getLogger()
	{
		return logger;
	}

	public ModelAndView returnOk(String msg, String continueUrl)
	{
		ModelAndView m = new ModelAndView("common/common_result");
		m.addObject(BaseConstants.CommonPageParam.SHOW_MSG, msg);
		if (continueUrl != null)
		{
			request.setAttribute(
					BaseConstants.CommonPageParam.PAGE_CONTINUE_URL,
					continueUrl);
		}
		return m;
	}

}
