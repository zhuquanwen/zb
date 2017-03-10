package com.iscas.zb.model;

import java.util.List;

/**
*@date: 2017年2月24日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class ChildRelation {
	private String childTableName;
	private String tableName;
	private List<String> childColNames;
	private List<String> colNames;
	public String getChildTableName() {
		return childTableName;
	}
	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<String> getChildColNames() {
		return childColNames;
	}
	public void setChildColNames(List<String> childColNames) {
		this.childColNames = childColNames;
	}
	public List<String> getColNames() {
		return colNames;
	}
	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}
	@Override
	public String toString() {
		return "ChildRelation [childTableName=" + childTableName + ", tableName=" + tableName + ", childColNames="
				+ childColNames + ", colNames=" + colNames + "]";
	}

}
