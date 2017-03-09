package com.iscas.zb.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ColInfo;

/**
*@date: 2017年2月23日
*@author: zhuquanwen
*@desc: 中英文转换工具
*/
@Component
@SuppressWarnings("rawtypes")
public class EnToChTools {
	@Autowired
	private UnEntityDao unEntityDao;

	public synchronized  void  getContentMap(boolean quartzFlag){
		if(StaticData.contentTransMap == null || quartzFlag){
			Map<String,String> map = new HashMap<String,String>();
			String sql = SqlData.commonSql;
			sql = sql.replace("@tableName", StaticData.translate_content_name);
			List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
			mapList.forEach(dbMap -> {
				map.put((String)dbMap.get("NAME"), (String)dbMap.get("NAME_CH"));
			});
			StaticData.contentTransMap = map;
		}

	}

	public synchronized void getTableMap(boolean quartzFlag){
		if(StaticData.tableTransMap == null || quartzFlag ){
			Map<String,String> map = new HashMap<String,String>();
			String sql = SqlData.commonSql;
			sql = sql.replace("@tableName", StaticData.translate_table_name);
			List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
			mapList.forEach(dbMap -> {
				map.put((String)dbMap.get("TABLE_NAME"), (String)dbMap.get("TABLE_NAME_CH"));
			});
			StaticData.tableTransMap = map;
		}

	}

	public synchronized void getColMap(boolean quartzFlag){
		if(StaticData.colTransMap == null || quartzFlag){
			Map<ColInfo,String> map = new HashMap<ColInfo,String>();
			String sql = SqlData.commonSql;
			sql = sql.replace("@tableName", StaticData.translate_col_name);
			List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
			mapList.forEach(dbMap -> {
				ColInfo ci = new ColInfo();
				ci.setColName((String)dbMap.get("COL_NAME"));
				ci.setTableName((String)dbMap.get("TABLE_NAME"));
				map.put(ci, (String)dbMap.get("COL_NAME_CH"));
			});
			StaticData.colTransMap = map;
		}

	}

	public static String enToCh_content(String name){
		String ch = name;
		ch = StaticData.contentTransMap.get(name);
		if(ch == null){
			ch = name;
		}
		return ch;

	}

	public static String enToCh_contentCanNull(String name){
		String ch = null;
		ch = StaticData.contentTransMap.get(name);
		return ch;

	}
	public static String enToCh_contentCanNull(Object name){
		String ch = null;
		ch = StaticData.contentTransMap.get(name);
		return ch;

	}
	public static String enToCh_content(Object name){
		String ch = name + "";
		ch = StaticData.contentTransMap.get(name);
		if(ch == null){
			ch = name + "";
		}
		return ch;

	}

	public static String enToCh_table(String name){
		String ch = name;
		ch = StaticData.tableTransMap.get(name);
		if(ch == null){
			ch = name;
		}
		return ch;

	}

	public static String enToCh_col(String tableName,String name){
		String ch = name;
		ColInfo ci = new ColInfo();
		ci.setColName(name);
		ci.setTableName(tableName);
		ch = StaticData.colTransMap.get(ci);
		if(ch == null){
			ch = name;
		}
		return ch;
	}
}
