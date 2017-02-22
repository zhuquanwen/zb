package com.iscas.zb.model.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 数据库有关联关系,但不需要显示的子表
*/
@XmlRootElement(name="childExcept")
public class ChildExcept {
	private String parentTableName;
	private String childTableName;
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


}
