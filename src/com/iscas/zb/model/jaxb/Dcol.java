package com.iscas.zb.model.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: jaxb 数据库中varchar2或char类型但是不需要显示英文列的配置dcol标签xml对应的模型
*/
@XmlRootElement(name="dcol")
public class Dcol {
	private String tableName;
	private String colName;
	public String getTableName() {
		return tableName;
	}
	@XmlAttribute(name="table_name")
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColName() {
		return colName;
	}
	@XmlAttribute(name="col_name")
	public void setColName(String colName) {
		this.colName = colName;
	}

}
