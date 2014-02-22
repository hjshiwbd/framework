package [module_pkg].[module_lower].dao.impl;

import org.springframework.stereotype.Repository;

import [module_pkg].[module_lower].dao.I[module_cap]DAO;

import framework.base.dao.impl.BaseDAOImpl;

/**
 * 
 * @author [author]
 * @cratedate [createdate]
 * 
 */
@Repository
public class [module_cap]DAOImpl extends BaseDAOImpl implements I[module_cap]DAO
{

	@Override
	public String getNamespace()
	{
		return "[module_mapper_pkg].[module_cap].";
	}
}
