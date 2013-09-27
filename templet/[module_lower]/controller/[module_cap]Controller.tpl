package [module_pkg].[module_lower].controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import framework.base.controller.BaseController;

/**
 * 
 * @author [author]
 * @cratedate [createdate]
 * 
 */
@Controller
@RequestMapping("/[module_lower]")
public class [module_cap]Controller extends BaseController
{
	private static final long serialVersionUID = 1L;

	@Override
	@ModelAttribute
	public void setLogger()
	{
		logger = LoggerFactory.getLogger(getClass());
	}

}
