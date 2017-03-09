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
@XmlRootElement(name="table")
public class JTable {
	private String name;
	private String nameEn;
	private List<JTables> tbs ;

	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name="name_en")
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getName() {
		return name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public List<JTables> getTbs() {
		return tbs;
	}
	@XmlElement(name="tables")
	public void setTbs(List<JTables> tbs) {
		this.tbs = tbs;
	}

}
