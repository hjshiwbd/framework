package framework.base.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.base.bean.ExecuteResult;

/**
 * 替换字符串中的占位符.
 * 
 * @author hjin
 * 
 */
public class StringFormatter
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String src;
	private Object object;
	private String regex;

	/**
	 * 替换字符串中的占位符.如"您的信息是{name},性别{sex}",会执行object中的getName和getSex方法,替换{xxx}
	 * 的内容
	 * 
	 * @param src
	 *            原字符串
	 * @param object
	 *            用于获取属性的对象(Object或者Map)
	 */
	public StringFormatter(String src, Object object)
	{
		this.src = src;
		this.object = object;
	}

	public String format()
	{
		if (StringUtils.isBlank(src))
		{
			logger.info("src is blank");
			return null;
		}
		if (object == null)
		{
			logger.info("object is null");
			return null;
		}

		Pattern pattern = Pattern.compile(getRegex());
		Matcher matcher = pattern.matcher(src);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find())
		{
			String group = matcher.group();
			String key = invokeKey(group);
			String value = getValue(object, key);
			matcher.appendReplacement(buffer, value);
		}
		matcher.appendTail(buffer);

		return buffer.toString();
	}

	/**
	 * 获取关键字
	 * 
	 * @param group
	 * @return
	 * @author hjin
	 */
	private String invokeKey(String group)
	{
		String regex = "[a-zA-Z]\\S*[a-zA-Z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(group);
		String s = "";
		while (matcher.find())
		{
			s += matcher.group();
		}
		return s;
	}

	@SuppressWarnings("rawtypes")
	private String getValue(Object obj, String key)
	{
		Object o = null;
		if (obj instanceof Map)
		{
			Map map = (Map) obj;
			o = map.get(key);
		}
		else
		{
			o = ReflectUtil.invokeGetMethod(obj, key);
		}
		return CommonUtil.null2Empty(ConvertUtil.objToString(o));
	}

	public String getRegex()
	{
		if (regex == null)
		{
			regex = "\\{\\S*?\\}";
		}
		return regex;
	}

	public static void main(String[] args)
	{
		String s = "111|{result}|22222|{message}|33333|{abc}|44444444";
		ExecuteResult e = new ExecuteResult();
		e.setResult("123");
		e.setMessage("msg");
		StringFormatter formatter = new StringFormatter(s, e);
		System.out.println(formatter.format());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", 1111);
		map.put("message", "sssssssssssss");
		formatter = new StringFormatter(s, map);
		System.out.println(formatter.format());
	}

}
