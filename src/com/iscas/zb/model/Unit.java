package com.iscas.zb.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;

/**
*@date: 2017年3月13日
*@author: zhuquanwen
*@desc: 部队实体
*/
public class Unit {
	private String shortName;
	private String higherHq;
	private List<Unit> units = new ArrayList<Unit>();
	private String type;
	private String nameCh;
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getHigherHq() {
		return higherHq;
	}
	public void setHigherHq(String higherHq) {
		this.higherHq = higherHq;
	}
	public List<Unit> getUnits() {
		return units;
	}
	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNameCh() {
		return nameCh;
	}
	public void setNameCh(String nameCh) {
		this.nameCh = nameCh;
	}

}
