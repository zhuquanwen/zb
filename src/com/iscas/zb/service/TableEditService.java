package com.iscas.zb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.jaxb.Dcol;
import com.iscas.zb.tools.CommonTools;
import com.iscas.zb.tools.EnToChTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
*@date: 2017年2月27日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Service
public class TableEditService {
	@Autowired
	private UnEntityDao unEntityDao;

	public ObservableList rowMapToColMap(Map rowMap,String tableName,boolean disponseCh){
		ObservableList obList = FXCollections.observableArrayList();
		List<Map> colMap = CommonTools.getTableCols(tableName, unEntityDao);
		colMap.forEach(map -> {
			String colName = (String) map.get("COLUMN_NAME");
			Object value = rowMap.get(colName);
			Map mapx = new HashMap();
			mapx.put("key", EnToChTools.enToCh_col(tableName, colName));
			mapx.put("value", value);
			mapx.put("colName", colName);
			obList.add(mapx);

			//将不翻译的列配置转换为Map,方便使用
			Map<String,String> disColMap = new HashMap<String,String>();
			List<Dcol> dcols = StaticData.dis_col_trans.getDcols();
			if(dcols != null){
				dcols.forEach(dcol -> {
					if(tableName.equalsIgnoreCase(dcol.getTableName())){
						disColMap.put(dcol.getColName().toUpperCase(), "this is flag");
					}
				});
			}
			//如果显示中文列
			if(!disponseCh){
				//如果是字符串列
				if("VARCHAR2".equals(map.get("DATA_TYPE")) ||
						"CHAR".equals(map.get("DATA_TYPE"))){
					//如果此列未加入了不翻译的列表
					//新增翻译列
					if(disColMap.get(colName) == null){
						Map mapy = new HashMap();
						mapy.put("key", EnToChTools.enToCh_col(tableName, colName) + "[中文]");
						mapy.put("value", EnToChTools.enToCh_contentCanNull(value));
						mapy.put("colName", colName + "_en");
						obList.add(mapy);
					}
				}
			}

		});
		return obList;
	}



	@SuppressWarnings("unchecked")
	public Node getNode(String tableName,Map map,Map<String,Object> updateMap
			,Map<String,String> updateChMap) {
		Node node = null;
		String colName = (String)map.get("colName");
		//下拉列表的判断
		if(StaticData.childTableRelationViewMap != null){
			List<ChildRelation> crs = StaticData.childTableRelationViewMap
					.get(tableName);
			if(crs != null){

				for(ChildRelation cr:crs){
					List<String> cols =  cr.getChildColNames();
					String tableNamex = cr.getTableName();
					List<String> mainCols = cr.getColNames();
					String mainTableCol = "";
					if(cols != null){
						String sql = "select ";
						String colNamex = "";
						String colNamey = "";
						if(cols.size() == 1){
							colNamex = cols.get(0);
						}else{
							colNamex = cols.get(0);
							colNamey = cols.get(1);
						}
						boolean flag = false;
						if(colNamex.equals(colName)){
							sql += mainCols.get(0) + " from " +
									tableNamex ;
							flag = true;
							mainTableCol = mainCols.get(0);
						}
						if(colNamey.equals(colName)){
							sql += mainCols.get(1) + " from " +
									tableNamex ;
							flag = true;
							mainTableCol = mainCols.get(1);
						}
						if(flag){
							List<Map> comboMap = CommonTools.getDBList(unEntityDao, sql);
							ObservableList obList = FXCollections.observableArrayList();
							final String mtc = mainTableCol;
							comboMap.forEach(cmap -> {
								obList.add(cmap.get(mtc));
							});
							//ObservableList subObList = FXCollections.observableArrayList();
							ComboBox cb = new ComboBox();

							cb.setEditable(true);
							cb.setItems(obList);
							cb.setValue(map.get("value"));

							cb.setItems(obList);

							cb.getEditor().setOnKeyReleased( e-> {
								String val = cb.getEditor().getText();
								ObservableList subObList = null;
								if(val == null || "".equals(val)){
									subObList = obList;
									cb.getEditor().setText("");
								}else{
									subObList = FXCollections.observableArrayList();
									for(Object str : obList) {
										if(str.toString().contains(val)){
											subObList.add(str);
										}
									}
								}
								//先让其show一次所有的，再show过滤的，防止
								//弹出的面板太小
								cb.setItems(obList);
								cb.show();
								cb.setItems(subObList);
								cb.show();
								cb.getEditor().setText(val);
								cb.getEditor().end();
								cb.setContextMenu(null);

							});
							updateMap.put(colName, cb);
							node = cb;
							break;
						}

					}
				}
			}
		}
		if(node == null){
			String obj = map.get("value") + "";
			TextField tf = new TextField(obj);
			if(colName.endsWith("_en")){
				String preColName = colName.substring(0,colName.indexOf("_en"));
				updateChMap.put(preColName, tf.getText());
			}else{
				updateMap.put(colName, tf);
			}


//			tf.setOnAction( e -> {
//				if(colName.endsWith("_en")){
//					String preColName = colName.substring(0,colName.indexOf("_en"));
//					updateChMap.put(preColName, tf.getText());
//				}else{
//					updateMap.put(colName, tf.getText());
//				}
//
//			});
//			tf.setOnKeyPressed(e -> {
//				if(colName.endsWith("_en")){
//					String preColName = colName.substring(0,colName.indexOf("_en"));
//					updateChMap.put(preColName, tf.getText());
//				}else{
//					updateMap.put(colName, tf.getText());
//				}
//			});

			node = tf;
		}
		return node;
	}
	 public String getStringField(Object o){
		 return o.toString();
		}


