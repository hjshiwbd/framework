package framework.base.common;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import framework.base.annotation.Print;
import framework.base.utils.CommonUtil;

/**
 * 分页类.继承mybatis.RowBounds类,配合mybatis-pager.jar,实现分页功能
 * 
 * @author hjin
 * @cratedate 2013-8-7 上午9:23:13
 */
public class Pager<T> extends PageBounds implements Serializable
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @description
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前页
	 */
	private Integer curtPage = 1;
	/**
	 * 每页行数
	 */
	private Integer countPerPage = 15;
	/**
	 * 总行数
	 */
	private Integer total = 0;
	/**
	 * 总页数
	 */
	private Integer totalPage;

	/**
	 * 需要分页的表
	 */
	private String tableName = "";

	/**
	 * 分页的where语句，以and开头，例如and sex='2'
	 */
	private String whereStr = "";

	/**
	 * 格式:以","或"."分开,不要有空格.<br/>
	 * ex:String orderby = "id.desc,name";
	 */
	private String orderby = "";
	/**
	 * improved orderby generation with property "orderby"<br/>
	 * first "?"->property, second "?"->direction<br/>
	 * eg:orderby="id.desc", orderbyFormat="t.??"-> "t.id desc"
	 */
	private String orderbyFormat = "";

	/**
	 * 需要返回的列,内层查询时用
	 */
	private String columns = "";

	/**
	 * 需要返回的列,外层查询时用
	 */
	private String columnsout = "";

	/**
	 * List的泛型.需要返回的bean对象，即把结果集放入到哪个bean中
	 */
	private String beanName = "";

	/**
	 * 翻页处显示的长度.如:显示...[3][4][5][6][7]...时,长度为5,其余均以...来省略.赋值奇数为宜.
	 */
	private int indexLength = 7;

	/**
	 * 翻页处显示的起始页.如:显示...[3][4][5][6][7]...时,其值为3.
	 */
	private int startPage;

	/**
	 * 翻页处显示的结束页.如:显示...[3][4][5][6][7]...时,其值为7.
	 */
	private int endPage;

	/**
	 * 数据容器
	 */
	private List<T> pageList;
	/**
	 * 分页样式
	 */
	private String style = "pagination";
	/**
	 * 当前页的按钮
	 */
	private String classLinkOn = "linkon";
	/**
	 * 非当前页的按钮
	 */
	private String classLinkOff = "linkoff";
	/**
	 * 翻页请求的url
	 */
	private String url = "#";
	/**
	 * 翻页请求的url参数
	 */
	private String urlQueryString = "";
	/**
	 * 翻页按钮的点击事件方法名
	 */
	private String clickMethod = "";
	/**
	 * 生成html串
	 */
	private String html;

	/**
	 * mysql分页参数
	 */
	private int offset;
	private int limit;

	/**
	 * 默认初始化curtPage=1,countPerPage=15
	 */
	public Pager()
	{
		offset = 1;
	}

	/**
	 * 分页查询时的构造函数
	 * 
	 * @param curtPage
	 *            当前页
	 * @param countPerPage
	 *            每页行数
	 * @param tableName
	 *            查询表名
	 * @param whereStr
	 *            查询条件:"and a1=a1 and b1=b1 and c1=c1 ..."
	 * @param orderBy
	 *            排序条件:"id desc,no asc,sortorder desc..."
	 * @param columns
	 *            查询字段:"no,sno,idnum..."
	 * @param beanName
	 *            结果集对象
	 */
	public Pager(int curtPage, int countPerPage, String tableName,
	        String whereStr, String orderBy, String columns, String beanName)
	{
		// curtPage,countPerPage,tableName,whereStr,orderBy, columns,beanName
		this.curtPage = curtPage;
		this.countPerPage = countPerPage;
		this.tableName = tableName;
		this.whereStr = whereStr;
		this.orderby = orderBy;
		this.columns = columns;
		this.beanName = beanName;
	}

	/**
	 * 构建翻页的页面代码
	 * 
	 * @return html str
	 */
	public String createHtml()
	{
		// hidden input
		String prefix = "pager_";
		String inputTemplate = "<input type=\"hidden\" name=\"" + prefix
		        + "{paramName}\" id=\"" + prefix
		        + "{paramName}\" value=\"{value}\" />";
		String curtPageInput = inputTemplate.replace("{paramName}",
		        "curtPageInput").replace("{value}", curtPage + "");
		String countPerPageInput = inputTemplate.replace("{paramName}",
		        "countPerPageInput").replace("{value}", countPerPage + "");
		String totalInput = inputTemplate.replace("{paramName}", "totalInput")
		        .replace("{value}", total + "");
		String totalPageInput = inputTemplate.replace("{paramName}",
		        "totalPageInput").replace("{value}", getTotalPage() + "");// totalPage需要用get方式计算得到

		// 三个模版
		// 全部span
		String template1 = "<div class=\"" + style + "\">" + curtPageInput
		        + countPerPageInput + totalInput + totalPageInput
		        + "{span}</div>";
		// 有link
		String template2 = "<a class=\"pageIndex\" pageIndex=\"{index}\" href=\"{url}?{urlQueryString}\" onclick=\"{clickMethod}\">{text}</a>";
		// 无link
		String template3 = "<span class=\"{class}\">{text}</span>";

		// 翻页链接
		url = url == null ? "#" : url;

		// 所有页码的html
		String span = "";
		// 翻页栏开始页
		startPage = getStartPage();
		// 翻页栏结束页
		endPage = getEndPage();
		// 翻页栏内个数少于预期时,补齐
		if ((endPage - startPage) < (indexLength - 1) && endPage > 1)
		{
			startPage = endPage - indexLength + 1;
		}
		startPage = startPage < 1 ? 1 : startPage;

		// 循环,构成html
		for (int i = startPage; i <= endPage; i++)
		{
			if (i != curtPage)
			{
				span += template2.replace("{class}", classLinkOn)
				        .replace("{text}", i + "").replace("{url}", url)
				        .replace("{clickMethod}", clickMethod)
				        .replace("{urlQueryString}", getUrlQueryString())
				        .replace("{index}", i + "");
			}
			else
			{
				// 当前显示页,无link
				span += template3.replace("{class}", classLinkOff).replace(
				        "{text}", i + "");
			}
		}

		// 首页
		String first = template2.replace("{class}", classLinkOn)
		        .replace("{text}", "首页").replace("{url}", url)
		        .replace("{clickMethod}", clickMethod)
		        .replace("{urlQueryString}", getUrlQueryString())
		        .replace("{index}", 1 + "");
		// 末页
		String last = template2.replace("{class}", classLinkOn)
		        .replace("{text}", "末页").replace("{url}", url)
		        .replace("{clickMethod}", clickMethod)
		        .replace("{urlQueryString}", getUrlQueryString())
		        .replace("{index}", totalPage + "");
		// 上一页
		String prevPage = String
		        .valueOf(curtPage - 1 == 0 ? "1" : curtPage - 1);
		String prev = template2.replace("{class}", classLinkOn)
		        .replace("{text}", "上页").replace("{url}", url)
		        .replace("{clickMethod}", clickMethod)
		        .replace("{urlQueryString}", getUrlQueryString())
		        .replace("{index}", prevPage);
		// 下一页
		String nextPage = String
		        .valueOf(curtPage + 1 > getTotalPage() ? getTotalPage()
		                : curtPage + 1);
		String next = template2.replace("{class}", classLinkOn)
		        .replace("{text}", "下页").replace("{url}", url)
		        .replace("{clickMethod}", clickMethod)
		        .replace("{urlQueryString}", getUrlQueryString())
		        .replace("{index}", nextPage);

		// 拼接
		String allHtml;
		allHtml = first + prev + span + next + last;
		// System.out.println(curtPage + "," + totalPage + "," + startPage + ","
		// + endPage);
		// if (curtPage == 1 && curtPage != totalPage)
		// {
		// // 是否首页
		// allHtml = span + next + last;
		// }
		// else if (curtPage != 1 && curtPage == totalPage)
		// {
		// // 是否末页
		// allHtml = first + prev + span;
		// }
		// else if (curtPage == totalPage)
		// {
		// // 既是首页也是末页,总页数=1
		// allHtml = span;
		// }
		// else
		// {
		// // 其他
		// allHtml = first + prev + span + next + last;
		// }

		String result = template1.replace("{span}", allHtml);
		if (logger.isDebugEnabled())
		{
			logger.debug(result);
		}
		return result;
	}

	/**
	 * 计算当前页
	 * 
	 * @return
	 */
	public Integer getCurtPage()
	{
		if (getTotalPage() > 0 && curtPage > getTotalPage())
		{
			curtPage = getTotalPage();
		}
		if (curtPage < 1)
		{
			curtPage = 1;
		}
		return curtPage;
	}

	public void setCurtPage(Integer curtPage)
	{
		this.curtPage = curtPage;
	}

	public Integer getCountPerPage()
	{
		return countPerPage;
	}

	public void setCountPerPage(Integer countPerPage)
	{
		this.countPerPage = countPerPage;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getTotalPage()
	{
		totalPage = total % countPerPage == 0 ? total / countPerPage : total
		        / countPerPage + 1;
		return totalPage;
	}

	public void setTotalPage(Integer totalPage)
	{
		this.totalPage = totalPage;
	}

	public int getIndexLength()
	{
		indexLength = indexLength > getTotalPage() ? getTotalPage()
		        : indexLength;
		return indexLength;
	}

	public void setIndexLength(int indexLength)
	{
		this.indexLength = indexLength;
	}

	public int getStartPage()
	{
		startPage = curtPage - (indexLength / 2);
		if (startPage < 1)
		{
			startPage = 1;
		}
		return startPage;
	}

	public void setStartPage(int startPage)
	{
		this.startPage = startPage;
	}

	public int getEndPage()
	{
		endPage = getStartPage() + indexLength - 1;
		if (endPage > getTotalPage())
		{
			endPage = getTotalPage();
		}
		return endPage;
	}

	public void setEndPage(int endPage)
	{
		this.endPage = endPage;
	}

	@Print(isPrint = false)
	public List<T> getPageList()
	{
		return pageList;
	}

	public void setPageList(List<T> pageList)
	{
		this.pageList = pageList;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	@Print(isPrint = false)
	public String getHtml()
	{
		html = createHtml();
		return html;
	}

	public void setHtml(String htmlOutput)
	{
		this.html = htmlOutput;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getWhereStr()
	{
		return whereStr;
	}

	public void setWhereStr(String whereStr)
	{
		this.whereStr = whereStr;
	}

	public String getOrderby()
	{
		return orderby;
	}

	/**
	 * 格式:以","或"."分开,不要有空格.<br/>
	 * ex:String orderby = "id.desc,name";
	 * 
	 * @param orderby
	 */
	public void setOrderby(String orderby)
	{
		this.orderby = orderby;
	}

	public String getColumns()
	{
		return columns;
	}

	public void setColumns(String columns)
	{
		this.columns = columns;
	}

	public String getBeanName()
	{
		return beanName;
	}

	public void setBeanName(String beanName)
	{
		this.beanName = beanName;
	}

	public String getColumnsout()
	{
		return columnsout;
	}

	public void setColumnsout(String columnsout)
	{
		this.columnsout = columnsout;
	}

	@Override
	public int getLimit()
	{
		limit = countPerPage;
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	@Override
	public int getOffset()
	{
		offset = (curtPage - 1) * countPerPage;
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getClickMethod()
	{
		return clickMethod;
	}

	public void setClickMethod(String clickMethod)
	{
		this.clickMethod = clickMethod;
	}

	public String getClassLinkOn()
	{
		return classLinkOn;
	}

	public void setClassLinkOn(String classLinkOn)
	{
		this.classLinkOn = classLinkOn;
	}

	public String getClassLinkOff()
	{
		return classLinkOff;
	}

	public void setClassLinkOff(String classLinkOff)
	{
		this.classLinkOff = classLinkOff;
	}

	public String getOrderbyFormat()
	{
		return orderbyFormat;
	}

	/**
	 * {@link #orderbyFormat}
	 */
	public void setOrderbyFormat(String orderbyFormat)
	{
		this.orderbyFormat = orderbyFormat;
	}

	public String getUrlQueryString()
	{
		if (CommonUtil.null2Empty(urlQueryString).equals(""))
		{
			return "curtPage={index}";
		}
		else
		{
			if (urlQueryString.charAt(0) == '&')
			{
				urlQueryString = urlQueryString.substring(1);
			}

			if (urlQueryString.indexOf("curtPage=") == -1)
			{
				return "&curtPage={index}&" + urlQueryString;
			}
			else
			{
				return "&" + urlQueryString;
			}
		}
	}

	public void setUrlQueryString(String urlQueryString)
	{
		this.urlQueryString = urlQueryString;
	}

}
