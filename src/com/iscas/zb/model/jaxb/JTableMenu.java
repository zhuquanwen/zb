package com.iscas.zb.model.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: xml��Ӧ��ģ��
*/
@XmlRootElement(name="table_menu")
public class JTableMenu {
	private List<JTables> tm ;
	@XmlElement(name="tables")
	public void setTm(List<JTables> tm) {
		this.tm = tm;
	}
	public List<JTables> getTm() {
		return tm;
	}

}
