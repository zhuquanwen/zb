package com.iscas.zb.model.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: jaxb ���ݿ���varchar2��char���͵��ǲ���Ҫ��ʾӢ���е�����dcol��ǩxml��Ӧ��ģ��
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
