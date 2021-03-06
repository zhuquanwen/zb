package com.iscas.zb.model.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: xml对应的模型
*/
@XmlRootElement(name="tables")
public class JTables {
	private List<JTable> table ;
	private Integer index;
	@XmlElement(name="table")
	public void setTable(List<JTable> table) {
		this.table = table;
	}
	@XmlAttribute(name="index")
	public void setIndex(Integer index) {
		this.index = index;
	}
	public List<JTable> getTable() {
		return table;
	}
	public Integer getIndex() {
		return index;
	}

}
