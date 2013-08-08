package demo.index.action;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import framework.base.controller.BaseController;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-5 下午2:22:08
 * 
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController
{

	@Override
	public void setLogger()
	{
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@RequestMapping("/welcome")
	public ModelAndView welcome()
	{
		model.addObject("username", "testuser");
		model.setViewName("index/welcome");
		return model;
	}

}
