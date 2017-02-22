package com.iscas.zb.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.test.TestData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableService {
	private volatile static TableService singleton;
	 private TableService (){}
	 public static TableService getSingleton() {
		 if (singleton == null) {
		     synchronized (TableService.class) {
		     if (singleton == null) {
		         singleton = new TableService();
		     }
		     }
		 }
		 return singleton;
	 }
	 /**
		 * 获得每页数目的pageSize列表
		 */
	public ObservableList<String> getPageSizeList(){
		ObservableList<String> obList = null;
		obList = FXCollections.observableArrayList(StaticData.table_page_size);
		return obList;
	}

	/**
	 * 获得列名
	 * */
	public LinkedHashMap<String, String> getColNamesByTableName(String tableName){
		LinkedHashMap<String, String> list = null;
		//先写一个测试的，待开发......
		list = new LinkedHashMap<String, String>();
		list.put("firstCol","第一列");
		list.put("secondCol","第二列");
		list.put("thirdCol","第三列");
		list.put("forthCol","第四列");
		list.put("fifthCol","第五列");
		list.put("sixthCol","第六列");

		return list;
	}

	/**获得data*/
	public ObservableList getTableData(String tableName , Integer page, Integer pageSize){
		ObservableList obList = null;
		//先写一个测试的，待开发......
		obList = FXCollections.observableArrayList(TestData.mapList);
		return obList;
	}
}
