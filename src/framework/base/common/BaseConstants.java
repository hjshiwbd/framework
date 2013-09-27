package framework.base.common;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 下午2:43:02
 * 
 */
public class BaseConstants
{
	/**
	 * 通用false
	 */
	public static final String GLOBAL_NO = "0";
	/**
	 * 通用true
	 */
	public static final String GLOBAL_YES = "1";

	/**
	 * 通用结果页面参数
	 * 
	 * @author hjin
	 * @cratedate 2013-8-6 下午2:43:52
	 * 
	 */
	public static class CommonPageParam
	{
		/**
		 * 通用结果页面.在project.properties中配置
		 */
		public static final String GLOBAL_PAGE_RESULT = "global.page.result";
		/**
		 * 请求处理结果.1成功0失败
		 */
		public static final String PROCESS_RESULT = "processResult";
		/**
		 * 结果页面提示内容
		 */
		public static final String SHOW_MSG = "showMsg";
		/**
		 * 结果页面的后续跳转地址
		 */
		public static final String PAGE_CONTINUE_URL = "pageContinueUrl";
	}

	/**
	 * 服务器缓存
	 * 
	 * @author hjin
	 * @cratedate 2013-8-20 上午9:45:39
	 * 
	 */
	public static class CacheKey
	{
		/**
		 * 服务物理路径.服务启动时初始化.f:\\workspace\\iispace\\WebRoot
		 */
		public static final String SERVER_ABSOLUTE_PATH = "serverAbsolutePath";
		/**
		 * 请求后缀.在project.properties中配置
		 */
		public static final String GLOBAL_CONTROLLER_SUFFIX = "global.controller.suffix";
	}

	/**
	 * 上传文件参数
	 * 
	 * @author hjin
	 * @cratedate 2013-8-30 上午9:18:44
	 * 
	 */
	public static class UploadParam
	{
		/**
		 * 已上传内容对象,一般存在session中用于ajax轮询
		 */
		public static final String UPLOAD_STATUS = "currentUploadStatus";
	}
}
