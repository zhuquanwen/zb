package com.iscas.zb.model.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: ���ݿ��й�����ϵ,������Ҫ��ʾ���ӱ�����xml��Ӧ��ģ��
*/
@XmlRootElement(name="child_table_except")
public class ChildExcepts {
	private List<ChildExcept> ces = new ArrayList<ChildExcept>();

	public List<ChildExcept> getCes() {
		return ces;
	}
	@XmlElement(name="childExcept")
	public void setCes(List<ChildExcept> ces) {
		this.ces = ces;
	}


}
