package com.iscas.zb.dao;

import java.util.List;
import java.util.Map;

/**
*@date: 2017Äê3ÔÂ2ÈÕ
*@author: zhuquanwen
*@desc: mybatis dao
*/
@SuppressWarnings("rawtypes")
public interface UnEntityDao {

	public List<Map> getTableInfo(String tableName);
	public int editTableSql(Map map);
	public List<Map> selectTableInMySql(String tableName);
	public List<Map> selectDataToList(Map map);
}
