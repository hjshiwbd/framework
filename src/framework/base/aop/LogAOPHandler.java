package framework.base.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import framework.base.annotation.LogInfo;
import framework.base.utils.JavaBeanUtil;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-7 下午3:44:02
 * 
 */
@Aspect
@Component
public class LogAOPHandler
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	private HttpServletRequest request;
	private HttpSession session;

	/**
	 * 只对controller中的方法进行日志记录
	 * 
	 * @author hjin
	 * @cratedate: 2012-11-2 上午10:58:19
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 * 
	 */
	// @After("(execution(* demo.*.controller.*.*(..)) || execution(* demo.*.*.controller.*.*(..)))")
	// @After("execution(@framework.base.annotation.LogInfo * *(..))")
	@AfterReturning("@annotation(loginfo)")
	// @AfterReturning("within(com.abchina.irms..*) && @annotation(rl)")
	public void logHandler(JoinPoint jp, LogInfo loginfo) throws Throwable
	{
		// 初始化http参数
		initHttpAttribute();
		
		// 类名
		String className = jp.getTarget().getClass().getSimpleName();
		// 获取目标方法名
		String signature = jp.getSignature().toString();
		String methodName = signature.substring(signature.lastIndexOf(".") + 1);
		// 注解
		String desc = loginfo.value();

		logger.info("cutpoint=" + className + "." + methodName + ".@desc="
		        + desc);
	}

	public void initHttpAttribute()
	{
		request = ((ServletRequestAttributes) RequestContextHolder
		        .getRequestAttributes()).getRequest();
		session = request.getSession();
	}
}
