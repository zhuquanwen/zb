package com.iscas.zb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.model.Unit;
import com.iscas.zb.tools.CommonTools;
import com.iscas.zb.tools.EnToChTools;

import javafx.scene.control.TreeItem;

/**
*@date: 2017年3月13日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Service
@Transactional
@SuppressWarnings("rawtypes")
public class TreeService {
	@Autowired
	private UnEntityDao unEntityDao;
	public TreeItem<Unit> getUnitTree() {
		Unit root = new Unit();
		root.setNameCh("部队编成树");
		root.setHigherHq(null);
		root.setShortName(null);
		TreeItem<Unit> rootItem = new TreeItem<Unit> (root);
		rootItem.setExpanded(true);
		List<Map> maps = CommonTools.getDBList(unEntityDao, SqlData.topUnitSql);
		if(maps != null){
			maps.forEach(map -> {
				Unit unit = new Unit();
				String shortName = (String)map.get("SHORT_NAME");
				String higherHq = (String) map.get("HIGHER_HQ");
				String nameCh = EnToChTools.enToCh_content(shortName);
				String type = (String) map.get("TY");
				unit.setHigherHq(higherHq);
				unit.setNameCh(nameCh);
				unit.setShortName(shortName);
				unit.setType(type);
				TreeItem<Unit> treeUnit = new TreeItem<Unit> (unit);
				rootItem.getChildren().add(treeUnit);
				root.getUnits().add(unit);
			});
		}
		return rootItem;

	}
	public List<Unit> getLeaf(Unit unit) {
		String shortName = unit.getShortName();
		List<Unit> us = new ArrayList<Unit>();
		String sql = SqlData.leafUnitSql;
		sql = sql.replace("@shortName", shortName);
		List<Map> maps = CommonTools.getDBList(unEntityDao, sql);
		if(maps != null){
			maps.forEach( map -> {
				Unit subUnit = new Unit();
				String subShortName = (String)map.get("SHORT_NAME");
				String subHigherHq = (String) map.get("HIGHER_HQ");
				String subNameCh = EnToChTools.enToCh_content(subShortName);
				String subType = (String) map.get("TY");
				subUnit.setHigherHq(subHigherHq);
				subUnit.setNameCh(subNameCh);
				subUnit.setShortName(subShortName);
				subUnit.setType(subType);
				us.add(subUnit);

			});
		}
		unit.setUnits(us);
		return us;
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUnitProp(String tableName, Unit unit) {
		String sql = SqlData.getUnitByShortNameSql;
		sql = sql.replace("@tableName", tableName);
		sql = sql.replace("@prefix", unit.getType());
		sql = sql.replace("@shortName", unit.getShortName());
		List<Map> maps = CommonTools.getDBList(unEntityDao, sql);
		if(maps != null && maps.size() > 0){
			Map map = maps.get(0);
			return (Map<String,Object>)map;
		}
		return null;
	}

}
