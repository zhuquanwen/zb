package com.iscas.zb.service;

import java.awt.Checkbox;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iscas.zb.controller.TableController;
import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.jaxb.Dcol;
import com.iscas.zb.tools.CommonTools;
import com.iscas.zb.tools.EnToChTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import oracle.sql.ROWID;
@Transactional(propagation=Propagation.REQUIRED,timeout=60)
@Service
public class TableService {
	@Autowired(required=true)
	private UnEntityDao unEntityDao;


	 /**
		 * ���ÿҳ��Ŀ��pageSize�б�
		 */
	public ObservableList<String> getPageSizeList(){
		ObservableList<String> obList = null;
		obList = FXCollections.observableArrayList(StaticData.table_page_size);
		return obList;
	}

	//�������Ϣ
	public LinkedHashMap<String,Map<String,String>> getColInfosByTableName(String tableName,TableController tc){
		LinkedHashMap<String,Map<String,String>> map = new LinkedHashMap<String,Map<String,String>>();
		LinkedHashMap<String,String> enchMap = new LinkedHashMap<String,String>();
		tableName = tableName.toUpperCase();
		final String tn = tableName;
		String sql = SqlData.selectColSql;
		sql = sql.replace("@tableName", tableName);
		Map paramMap = new HashMap();
		paramMap.put("sql", sql);
		List<Map> mapList = unEntityDao.selectDataToList(paramMap);
		//���������������ת��ΪMap,����ʹ��
		Map<String,String> disColMap = new HashMap<String,String>();
		List<Dcol> dcols = StaticData.dis_col_trans.getDcols();
		if(dcols != null){
			dcols.forEach(dcol -> {
				if(tn.equalsIgnoreCase(dcol.getTableName())){
					disColMap.put(dcol.getColName().toUpperCase(), "this is flag");
				}
			});
		}
		//��ӷ�����
		mapList.forEach(infos -> {
			String colName = (String)infos.get("COLUMN_NAME");
			map.put(colName, infos);
			enchMap.put(colName, EnToChTools.enToCh_col(tn, colName));
			//������ַ�����
			if("VARCHAR2".equals(infos.get("DATA_TYPE")) ||
					"CHAR".equals(infos.get("DATA_TYPE"))){
				//�������δ�����˲�������б�
				//����������
				if(disColMap.get(colName) == null){
					enchMap.put(colName + "_EN", EnToChTools.enToCh_col(tn, colName) + "[����]");
					Map mapx = new HashMap();
					mapx.put("COLUMN_NAME", colName + "_EN");
					map.put(colName + "_EN", mapx);
				}
			}
		});
		//����Щ��Ϣע�뵽Controller���ϣ���ʹ��
		tc.setColEnChMap(enchMap);
		tc.setDisColMap(disColMap);
		tc.setColInfoMap(map);
		return map;
	}



	/**���data*/
	public ObservableList getTableData(String tableName , Integer page, Integer pageSize ,
			LinkedHashMap<String,Map<String,String>> colInfoMap ,
			TableController tc , String condition){

		ObservableList obList = null;
		tableName = tableName.toUpperCase();
		String sql = SqlData.commonPageSql;
		sql = sql.replace("@tableName", tableName);
		sql = sql.replace("@condition", condition);
		sql = sql.replace("@down", String.valueOf((page-1)*pageSize + 1));
		sql = sql.replace("@up", String.valueOf(page*pageSize));
		Map<String,String> disColMap = tc.getDisColMap();
		List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
		mapList = mapList.stream()
			.map(map -> {
				Map data = (Map) ((HashMap)map).clone();
				Object rowIdObj = data.get("RID");
				ROWID rId = (ROWID)rowIdObj;
				String rowId = rId.stringValue();
				CheckBox cb = new CheckBox();
				cb.setUserData(rowId);
				data.put("cb", cb);

				Set set = map.keySet();
				for (Object obj : set) {
					String colName = (String)obj;
					Map<String,String> colInfo = colInfoMap.get(colName);
					//������ַ�����
					if(colInfo != null && ("VARCHAR2".equals(colInfo.get("DATA_TYPE")) ||
							"CHAR".equals(colInfo.get("DATA_TYPE"))) ){
						//�������δ�����˲�������б�
						if(disColMap.get(colName) == null){
							//����������
							data.put(colName + "_EN", EnToChTools.enToCh_contentCanNull((String)map.get(obj)));
						}
					}
				}
				return data;
			})
			.collect(Collectors.toList());
		obList = FXCollections.observableArrayList(mapList);
		return obList;
	}

	/**���ҳ��*/
	public Integer getTotal(String tableName,  String condition) {
		Integer total = 0;
		tableName = tableName.toUpperCase();
		String sql = SqlData.selectTotal;
		sql = sql.replace("@tableName", tableName);
		sql = sql.replace("@condition", condition);
		List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
		if(mapList != null && mapList.size() > 0){
			total = ((BigDecimal)mapList.get(0).get("TOTAL")).intValue();
		}
		return total;
	}