	/**确定编辑*/
	public void commit(Map<String, Object> updateMap, Map<String, String> updateChMap,String tableName,String rowId) {
		//提交非中文列
		Set<String> keys =updateMap.keySet();
		String sql = " update " + tableName + " set ";
		if(keys.size() > 0){
			int i =0;
			for (String key : keys) {
				if(i != 0 ){
					sql += " , ";
				}
				Object value = updateMap.get(key);
				TextField tf = null;
				if(value instanceof TextField){
					tf = (TextField)value;
				}else{
					ComboBox cb = (ComboBox)value;
					tf = cb.getEditor();
				}

				if(tf.getText() == null || "".equals(tf.getText())){
					sql += key + " = null  ";
				}else{
					sql += key + " = '"+tf.getText()+"'";
				}
				i++;
			}
			sql += " where ROWID = '" + rowId + "'";
		}
		Map map = new HashMap();
		map.put("sql", sql);
		unEntityDao.editTableSql(map);

		//提交中文列
		Set<String> chKeys = updateChMap.keySet();
		if(chKeys.size() > 0){
			for (String key : chKeys) {
				String chVal = updateChMap.get(key);
				String sql1 = "select " + key +" from "+ tableName +
						" where ROWID = '" + rowId + "'";
				List<Map> maps = CommonTools.getDBList(unEntityDao, sql1);
				if(maps != null && maps.size() > 0){
					String val = (String)maps.get(0).get(key);
					String sql2 = " select name from " + StaticData.translate_content_name
							 + " where name = '" + val + "'";
					List<Map> maps2 = CommonTools.getDBList(unEntityDao, sql2);
					if(maps2 != null && maps2.size() > 0){
						if(chVal == null || "".equals(chVal)){
							String sql3 = " delete from " + StaticData.translate_content_name
									+ " where name = '" + val + "'";
							CommonTools.getDBList(unEntityDao, sql3);
						}else{
							String sql4 = "update " +StaticData.translate_content_name
									+ " set name_ch = '" + chVal + "'";
							CommonTools.getDBList(unEntityDao, sql4);
						}
					}else{
						if(chVal == null || "".equals(chVal)){
							//什么都不干
						}else{
							String sql5 = "insert into " +StaticData.translate_content_name
									+ " (name,name_ch) values ('" + val + "','" + chVal + "') ";
							CommonTools.getDBList(unEntityDao, sql5);
						}
					}
				}
			}
		}
	}
}
