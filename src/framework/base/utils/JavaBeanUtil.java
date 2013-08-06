package framework.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import framework.base.annotation.Print;

/**
 * javabean基础类
 * 
 * @author hjin
 * 
 */
@SuppressWarnings("all")
public class JavaBeanUtil
{

	public static void main(String[] args)
	{

	}

	/**
	 * 打印所有属性,不显示annotation,打印null,使用IsPrint注解
	 * 
	 * @author hjin
	 * @cratedate 2012-12-17 下午3:23:16
	 * @param obj
	 * @return
	 * 
	 */
	public static String toString(Object obj)
	{
		return toString(obj, false, false, true);
	}

	/**
	 * 生成日志所需的属性(有注解,不为null)
	 * 
	 * @author hjin
	 * @cratedate 2012-12-26 下午3:24:26
	 * @param obj
	 * @return
	 * 
	 */
	public static String writelog(Object obj)
	{
		return toString(obj, true, false, true);
	}

	/**
	 * 打印对象内所有属性值
	 * 
	 * @param obj
	 * @param isAnnotation
	 *            是否只打印有注解的属性.true:只打印有注解的属性;false:不论是否存在注解都打印英文属性
	 * @param isShowNull
	 *            是否打印为null的属性值
	 * @param isPrint
	 *            是否读取并使用Print注解的值
	 * @return
	 */
	public static String toString(Object obj, boolean isAnnotation,
			boolean isShowNull, boolean isPrint)
	{
		if (obj == null)
		{
			return "null";
		}
		// 输出字符串
		String output = "";
		// 当obj为List类型
		if (obj instanceof List)
		{
			StringBuffer buffer = new StringBuffer(obj.getClass()
					.getSimpleName());
			List objList = (List) obj;
			for (int j = 0; j < objList.size(); j++)
			{
				Object o = objList.get(j);
				if (j == 0)
				{
					buffer.append("<" + o.getClass().getSimpleName() + ">("
							+ objList.size() + "):");
				}
				String propertyOut = "【"
						+ toString(o, isAnnotation, isShowNull, isPrint) + "】";
				buffer.append(propertyOut);
			}
			output = buffer.toString();
		} else if (obj instanceof Map)
		{
			StringBuffer buffer = new StringBuffer(obj.getClass()
					.getSimpleName());
			buffer.append(":");
			Map map = (Map) obj;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				String s = key + "="
						+ toString(value, isAnnotation, isShowNull, isPrint)
						+ ",";
				buffer.append(s);
			}
			output = buffer.toString().substring(0, buffer.length() - 1);
		} else if (obj instanceof String || obj instanceof Number
				|| obj instanceof Boolean)
		{
			output = obj.toString();
		} else if (obj instanceof Date)
		{
			output = DateUtil.formatDate((Date) obj);
		} else if (obj instanceof Object[])
		{
			Object[] os = (Object[]) obj;
			for (int i = 0; i < os.length; i++)
			{
				String s = "{"
						+ toString(os[i], isAnnotation, isShowNull, isPrint)
						+ "}";
				output += s;
			}
			// output = Arrays.deepToString((Object[]) obj);
		} else
		{
			// 当obj为JavaBean类型
			Class cls = obj.getClass();
			StringBuffer buffer = new StringBuffer(obj.getClass()
					.getSimpleName());
			buffer.append(":");
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				if (fields[i].getName().equals("serialVersionUID"))
				{
					continue;
				}

				// 如果Print的注解值为false,则跳过
				if (isPrint && !isPrint(fields[i]))
				{
					continue;
				}

				// 对象的属性
				String fieldName = fields[i].getName();
				if (fieldName == null)
				{
					continue;
				}
				// 属性的get方法
				String getMethod = generateGet(fields[i].getName());
				// 执行get方法
				Object methodResult = invokeMethodNoParam(cls, getMethod, obj);
				if (methodResult instanceof List)
				{
					if (i == 0)
					{
						buffer.append(fieldName + "={");
					} else
					{
						buffer.append("," + fieldName + "={");
					}
					List list = (List) methodResult;
					for (int j = 0; j < list.size(); j++)
					{
						Object o = list.get(j);
						String propertyOut = "【["
								+ JavaBeanUtil.toString(o, isAnnotation,
										isShowNull, isPrint) + "]】";
						buffer.append(propertyOut);
					}
					buffer.append("}");
				} else if (methodResult instanceof Object[])
				{
					String result = Arrays.toString((Object[]) methodResult);
					if (i == 0)
					{
						buffer.append(fieldName + "=" + result);
					} else
					{
						buffer.append("," + fieldName + "=" + result);
					}
				} else
				{
					if (methodResult == null)
					{
						if (isShowNull)
						{
							String result = "null";
							if (i == 0)
							{
								buffer.append(fieldName + "=" + result);
							} else
							{
								buffer.append("," + fieldName + "=" + result);
							}
						}
					} else
					{
						String result = methodResult.toString();
						if (i == 0)
						{
							buffer.append(fieldName + "=" + result);
						} else
						{
							buffer.append("," + fieldName + "=" + result);
						}
					}
				}
			}
			output = buffer.toString();
		}

		return output;
	}

	/**
	 * 生成属性的get方法名
	 * 
	 * @param fieldName
	 * @return
	 */
	private static String generateGet(String fieldName)
	{
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	/**
	 * invoke无参数方法
	 * 
	 * @param cls
	 * @param methodName
	 * @param obj
	 * @return
	 */
	private static Object invokeMethodNoParam(Class cls, String methodName,
			Object obj)
	{
		Object retObj = null;
		try
		{
			Method method = cls.getMethod(methodName);
			retObj = method.invoke(obj);
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}

		// 格式化日期格式
		if (retObj instanceof Date)
		{
			retObj = DateUtil.formatDate((Date) retObj);
		}
		return retObj;
	}

	/**
	 * 不打印的annotation
	 * 
	 * @author hjin
	 * @cratedate 2013-5-8 上午8:44:10
	 * @param field
	 * @return
	 * 
	 */
	public static boolean isPrint(Field field)
	{
		boolean flag = true;
		Print define = field.getAnnotation(Print.class);
		if (define != null && !define.isPrint())
		{
			flag = false;
		}
		return flag;

	}
}
