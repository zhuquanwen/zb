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
		 * ���ÿҳ��Ŀ��pageSize�б�
		 */
	public ObservableList<String> getPageSizeList(){
		ObservableList<String> obList = null;
		obList = FXCollections.observableArrayList(StaticData.table_page_size);
		return obList;
	}

	/**
	 * �������
	 * */
	public LinkedHashMap<String, String> getColNamesByTableName(String tableName){
		LinkedHashMap<String, String> list = null;
		//��дһ�����Եģ�������......
		list = new LinkedHashMap<String, String>();
		list.put("firstCol","��һ��");
		list.put("secondCol","�ڶ���");
		list.put("thirdCol","������");
		list.put("forthCol","������");
		list.put("fifthCol","������");
		list.put("sixthCol","������");

		return list;
	}

	/**���data*/
	public ObservableList getTableData(String tableName , Integer page, Integer pageSize){
		ObservableList obList = null;
		//��дһ�����Եģ�������......
		obList = FXCollections.observableArrayList(TestData.mapList);
		return obList;
	}
}
