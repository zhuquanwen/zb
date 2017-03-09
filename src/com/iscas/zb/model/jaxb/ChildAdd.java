package com.iscas.zb.model.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: xml对应的模型
*/
@XmlRootElement(name="childAdd")
public class ChildAdd {
	private String parentTableName;
	private String childTableName;
	private String parentColName1;
	private String parentColName2;
	private String childColName1;
	private String childColName2;
	public String getParentTableName() {
		return parentTableName;
	}
	@XmlAttribute(name="parent_table_name")
	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}
	public String getChildTableName() {
		return childTableName;
	}
	@XmlAttribute(name="child_table_name")
	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}
	public String getParentColName1() {
		return parentColName1;
	}
	@XmlAttribute(name="parent_col_name1")
	public void setParentColName1(String parentColName1) {
		this.parentColName1 = parentColName1;
	}
	public String getParentColName2() {
		return parentColName2;
	}
	@XmlAttribute(name="parent_col_name2")
	public void setParentColName2(String parentColName2) {
		this.parentColName2 = parentColName2;
	}
	public String getChildColName1() {
		return childColName1;
	}
	@XmlAttribute(name="child_col_name1")
	public void setChildColName1(String childColName1) {
		this.childColName1 = childColName1;
	}
	public String getChildColName2() {
		return childColName2;
	}
	@XmlAttribute(name="child_col_name2")
	public void setChildColName2(String childColName2) {
		this.childColName2 = childColName2;
	}

}
