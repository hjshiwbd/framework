package framework.base.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-30 上午8:51:10
 * 
 */
public class MultipartResolver extends CommonsMultipartResolver
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request)
	        throws MultipartException
	{
		logger.info("custom MultipartResolver");

		String encoding = "utf-8";
		FileUpload fileUpload = prepareFileUpload(encoding);

		MultipartUploadListener uploadProgressListener = new MultipartUploadListener(
		        request.getSession());
		fileUpload.setProgressListener(uploadProgressListener);
		try
		{
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload)
			        .parseRequest(request);
			return parseFileItems(fileItems, encoding);
		}
		catch (FileUploadBase.SizeLimitExceededException ex)
		{
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(),
			        ex);
		}
		catch (FileUploadException ex)
		{
			throw new MultipartException(
			        "Could not parse multipart servlet request", ex);
		}
	}

}
