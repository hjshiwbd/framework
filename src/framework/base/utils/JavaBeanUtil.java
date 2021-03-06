package framework.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import framework.base.bean.BaseBean;

/**
 * javabean基础类
 * 
 * @author hjin
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
	 * @param obj
	 * @return
	 * @author hjin
	 * @cratedate 2012-12-17 下午3:23:16
	 */
	public static String toString(Object obj)
	{
		return toString(obj, false, true);
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
	public static String toString(Object obj, boolean isShowNull,
	        boolean isPrint)
	{
		if (obj == null)
		{
			return "null";
		}
		// 输出字符串
		String output = "";
		if (obj instanceof List)
		{
			// 当obj为List类型
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
				String propertyOut = "【" + toString(o, isShowNull, isPrint)
				        + "】";
				buffer.append(propertyOut);
			}
			output = buffer.toString();
		}
		else if (obj instanceof Map)
		{
			// 当obj为Map类型
			StringBuffer buffer = new StringBuffer(obj.getClass()
			        .getSimpleName());
			buffer.append(":");
			Map map = (Map) obj;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Entry entry = (Entry) it.next();
				String key = objectToString(entry.getKey());
				Object value = entry.getValue();
				String s = key + "=" + toString(value, isShowNull, isPrint)
				        + ",";
				buffer.append(s);
			}
			output = buffer.toString().substring(0, buffer.length() - 1);
		}
		else if (obj instanceof String || obj instanceof Number
		        || obj instanceof Boolean)
		{
			// 当obj为常见类型类型
			output = obj.toString();
		}
		else if (obj instanceof Date)
		{
			// 当obj为Date类型
			output = DateUtil.formatDate((Date) obj);
		}
		else if (obj instanceof Object[])
		{
			// 当obj为数组对象
			Object[] os = (Object[]) obj;
			for (int i = 0; i < os.length; i++)
			{
				String s = "{" + toString(os[i], isShowNull, isPrint) + "}";
				output += s;
			}
			// output = Arrays.deepToString((Object[]) obj);
		}
		else
		{
			// 当obj为自定义类型
			Class cls = obj.getClass();

			StringBuffer buffer = new StringBuffer(obj.getClass()
			        .getSimpleName());
			buffer.append(":");

			// 获取obj所有父类的所有方法
			Method[] allMethods = cls.getDeclaredMethods();
			// 当前类的父类
			Class<?> superCls = cls.getSuperclass();
			while (!superCls.getName().equals(Object.class.getName()))
			{
				// 递归,找到所有父类的方法
				// 当父类不是Object
				// 获取父类方法
				Method[] methodsSuper = superCls.getDeclaredMethods();
				// 合并所有方法
				allMethods = addAllArrays(allMethods, methodsSuper);

				superCls = superCls.getSuperclass();
			}

			// 非null的属性的出现次数
			int notNullProp = 0;
			// 执行所有get方法
			for (int i = 0; i < allMethods.length; i++)
			{
				Method method = allMethods[i];
				String methodName = method.getName();

				// 只处理get方法
				if (!methodName.startsWith("get"))
				{
					continue;
				}

				if (methodName.equalsIgnoreCase("getSerialVersionUID"))
				{
					continue;
				}

				// 如果不要求打印或者Print的注解值为false,则跳过
				if (!isPrint || !isPrint(method))
				{
					continue;
				}

				// 方法对应的属性名,去掉get后首字母大写
				String fieldName = uncapitalize(methodName.substring(3));

				// 反射得到get方法的执行结果
				Object methodResult = invokeMethodNoParam(cls, methodName, obj);

				// 转换执行结果->string,用于打印输出
				if (methodResult instanceof List)
				{
					// 结果值类型=list
					if (notNullProp == 0)
					{
						buffer.append(fieldName + "={");
					}
					else
					{
						buffer.append("," + fieldName + "={");
					}
					List list = (List) methodResult;
					for (int j = 0; j < list.size(); j++)
					{
						Object o = list.get(j);
						String propertyOut = "【["
						        + JavaBeanUtil.toString(o, isShowNull, isPrint)
						        + "]】";
						buffer.append(propertyOut);
					}
					buffer.append("}");
					notNullProp++;
				}
				else if (methodResult instanceof Object[])
				{
					// 结果值类型=数组
					String result = Arrays.toString((Object[]) methodResult);
					if (notNullProp == 0)
					{
						buffer.append(fieldName + "=" + result);
					}
					else
					{
						buffer.append("," + fieldName + "=" + result);
					}
					notNullProp++;
				}
				else
				{

					if (methodResult == null)
					{
						// 结果值=null
						if (isShowNull)
						{
							// 入参要求显示null值
							String result = "null";
							if (notNullProp == 0)
							{
								buffer.append(fieldName + "=" + result);
							}
							else
							{
								buffer.append("," + fieldName + "=" + result);
							}
						}
					}
					else
					{
						if (methodResult instanceof BaseBean)
						{
							if (notNullProp == 0)
							{
								buffer.append(fieldName + "=["
								        + toString(methodResult) + "]");
							}
							else
							{
								buffer.append("," + fieldName + "=["
								        + toString(methodResult) + "]");
							}
						}
						else
						{
							String result = methodResult.toString();
							if (notNullProp == 0)
							{
								buffer.append(fieldName + "=" + result);
							}
							else
							{
								buffer.append("," + fieldName + "=" + result);
							}
						}

						notNullProp++;
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
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
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
	 * @param field
	 * @return
	 * @author hjin
	 * @cratedate 2013-5-8 上午8:44:10
	 */
	public static boolean isPrint(Method method)
	{
		String className = "framework.base.annotation.Print";
		try
		{
			Class cls = Class.forName(className);
			Annotation anno = method.getAnnotation(cls);
			if (anno != null)
			{
				return (Boolean) cls.getMethod("isPrint").invoke(anno);
			}
			else
			{
				// 没找到Print的注解
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * 合并两个Method数组
	 * 
	 * @author hjin
	 * @cratedate 2013-12-13 下午2:55:19
	 * @param arr1
	 * @param arr2
	 * @return
	 * 
	 */
	private static Method[] combineMethodArray(Method[] arr1, Method[] arr2)
	{
		Method[] result = new Method[arr1.length + arr2.length];
		for (int i = 0; i < result.length; i++)
		{
			if (i < arr1.length)
			{
				result[i] = arr1[i];
			}
			else
			{
				result[i] = arr2[i - arr1.length];
			}
		}
		return result;
	}

	/**
	 * 引用自apache StringUtils
	 * <p>
	 * Uncapitalizes a String changing the first letter to title case as per
	 * {@link Character#toLowerCase(char)}. No other letters are changed.
	 * </p>
	 * 
	 * <p>
	 * For a word based algorithm, see
	 * {@link org.apache.commons.lang3.text.WordUtils#uncapitalize(String)}. A
	 * {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.uncapitalize(null)  = null
	 * StringUtils.uncapitalize("")    = ""
	 * StringUtils.uncapitalize("Cat") = "cat"
	 * StringUtils.uncapitalize("CAT") = "cAT"
	 * </pre>
	 * 
	 * @param str
	 *            the String to uncapitalize, may be null
	 * @return the uncapitalized String, {@code null} if null String input
	 * @see org.apache.commons.lang3.text.WordUtils#uncapitalize(String)
	 * @see #capitalize(String)
	 * @since 2.0
	 */
	public static String uncapitalize(String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return str;
		}
		return new StringBuilder(strLen)
		        .append(Character.toLowerCase(str.charAt(0)))
		        .append(str.substring(1)).toString();
	}

	/**
	 * 引用自apache ArrayUtils
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * </p>
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by
	 * all of the elements {@code array2}. When an array is returned, it is
	 * always a new array.
	 * </p>
	 * 
	 * <pre>
	 * ArrayUtils.addAll(null, null)     = null
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * ArrayUtils.addAll([null], [null]) = [null, null]
	 * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
	 * </pre>
	 * 
	 * @param <T>
	 *            the component type of the array
	 * @param array1
	 *            the first array whose elements are added to the new array, may
	 *            be {@code null}
	 * @param array2
	 *            the second array whose elements are added to the new array,
	 *            may be {@code null}
	 * @return The new array, {@code null} if both arrays are {@code null}. The
	 *         type of the new array is the type of the first array, unless the
	 *         first array is null, in which case the type is the same as the
	 *         second array.
	 * @since 2.1
	 * @throws IllegalArgumentException
	 *             if the array types are incompatible
	 */
	public static <T> T[] addAllArrays(T[] array1, T... array2)
	{
		final Class<?> type1 = array1.getClass().getComponentType();
		@SuppressWarnings("unchecked")
		// OK, because array is of type T
		T[] joinedArray = (T[]) Array.newInstance(type1, array1.length
		        + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		try
		{
			System.arraycopy(array2, 0, joinedArray, array1.length,
			        array2.length);
		}
		catch (ArrayStoreException ase)
		{
			// Check if problem was due to incompatible types
			/*
			 * We do this here, rather than before the copy because: - it would
			 * be a wasted check most of the time - safer, in case check turns
			 * out to be too strict
			 */
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2))
			{
				throw new IllegalArgumentException("Cannot store "
				        + type2.getName() + " in an array of "
				        + type1.getName(), ase);
			}
			throw ase; // No, so rethrow original
		}
		return joinedArray;
	}

	public static String objectToString(Object obj)
	{
		if (obj == null)
		{
			return "";
		}
		if (obj instanceof String)
		{
			return (String) obj;
		}
		else if (obj instanceof Integer)
		{
			return String.valueOf(obj);
		}

		return obj.toString();
	}
}
