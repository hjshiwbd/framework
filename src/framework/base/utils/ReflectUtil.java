/**
 * 
 */
package framework.base.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import framework.base.bean.UploadProcessBean;

/**
 * 反射工具类
 * 
 * @author hjin
 * @cratedate 2013-9-2 下午3:55:06
 * 
 */
public class ReflectUtil
{
	private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

	public static void main(String[] args)
	{
		// 初始化
		UploadProcessBean bean = (UploadProcessBean) newInstance(UploadProcessBean.class
		        .getName());
		// 执行set方法
		invokeSetMethod(bean, "percent", "11中文");
		// 执行get方法
		System.out.println(invokeGetMethod(bean, "percent"));
	}

	/**
	 * 新建实例
	 * 
	 * @param className
	 *            类名
	 * @param constructorArgs
	 *            构造函数的参数
	 * @return 新建的实例
	 * @throws Exception
	 */
	public static Object newInstance(String className,
	        Object... constructorArgs)
	{
		try
		{
			Class<?> newoneClass = Class.forName(className);
			if (constructorArgs == null)
			{
				Constructor<?> cons = newoneClass.getConstructor();
				return cons.newInstance();
			}

			Class<?>[] argsClass = new Class[constructorArgs.length];
			for (int i = 0, j = constructorArgs.length; i < j; i++)
			{
				if (constructorArgs[i] != null)
				{
					argsClass[i] = constructorArgs[i].getClass();
				}
				else
				{
					argsClass[i] = null;
				}
			}
			Constructor<?> cons = newoneClass.getConstructor(argsClass);
			return cons.newInstance(constructorArgs);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 反射执行get方法
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午4:03:46
	 * @param target
	 *            对象
	 * @param propertyName
	 *            属性名
	 * @return
	 * 
	 */
	public static Object invokeGetMethod(Object target, String propertyName)
	{
		if (target != null)
		{
			// 找到对应的get方法
			Method method = ReflectionUtils.findMethod(target.getClass(),
			        getGetMethodName(propertyName));

			return invokeMethod(target, method);
		}
		else
		{
			logger.info("target is null");
			return null;
		}

	}

	/**
	 * 反射执行set方法
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午4:03:06
	 * @param target
	 *            对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            赋值
	 * 
	 */
	public static void invokeSetMethod(Object target, String propertyName,
	        Object value)
	{
		if (target != null)
		{
			// set方法名
			String methodName = getSetMethodName(propertyName);
			// 找到property
			Field field = ReflectionUtils.findField(target.getClass(),
			        propertyName);
			// 找到对应的set方法
			Method method = ReflectionUtils.findMethod(target.getClass(),
			        methodName, field.getType());

			invokeMethod(target, method, value);
		}
		else
		{
			logger.info("target is null");
		}
	}

	/**
	 * Attempt to find a Method on the supplied target's class with the supplied
	 * name and no parameters. Searches all superclasses up to Object. And
	 * invoke this method.
	 * 
	 * @author hjin
	 * @cratedate 2013-9-4 下午4:02:22
	 * @param target
	 *            目标
	 * @param methodName
	 *            方法名
	 * @param objects
	 *            参数
	 * @return
	 * 
	 */
	public static Object invokeMethod(Object target, String methodName,
	        Object... objects)
	{
		Method method = ReflectionUtils.findMethod(target.getClass(),
		        methodName);
		return invokeMethod(target, method, objects);
	}

	/**
	 * Attempt to find a Method on the supplied class with the supplied name and
	 * parameter types. Searches all superclasses up to Object. And invoke this
	 * method.
	 * 
	 * @author hjin
	 * @cratedate 2013-9-4 下午4:07:23
	 * @param target
	 *            目标
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            参数类型
	 * @param parameters
	 *            参数
	 * @return
	 * 
	 */
	public static Object invokeMethod(Object target, String methodName,
	        Class<?>[] parameterTypes, Object[] parameters)
	{
		Method method = ReflectionUtils.findMethod(target.getClass(),
		        methodName, parameterTypes);
		return invokeMethod(target, method, parameters);
	}

	/**
	 * 反射执行方法
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午4:02:55
	 * @param target
	 *            对象
	 * @param method
	 *            方法
	 * @param parameters
	 *            参数
	 * 
	 */
	public static Object invokeMethod(Object target, Method method,
	        Object... parameters)
	{
		if (target == null)
		{
			logger.info("target is null");
			return null;
		}
		if (method == null)
		{
			logger.info("method is null");
			return null;
		}
		return ReflectionUtils.invokeMethod(method, target, parameters);
	}

	/**
	 * 根据名称找方法
	 * 
	 * @author hjin
	 * @cratedate 2013-9-9 下午2:00:38
	 * @param clazz
	 * @param name
	 *            方法名
	 * @return
	 * 
	 */
	public static Method findMethodByName(Class<?> clazz, String name)
	{
		Method result = null;
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
		for (Method method : methods)
		{
			String mname = method.getName();
			if (mname.equals(name))
			{
				result = method;
				break;
			}
		}
		return result;
	}

	/**
	 * 反射匹配方法
	 * 
	 * @author hjin
	 * @cratedate 2013-9-6 上午8:53:49
	 * @param clazz
	 * @param name
	 * @param paramTypes
	 * @return
	 * 
	 */
	public static Method findMethodByParamType(Class<?> clazz, String name,
	        Class<?>... paramTypes)
	{
		if (paramTypes == null)
		{
			return ReflectionUtils.findMethod(clazz, name);
		}
		else
		{
			return ReflectionUtils.findMethod(clazz, name, paramTypes);
		}
	}

	/**
	 * 获得get/set方法
	 * 
	 * @author hjin
	 * @cratedate 2013-9-6 上午9:09:40
	 * @param clazz
	 * @param property
	 * @return [0]get,[1]set
	 * 
	 */
	public static Method[] findGetAndSetMethod(Class<?> clazz, String property)
	{
		Method[] result = new Method[2];
		// get方法
		String getmethodName = ReflectUtil.getGetMethodName(property);
		Method getMethod = ReflectUtil.findMethodByParamType(clazz,
		        getmethodName);
		if (getMethod == null)
		{
			logger.info("no get method for property " + property);

			result[0] = null;
			result[1] = null;
			return result;
		}
		else
		{
			// get方法返回类型
			Class<?> cls = getMethod.getReturnType();

			// set方法
			String setmethod = ReflectUtil.getSetMethodName(property);
			Method setMethod = ReflectUtil.findMethodByParamType(clazz,
			        setmethod, cls);

			result[0] = getMethod;
			result[1] = setMethod;
			return result;
		}
	}

	/**
	 * 属性对应的set方法名字
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午4:01:55
	 * @param propertyName
	 *            属性名
	 * @return
	 * 
	 */
	public static String getGetMethodName(String propertyName)
	{
		return "get" + StringUtils.capitalize(propertyName);
	}

	/**
	 * 属性对应的set方法名字
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午4:01:55
	 * @param propertyName
	 *            属性名
	 * @return
	 * 
	 */
	public static String getSetMethodName(String propertyName)
	{
		return "set" + StringUtils.capitalize(propertyName);
	}
}
