package com.iscas.zb.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iscas.zb.dao.UnEntityDao;

/**
*@date: 2017年2月23日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class CommonTools {
	public static List<Map> getDBList(UnEntityDao dao,String sql){
		Map paramMap = new HashMap();
		paramMap.put("sql", sql);
		List<Map> mapList = dao.selectDataToList(paramMap);
		return mapList;
	}
}
