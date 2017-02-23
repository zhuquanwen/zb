package com.iscas.zb.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iscas.zb.dao.UnEntityDao;

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
}
