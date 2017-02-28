package com.iscas.zb.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.jaxb.ChildAdd;
import com.iscas.zb.model.jaxb.ChildExcept;

/**
*@date: 2017年2月24日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Component
public class TableRelationTools {
	@Autowired(required=true)
	private UnEntityDao unEntityDao;

	public void getRelation(boolean quartzFlag){
		if(StaticData.tableRelationMap == null || quartzFlag){
			Map<String,List<ChildRelation>> map1 = new HashMap<String,List<ChildRelation>>();
			Map<String,List<ChildRelation>> map2 = new HashMap<String,List<ChildRelation>>();

			//获得主表对应的子表信息
			String sql = SqlData.tableRelationSql + SqlData.tableRelationSqlOrderBy1;
			List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
			String tempTableName = "";
			String tempChildTableName = "";
			String tempChildColName = "";
			String tempColName = "";
			List<ChildRelation> childRelations = null;
			ChildRelation cr = new ChildRelation();
			boolean flag = false;
			boolean canFlag=true;
			for (int i = 0; i < mapList.size(); i++) {
				Map map = mapList.get(i);

				String childTableName = (String)map.get("外键表名");
				String childColName = (String)map.get("外键列名");
				String tableName = (String)map.get("主键表名");
				String colName = (String)map.get("主键列名");

				if(tempTableName.equals(tableName)){
					//如果还有一样的tableName,判断子表
					if(tempChildTableName.equals(childTableName)){
						if(tempChildColName.equals(childColName)){
							if(canFlag){
								flag = true;
							}else{
								flag = false;
							}
							canFlag = true;
						}else{
							if(cr.getTableName() != null ){
								if(!flag){
									childRelations.add(cr);
								}else{
									canFlag =false;
								}

							}
							if(flag){
								cr.getChildColNames().add(childColName);
								cr.getColNames().add(tempColName);

							}else{
								cr = new ChildRelation();
								cr.setTableName(tableName);
								List<String> colNames = new ArrayList<String>();
								colNames.add(colName);
								cr.setColNames(colNames);
								cr.setChildTableName(childTableName);
								List<String> childColNames = new ArrayList<String>();
								childColNames.add(childColName);
								cr.setChildColNames(childColNames);
							}

						}
					}else{
						if(cr.getTableName() != null){
							childRelations.add(cr);
						}
						cr = new ChildRelation();
						cr.setTableName(tableName);
						List<String> colNames = new ArrayList<String>();
						colNames.add(colName);
						cr.setColNames(colNames);
						cr.setChildTableName(childTableName);
						List<String> childColNames = new ArrayList<String>();
						childColNames.add(childColName);
						cr.setChildColNames(childColNames);

					}


				}else{
					if(childRelations != null){
						childRelations.add(cr);
						map1.put(tempTableName, childRelations);
						map2.put(tempTableName, childRelations);
					}
					childRelations = new ArrayList<ChildRelation>();
					cr = new ChildRelation();
					cr.setTableName(tableName);
					List<String> colNames = new ArrayList<String>();
					colNames.add(colName);
					cr.setColNames(colNames);
					cr.setChildTableName(childTableName);
					List<String> childColNames = new ArrayList<String>();
					childColNames.add(childColName);
					cr.setChildColNames(childColNames);

					//childRelations.add(cr);
				}

				tempTableName = tableName;
				tempChildTableName = childTableName;
				tempChildColName = childColName;
				tempColName = colName;
			}
			if(childRelations != null){
				childRelations.add(cr);
				map1.put(tempTableName, childRelations);
				map2.put(tempTableName, childRelations);
			}


			StaticData.tableRelationMap = map1;
			//减去刨除的子表
			deleteTableRelation(map2);
			//加上额外加上的关系
			addTableRelation(map2);
			StaticData.tableRelationViewMap = map2;


		}

		if(StaticData.childTableRelationMap == null || quartzFlag){
			Map<String,List<ChildRelation>> map1 = new HashMap<String,List<ChildRelation>>();
			Map<String,List<ChildRelation>> map2 = new HashMap<String,List<ChildRelation>>();

			//获得主表对应的子表信息
			String sql = SqlData.tableRelationSql + SqlData.tableRelationSqlOrderBy2;
			List<Map> mapList = CommonTools.getDBList(unEntityDao, sql);
			String tempTableName = "";
			String tempChildTableName = "";
			String tempChildColName = "";
			String tempColName = "";
			List<ChildRelation> childRelations = null;
			ChildRelation cr = new ChildRelation();
			boolean flag = false;
			boolean canFlag=true;
			for (int i = 0; i < mapList.size(); i++) {
				Map map = mapList.get(i);

				String childTableName = (String)map.get("外键表名");
				String childColName = (String)map.get("外键列名");
				String tableName = (String)map.get("主键表名");
				String colName = (String)map.get("主键列名");

				if(tempChildTableName.equals(childTableName)){
					//如果还有一样的tableName,判断子表
					if(tempTableName.equals(tableName)){
						if(tempChildColName.equals(childColName)){
							if(canFlag){
								flag = true;
							}else{
								flag = false;
							}
							canFlag = true;
						}else{
							if(cr.getTableName() != null ){
								if(!flag){
									childRelations.add(cr);
								}else{
									canFlag =false;
								}

							}
							if(flag){
								cr.getChildColNames().add(childColName);
								cr.getColNames().add(tempColName);

							}else{
								cr = new ChildRelation();
								cr.setTableName(tableName);
								List<String> colNames = new ArrayList<String>();
								colNames.add(colName);
								cr.setColNames(colNames);
								cr.setChildTableName(childTableName);
								List<String> childColNames = new ArrayList<String>();
								childColNames.add(childColName);
								cr.setChildColNames(childColNames);
							}

						}
					}else{
						if(cr.getTableName() != null){
							childRelations.add(cr);
						}
						cr = new ChildRelation();
						cr.setTableName(tableName);
						List<String> colNames = new ArrayList<String>();
						colNames.add(colName);
						cr.setColNames(colNames);
						cr.setChildTableName(childTableName);
						List<String> childColNames = new ArrayList<String>();
						childColNames.add(childColName);
						cr.setChildColNames(childColNames);

					}


				}else{
					if(childRelations != null){
						childRelations.add(cr);
						map1.put(tempChildTableName, childRelations);
						map2.put(tempChildTableName, childRelations);
					}
					childRelations = new ArrayList<ChildRelation>();
					cr = new ChildRelation();
					cr.setTableName(tableName);
					List<String> colNames = new ArrayList<String>();
					colNames.add(colName);
					cr.setColNames(colNames);
					cr.setChildTableName(childTableName);
					List<String> childColNames = new ArrayList<String>();
					childColNames.add(childColName);
					cr.setChildColNames(childColNames);

					//childRelations.add(cr);
				}

				tempTableName = tableName;
				tempChildTableName = childTableName;
				tempChildColName = childColName;
				tempColName = colName;
			}
			if(childRelations != null){
				childRelations.add(cr);
				map1.put(tempChildTableName, childRelations);
				map2.put(tempChildTableName, childRelations);
			}


			StaticData.childTableRelationMap = map1;

			//加上额外的下拉列表关系
			addTableComboboxRelation(map2);
			StaticData.childTableRelationViewMap = map2;
		}
	}

	/**数据库没有对应关系，额外的下拉列表关系*/
	private void addTableComboboxRelation(Map<String, List<ChildRelation>> map2){
		if(StaticData.combobox_adds != null){
			List<ChildAdd> cas = StaticData.combobox_adds.getCas();
			if(cas != null && cas.size() > 0){
				cas.forEach(ca -> {
					ChildRelation cr = new ChildRelation();
					cr.setTableName(ca.getParentTableName());
					cr.setChildTableName(ca.getChildTableName());
					List<String> cols = new ArrayList<String>();
					cols.add(ca.getParentColName1());
					if(ca.getParentColName2() != null && !"".equals(ca.getParentColName2())){
						cols.add(ca.getParentColName2());
					}
					cr.setColNames(cols);
					List<String> childCols = new ArrayList<String>();
					childCols.add(ca.getChildColName1());
					if(ca.getChildColName2() != null && !"".equals(ca.getChildColName2())){
						childCols.add(ca.getChildColName2());
					}
					cr.setChildColNames(childCols);

					if(map2.get(ca.getChildTableName()) == null){
						List<ChildRelation> crs = new ArrayList<ChildRelation>();
						crs.add(cr);
						map2.put(ca.getChildTableName(), crs);
					}else{
						map2.get(ca.getChildTableName()).add(cr);
					}

				});
			}
		}
	}

	/**数据库没关系，额外加上的关系*/
	private void addTableRelation(Map<String, List<ChildRelation>> map2) {
		if(StaticData.child_adds != null){
			List<ChildAdd> cas = StaticData.child_adds.getCas();
			if(cas != null && cas.size() > 0){
				cas.forEach(ca -> {
					ChildRelation cr = new ChildRelation();
					cr.setTableName(ca.getParentTableName());
					cr.setChildTableName(ca.getChildTableName());
					List<String> cols = new ArrayList<String>();
					cols.add(ca.getParentColName1());
					if(ca.getParentColName2() != null && !"".equals(ca.getParentColName2())){
						cols.add(ca.getParentColName2());
					}
					cr.setColNames(cols);
					List<String> childCols = new ArrayList<String>();
					childCols.add(ca.getChildColName1());
					if(ca.getChildColName2() != null && !"".equals(ca.getChildColName2())){
						childCols.add(ca.getChildColName2());
					}
					cr.setChildColNames(childCols);

					if(map2.get(ca.getParentTableName()) == null){
						List<ChildRelation> crs = new ArrayList<ChildRelation>();
						crs.add(cr);
						map2.put(ca.getParentTableName(), crs);
					}else{
						map2.get(ca.getParentTableName()).add(cr);
					}

				});
			}
		}
	}
	/**刨除有数据库外键关系，但不需要显示的子表*/
	private static void deleteTableRelation(Map<String,List<ChildRelation>> map2){
		if(StaticData.child_excepts != null){
			List<ChildExcept> ces = StaticData.child_excepts.getCes();
			if(ces != null && ces.size() > 0){
				ces.forEach(ce -> {
					String tableName = ce.getParentTableName();
					String childTableName = ce.getChildTableName();
					if(map2 != null){
						if(map2.get(tableName.toUpperCase()) != null){
							List<ChildRelation> crs = map2.get(tableName.toUpperCase());
							for (int i = crs.size() -1 ; i >= 0 ; i--) {
								ChildRelation cr = crs.get(i);
								if(childTableName.equals(cr.getChildTableName())){
									crs.remove(i);
								}
							}
						}
					}
				});
			}
		}
	}
}