	/**���һ���յ�һ������*/
	public Map<String, Object> getEmptyMap(String tableName,TableController tc) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,String> disColMap = tc.getDisColMap();
		List<Map> colInfos = CommonTools.getTableCols(tableName, unEntityDao);
		colInfos.forEach(map -> {
			String colName = (String)map.get("COLUMN_NAME");
			String dataType = (String)map.get("DATA_TYPE");
			returnMap.put(colName, null);
			if(dataType != null && ("VARCHAR2".equals(dataType) ||
					"CHAR".equals(dataType)) ){
				//�������δ�����˲�������б�
				if(disColMap.get(colName) == null){
					//����������
					returnMap.put(colName + "_EN", null);
				}
			}

		});
		return returnMap;
	}

	/**��ͨɾ��*/
	public void normalDelete(Map<String, Object> map,String tableName) {
		Object obj = map.get("RID");
		ROWID rd = (ROWID)obj;
		String rowid = rd.stringValue();
		makeChildColNull(map,tableName);
		String sql = " delete from " + tableName +" where rowid = '"+ rowid +"'";
		Map mapx = new HashMap();
		mapx.put("sql", sql);
		unEntityDao.editTableSql(mapx);
	}
	private void makeChildColNull(Map<String, Object> map,String tableName){

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
					//��ѯ��SQL
					String sql = "select t.*,t.rowid as rid from " + childTableName + " t ";
					//���ӱ��������Ϊnull��SQL
					String nullSql = " update " + childTableName;
					if(cols != null && cols.size() == 1){
						colName1 = cols.get(0);
						childColName1 = childCols.get(0);
						String condition =
						 " where " + childColName1 + " = '" + map.get(colName1) + "'";
						sql += condition;
						nullSql += " set " + childColName1 + " = null " + condition;
					}else{
						colName1 = cols.get(0);
						childColName1 = childCols.get(0);
						colName2 = cols.get(1);
						childColName2 = childCols.get(1);
						String condition =
						 " where " + childColName1 + " = '" + map.get(colName1) + "'"
								+ " and " + childColName2 + " = '" + map.get(colName2) + "'";

						sql += condition;
						nullSql += " set " + childColName1 + " = null , " + childColName2 + " = null " + condition;
					}
//					if(StaticData.tableRelationMap.get(childTableName) != null){
//						List<Map> listMap = CommonTools.getDBList(unEntityDao, sql);
//						if(listMap != null && listMap.size() > 0){
//							listMap.forEach(sMap -> {
//								//���õݹ�
//								this.makeChildColNull(sMap, childTableName);
//
//							});
//						}
//					}

					//���ӱ�Ķ�Ӧ��¼���������Ϊnull
					CommonTools.getDBList(unEntityDao, nullSql);


				});
			}
		}
	}

	public void cascadeDelete(Map<String, Object> map, String tableName) {
		Object obj = map.get("RID");
		ROWID rd = (ROWID)obj;
		String rowid = rd.stringValue();

		String sql = " delete from " + tableName +" where rowid = '"+ rowid +"'";
		Map mapx = new HashMap();
		mapx.put("sql", sql);
		try{
			unEntityDao.editTableSql(mapx);
		}catch(Exception e){
			e.printStackTrace();
			makeChildCascade(map,tableName);
			//������쳣��֤��������ôɾ�����������ӱ���ɾ��һ��
			unEntityDao.editTableSql(mapx);
		}

	}

	private void makeChildCascade(Map<String, Object> map, String tableName) {
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
					//��ѯ��SQL
					String sql = "select t.*,t.rowid as rid from " + childTableName + " t ";
					//���ӱ��������Ϊnull��SQL
					String deleteSql = " delete from  " + childTableName;
					if(cols != null && cols.size() == 1){
						colName1 = cols.get(0);
						childColName1 = childCols.get(0);
						String condition =
						 " where " + childColName1 + " = '" + map.get(colName1) + "'";
						sql += condition;
						deleteSql +=   condition;
					}else{
						colName1 = cols.get(0);
						childColName1 = childCols.get(0);
						colName2 = cols.get(1);
						childColName2 = childCols.get(1);
						String condition =
						 " where " + childColName1 + " = '" + map.get(colName1) + "'"
								+ " and " + childColName2 + " = '" + map.get(colName2) + "'";

						sql += condition;
						deleteSql +=  condition;
					}
					//���ӱ��Ӧ��¼ɾ��
					try{
						CommonTools.getDBList(unEntityDao, deleteSql);
					}catch(Exception e){
						e.printStackTrace();
						if(StaticData.tableRelationMap.get(childTableName) != null){
							List<Map> listMap = CommonTools.getDBList(unEntityDao, sql);
							if(listMap != null && listMap.size() > 0){
								listMap.forEach(sMap -> {
									//���õݹ�
									this.makeChildCascade(sMap, childTableName);

								});
							}
						}
						//������������ӱ���ɾ��һ��
						CommonTools.getDBList(unEntityDao, deleteSql);
					}


				});
			}
		}

	}


}
