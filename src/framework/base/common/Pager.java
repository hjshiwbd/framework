package framework.base.common;

import java.io.Serializable;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import framework.base.annotation.Print;

/**
 * 分页类.继承mybatis.RowBounds类,配合mybatis-pager.jar,实现分页功能
 * 
 * @author hjin
 * @cratedate 2013-8-7 上午9:23:13
 * 
 */
public class Pager<T> extends PageBounds implements Serializable
{
	/**
	 * 当前页
	 */
	private int curtPage = 1;
	/**
	 * 每页行数
	 */
	private int countPerPage = 15;

	/**
	 * 需要分页的表
	 */
	private String tableName = "";

	/**
	 * 分页的where语句，以and开头，例如and sex='2'
	 */
	private String whereStr = "";

	/**
	 * 排序.ex:String orderby = "id.desc,name";
	 */
	private String orderby = "";

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
	 * 总行数
	 */
	private int total = 0;
	/**
	 * 总页数
	 */
	private int totalPage;

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
	@Print(isPrint = false)
	private List<T> pageList;
	/**
	 * 分页样式
	 */
	private String style;
	/**
	 * 生成html串
	 */
	private String htmlOutput;

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

	public int getCurtPage()
	{
		if (getTotalPage() > 0 && curtPage > getTotalPage())
		{
			curtPage = getTotal();
		}
		if (curtPage < 1)
		{
			curtPage = 1;
		}
		return curtPage;
	}

	public void setCurtPage(int curtPage)
	{
		this.curtPage = curtPage;
	}

	public int getCountPerPage()
	{
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage)
	{
		this.countPerPage = countPerPage;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public int getTotalPage()
	{
		totalPage = total % countPerPage == 0 ? total / countPerPage : total
		        / countPerPage + 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public int getIndexLength()
	{
		return indexLength;
	}

	public void setIndexLength(int indexLength)
	{
		this.indexLength = indexLength;
	}

	public int getStartPage()
	{
		return startPage;
	}

	public void setStartPage(int startPage)
	{
		this.startPage = startPage;
	}

	public int getEndPage()
	{
		return endPage;
	}

	public void setEndPage(int endPage)
	{
		this.endPage = endPage;
	}

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

	public String getHtmlOutput()
	{
		return htmlOutput;
	}

	public void setHtmlOutput(String htmlOutput)
	{
		this.htmlOutput = htmlOutput;
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
		if (offset == 0)
		{
			offset = 1;
		}
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}
}