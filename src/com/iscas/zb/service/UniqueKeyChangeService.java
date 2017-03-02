package com.iscas.zb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.tools.CommonTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
*@date: 2017年3月2日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Service
public class UniqueKeyChangeService {
	@Autowired
	private UnEntityDao unEntityDao;

	/**获得要修改的主键和唯一键对应的值*/
	public ObservableList getChangeInfos(Map<String, Object> rowMap, Map<String, Object> updateMap,String tableName) {
		ObservableList<Map> obList = FXCollections.observableArrayList();
		String sql = SqlData.getUniqueSql;
		sql = sql.replace("@tableName", tableName);
		List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
		if(mapList != null && mapList.size() > 0){
			for (Map map : mapList) {
				String colName = (String)map.get("COLUMN_NAME");
				Object val = rowMap.get(colName);
				TextField tf = new TextField(val == null ? "" : val.toString());
				updateMap.put(colName, tf);
				Map<String,Object> mapx = new HashMap<String,Object>();
				mapx.put("key", colName);
				mapx.put("value", tf);
				obList.add(mapx);
			}
		}
		return obList;
	}

	/**行复制*/
	public boolean copyRow(Map<String, Object> rowMap, Map<String, Object> updateMap,
			String tableName, boolean cascadeFlag) {
		final boolean[] flag = new boolean[1];
		flag[0] = true;
		Map<String,String> colMap = new HashMap<String,String>();
		List<Map> colMaps = CommonTools.getTableCols(tableName, unEntityDao);
		colMaps.forEach(cm -> {
			colMap.put((String)cm.get("COLUMN_NAME"), "this is flag");
		});
		Map<String, Object> rowMap1 = (Map<String, Object>) ((HashMap)rowMap).clone();

		Set<String> set = updateMap.keySet();
		//将修改的值写入rowMap
		for (String key : set) {
			TextField tf = (TextField) updateMap.get(key);
			rowMap1.put(key, tf.getText());
		}
		String insertSql = "insert into " + tableName + " ( ";
		Set<String> keys = rowMap1.keySet();
		int i = 0;
		for (String key : keys) {
			if(colMap.get(key) != null){
				if(i != 0 ){
					insertSql += " , ";
				}
				insertSql += key;
				i++;
			}

		}
		insertSql += " ) values (";
		int j = 0;
		for (String key : keys) {
			if(colMap.get(key) != null){
				if(j != 0 ){
					insertSql += " , ";
				}

				Object val = rowMap1.get(key);
				if(val == null || "".equals(val)){
					insertSql += " null ";
				}else{
					insertSql += "'" + val + "'";
				}

				j++;
			}

		}
		insertSql += " ) ";
		CommonTools.getDBList(unEntityDao, insertSql);
		if(cascadeFlag){
			//上面对主表进行了复制，下面复制子表
			if(StaticData.tableRelationMap != null){
				List<ChildRelation> crs = StaticData.tableRelationMap.get(tableName);
				if(crs != null && crs.size() > 0){
					crs.forEach(cr -> {
						String childTableName = cr.getChildTableName();
						List<String> childCols = cr.getChildColNames();
						List<String> cols = cr.getColNames();
						String colName1 = "";
						String colName2 = "";
						String childColName1 = "";
						String childColName2 = "";
						//查询的SQL
						String sql = "select * from " + childTableName + " t ";
						sql += " where 1=1 ";
						if(childCols.size() == 1){
							colName1 = cols.get(0);
							childColName1 = childCols.get(0);
						}else{
							colName1 = cols.get(0);
							childColName1 = childCols.get(0);
							colName2 = cols.get(1);
							childColName2 = childCols.get(1);
						}
						sql += " and " + childColName1 ;
						Object val1 = rowMap.get(colName1);
						if(val1 == null || "".equals(val1)){
							sql += " is null ";
						}else{
							sql += " = '" + val1 + "'";
						}
						if(!"".equals(childColName2)){
							sql += " and " +childColName2;
							Object val2 = rowMap.get(colName2);
							if(val2 == null || "".equals(val2)){
								sql += " is null ";
							}else{
								sql += " = '" + val2 + "'";
							}
						}

						try{
							List<Map> childMaps = CommonTools.getDBList(unEntityDao, sql);
							if(childMaps != null && childMaps.size() > 0){
								for (Map cMap : childMaps) {
									String childInsertSql = " insert into " + childTableName +" (";
									Set cSet = cMap.keySet();
									int k = 0;
									for (Object key : cSet) {
										if(k >0 ){
											childInsertSql += " , ";
										}
										childInsertSql += key;
										k++;
									}
									childInsertSql += " ) values ( ";
									int m = 0;
									for (Object key : cSet) {
										if(m >0 ){
											childInsertSql += " , ";
										}

										Object value = cMap.get(key);
										Object myVal = value;
										if(key.equals(childColName1)){
											TextField tf = (TextField)updateMap.get(colName1);
											myVal = tf.getText();
										}
										if(key.equals(childColName2)){
											TextField tf = (TextField)updateMap.get(colName1);
											myVal = tf.getText();
										}
										if(myVal == null || "".equals(myVal)){
											childInsertSql += " null ";
										}else{
											childInsertSql += " '" + myVal + "' ";
										}
										m++;
									}
									childInsertSql += " ) ";
									CommonTools.getDBList(unEntityDao, childInsertSql);
								}
							}
						}catch(Exception e){
							e.printStackTrace();
							//这里抓取一下异常，防止数据库如果有触发器，这个时候会产生主键冲突
							flag[0] = false;
						}

					});
				}
			}
		}
		return flag[0];
	}
}
