package framework.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author hjin
 * 
 */
public class DateUtil
{
	/**
	 * 字符串转date,默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param source
	 * @return
	 */
	public static Date parseDate(String source)
	{
		return parseDate(source, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 字符串转date
	 * 
	 * @param source
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date parseDate(String source, String pattern)
	{
		if (source == null || source.equals(""))
		{
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			return sdf.parse(source);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期,默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date)
	{
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String formatDate(Date date, String pattern)
	{
		if (date == null)
		{
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 格式化当前的日期
	 * 
	 * @author hjin
	 * @cratedate 2013-10-31 上午10:40:18
	 * @param pattern
	 * @return
	 * 
	 */
	public static String formatToday(String pattern)
	{
		Date today = new Date();
		return formatDate(today, pattern);
	}
}
