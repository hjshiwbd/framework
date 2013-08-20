package demo.mapper;

import java.util.List;

import demo.bean.DemoUserBean;

import framework.base.mapper.BaseMapper;

/**
 * 
 * @author hjin
 * @cratedate 2013-8-6 上午10:20:42
 * 
 */
public interface DemoUserMapper extends BaseMapper
{
	public List<DemoUserBean> select(Object param);

	public int insert(DemoUserBean user);
	
	public int update(DemoUserBean user);
}
