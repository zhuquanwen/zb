package com.iscas.zb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.tools.CommonTools;
import com.iscas.zb.tools.EnToChTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
*@date: 2017年2月27日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Service
public class TableEditService {
	@Autowired
	private UnEntityDao unEntityDao;



	public ObservableList rowMapToColMap(Map rowMap,String tableName){
		ObservableList obList = FXCollections.observableArrayList();
		List<Map> colMap = CommonTools.getTableCols(tableName, unEntityDao);
		colMap.forEach(map -> {
			String colName = (String) map.get("COLUMN_NAME");
			Object value = rowMap.get(colName);
			Map mapx = new HashMap();
			mapx.put("key", EnToChTools.enToCh_col(tableName, colName));
			mapx.put("value", value);
			obList.add(mapx);
		});
		return obList;
	}
}
