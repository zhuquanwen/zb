package com.iscas.zb.dao;

import java.util.List;
import java.util.Map;

/**
 *author:zhuquanwen
 *date:2015-9-25
 *describe:
 */
public interface UnEntityDao {
	public List<Map> getTableInfo(String tableName);
	public int editTableSql(Map map);
	public List<Map> selectTableInMySql(String tableName);
	public List<Map> selectDataToList(Map map);
}
