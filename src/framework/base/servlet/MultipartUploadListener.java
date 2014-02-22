package framework.base.servlet;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import framework.base.bean.UploadProcessBean;
import framework.base.common.BaseConstants.UploadParam;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-30 上午8:59:53
 * 
 */
public class MultipartUploadListener implements ProgressListener
{
	private HttpSession session;

	// 上传是否完成
	private boolean isComplete = false;

	public MultipartUploadListener(HttpSession session)
	{
		this.session = session;
	}

	/**
	 * 监听文件上传
	 * 
	 * @param pBytesRead
	 *            已读长度
	 * @param pContentLength
	 *            总长度
	 * @param pItems
	 *            当前正在被读取的field
	 * @see org.apache.commons.fileupload.ProgressListener#update(long, long,
	 *      int)
	 */
	public void update(long pBytesRead, long pContentLength, int pItems)
	{
		// 自动计算已上传比例
		UploadProcessBean process = new UploadProcessBean();
		process.setUploadedLength(pBytesRead);
		process.setFileLegnth(pContentLength);

		session.setAttribute(UploadParam.UPLOAD_STATUS, process);
	}

	public boolean isComplete()
	{
		return isComplete;
	}

	public void setComplete(boolean isComplete)
	{
		this.isComplete = isComplete;
	}
}
