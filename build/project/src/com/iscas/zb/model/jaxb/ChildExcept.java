package com.iscas.zb.model.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: ���ݿ��й�����ϵ,������Ҫ��ʾ���ӱ�
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
