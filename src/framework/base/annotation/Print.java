package framework.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author hjin
 * @cratedate 2013-5-7 下午5:53:22
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Print
{
	boolean isPrint() default true;
}
