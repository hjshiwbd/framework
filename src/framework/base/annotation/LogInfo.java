package framework.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-7 下午5:04:24
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo
{
	/**
	 * 日志描述
	 * @author hjin
	 * @cratedate 2013-8-7 下午5:17:28
	 * @return
	 *
	 */
	String[] value();
}
