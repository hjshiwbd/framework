package framework.base.bean;

/**
 * 上传进度对象
 * 
 * @author hjin
 * @cratedate 2013-8-30 上午9:19:50
 * 
 */
public class UploadProcessBean extends BaseBean
{
	/**
	 * 文件总长度
	 */
	private double fileLegnth = 0;
	/**
	 * 已上传的文件长度
	 */
	private double uploadedLength = 0;
	/**
	 * 已上传百分比
	 */
	private String percent;
	/**
	 * 上传是否结束,0否1是
	 */
	private String isUploadFinish;

	public String getPercent()
	{
		if (percent == null)
		{
			try
			{
				percent = uploadedLength * 1.0 / fileLegnth * 100 + "";
			}
			catch (Exception e)
			{
				percent = null;
			}
		}
		return percent;
	}

	public void setPercent(String percent)
	{
		this.percent = percent;
	}

	public double getFileLegnth()
	{
		return fileLegnth;
	}

	public void setFileLegnth(double fileLegnth)
	{
		this.fileLegnth = fileLegnth;
	}

	public double getUploadedLength()
	{
		return uploadedLength;
	}

	public void setUploadedLength(double uploadedLength)
	{
		this.uploadedLength = uploadedLength;
	}

	public String getIsUploadFinish()
	{
		if (isUploadFinish == null)
		{
			isUploadFinish = uploadedLength >= fileLegnth ? "1" : "0";
		}
		return isUploadFinish;
	}

	public void setIsUploadFinish(String isUploadFinish)
	{
		this.isUploadFinish = isUploadFinish;
	}
}
