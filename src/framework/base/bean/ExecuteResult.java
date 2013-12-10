package framework.base.bean;

public class ExecuteResult
{
	/**
	 * 执行结果.0失败1成功
	 */
	private String result;
	/**
	 * 执行结果描述
	 */
	private String message;
	/**
	 * 执行结果携带返回值
	 */
	private Object object;

	public ExecuteResult()
	{

	}

	public ExecuteResult(String result, String message)
	{
		super();
		this.result = result;
		this.message = message;
	}

	public ExecuteResult(String result, String message, Object object)
	{
		super();
		this.result = result;
		this.message = message;
		this.object = object;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getObject()
	{
		return object;
	}

	public void setObject(Object object)
	{
		this.object = object;
	}

}
