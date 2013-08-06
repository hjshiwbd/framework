package framework.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用异常
 * 
 * @author hjin
 * @cratedate 2013-8-6 下午2:48:32
 * 
 */
public class BaseException extends RuntimeException
{

	private static final Logger logger = LoggerFactory
	        .getLogger(BaseException.class);

	protected String errorCode;
	protected String errorMessage;
	protected Object[] params;

	// 日志对象
	public BaseException(String msg)
	{
		super(msg);
		logger.error(getStackTrace()[1].getMethodName() + ": 出现异常!错误原因:" + msg);
	}

	public BaseException(Object className, Throwable cause)
	{
		super(className.toString(), cause);
		logger.error(cause.getStackTrace()[0].getMethodName() + ": 出现异常!错误原因:"
		        + cause.getMessage());
	}

	public BaseException(Exception e)
	{
		super(e);
		StringBuilder message = new StringBuilder();
		for (int i = 0; i < e.getStackTrace().length; i++)
		{
			message.append(e.getStackTrace()[i] + "\r\n");
		}
		// message消息转换

		// 打印Exception堆栈错误信息
		logger.error(message.toString());

		logger.error(getStackTrace()[1].getMethodName() + ": 出现异常!错误原因:"
		        + e.getMessage());
	}

	public BaseException(String code, String msg, Exception e)
	{
		super(msg, e);
		this.errorCode = code;
	}

	public BaseException(String code, String msg, Exception e, Object[] params)
	{
		super(msg, e);
		this.errorCode = code;
		this.params = params;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	private static final long serialVersionUID = -903912364023186153L;

}
