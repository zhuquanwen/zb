package com.iscas.zb.model.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 数据库有关联关系,但不需要显示的子表配置xml对应的模型
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
