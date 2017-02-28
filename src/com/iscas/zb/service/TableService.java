package com.iscas.zb.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iscas.zb.controller.TableController;
import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.Dcol;
import com.iscas.zb.test.TestData;
import com.iscas.zb.tools.CommonTools;
import com.iscas.zb.tools.EnToChTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
@Transactional
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
			LinkedHashMap<String,Map<String,String>> colInfoMap , TableController tc , String condition){

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
}
