package framework.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import framework.base.annotation.LogInfo;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-7 下午3:44:02
 * 
 */
@Aspect
public class LogAOPHandler
{
	private Logger logger = LoggerFactory.getLogger(getClass());

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
	@After("(execution(* com.synjones.test.*.controller.*.*(..)) || execution(* com.synjones.test.*.*.controller.*.*(..)))")
//	@After("execution(@framework.base.annotation.LogInfo * *(..))")
//	@AfterReturning("within(com.synjones.test.demo.user..*)")
//	@AfterReturning("within(com.abchina.irms..*) && @annotation(rl)")  
	public void logHandler(JoinPoint jp) throws Throwable
	{
		// 类名
		String className = jp.getTarget().getClass().getSimpleName();
		// 获取目标方法名
		String signature = jp.getSignature().toString();
		String methodName = signature.substring(signature.lastIndexOf(".") + 1);

		logger.info("cutpoint=" + className + "." + methodName);
	}
}
