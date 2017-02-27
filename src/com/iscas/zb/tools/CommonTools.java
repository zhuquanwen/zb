package com.iscas.zb.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;

/**
*@date: 2017��2��23��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class CommonTools {
	public static List<Map> getDBList(UnEntityDao dao,String sql){
		Map paramMap = new HashMap();
		paramMap.put("sql", sql);
		List<Map> mapList = dao.selectDataToList(paramMap);
		return mapList;
	}

	public static List<Map> getTableCols(String tableName,UnEntityDao unEntityDao){
		String sql =SqlData.selectColSql;
		sql = sql.replace("@tableName", tableName);
		List<Map> maps = CommonTools.getDBList(unEntityDao, sql);
		return maps;

	}
}
