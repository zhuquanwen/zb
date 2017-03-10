package com.iscas.zb.model.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017��2��27��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
@XmlRootElement(name="col")
public class UnEditCol {
	private String colName;
	private String tableName;
	public String getColName() {
		return colName;
	}
	@XmlAttribute(name="col_name")
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getTableName() {
		return tableName;
	}
	@XmlAttribute(name="table_name")
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
