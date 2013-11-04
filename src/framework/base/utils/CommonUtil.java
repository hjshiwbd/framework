package framework.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-29 下午2:21:21
 * 
 */
public class CommonUtil
{
	/**
	 * 进行MD5大写加密
	 * 
	 * @author hjin
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的字符串
	 */
	public static String encryptToMD5(String info)
	{
		byte[] digesta = null;
		try
		{
			// 得到一个md5的消息摘要
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes("utf-8"));
			// 得到该摘要
			digesta = alga.digest();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// 将摘要转为字符串
		String rs = byte2hex(digesta);
		return rs;
	}

	/**
	 * 将二进制转化为16进制字符串
	 * 
	 * @author hjin
	 * @param b
	 *            二进制字节数组
	 * @return String
	 */
	public static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			}
			else
			{
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * null->""
	 * 
	 * @author hjin
	 * @cratedate 2013-8-29 下午3:47:34
	 * @param str
	 * @return
	 * 
	 */
	public static String null2Empty(String str)
	{
		return StringUtils.isEmpty(str) ? "" : str;
	}

	/**
	 * uuid,无"-"
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午3:35:14
	 * @return
	 * 
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取id值,若为空则赋值uuid
	 * 
	 * @author hjin
	 * @cratedate 2013-9-2 下午4:33:44
	 * @param target
	 * @param propertyName
	 * 
	 */
	public static void setIdValue(Object target, String propertyName)
	{
		Method[] getAndSet = ReflectUtil.findGetAndSetMethod(target.getClass(),
		        propertyName);
		// 属性对应的get/set方法存在
		if (getAndSet != null && getAndSet[0] != null && getAndSet[1] != null)
		{
			// 获取id
			String id = (String) ReflectUtil.invokeGetMethod(target,
			        propertyName);
			if (StringUtils.isBlank(id))
			{
				// 为空,反射赋值uuid
				ReflectUtil.invokeSetMethod(target, propertyName,
				        CommonUtil.getUUID());
			}
		}
	}

	/**
	 * 反射,赋值.将jsp中user.name->赋值到user对象的name属性中<br/>
	 * 只对String,Integer,java.util.Date的属性赋值<br/>
	 * Date格式:yyyy-MM-dd,其他格式会报错
	 * 
	 * @author hjin
	 * @cratedate 2013-9-9 下午4:59:32
	 * @param objNamePrefix
	 *            对象名
	 * @param clazz
	 *            类名
	 * @param request
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRequestObject(String objNamePrefix, Class<T> clazz,
	        HttpServletRequest request)
	{
		T t = (T) ReflectUtil.newInstance(clazz.getName());
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);

		// set方法执行的次数
		int count = 0;
		for (Method method : methods)
		{
			String methodName = method.getName();
			// set方法
			if (methodName.startsWith("set"))
			{
				// 属性名,去掉set后首字母变小写
				String propertyName = methodName.substring(3);
				propertyName = StringUtils.uncapitalize(propertyName);

				// 属性
				Field property = ReflectionUtils.findField(clazz, propertyName);

				// request取值
				String str = request.getParameter(objNamePrefix + "."
				        + propertyName);
				if (StringUtils.isBlank(str))
				{
					continue;
				}

				if (property.getType().getName().equals(String.class.getName()))
				{
					// string
					ReflectUtil.invokeSetMethod(t, propertyName, str);
					count++;
				}
				else if (property.getType().getName()
				        .equals(String.class.getName()))
				{
					// int
					Integer value = Integer.parseInt(str);
					ReflectUtil.invokeSetMethod(t, propertyName, value);
					count++;
				}
				else if (property.getType().getName()
				        .equals(Date.class.getName()))
				{
					// date
					Date value = DateUtil.parseDate(str, "yyyy-MM-dd");
					ReflectUtil.invokeSetMethod(t, propertyName, value);
					count++;
				}
			}
		}

		// 未set任何属性,返回null
		return count == 0 ? null : t;
	}

	/**
	 * 邮箱格式校验
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailFormatCheck(String email)
	{
		if (email == null || "".equals(email))
		{
			return false;
		}
		// String reg =
		// "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{1,4}|[0-9]{1,3})(\\]?)$";
		String reg = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z\\.]{2,10})+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * 手机号格式校验
	 * 
	 * @author hjin
	 * @cratedate 2013-9-10 上午10:37:33
	 * @param mobile
	 * @return
	 * 
	 */
	public static boolean mobileFormatCheck(String mobile)
	{
		if (mobile == null || "".equals(mobile))
		{
			return false;
		}
		String reg = "^1[3|4|5|8][\\d]{9}$";
		return mobile.matches(reg);
	}
	
	/**
	 * 获取http请求的客户IP
	 * 
	 * @param request
	 * @return IP地址
	 */
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	public static void main(String[] args)
	{
		System.out.println(mobileFormatCheck("13987654321"));
	}
}
