package com.iscas.zb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.tools.CommonTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
*@date: 2017年3月3日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Transactional
@Service
public class SelectService {
	@Autowired
	private UnEntityDao unEntityDao;
	/**查询一个表的主键*/
	public List<String> getPrimaryKeys(String tableName) {
		List<String> ps = new ArrayList<String>();
		String sql = SqlData.getPrimarySql;
		sql = sql.replace("@tableName", tableName);
		List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
		if(mapList != null && mapList.size() > 0){
			mapList.forEach(ml -> {
				ps.add((String)ml.get("COLUMN_NAME"));
			});
		}
		return ps;
	}

	/**获取一个表的所有列，放入List*/
	public ObservableList<String> getCols(String tableName) {
		ObservableList<String> cols = FXCollections.observableArrayList();
		List<Map> colMaps =  CommonTools.getTableCols(tableName, unEntityDao);
		colMaps.forEach(colMap -> {
			cols.add((String)colMap.get("COLUMN_NAME"));
		});
		return cols;
	}

}
