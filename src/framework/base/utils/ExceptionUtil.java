package framework.base.utils;

public class ExceptionUtil
{
	public static String formatExString(Exception ex)
	{
		StackTraceElement[] stackTraceElement = ex.getStackTrace();
		StringBuffer errorInfo = new StringBuffer("errorStack:\r\n" + ">>>\t"
		        + ex.getClass().getName() + " " + ex.getMessage() + "\r\n");
		for (int i = 0; i < stackTraceElement.length; i++)
		{
			StackTraceElement sElement = stackTraceElement[i];
			errorInfo.append(">>>\t" + sElement.toString() + "\r\n");
		}
		return errorInfo.toString();
	}
}
