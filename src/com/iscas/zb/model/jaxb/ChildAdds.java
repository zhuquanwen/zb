package com.iscas.zb.model.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: xml对应的模型
*/
@XmlRootElement(name="child_table_add")
public class ChildAdds {
	private List<ChildAdd> cas = new ArrayList<ChildAdd>();

	public List<ChildAdd> getCas() {
		return cas;
	}
	@XmlElement(name="childAdd")
	public void setCas(List<ChildAdd> cas) {
		this.cas = cas;
	}

}
